package com.seanchenxi.gwt.serenity.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.seanchenxi.gwt.logging.api.Log;
import com.seanchenxi.gwt.serenity.client.SerenityFactory;
import com.seanchenxi.gwt.serenity.client.SerenityUtil;
import com.seanchenxi.gwt.serenity.client.event.ArticleCloseEvent;
import com.seanchenxi.gwt.serenity.client.place.ArticlePlace;
import com.seanchenxi.gwt.serenity.client.place.SerenityPlace;
import com.seanchenxi.gwt.serenity.client.view.ContentListView;
import com.seanchenxi.gwt.wordpress.json.WPJsonAPI;
import com.seanchenxi.gwt.wordpress.json.api.model.PagingPostList;
import com.seanchenxi.gwt.wordpress.json.api.model.Post;
import com.seanchenxi.gwt.wordpress.json.api.service.JCoreService;

public abstract class ContentListActivity extends AbstractActivity implements ContentListView.Presenter {
	
	protected SerenityFactory clientFactory;
	private int total, size;
	private final int page;
	private final String slug;
	private HandlerRegistration closeHandlerRegister;

	protected ContentListActivity(String slug, int page, SerenityFactory clientFactory) {
		this.slug = slug;
		this.page = page;
		this.clientFactory = clientFactory;
		this.size = SerenityUtil.getPageSize();
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		initArticleCloseHandler(eventBus);
		ContentListView view = clientFactory.getContentList();
		view.bindPresenter(this);
		fetchContents(slug, getOffset(), size, view);
		panel.setWidget(view);
	}
	
	@Override
	public void onCancel() {
		removeArticleCloseHandler();
		super.onCancel();
	}
	
	@Override
	public void onStop() {
		removeArticleCloseHandler();
		super.onStop();
	}

	@Override
	public final void goForPost(String postSlug) {
		if(postSlug == null){
			clientFactory.getPlaceController().goTo(nextPlace(slug, page));
		}else{
			clientFactory.getPlaceController().goTo(new ArticlePlace(postSlug));
		}
	}
	
	@Override
	public final void nextPage() {
		if(total <= (page + 1) * size) return;
		clientFactory.getPlaceController().goTo(nextPlace(slug, page + 1));
	}
	
	@Override
	public final void prevPage(){
		if(page <= 0) return;
		clientFactory.getPlaceController().goTo(nextPlace(slug, page - 1));
	}
	
	public void hilightContent(String contentSlug) {
		clientFactory.getContentList().highlightContentItem(contentSlug);
	}
	
	public final int getPage() {
		return page;
	}
	
	public final String getSlug() {
		return slug;
	}
	
	public final int getOffset(){
		return page * size;
	}
	
	protected abstract void fetchContents(String slug, int offset, int size, ContentListView view);
	protected abstract SerenityPlace nextPlace(String slug, int page);
	
	protected JCoreService getWPCoreService(){
		return WPJsonAPI.get().getCoreService();
	}
	
	private void initArticleCloseHandler(EventBus eventBus){
		removeArticleCloseHandler();
		closeHandlerRegister = eventBus.addHandler(ArticleCloseEvent.getType(), new ArticleCloseEvent.Handler() {
			@Override
			public void onArticleClose(ArticleCloseEvent event) {
				clientFactory.getPlaceController().goTo(nextPlace(slug, page));	
			}
		});
	}
	
	private void removeArticleCloseHandler(){
		if(closeHandlerRegister != null){
			closeHandlerRegister.removeHandler();
			closeHandlerRegister = null;
		}
	}
	
	protected class GetContentsResult implements AsyncCallback<PagingPostList> {

		private int offset;
		private String title;
		private ContentListView view;
		
		protected GetContentsResult(String title, int offset, ContentListView view){
			this.title = title;
			this.offset = offset;
			this.view = view;
		}

		@Override
		public void onFailure(Throwable caught) {
			if(caught.getMessage().contains("Not found")){
				Window.alert("Not Found");
			}else{
				Log.severe("GetContentsResult ERROR", caught);
			}
		}

		@Override
		public void onSuccess(PagingPostList result) {
			if(result != null){
				total = result.getTotal();
				view.clearContentList();
				view.setPagingInfo(title, offset, result.getCount(), result.getTotal());
				for(Post post : result.getList()){
					view.addContent(post.getSlug(), post.getTitle(), post.getExcerpt(), post.getCreatedDate());
				}
			}
		}
	}
}

package com.seanchenxi.gwt.serenity.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.seanchenxi.gwt.logging.api.Log;
import com.seanchenxi.gwt.serenity.client.SerenityFactory;
import com.seanchenxi.gwt.serenity.client.SerenityUtil;
import com.seanchenxi.gwt.serenity.client.event.ArticleCloseEvent;
import com.seanchenxi.gwt.serenity.client.place.ArticlePlace;
import com.seanchenxi.gwt.serenity.client.place.SerenityPlaceUtil;
import com.seanchenxi.gwt.serenity.client.view.ArticleView;
import com.seanchenxi.gwt.wordpress.json.WPJsonAPI;
import com.seanchenxi.gwt.wordpress.json.api.model.Category;
import com.seanchenxi.gwt.wordpress.json.api.model.Comment;
import com.seanchenxi.gwt.wordpress.json.api.model.Post;
import com.seanchenxi.gwt.wordpress.json.api.model.Tag;
import com.seanchenxi.gwt.wordpress.json.api.service.JCoreService;

public class ArticleActivity extends AbstractActivity implements ArticleView.Presenter {

	private ArticlePlace place;
	private SerenityFactory clientFactory;
	
	public ArticleActivity(ArticlePlace place, SerenityFactory clientFactory){
		this.place = place;
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ArticleView view = clientFactory.getArticleView();
		view.bindPresenter(this);
		fetchContentAndShow(panel, view);
	}
	
	@Override
	public void closeView() {
		clientFactory.getEventBus().fireEvent(new ArticleCloseEvent(place.getSlug()));
	}

	@Override
	public void submitComment(int articleId, String name, String email,
			String content) {
		WPJsonAPI.get().getRespondService().submitComment(articleId, name, email, content, new AsyncCallback<Comment>() {
			
			@Override
			public void onSuccess(Comment result) {
				ArticleView view = clientFactory.getArticleView();
				view.addComment(result.getId(), result.getName(), result.getDate(), result.getContent());
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Log.severe("error", caught);
				
			}
		});
	}
	
	private void fetchContentAndShow(final AcceptsOneWidget panel, final ArticleView view) {
		getWPCoreService().getPost(place.getSlug(), new AsyncCallback<Post>() {
			@Override
			public void onSuccess(Post result) {
				view.clearContent();
				view.setArticleId(result.getId());
				view.setTitle(result.getTitle());
				view.setDateString(SerenityUtil.toDateString(result.getCreatedDate()));
				view.setContent(result.getContent());
				view.setCommentsCount(result.getCommentCount());
				for(Category cat : result.getCategories()){
					String anchorHref = SerenityPlaceUtil.getCategoryAnchor(cat.getSlug(), 0);
					view.addCategory(anchorHref, cat.getTitle());
				}
				for(Tag tag : result.getTags()){
					String anchorHref = SerenityPlaceUtil.getTagAnchor(tag.getSlug(), 0);
					view.addTag(anchorHref, tag.getTitle());
				}
				for(Comment cmt : result.getComments()){
					view.addComment(cmt.getId(), cmt.getName(), cmt.getDate(), cmt.getContent());
				}
				panel.setWidget(view);
			}

			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}

	private JCoreService getWPCoreService() {
		return WPJsonAPI.get().getCoreService();
	}
	
}

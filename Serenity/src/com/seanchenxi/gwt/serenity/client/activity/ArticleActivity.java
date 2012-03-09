package com.seanchenxi.gwt.serenity.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Image;
import com.seanchenxi.gwt.logging.api.Log;
import com.seanchenxi.gwt.serenity.client.SerenityFactory;
import com.seanchenxi.gwt.serenity.client.SerenityUtil;
import com.seanchenxi.gwt.serenity.client.event.ArticleCloseEvent;
import com.seanchenxi.gwt.serenity.client.place.ArticlePlace;
import com.seanchenxi.gwt.serenity.client.place.SerenityPlaceUtil;
import com.seanchenxi.gwt.serenity.client.view.ArticleView;
import com.seanchenxi.gwt.serenity.client.view.DiscussionListView;
import com.seanchenxi.gwt.serenity.client.view.RespondView;
import com.seanchenxi.gwt.wordpress.json.WPJsonAPI;
import com.seanchenxi.gwt.wordpress.json.api.model.Category;
import com.seanchenxi.gwt.wordpress.json.api.model.Comment;
import com.seanchenxi.gwt.wordpress.json.api.model.CommentStatus;
import com.seanchenxi.gwt.wordpress.json.api.model.Post;
import com.seanchenxi.gwt.wordpress.json.api.model.PostCommentStatus;
import com.seanchenxi.gwt.wordpress.json.api.model.Tag;

public class ArticleActivity extends AbstractActivity implements ArticleView.Presenter, RespondView.Presenter {

	private ArticlePlace place;
	private SerenityFactory clientFactory;
	
	private ArticleView view;
	private DiscussionListView discussionListView;
	
	public ArticleActivity(ArticlePlace place, SerenityFactory clientFactory){
		this.place = place;
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		fetchContentAndShow(panel);
	}
	
	private void fetchContentAndShow(final AcceptsOneWidget panel) {
	  WPJsonAPI.get().getCoreService().getPost(place.getSlug(), new AsyncCallback<Post>() {
			@Override
			public void onSuccess(Post result) {
			  showView(result, panel);
			}

			@Override
			public void onFailure(Throwable caught) {
			  showError();
			}
		});
	}

	private void showError() {
    // TODO Auto-generated method stub
  }
	
	private void showView(Post post, AcceptsOneWidget panel){
	  view = clientFactory.getArticleView();
	  view.bindPresenter(this);
	  view.clearContent();
    view.setArticleId(post.getId());
    view.setTitle(post.getTitle());
    view.setDateString(SerenityUtil.toDateString(post.getCreatedDate()));
    view.setContent(post.getContent());
    //Show categories
    for(Category cat : post.getCategories()){
      String anchorHref = SerenityPlaceUtil.getCategoryAnchor(cat.getSlug(), 0);
      view.addCategory(anchorHref, cat.getTitle());
    }
    //Show tags
    for(Tag tag : post.getTags()){
      String anchorHref = SerenityPlaceUtil.getTagAnchor(tag.getSlug(), 0);
      view.addTag(anchorHref, tag.getTitle());
    }
    // Show comments
    boolean commentOpened = PostCommentStatus.OPEN.equals(post.getCommentStatus());
    int discussionCount = post.getComments().size();
    if(discussionCount > 1 || commentOpened){
      discussionListView = clientFactory.getDiscussionListView();
      discussionListView.intView(discussionCount, commentOpened);
      for(Comment cmt : post.getComments()){
        Image.prefetch(cmt.getGravatarURL()); // prefetch gravatar  
        discussionListView.addDiscussion(cmt.getId(), cmt.getGravatarURL(), cmt.getName(), cmt.getURL(), cmt.getContent(), cmt.getDate(), cmt.getParentId());
      }
      // check and show respond view
      if(commentOpened){
        RespondView respondView = clientFactory.getRespondView();
        respondView.reset();
        respondView.setArticleId(post.getId());
        respondView.bindPresenter(ArticleActivity.this);
        discussionListView.setRespondView(respondView);
      }
      view.setCommentsCount(discussionCount);
      view.setDiscussionListView(discussionListView);
    }
    
    panel.setWidget(view);
	}
	 
  @Override
  public void reply(int articleId, String name, String email, String url, String content, int discussionId) {
    WPJsonAPI.get().getRespondService().submitComment(articleId, name, email, url, content, discussionId, new AsyncCallback<Comment>() {
      
      @Override
      public void onSuccess(Comment result) {
        String content = result.getContent();
        if(result.getStatus().equals(CommentStatus.PENDING)){
          content = "<p class=\"pending-discussion\">Your comment is awaiting moderation.</p>" + content;
        }
        discussionListView.addDiscussion(result.getId(), result.getGravatarURL(), result.getName(), result.getURL(), content, result.getDate(), result.getParentId());
      }
      
      @Override
      public void onFailure(Throwable caught) {
        Log.severe("[ArticleActivity] SubmitComment onFailure", caught);
      }
    });
  }

  @Override
  public void cancel(int articleId, int discussionId) {
    discussionListView.updateReponseViewPosition(discussionId); 
  }
	
  @Override
  public void closeView() {
    clientFactory.getEventBus().fireEvent(new ArticleCloseEvent(place.getSlug()));
  }
  
}

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
import com.seanchenxi.gwt.serenity.client.view.DiscussionView;
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
	
	private void fetchContentAndShow(final AcceptsOneWidget panel, final ArticleView view) {
	  WPJsonAPI.get().getCoreService().getPost(place.getSlug(), new AsyncCallback<Post>() {
			@Override
			public void onSuccess(Post result) {
			  showView(result, panel, view);
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
	
	private void showView(Post post, AcceptsOneWidget panel, ArticleView view){
	  view.clearContent();
    view.setArticleId(post.getId());
    view.setTitle(post.getTitle());
    view.setDateString(SerenityUtil.toDateString(post.getCreatedDate()));
    view.setContent(post.getContent());
    view.setCommentsCount(post.getCommentCount());
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
    // Check and show comment list
    if(PostCommentStatus.OPEN.equals(post.getCommentStatus())
        || (PostCommentStatus.CLOSED.equals(post.getCommentStatus()) 
            && post.getCommentCount() > 0)){
      DiscussionView discussionView = clientFactory.getDiscussionView();
      discussionView.clearAll();
      discussionView.setDiscussionsCount(post.getCommentCount());
      for(Comment cmt : post.getComments()){
        discussionView.addDiscussion(cmt.getId(), cmt.getName(), cmt.getDate(), cmt.getContent());
      }
      view.setDiscussionView(discussionView);
    }
 // Check and show comment list
    if(PostCommentStatus.OPEN.equals(post.getCommentStatus())){
      RespondView respondView = clientFactory.getRespondView();
      respondView.reset();
      respondView.setArticleId(post.getId());
      respondView.bindPresenter(ArticleActivity.this);
      view.setRespondView(respondView);
    }
    panel.setWidget(view);
	}
	
  @Override
  public void closeView() {
    clientFactory.getEventBus().fireEvent(new ArticleCloseEvent(place.getSlug()));
  }
	 
  @Override
  public void submitResponse(int articleId, String name, String email, String content) {
    WPJsonAPI.get().getRespondService().submitComment(articleId, name, email, content, new AsyncCallback<Comment>() {
      
      @Override
      public void onSuccess(Comment result) {
        DiscussionView view = clientFactory.getDiscussionView();
        String content = result.getContent();
        if(result.getStatus().equals(CommentStatus.PENDING)){
          content = "<p class=\"pending-msg\">Your comment is awaiting moderation.</p>" + content;
        }
        view.addDiscussion(result.getId(), result.getName(), result.getDate(), content);
      }
      
      @Override
      public void onFailure(Throwable caught) {
        Log.severe("error", caught);
        
      }
    });
  }
	
}

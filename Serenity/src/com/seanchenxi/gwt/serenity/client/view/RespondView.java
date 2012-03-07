package com.seanchenxi.gwt.serenity.client.view;

import com.google.gwt.user.client.ui.IsWidget;

public interface RespondView extends IsWidget {

  void setArticleId(int articleId);
  void setDiscussionId(int discussionId);
	void reset();
	
	void bindPresenter(Presenter presenter);
	
	public interface Presenter {
    void reply(int articleId, String name, String email, String url, String content, int discussionId);
    void cancel(int articleId, int discussionId);
  }

}

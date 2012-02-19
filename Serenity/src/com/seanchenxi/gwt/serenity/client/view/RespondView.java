package com.seanchenxi.gwt.serenity.client.view;

import com.google.gwt.user.client.ui.IsWidget;

public interface RespondView extends IsWidget {

  void setArticleId(int articleId);
	void reset();
	
	void bindPresenter(Presenter presenter);
	
	public interface Presenter {
    void submitResponse(int articleId, String name, String email, String content);
  }

}

package com.seanchenxi.gwt.serenity.client.view;

import com.google.gwt.user.client.ui.IsWidget;

public interface ArticleView extends IsWidget {
	
	void setArticleId(int id);
	void setTitle(String title);
	void setDateString(String date);
	void setContent(String content);
	void setCommentsCount(int count);
	
  void setDiscussionView(DiscussionListView discussionView);
  void setRespondView(RespondView respondView);

	void addCategory(String slug, String name);
	void addTag(String slug, String name);

	void clearContent();
	
	void bindPresenter(Presenter presenter);
	
	public interface Presenter {
		void closeView();
	}
	
}

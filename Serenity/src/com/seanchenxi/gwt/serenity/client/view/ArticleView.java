package com.seanchenxi.gwt.serenity.client.view;

import java.util.Date;

import com.google.gwt.user.client.ui.IsWidget;

public interface ArticleView extends IsWidget {
	
	void setArticleId(int id);
	void setTitle(String title);
	void setDateString(String date);
	void setContent(String content);
	
	void setCommentsCount(int count);
	void addComment(int id, String name, Date date, String content);
	void addCategory(String slug, String name);
	void addTag(String slug, String name);

	void clearContent();
	
	void bindPresenter(Presenter presenter);
	
	public interface Presenter {
		void closeView();
		void submitComment(int articleId, String name, String email, String content);
	}
	
}

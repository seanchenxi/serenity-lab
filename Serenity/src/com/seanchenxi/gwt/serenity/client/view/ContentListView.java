package com.seanchenxi.gwt.serenity.client.view;

import java.util.Date;

import com.google.gwt.user.client.ui.IsWidget;

public interface ContentListView extends IsWidget {

  void bindPresenter(Presenter presenter);
  void clearContentList();
  void setPagingInfo(String title, int offset, int count, int total);
  void addContent(String slug, String title, String excerpt, Date date);
  void highlightContentItem(String contentSlug);
  
  public interface Presenter {
    void goForPost(String slug);
	void nextPage();
	void prevPage();
  }
  
}

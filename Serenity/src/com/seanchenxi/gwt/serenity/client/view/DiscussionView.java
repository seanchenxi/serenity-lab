package com.seanchenxi.gwt.serenity.client.view;

import java.util.Date;

import com.google.gwt.user.client.ui.IsWidget;

public interface DiscussionView extends IsWidget {

	void clearAll();
	void addDiscussion(int id, String name, Date date, String content);
  void setDiscussionsCount(int count);
	
}

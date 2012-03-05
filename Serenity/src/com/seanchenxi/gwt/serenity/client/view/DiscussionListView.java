package com.seanchenxi.gwt.serenity.client.view;

import java.util.Date;

import com.google.gwt.user.client.ui.IsWidget;

public interface DiscussionListView extends IsWidget {

	void clearAll();
	void addDiscussion(int id, String gravatar, String name, String url, String content, Date date, int parentId);
  void setDiscussionsCount(int count);
  void setRespondView(RespondView respondView);
	
}

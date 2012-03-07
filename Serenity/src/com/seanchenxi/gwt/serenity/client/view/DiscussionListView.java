package com.seanchenxi.gwt.serenity.client.view;

import java.util.Date;

import com.google.gwt.user.client.ui.IsWidget;

public interface DiscussionListView extends IsWidget {

  void intView(int discussionCount);
  void addDiscussion(int id, String gravatar, String name, String url, String content, Date date, int parentId);
  void setRespondView(RespondView respondView);
  void updateReponseViewPosition(int discussionId);
  
}

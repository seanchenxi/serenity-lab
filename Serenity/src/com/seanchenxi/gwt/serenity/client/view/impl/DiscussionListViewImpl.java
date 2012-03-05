package com.seanchenxi.gwt.serenity.client.view.impl;

import java.util.Date;
import java.util.HashMap;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.seanchenxi.gwt.serenity.client.event.ReplyDiscussionEvent;
import com.seanchenxi.gwt.serenity.client.view.DiscussionListView;
import com.seanchenxi.gwt.serenity.client.view.RespondView;

public class DiscussionListViewImpl extends Composite implements DiscussionListView, ReplyDiscussionEvent.Handler {

  protected static final String SUB_PREFIX = "child-";

  private HTML title;
  private FlowPanel main;
  private RespondView respond;
  
  private HashMap<Integer, DiscussionView> discussions;
  private int total;
  
  public DiscussionListViewImpl() {
    discussions = new HashMap<Integer, DiscussionView>();
    initWidget(main = new FlowPanel());
    main.add(title = new HTML());
    title.setStyleName("discussions-title");
    getElement().setId("discussions");
  }

  @Override
  public void setDiscussionsCount(int count) {
    title.setText((total = count) + " Comments:");
  }

  @Override
  public void setRespondView(RespondView respondView) {
    respond = respondView;
    if(respond != null && discussions.size() >= total){
      main.add(respond);
    }
  }
  
  @Override
  public void addDiscussion(int id, String gravatar, String name, String url, String content, Date date, int parentId) {
    DiscussionView discussion = new DiscussionView(id);
    discussion.setAthorInfo(gravatar, name, url, date);
    discussion.setMessage(content);
    discussion.addReplyDiscussionHandler(this);
    
    DiscussionView parent = null;
    if (parentId > 0 && (parent = discussions.get(parentId)) != null) {
      discussion.setLevel(parent.getLevel() + 1);
      main.insert(discussion, main.getWidgetIndex(parent) + 1);
    } else {
      main.add(discussion);
    }
    discussions.put(id, discussion);
    
    Scheduler.get().scheduleIncremental(new IndexUpdateCommand());
  }

  @Override
  public void clearAll() {
    discussions.clear();
    main.clear();
    main.add(title);
  }
  
  private class IndexUpdateCommand implements RepeatingCommand {
    private int index = 0;  
    @Override
    public boolean execute() {
      if (discussions.size() < total) 
        return false;
      Widget w = main.getWidget(index);
      if(w instanceof DiscussionView){
        ((DiscussionView) w).setIndex(index);
      }
      if((++index) == main.getWidgetCount() && respond != null){
        main.add(respond);
      }
      return index < main.getWidgetCount();
    }
  }

  @Override
  public void onReply(ReplyDiscussionEvent event) {
    if(respond == null) return;
    DiscussionView discussion = discussions.get(event.getDiscussionId());
    if(discussion != null){
      Widget.asWidgetOrNull(respond).addStyleName(discussion.getStyleName());
      respond.setDiscussionId(discussion.getId());
      main.insert(respond, main.getWidgetIndex(discussion) + 1);
    }
  }

  @Override
  public void onCancel(ReplyDiscussionEvent event) {
    respond.setDiscussionId(0);
    main.add(respond);
  }

}

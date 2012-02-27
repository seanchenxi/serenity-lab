package com.seanchenxi.gwt.serenity.client.view.impl;

import java.util.Date;
import java.util.HashMap;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.seanchenxi.gwt.serenity.client.view.DiscussionListView;

public class DiscussionListViewImpl extends Composite implements DiscussionListView {

  protected static final String SUB_PREFIX = "child-";

  private FlowPanel main;
  private HTML title;
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
  public void addDiscussion(int id, String gravatar, String name, String url, String content, Date date, int parentId) {
    DiscussionView discussion = new DiscussionView(id);
    discussion.setAthorInfo(gravatar, name, url, date);
    discussion.setMessage(content);
    DiscussionView parent = null;
    if (parentId > 0 && (parent = discussions.get(parentId)) != null) {
      discussion.setLevel(parent.getLevel() + 1);
      main.insert(discussion, parent.getIndex() + 1);
    } else {
      main.add(discussion);
    }
    discussion.setIndex(main.getWidgetIndex(discussion));
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
      index++;
      return index < main.getWidgetCount();
    }
  }

}

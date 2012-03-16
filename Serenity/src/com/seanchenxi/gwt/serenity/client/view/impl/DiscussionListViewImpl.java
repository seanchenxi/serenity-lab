/*******************************************************************************
 * Copyright 2012 Xi CHEN
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.seanchenxi.gwt.serenity.client.view.impl;

import java.util.Date;
import java.util.HashMap;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.seanchenxi.gwt.logging.api.Log;
import com.seanchenxi.gwt.serenity.client.event.ReplyDiscussionEvent;
import com.seanchenxi.gwt.serenity.client.view.DiscussionListView;
import com.seanchenxi.gwt.serenity.client.view.RespondView;
import com.seanchenxi.gwt.serenity.share.StringPool;

public class DiscussionListViewImpl extends Composite implements DiscussionListView, ReplyDiscussionEvent.Handler {

  protected static final String SUB_PREFIX = "child-";

  private HTML title;
  private FlowPanel main;
  
  private RespondView respond;
  
  private HashMap<Integer, DiscussionViewImpl> discussions;
  private int total;
  private boolean showReply = true;
  
  public DiscussionListViewImpl() {
    discussions = new HashMap<Integer, DiscussionViewImpl>();
    initWidget(main = new FlowPanel());
    main.add(title = new HTML());
    title.setStyleName("discussions-title");
    getElement().setId("discussions");
  }

  @Override
  public void intView(int discussionCount, boolean showReply) {
    clearAll();
    this.title.setText((total = discussionCount) + " Comments:");
    this.showReply = showReply;
    Log.finest("[DiscussionListViewImpl] showReply="+showReply);
  }

  @Override
  public void setRespondView(RespondView respondView) {
    if(showReply){
      respond = respondView;
      if(respond != null && discussions.size() >= total){
        main.add(respond);
      }
    }
  }
  
  @Override
  public void addDiscussion(int id, String gravatar, String name, String url, String content, Date date, int parentId) {
    DiscussionViewImpl discussion = new DiscussionViewImpl(id, showReply);
    discussion.setAthorInfo(gravatar, name, url, date);
    discussion.setMessage(content);
    discussion.addReplyDiscussionHandler(this);
    
    DiscussionViewImpl parent = null;
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
  public void updateReponseViewPosition(int discussionId) {
    if(respond == null) return;
    DiscussionViewImpl discussion = discussions.get(discussionId);
    if(discussion != null){
      respond.setDiscussionId(discussion.getId());
      Widget.asWidgetOrNull(respond).addStyleName("child-" + discussion.getLevel());
      main.insert(respond, main.getWidgetIndex(discussion) + 1);
    }else{
      respond.setDiscussionId(0);
      Widget.asWidgetOrNull(respond).setStyleName(StringPool.BLANK);
      main.add(respond);
    }
  }
  
  @Override
  public void onReplyDiscussion(ReplyDiscussionEvent event) {
    updateReponseViewPosition(event.getDiscussionId());
  }

  private void clearAll() {
    discussions.clear();
    main.clear();
    main.add(title);
    respond = null;
  }
  
  private class IndexUpdateCommand implements RepeatingCommand {
    private int index = 0;  
    private int count = 0;
    @Override
    public boolean execute() {
      if (discussions.size() < total) 
        return false; //means add discussion will continue
      Widget w = main.getWidget(index);
      if(w instanceof DiscussionViewImpl){
        ((DiscussionViewImpl) w).setIndex(index);
        count++; // count number of discussion
      }
      index++;
      // if number of discussion is equals to discussion list's size,
      // and respond view is not null, show respond view at last
      if(count == discussions.size() && respond != null){
        main.add(respond);
      }
      // continue only if the number of discussion is less than discussion list's size
      return count < discussions.size();
    }
  }


  
}

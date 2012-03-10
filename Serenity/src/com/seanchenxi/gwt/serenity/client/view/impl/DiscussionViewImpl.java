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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.seanchenxi.gwt.serenity.client.SerenityUtil;
import com.seanchenxi.gwt.serenity.client.event.ReplyDiscussionEvent;
import com.seanchenxi.gwt.serenity.client.event.ReplyDiscussionEvent.HasReplyDiscussionHandlers;
import com.seanchenxi.gwt.serenity.client.resource.SerenityResources;

public class DiscussionViewImpl extends Composite implements HasReplyDiscussionHandlers {
  
  interface Template extends SafeHtmlTemplates {

    @SafeHtmlTemplates.Template(
        "<img class=\"avatar\" alt=\"\" src=\"{0}\" width=\"{1}\" height=\"{1}\" />" +
        "<h4 class=\"" + PREFIX + "-author\">{2}</h4>" +
        "<span class=\"" + PREFIX + "-date\">{3}</span>")
    SafeHtml AuthorMeta(SafeUri gravatar, int size, String name, String dateString);
    
    @SafeHtmlTemplates.Template(
        "<img class=\"avatar\" alt=\"\" src=\"{0}\" width=\"{1}\" height=\"{1}\" />" +
        "<h4 class=\"" + PREFIX + "-author\"><a target=\"_balnk\" href=\"{4}\" title=\"{2}'s website\">{2}</a></h4>" +
        "<span class=\"" + PREFIX + "-date\">{3}</span>")
    SafeHtml AuthorMetaWithUrl(SafeUri gravatar, int size, String name, String dateString, SafeUri url);
  }
  
  interface DiscussionViewImplUiBinder extends UiBinder<Widget, DiscussionViewImpl>{}
  
  protected static final String PREFIX = "discussion";
  protected static final String SUB_PREFIX = "child-";
  protected static final int AVATAR_SIZE = 32;
  protected static final Template TEMPLATE = GWT.create(Template.class);
  protected static final DiscussionViewImplUiBinder uiBinder = GWT.create(DiscussionViewImplUiBinder.class);
  
  @UiField HTML author;
  @UiField HTMLPanel contentWrap;
  @UiField HTML content;
  @UiField HTML counter;
  @UiField HTML reply;
  
  private int index;
  private int id;
  private int level;
  
  public DiscussionViewImpl(int id, boolean isReplyEnabled){
    initWidget(uiBinder.createAndBindUi(this));
    setStyleName(PREFIX);
    getElement().setId(PREFIX + "-" + id);
    this.id = id;
    this.index = -1;
    this.level = 0;
    this.author.setStyleName(PREFIX + "-meta");
    this.content.setStyleName("");
    this.counter.setStyleName(PREFIX + "-counter");
    if(!isReplyEnabled && reply != null){
      contentWrap.remove(reply);
    }
  }
  
  public int getId() {
    return id;
  }
  
  public int getIndex() {
    return index;
  }
  
  public int getLevel() {
    return level;
  }
  
  public void setAthorInfo(String gravatar, String name, String url, Date date){
    SafeUri avatar = getGravatarUri(gravatar);
    String dateString = SerenityUtil.toDateTimeString(date);
    if(url != null && !url.isEmpty()){
      SafeUri site = UriUtils.fromString(url);
      author.setHTML(TEMPLATE.AuthorMetaWithUrl(avatar, AVATAR_SIZE, name, dateString, site));
    }else{
      author.setHTML(TEMPLATE.AuthorMeta(avatar, AVATAR_SIZE, name, dateString));
    }
  }
  
  public void setMessage(String message){
    content.setHTML(SafeHtmlUtils.fromTrustedString(message));
  }
  
  public void setIndex(int index){
    contentWrap.setStyleName(PREFIX + "-content "+ (index % 2 == 0 ? "odd" : "even"));
    counter.setHTML((this.index = index)+"");
  }
  
  public void setLevel(int level){
    this.level = level;
    addStyleName(SUB_PREFIX + level);
  }
  
  @UiHandler("reply")
  public void onClickReply(ClickEvent event){
    ReplyDiscussionEvent.fire(this.id, this);
  }
  
  @Override
  public HandlerRegistration addReplyDiscussionHandler(ReplyDiscussionEvent.Handler handler) {
    return addHandler(handler, ReplyDiscussionEvent.getType());
  }
  
  private SafeUri getGravatarUri(String gravatar){
    if(gravatar != null && !gravatar.isEmpty())
      return UriUtils.fromString(gravatar+"?s=" + AVATAR_SIZE + "&d=mm");
    else
      return SerenityResources.IMG.avatar32().getSafeUri();
  }

}

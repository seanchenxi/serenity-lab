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
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
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
import com.seanchenxi.gwt.serenity.client.resource.message.MessageResources;

public class DiscussionViewImpl extends Composite implements HasReplyDiscussionHandlers {

  public interface Resources extends ClientBundle {

    public interface Style extends CssResource {
      final static String DEFAULT_CSS = "com/seanchenxi/gwt/serenity/client/resource/view/DiscussionView.css";
      String discussion();
      String discussionContent();
      String discussionMeta();
      String discussionCounter();
      String discussionAuthor();
      String discussionDate();
      String pendingDiscussion();
      String discussionReply();
      String odd();
      String even();
      String avatar();
    }

    @ClientBundle.Source(Style.DEFAULT_CSS)
    Style style();

  }

  interface Template extends SafeHtmlTemplates {

    @SafeHtmlTemplates.Template(
        "<img class=\"{6}\" alt=\"\" src=\"{0}\" width=\"{1}\" height=\"{1}\" />" +
        "<h4 class=\"{4}\">{2}</h4>" +
        "<span class=\"{5}\">{3}</span>")
    SafeHtml AuthorMeta(SafeUri gravatar, int size, String name, String dateString, String authorCSS, String dateCSS, String avatarCSS);
    
    @SafeHtmlTemplates.Template(
        "<img class=\"{7}\" alt=\"\" src=\"{0}\" width=\"{1}\" height=\"{1}\" />" +
        "<h4 class=\"{5}\"><a target=\"_balnk\" href=\"{4}\" title=\"{2}'s website\">{2}</a></h4>" +
        "<span class=\"{6}\">{3}</span>")
    SafeHtml AuthorMetaWithUrl(SafeUri gravatar, int size, String name, String dateString, SafeUri url, String authorCSS, String dateCSS, String avatarCSS);
  }
  
  interface DiscussionViewImplUiBinder extends UiBinder<Widget, DiscussionViewImpl>{}

  protected static final int AVATAR_SIZE = 32;
  protected static final Template TEMPLATE = GWT.create(Template.class);
  protected static final DiscussionViewImplUiBinder uiBinder = GWT.create(DiscussionViewImplUiBinder.class);

  private static Resources resources;

  @UiField HTML author;
  @UiField HTMLPanel contentWrap;
  @UiField HTML content;
  @UiField HTML counter;
  @UiField HTML reply;
  
  private int index;
  private int id;
  private int level;

  public static Resources getResources(){
    if(resources == null){
      resources = GWT.create(Resources.class);
      resources.style().ensureInjected();
    }
    return resources;
  }

  private static Resources.Style getStyle(){
    return getResources().style();
  }
  
  public DiscussionViewImpl(int id, boolean isReplyEnabled){
    initWidget(uiBinder.createAndBindUi(this));
    getElement().setId(getStyle().discussion() + "-" + id);
    this.id = id;
    this.index = -1;
    this.level = 0;
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
  
  public void setAuthorInfo(String gravatar, String name, String url, Date date){
    SafeUri avatar = getGravatarUri(gravatar);
    String dateString = SerenityUtil.toDateTimeString(date);
    String authorCSS = getStyle().discussionAuthor();
    String dateCSS = getStyle().discussionDate();
    String avatarCSS = getStyle().avatar();
    if(url != null && !url.isEmpty()){
      SafeUri site = UriUtils.fromString(url);
      author.setHTML(TEMPLATE.AuthorMetaWithUrl(avatar, AVATAR_SIZE, name, dateString, site, authorCSS, dateCSS, avatarCSS));
    }else{
      author.setHTML(TEMPLATE.AuthorMeta(avatar, AVATAR_SIZE, name, dateString, authorCSS, dateCSS, avatarCSS));
    }
  }
  
  public void setMessage(boolean isPending, String message){
    SafeHtml shtml;
    if(isPending){
      SafeHtmlBuilder shb = new SafeHtmlBuilder();
      shb.appendHtmlConstant("<p class=\"")
              .appendHtmlConstant(getStyle().pendingDiscussion())
              .appendHtmlConstant("\">")
              .appendHtmlConstant(SerenityResources.MSG.msg_yourCommentIsAwaitingMod())
              .appendHtmlConstant("</p>")
              .appendHtmlConstant(message);
      shtml = shb.toSafeHtml();
    }else{
      shtml = SafeHtmlUtils.fromTrustedString(message);
    }
    content.setHTML(shtml);
  }
  
  public void setIndex(int index){
    contentWrap.setStyleName(getStyle().discussionContent());
    contentWrap.addStyleName(index % 2 == 0 ? getStyle().odd() : getStyle().even());
    counter.setHTML(String.valueOf(this.index = index));
  }
  
  public void setLevel(int level){
    this.level = level;
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
      return UriUtils.fromString(gravatar + "?s=" + AVATAR_SIZE + "&d=mm");
    else
      return SerenityResources.IMG.avatar32().getSafeUri();
  }

}

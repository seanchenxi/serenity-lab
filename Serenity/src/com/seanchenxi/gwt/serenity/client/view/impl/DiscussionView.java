package com.seanchenxi.gwt.serenity.client.view.impl;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.seanchenxi.gwt.serenity.client.SerenityUtil;
import com.seanchenxi.gwt.serenity.client.resource.SerenityResources;

public class DiscussionView extends Composite {
  
  interface Template extends SafeHtmlTemplates {

    @SafeHtmlTemplates.Template(
        "<div class=\"" + PREFIX + "-meta\">" +
          "<img class=\"avatar\" alt=\"\" src=\"{0}\" width=\"{1}\" height=\"{1}\" />" +
          "<h4 class=\"" + PREFIX + "-author\">{2}</h4>" +
          "<span class=\"" + PREFIX + "-date\">{3}</span>" +
        "</div>")
    SafeHtml AuthorMeta(SafeUri gravatar, int size, String name, String dateString);
    
    @SafeHtmlTemplates.Template(
        "<div class=\"" + PREFIX + "-meta\">" +
          "<img class=\"avatar\" alt=\"\" src=\"{0}\" width=\"{1}\" height=\"{1}\" />" +
          "<h4 class=\"" + PREFIX + "-author\"><a target=\"_balnk\" href=\"{4}\" title=\"{2}'s website\">{2}</a></h4>" +
          "<span class=\"" + PREFIX + "-date\">{3}</span>" +
        "</div>")
    SafeHtml AuthorMetaWithUrl(SafeUri gravatar, int size, String name, String dateString, SafeUri url);
    
    @SafeHtmlTemplates.Template(
        "<div class=\"" + PREFIX + "-content {0}\">" +
            "{1}<span class=\"" + PREFIX + "-counter\">{2}</span>" +
        "</div>")
    SafeHtml Content(String style, SafeHtml content, int index);
  }
  
  protected static final String PREFIX = "discussion";
  protected static final String SUB_PREFIX = "child-";
  protected static final int AVATAR_SIZE = 32;
  private static final Template TEMPLATE = GWT.create(Template.class);
  
  private int index;
  private HTML container;

  private SafeHtml author;
  private SafeHtml content;
 
  private int id;
  private int level;
  
  public DiscussionView(int id){
    this.id = id;
    this.index = -1;
    this.level = 0;
    initWidget(container = new HTML());
    setStyleName(PREFIX);
    getElement().setId(PREFIX + "-" + id);
  }
  
  public int getId() {
    return id;
  }
  
  public void setAthorInfo(String gravatar, String name, String url, Date date){
    if(url != null && !url.isEmpty()){
      author = TEMPLATE.AuthorMetaWithUrl(getGravatarUri(gravatar), AVATAR_SIZE, name, SerenityUtil.toDateTimeString(date), UriUtils.fromString(url));
    }else{
      author = TEMPLATE.AuthorMeta(getGravatarUri(gravatar), AVATAR_SIZE, name, SerenityUtil.toDateTimeString(date));
    }
    updateView();
  }
  
  public void setMessage(String message){
    content = SafeHtmlUtils.fromTrustedString(message);
    updateView();
  }
  
  public void setIndex(int index){
    this.index = index;
    updateView();
  }
  
  public void setLevel(int level){
    this.level = level;
    addStyleName(SUB_PREFIX + level);
  }
  
  public int getIndex() {
    return index;
  }
  
  public int getLevel() {
    return level;
  }
  
  private void updateView() {
    if(author != null && content != null && index != -1){
      SafeHtmlBuilder shb = new SafeHtmlBuilder();
      shb.append(author);
      shb.append(TEMPLATE.Content(index % 2 == 0 ? "odd" : "even", content, index));
      container.setHTML(shb.toSafeHtml());
    }
  }
  
  private SafeUri getGravatarUri(String gravatar){
    if(gravatar != null && !gravatar.isEmpty())
      return UriUtils.fromString(gravatar+"?s=" + AVATAR_SIZE + "&d=mm");
    else
      return SerenityResources.IMG.avatar32().getSafeUri();
  }
  
}

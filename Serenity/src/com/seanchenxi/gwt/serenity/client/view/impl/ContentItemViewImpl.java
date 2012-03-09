package com.seanchenxi.gwt.serenity.client.view.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.seanchenxi.gwt.serenity.share.StringPool;

public class ContentItemViewImpl extends Composite implements HasClickHandlers {

  interface ContentItemViewImplUiBinder extends UiBinder<Widget, ContentItemViewImpl>{}
  
  private static final ContentItemViewImplUiBinder uiBinder = GWT.create(ContentItemViewImplUiBinder.class);
  private static final String TITLE_PREFIX = "<span class=\"unSelectable\">&Xi;&nbsp;</span>";
  private static final String SUMMARY_POSTFIX = "...";
  private static final String HILIGHT_STYLE = "hilight";

  @UiField HeadingElement title;
  @UiField HTML content;
  @UiField HTML meta;
  
  private String id;
  
  public ContentItemViewImpl(){
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  public void setId(String id){
    this.id = id;
  }
  
  public String getId() {
    return id;
  }
  
  public void setTitle(String postTitle){
    title.setInnerHTML(TITLE_PREFIX + postTitle);
  }
  
  public void setSummary(String summary){
    content.setHTML(shorten(summary, 200));
  }
  
  public void setMeta(String metaInfo){
    meta.setHTML(StringPool.AT + "&nbsp;" + metaInfo);
  }

  public boolean isHighlighted() {
    return getStyleName().indexOf(HILIGHT_STYLE) != -1;
  }
  
  public void setHighlight(boolean hilight){
    if(hilight){
      addStyleName(HILIGHT_STYLE);
    }else{
      removeStyleName(HILIGHT_STYLE);
    }
  }
  
  @Override
  public HandlerRegistration addClickHandler(ClickHandler handler) {
    return addDomHandler(handler, ClickEvent.getType());
  }

  private String shorten(String str, int length){
    if(str.length() > length){
      return str.substring(0,length) + SUMMARY_POSTFIX;
    }
    return str + SUMMARY_POSTFIX;
  }
  
}

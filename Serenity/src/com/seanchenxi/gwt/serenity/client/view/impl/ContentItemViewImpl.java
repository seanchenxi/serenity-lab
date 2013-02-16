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

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class ContentItemViewImpl extends Composite implements HasClickHandlers {

    public interface Resources extends ClientBundle {

        public interface Style extends CssResource {
            final static String DEFAULT_CSS = "com/seanchenxi/gwt/serenity/client/resource/view/ContentItemView.css";
            String contentItem();
            String contentItemTitle();
            String contentItemBody();
            String contentItemMeta();
            String highlight();
        }

        @ClientBundle.Source(Style.DEFAULT_CSS)
        Style style();
    }

  interface ContentItemViewImplUiBinder extends UiBinder<Widget, ContentItemViewImpl>{}
  
  private static final ContentItemViewImplUiBinder uiBinder = GWT.create(ContentItemViewImplUiBinder.class);
  private static final String TITLE_PREFIX = "<span class=\"unSelectable\">&Xi;&nbsp;</span>";
  private static final String META_PREFIX = "<span class=\"unSelectable\">-&nbsp;-&nbsp;&nbsp;</span>";
  private static final String SUMMARY_POSTFIX = "...";

  @UiField HeadingElement title;
  @UiField HTML content;
  @UiField HTML meta;
  @UiField Resources resource;
  
  private String id;
  
  public ContentItemViewImpl(){
    initWidget(uiBinder.createAndBindUi(this));
    resource.style().ensureInjected();
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
    meta.setHTML(META_PREFIX + metaInfo);
  }

  public boolean isHighlighted() {
    return getStyleName().indexOf(resource.style().highlight()) != -1;
  }
  
  public void setHighlight(boolean highlight){
    if(highlight){
      addStyleName(resource.style().highlight());
    }else{
      removeStyleName(resource.style().highlight());
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

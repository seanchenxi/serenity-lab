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
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.seanchenxi.gwt.serenity.client.view.ArticleView;
import com.seanchenxi.gwt.serenity.client.view.DiscussionListView;
import com.seanchenxi.gwt.serenity.share.StringPool;
import com.seanchenxi.gwt.ui.widget.Heading;

public class ArticleViewImpl extends Composite implements ArticleView {

  public interface Resources extends ClientBundle {

    public interface Style extends CssResource {     
      final static String DEFAULT_CSS = "com/seanchenxi/gwt/serenity/client/resource/view/ArticleView.css";      
      String article();
      String articleHeader();
      String articleTitle();
      String articleMeta();
      String articleDate();
      String articleComments();
      String articleCategories();
      String articleCategoryAnchor();
      String articleContent();
      String articleTags();
      String articleTagAnchor();
      String articleTagIcon();
      String articleBody();
      String articleClose();
    }

    @ClientBundle.Source(Style.DEFAULT_CSS)
    Style style();

    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None, height = 16, width = 16)
    @ClientBundle.Source("com/seanchenxi/gwt/serenity/client/resource/view/tag.png")
    ImageResource tagImg();
  }
  
	interface ArticleViewImplUiBinder extends UiBinder<Widget, ArticleViewImpl>{}
	
	private static ArticleViewImplUiBinder uiBinder = GWT.create(ArticleViewImplUiBinder.class);
  private static HTML tagLbl = new HTML("Tags:");
	
  @UiField Resources resource;
	@UiField Heading titleField;
	@UiField Label dateField;
	@UiField FlowPanel categoryList;
	@UiField Label commentCountField;
	
	@UiField ScrollPanel scroller;
	@UiField HTMLPanel mainField;
	@UiField HTML contentField;
	@UiField FlowPanel tagList;
	
	private DiscussionListView discussion;

	private Presenter presenter;
	private int articleId;
	
	public ArticleViewImpl(){
		initWidget(uiBinder.createAndBindUi(this));
    resource.style().ensureInjected();
    scroller.getElement().getStyle().clearPosition();
		Event.addNativePreviewHandler(new NativePreviewHandler() {
      @Override
      public void onPreviewNativeEvent(NativePreviewEvent event) {
      if(presenter != null && event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ESCAPE && isAttached()){
        presenter.closeView();
        event.getNativeEvent().preventDefault();
      }
      }
    });
	}
	
	@Override
	public void clearContent() {
		titleField.setText(StringPool.BLANK);
		dateField.setText(StringPool.BLANK);
		categoryList.clear();
		commentCountField.setText(StringPool.BLANK);
		contentField.setHTML(StringPool.BLANK);
		tagList.clear();
    tagLbl.setStyleName(resource.style().articleTagIcon());
		tagList.add(tagLbl);
		if(discussion != null){
      mainField.remove(discussion);
      discussion = null;
    }
	}
	
	@Override
	public void setArticleId(int id) {
		this.articleId = id;
		getElement().setId("article-" + articleId);
	}
	
	@Override
	public void setTitle(String title) {
		titleField.setText(title);
    getElement().setTitle(title);
	}

	@Override
	public void setDateString(String date) {
		dateField.setText(date);
	}

	@Override
	public void setContent(String content) {
		contentField.setHTML(content);
	}

	@Override
	public void addCategory(String anchorHref, String name) {
    Anchor anchor = new Anchor(name, anchorHref);
    anchor.setStyleName(resource.style().articleCategoryAnchor());
    categoryList.add(anchor);
	}

	@Override
	public void addTag(String anchorHref, String name) {
    Anchor anchor = new Anchor(name, anchorHref);
    anchor.setStyleName(resource.style().articleTagAnchor());
    tagList.add(anchor);
	}
	
	@Override
	public void setCommentsCount(int count) {
		commentCountField.setText(count + " comments");
	}

  @Override
  public void setDiscussionListView(DiscussionListView discussionView) {
    mainField.add(discussion = discussionView);
  }
  
	@Override
	public void bindPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
    getElement().getStyle().clearRight();
    getElement().getStyle().clearBottom();
		//getElement().getParentElement().addClassName("shadow corner-radius");
		if(tagList.getWidgetCount() < 2){
		  tagList.setVisible(false);
    }
		if(discussion != null){
		  mainField.add(discussion);
		}
	}
	
	@UiHandler("closeLbl")
	void onClickClose(ClickEvent e){
		presenter.closeView();
	}
	
	@UiHandler("commentCountField")
  void onClickCommentCount(ClickEvent e){
	  int discussionPosition = contentField.getOffsetHeight() + tagList.getOffsetHeight();
	  scroller.setVerticalScrollPosition(discussionPosition);
  }  
}

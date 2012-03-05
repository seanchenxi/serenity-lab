package com.seanchenxi.gwt.serenity.client.view.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
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

public class ArticleViewImpl extends Composite implements ArticleView {

	interface ArticleViewImplUiBinder extends UiBinder<Widget, ArticleViewImpl>{}
	
	private static ArticleViewImplUiBinder uiBinder = GWT.create(ArticleViewImplUiBinder.class);
	
	@UiField HeadingElement titleField;
	@UiField Label dateField;
	@UiField FlowPanel categoryList;
	@UiField Label commentCountField;
	
	@UiField ScrollPanel scroller;
	@UiField HTMLPanel mainField;
	@UiField DivElement contentField;
	@UiField FlowPanel tagList;
	
	private DiscussionListView discussion;

	private Presenter presenter;
	private int articleId;
	
	public ArticleViewImpl(){
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void clearContent() {
		titleField.setInnerHTML(StringPool.BLANK);
		dateField.setText(StringPool.BLANK);
		categoryList.clear();
		commentCountField.setText(StringPool.BLANK);
		contentField.setInnerHTML(StringPool.BLANK);
		tagList.clear();
		tagList.add(new HTML("<i>Tags:&nbsp;</i>"));
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
		titleField.setInnerHTML(title);
	}

	@Override
	public void setDateString(String date) {
		dateField.setText(date);
	}

	@Override
	public void setContent(String content) {
		contentField.setInnerHTML(content);
	}

	@Override
	public void addCategory(String anchorHref, String name) {
		categoryList.add(new Anchor(name, anchorHref));
	}

	@Override
	public void addTag(String anchorHref, String name) {
		tagList.add(new Anchor(name, anchorHref));
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
		getElement().getParentElement().addClassName("shadow corner-radius");
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
	  int discussionPosition = contentField.getClientHeight() + tagList.getOffsetHeight();
	  scroller.setVerticalScrollPosition(discussionPosition);
  }
	
}

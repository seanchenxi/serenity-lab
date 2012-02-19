package com.seanchenxi.gwt.serenity.client.view.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.seanchenxi.gwt.serenity.client.view.ArticleView;
import com.seanchenxi.gwt.serenity.client.view.DiscussionView;
import com.seanchenxi.gwt.serenity.client.view.RespondView;
import com.seanchenxi.gwt.serenity.share.StringPool;

public class ArticleViewImpl extends Composite implements ArticleView {

	interface ArticleViewImplUiBinder extends UiBinder<Widget, ArticleViewImpl>{}
	
	private static ArticleViewImplUiBinder uiBinder = GWT.create(ArticleViewImplUiBinder.class);
	
	@UiField HeadingElement titleField;
	@UiField LIElement dateField;
	@UiField LIElement categoryList;
	@UiField LIElement commentCountField;
	@UiField DivElement contentField;
	@UiField DivElement tagList;
	
	@UiField ScrollPanel scroller;
	@UiField HTMLPanel mainField;
	
	private DiscussionView discussion;
	private RespondView respond;

	private Presenter presenter;
	private int articleId;
	
	public ArticleViewImpl(){
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void clearContent() {
		titleField.setInnerHTML(StringPool.BLANK);
		dateField.setInnerHTML(StringPool.BLANK);
		categoryList.setInnerHTML(StringPool.BLANK);
		commentCountField.setInnerHTML(StringPool.BLANK);
		contentField.setInnerHTML(StringPool.BLANK);
		tagList.setInnerHTML(StringPool.BLANK);
		if(discussion != null){
      mainField.remove(discussion);
      discussion = null;
    }
		if(respond != null){
      mainField.remove(respond);
      respond = null;
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
		dateField.setInnerHTML(date);
	}

	@Override
	public void setContent(String content) {
		contentField.setInnerHTML(content);
	}

	@Override
	public void addCategory(String anchorHref, String name) {
		AnchorElement cat = Document.get().createAnchorElement();
		cat.setHref(anchorHref);
		cat.setInnerHTML(name);
		categoryList.appendChild(cat);
	}

	@Override
	public void addTag(String anchorHref, String name) {
		AnchorElement tag = Document.get().createAnchorElement();
		tag.setHref(anchorHref);
		tag.setInnerHTML(name);
		tagList.appendChild(tag);
	}
	
	@Override
	public void setCommentsCount(int count) {
		commentCountField.setInnerHTML(count + " comments");
	}

  @Override
  public void setDiscussionView(DiscussionView discussionView) {
    mainField.add(discussion = discussionView);
  }

  @Override
  public void setRespondView(RespondView respondView) {
    mainField.add(respond = respondView);
  }
  
	@Override
	public void bindPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
		getElement().getParentElement().addClassName("shadow corner-radius");
		if(discussion != null){
		  mainField.add(discussion);
		}
		if(respond != null){
		  mainField.add(respond);
		}
	}
	
	@UiHandler("closeLbl")
	void onClickClose(ClickEvent e){
		presenter.closeView();
	}
	
}

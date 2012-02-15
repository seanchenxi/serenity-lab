package com.seanchenxi.gwt.serenity.client.view.impl;

import java.util.Date;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.seanchenxi.gwt.serenity.client.SerenityUtil;
import com.seanchenxi.gwt.serenity.client.view.ContentListView;
import com.seanchenxi.gwt.serenity.client.view.SummaryShow;

public class ContentListViewImpl extends Composite implements ContentListView, ClickHandler {

	interface ContentListViewImplUiBinder extends UiBinder<LayoutPanel, ContentListViewImpl>{}
	
	private static ContentListViewImplUiBinder uiBinder = GWT.create(ContentListViewImplUiBinder.class);

	@UiField HTML titleField;
	@UiField FlowPanel listBody;
	@UiField Label pagingInfoLbl;
	@UiField Label newer;
	@UiField Label older;
	@UiField ScrollPanel scroller;
	
	private Presenter presenter;
	private String highlightId;
	
	public ContentListViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void bindPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	public void clearContentList(){
		listBody.clear();
	}

	@Override
	public void highlightContentItem(String contentSlug) {
		highlightId = contentSlug;
		Iterator<Widget> itW = listBody.iterator();
		while(itW.hasNext()){
			Widget w = itW.next();
			if(w instanceof SummaryShow){
				if(((SummaryShow) w).getId().equalsIgnoreCase(contentSlug)){
					((SummaryShow) w).setHighlight(true);
				}else{
					((SummaryShow) w).setHighlight(false);
				}
			}
		}
	}
	
	@Override
	public void addContent(String slug, String title, String excerpt, Date date){
		SummaryShow ss = new SummaryShow();
		ss.setId(slug);
		ss.setPostTitle(title);
		ss.setPostSummary(excerpt);
		ss.setPostMetaInfo(SerenityUtil.toDateTimeString(date));
		ss.setHighlight(slug.equalsIgnoreCase(highlightId));
		ss.addClickHandler(this);
		listBody.add(ss);
	}
	
	@Override
	public void setPagingInfo(String titleHtml, int offset, int size, int total){
	  scroller.scrollToTop();
		titleField.setHTML(titleHtml);
		int end = Math.min(offset + size, total);
		pagingInfoLbl.setText(offset + "-" + end + " of " + total);
		enablePagingBtn(newer, offset > 0);
		enablePagingBtn(older, end < total);
	}

	@Override
	public void onClick(ClickEvent event) {
		Object o = event.getSource();
		if(o instanceof SummaryShow){
			SummaryShow ss = ((SummaryShow) o);
			if(ss.isHighlighted()){
				presenter.goForPost(null);
			}else{
				presenter.goForPost(ss.getId());
			}
		}
	}
	
	@UiHandler("newer")
	void newerClicked(ClickEvent e){
		presenter.prevPage();
	}
	
	@UiHandler("older")
	void olderClicked(ClickEvent e){
		presenter.nextPage();
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
		getElement().getParentElement().addClassName("shadow top-corner-radius");
	}
	
	private void enablePagingBtn(Label l, boolean enable){
		if(enable){
			l.removeStyleDependentName("disable");
		}else{
			l.addStyleDependentName("disable");
		}
	}
}

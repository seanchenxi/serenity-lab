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

import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.seanchenxi.gwt.serenity.client.resource.SerenityResources;
import com.seanchenxi.gwt.serenity.client.view.ContentListView;
import com.seanchenxi.gwt.ui.widget.Mask;

public class ContentListViewImpl extends Composite implements ContentListView, ClickHandler {

  public interface Resources extends ClientBundle {

    public interface Style extends CssResource {
      final static String DEFAULT_CSS = "com/seanchenxi/gwt/serenity/client/resource/view/ContentListView.css";
      String listTitle();
      String listPagingControl();
      String listPagingInfoLbl();
      String listPagingControlLbl();
      String listScroller();
      String listBody();
      String listContainer();
      String listPagingControlLblDisable();
    }

    @ClientBundle.Source(Style.DEFAULT_CSS)
    Style style();
  }
  
	interface ContentListViewImplUiBinder extends UiBinder<HTMLPanel, ContentListViewImpl>{}
	
	private static ContentListViewImplUiBinder UIBINDER = GWT.create(ContentListViewImplUiBinder.class);

	@UiField HTML titleField;
	@UiField FlowPanel listBody;
	@UiField Label pagingInfoLbl;
	@UiField Label newer;
	@UiField Label older;
	@UiField ScrollPanel scroller;
  @UiField Resources resource;

  private Presenter presenter;
	private String highlightId;
	
	public ContentListViewImpl() {
		initWidget(UIBINDER.createAndBindUi(this));
    resource.style().ensureInjected();
    scroller.getElement().getStyle().clearPosition();
    newer.addStyleName(SerenityResources.COMMON.commonCSS().unSelectable());
    older.addStyleName(SerenityResources.COMMON.commonCSS().unSelectable());
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
			if(w instanceof ContentItemViewImpl){
				if(((ContentItemViewImpl) w).getId().equalsIgnoreCase(contentSlug)){
					((ContentItemViewImpl) w).setHighlight(true);
				}else{
					((ContentItemViewImpl) w).setHighlight(false);
				}
			}
		}
	}

  @Override
  public void showMask(){
    Mask.showMaskFor(this);
  }

  @Override
  public void hideMask(){
    Mask.hideMask();
  }

  @Override
	public void addContent(String slug, String title, String excerpt, String meta){
	  ContentItemViewImpl ss = new ContentItemViewImpl();
		ss.setId(slug);
		ss.setTitle(title);
		ss.setSummary(excerpt);
		ss.setMeta(meta);
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
		if(o instanceof ContentItemViewImpl){
		  ContentItemViewImpl ss = ((ContentItemViewImpl) o);
			if(ss.isHighlighted()){
				presenter.goForArticle(null);
			}else{
				presenter.goForArticle(ss.getId());
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
	
	private void enablePagingBtn(Label l, boolean enable){
		if(enable){
			l.removeStyleName(resource.style().listPagingControlLblDisable());
		}else{
      l.addStyleName(resource.style().listPagingControlLblDisable());
		}
	}
}

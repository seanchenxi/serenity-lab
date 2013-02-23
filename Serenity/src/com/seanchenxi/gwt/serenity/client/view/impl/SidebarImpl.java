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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.seanchenxi.gwt.logging.api.Log;
import com.seanchenxi.gwt.serenity.client.resource.SerenityResources;
import com.seanchenxi.gwt.serenity.client.view.Sidebar;
import com.seanchenxi.gwt.serenity.client.widget.NavigationBar;
import com.seanchenxi.gwt.serenity.client.widget.NavigationItem;
import com.seanchenxi.gwt.serenity.client.widget.PopupLabelBox;
import com.seanchenxi.gwt.ui.event.SearchEvent;
import com.seanchenxi.gwt.ui.widget.SearchBox;

public class SidebarImpl implements Sidebar {

  public interface IDS extends Constants {
    @DefaultStringValue("home")
    String home();
    @DefaultStringValue("search")
    String search();
    @DefaultStringValue("category")
    String category();
    @DefaultStringValue("about")
    String about();
  }

  public interface Resources extends ClientBundle {

    public interface Style extends CssResource {
      final static String DEFAULT_CSS = "com/seanchenxi/gwt/serenity/client/resource/view/SideBar.css";
      String sideBar();
    }

    @ClientBundle.Source(Style.DEFAULT_CSS)
    Style style();
  }

  private static final IDS ids = GWT.create(IDS.class);
  private static Resources resources;

	private Presenter presenter;
	
	private NavigationBar menu;
	private PopupLabelBox catPopup;
	private SearchBox searchBox;

  public static Resources getResources(){
    if(resources == null){
      resources = GWT.create(Resources.class);
      resources.style().ensureInjected();
    }
    return resources;
  }
	
	public SidebarImpl(){
		menu = new NavigationBar();
		menu.addStyleName(getResources().style().sideBar());

		NavigationItem item = new NavigationItem(SerenityResources.IMG.icon_Home_black());
		item.setId(ids.home());
		menu.addItem(item);
		
		searchBox = new SearchBox();
		searchBox.setFieldWidth("250px");
		searchBox.addSearchHandler(new SearchEvent.Handler() {
			@Override
			public void onSearch(SearchEvent event) {
				presenter.search(event.getSearchValue());
			}
		});
		item = new NavigationItem(SerenityResources.IMG.icon_Search_black());
		item.setId(ids.search());
		item.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
			  Object obj = event.getSource();
			  searchBox.show();
			  if(obj instanceof UIObject){
			    UIObject uio = (UIObject) obj;
			    searchBox.setPopupPosition(uio.getAbsoluteLeft() + uio.getOffsetWidth(), uio.getAbsoluteTop());
			  }else{
			    searchBox.center();
			  }
			}
		});
		menu.addItem(item);
		
		item = new NavigationItem(SerenityResources.IMG.icon_Grid_black());
		item.setLabelBox(catPopup = new PopupLabelBox(1));
		item.setId(ids.category());
		menu.addItem(item);
		
		item = new NavigationItem(SerenityResources.IMG.icon_Info_black());
    item.setId(ids.about());
    menu.addItem(item);
		
		menu.addSelectionHandler(new SelectionHandler<NavigationItem>() {	
			@Override
			public void onSelection(SelectionEvent<NavigationItem> event) {
				NavigationItem item = event.getSelectedItem();
				if(ids.home().equalsIgnoreCase(item.getId())){
					presenter.goToHome();
				}else if(ids.about().equalsIgnoreCase(item.getId())){
					presenter.goToAbout();
				}else if(ids.category().equalsIgnoreCase(item.getId())){
					String slug = item.getLabelBox().getSelectedLabel();
					if(slug != null){
						presenter.goToCategory(slug);
					}
				}
			}
		});
	}
	
	@Override
	public void addCategory(String slug, String title) {
		catPopup.addLabel(slug, title);
	}
	
	@Override
	public Widget asWidget() {
		return menu.asWidget();
	}

	@Override
	public void bindPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	public void updateSelectionForPlace(String itemId, String slug) {
		Log.finest("[SidebarImpl] updateSelectionForPlace " + itemId + ", " + slug);
		NavigationItem item = menu.findItem(itemId);
		if(item != null){
		  if(slug != null){
		    item.setTitle(itemId + " " + slug.replace("-", " "));
		  }
			if(slug != null && item.getLabelBox() != null){
				Label label = item.getLabelBox().findLabel(slug);
				item.getLabelBox().select(label);
			}
			menu.select(item);
		}
	}

	@Override
	public void clearSelection() {
		menu.clearSelection();
	}

	@Override
	public void setSearchValue(String slug) {
		searchBox.setSeachValue(slug);
	}
	
}
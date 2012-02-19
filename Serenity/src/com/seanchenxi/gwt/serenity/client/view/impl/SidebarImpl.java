package com.seanchenxi.gwt.serenity.client.view.impl;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Label;
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

	private Presenter presenter;
	
	private NavigationBar menu;
	private PopupLabelBox catPopup;
	private SearchBox searchBox;
	
	public SidebarImpl(){
		menu = new NavigationBar();
		menu.addStyleName("sidebar");

		NavigationItem item = new NavigationItem(SerenityResources.IMG.icon_Home_black());
		item.setId("home");
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
		item.setId("search");
		item.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
			  searchBox.show();
        searchBox.center();
			}
		});
		menu.addItem(item);
		
		item = new NavigationItem(SerenityResources.IMG.icon_Info_black());
		item.setId("about");
		menu.addItem(item);
		
		item = new NavigationItem(SerenityResources.IMG.icon_Grid_black());
		item.setLabelBox(catPopup = new PopupLabelBox(3));
		item.setId("category");
		menu.addItem(item);
		
		menu.addSelectionHandler(new SelectionHandler<NavigationItem>() {	
			@Override
			public void onSelection(SelectionEvent<NavigationItem> event) {
				NavigationItem item = event.getSelectedItem();
				if("home".equalsIgnoreCase(item.getId())){
					presenter.goToHome();
				}else if("about".equalsIgnoreCase(item.getId())){
					presenter.goToAbout();
				}else if("category".equalsIgnoreCase(item.getId())){
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
	public void setSeachValue(String slug) {
		searchBox.setSeachValue(slug);
		updateSelectionForPlace("search", null);
	}
	
}
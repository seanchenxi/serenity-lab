package com.seanchenxi.gwt.serenity.client.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.seanchenxi.gwt.logging.api.Log;
import com.seanchenxi.gwt.serenity.client.SerenityFactory;
import com.seanchenxi.gwt.serenity.client.place.AboutPlace;
import com.seanchenxi.gwt.serenity.client.place.CategoryPlace;
import com.seanchenxi.gwt.serenity.client.place.HomePlace;
import com.seanchenxi.gwt.serenity.client.place.SearchPlace;
import com.seanchenxi.gwt.serenity.client.place.SerenityPlace;
import com.seanchenxi.gwt.serenity.client.place.SlugPlace;
import com.seanchenxi.gwt.serenity.client.view.Sidebar;
import com.seanchenxi.gwt.wordpress.json.WPJsonAPI;
import com.seanchenxi.gwt.wordpress.json.api.model.Category;
import com.seanchenxi.gwt.wordpress.json.api.model.CategoryIndex;

public class SidebarActivity extends AbstractActivity implements Sidebar.Presenter {

	private SerenityFactory clientFactory;
	private Sidebar sidebar;
	private SerenityPlace place;
	
	public SidebarActivity(SerenityFactory clientFactory) {
		this.clientFactory = clientFactory;
		this.sidebar = clientFactory.getSidebar();
		this.sidebar.bindPresenter(this);
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		if(!sidebar.asWidget().isAttached())
			WPJsonAPI.get().getCoreService().getCategoryIndex(new GetCategoriesAction(sidebar));
		panel.setWidget(sidebar);
	}
	
	@Override
	public void goToHome() {
		Log.finest("[SidebarActivity] Go To Home");
		clientFactory.getPlaceController().goTo(new HomePlace());
	}
	
	@Override
	public void goToAbout() {
		Log.finest("[SidebarActivity] Go To About");
		clientFactory.getPlaceController().goTo(new AboutPlace());
	}

	@Override
	public void goToCategory(String slug) {
		Log.finest("[SidebarActivity] Go To Category: " + slug);
		clientFactory.getPlaceController().goTo(new CategoryPlace(slug, 0));
	}
	
	@Override
	public void search(String searchValue) {
		Log.finest("[SidebarActivity] Search : " + searchValue);
		clientFactory.getPlaceController().goTo(new SearchPlace(searchValue, 0));
	}
	
	public void updateForPlace(SerenityPlace place) {
		this.place = place;
		if (place instanceof SearchPlace){
			sidebar.setSeachValue(((SearchPlace) place).getSlug());
		} else {
			sidebar.setSeachValue("");
			if(place instanceof SlugPlace) {
				sidebar.updateSelectionForPlace(place.getPrefix(), ((SlugPlace) place).getSlug());
			}else{
				sidebar.updateSelectionForPlace(place.getPrefix(), null);
			}
		}
	}
	
	private class GetCategoriesAction implements AsyncCallback<CategoryIndex>{
		
		private Sidebar sidebar;
		
		public GetCategoriesAction(Sidebar sidebar){
			this.sidebar = sidebar;
		}
		
		@Override
		public void onFailure(Throwable caught) {
			Log.severe("GetCategoriesAction", caught);
		}

		@Override
		public void onSuccess(CategoryIndex result) {
			ArrayList<Category> cagetories = result.getCagetories();
			Collections.sort(cagetories, new Comparator<Category>() {
				@Override
				public int compare(Category o1, Category o2) {
					return o1.getTitle().compareToIgnoreCase(o2.getTitle());
				}
			});
			for(Category cat : cagetories){
				sidebar.addCategory(cat.getSlug(), cat.getTitle());
			}
			if(place instanceof CategoryPlace)
				updateForPlace(place);
		}
		
	}
	
}

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
package com.seanchenxi.gwt.serenity.client.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.seanchenxi.gwt.logging.api.Log;
import com.seanchenxi.gwt.serenity.client.SerenityFactory;
import com.seanchenxi.gwt.serenity.client.place.AboutPlace;
import com.seanchenxi.gwt.serenity.client.place.ArticlePlace;
import com.seanchenxi.gwt.serenity.client.place.CategoryPlace;
import com.seanchenxi.gwt.serenity.client.place.HomePlace;
import com.seanchenxi.gwt.serenity.client.place.SearchPlace;
import com.seanchenxi.gwt.serenity.client.place.SerenityPlace;
import com.seanchenxi.gwt.serenity.client.place.SlugPlace;
import com.seanchenxi.gwt.serenity.client.view.Sidebar;
import com.seanchenxi.gwt.wordpress.json.WPJsonAPI;
import com.seanchenxi.gwt.wordpress.json.api.model.Category;
import com.seanchenxi.gwt.wordpress.json.api.model.CategoryIndex;

// TODO Side bar should be a place controller
public class SidebarActivity extends AbstractActivity implements Sidebar.Presenter {

	private SerenityFactory clientFactory;
	private Sidebar sidebar;
	private SerenityPlace place;
	private HandlerRegistration keyUpRegistration;
	
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
		keyUpRegistration = clientFactory.getEventBus().addHandler(KeyUpEvent.getType(), new KeyUpHandler() {     
	      @Override
	      public void onKeyUp(KeyUpEvent event) {
	        System.out.println(event.getNativeKeyCode());
	      }
	    });
	}
	
	@Override
	public void onCancel() {
	 if(keyUpRegistration != null){
	   keyUpRegistration.removeHandler();
	   keyUpRegistration = null;
	 }
	}
	
	@Override
	public void onStop() {
	  if(keyUpRegistration != null){
	     keyUpRegistration.removeHandler();
	     keyUpRegistration = null;
	   }
	}
	
	@Override
	public void goToHome() {
		Log.finest("[SidebarActivity] Go To Home");
		clientFactory.getPlaceController().goTo(new HomePlace());
	}
	
	@Override
	public void goToAbout() {
		Log.finest("[SidebarActivity] Go To About");
		clientFactory.getPlaceController().goTo(new AboutPlace(""));
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
			sidebar.updateSelectionForPlace("search", null);
		} else {
			sidebar.setSeachValue("");
			if(place instanceof SlugPlace) {
			  String prefix = place.getPrefix();
			  if(place instanceof ArticlePlace){
			    prefix = "home";
			  }
				sidebar.updateSelectionForPlace(prefix, ((SlugPlace) place).getSlug());
			}else if(place instanceof AboutPlace){
			  sidebar.updateSelectionForPlace(place.getPrefix(), ((AboutPlace) place).getSlug());
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

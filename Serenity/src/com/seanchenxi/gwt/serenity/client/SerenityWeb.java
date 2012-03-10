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
package com.seanchenxi.gwt.serenity.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.seanchenxi.gwt.logging.api.Log;
import com.seanchenxi.gwt.serenity.client.activity.mapper.ArticleActivityMapper;
import com.seanchenxi.gwt.serenity.client.activity.mapper.ContentListActivityMapper;
import com.seanchenxi.gwt.serenity.client.activity.mapper.SidebarActivityMapper;
import com.seanchenxi.gwt.serenity.client.place.HomePlace;
import com.seanchenxi.gwt.serenity.client.place.mapper.SerenityPlaceHistoryMapper;

class SerenityWeb implements UncaughtExceptionHandler {

	private PlaceHistoryMapper historyMapper;
	private PlaceHistoryHandler historyHandler;
	
	private final ActivityMapper sidebarActivityMapper;
	private final ActivityManager sidebarActivityManager;

	private final ActivityMapper contentListActivityMapper;
	private final ActivityManager contentListActivityManager;

	private final ActivityMapper articleActivityMapper;
	private final ActivityManager articleActivityManager;
	
	private final SerenityFactory clientFactory;
	
	private final static SerenityWeb INSTANCE = new SerenityWeb();
	
	SerenityWeb(){
		clientFactory = GWT.create(SerenityFactory.class);
		
		sidebarActivityMapper = new SidebarActivityMapper(clientFactory);
		sidebarActivityManager = new ActivityManager(sidebarActivityMapper, clientFactory.getEventBus());
		sidebarActivityManager.setDisplay(clientFactory.getLayout().getSidebarContainer());
		
		contentListActivityMapper = new ContentListActivityMapper(clientFactory);
		contentListActivityManager = new ActivityManager(contentListActivityMapper, clientFactory.getEventBus());
		contentListActivityManager.setDisplay(clientFactory.getLayout().getContentListContainer());
		
		articleActivityMapper = new ArticleActivityMapper(clientFactory);
		articleActivityManager = new ActivityManager(articleActivityMapper, clientFactory.getEventBus());
		articleActivityManager.setDisplay(clientFactory.getLayout().getArticleContainer());
	}
	
	static SerenityWeb getInstance() {
		return INSTANCE;
	}
	
	void run(){
		clientFactory.getLayout().show();
		if(historyHandler == null){
			historyMapper = GWT.create(SerenityPlaceHistoryMapper.class);
			historyHandler = new PlaceHistoryHandler(historyMapper);
			historyHandler.register(clientFactory.getPlaceController(), clientFactory.getEventBus(), new HomePlace());
		}
		historyHandler.handleCurrentHistory();
	}

	@Override
	public void onUncaughtException(Throwable e) {
		Log.severe("GWT Uncaught Exception", e);
	}
	
}

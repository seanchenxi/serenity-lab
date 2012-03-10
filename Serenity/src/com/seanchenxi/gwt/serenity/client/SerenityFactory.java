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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.seanchenxi.gwt.serenity.client.view.ArticleView;
import com.seanchenxi.gwt.serenity.client.view.ContentListView;
import com.seanchenxi.gwt.serenity.client.view.DiscussionListView;
import com.seanchenxi.gwt.serenity.client.view.RespondView;
import com.seanchenxi.gwt.serenity.client.view.SerenityLayout;
import com.seanchenxi.gwt.serenity.client.view.Sidebar;

public class SerenityFactory {
	
	private final EventBus eventBus;
	private final PlaceController placeController;

	private final SerenityLayout layout;
	private final Sidebar sidebar;
	private final ContentListView contentList;
	private final ArticleView article;
	private final DiscussionListView discussion;
	private final RespondView respond;

	public SerenityFactory() {	
		eventBus = GWT.create(SimpleEventBus.class);
		placeController = new PlaceController(eventBus);
		
		layout = GWT.create(SerenityLayout.class);
		sidebar = GWT.create(Sidebar.class);
		contentList = GWT.create(ContentListView.class);
		article = GWT.create(ArticleView.class);
		discussion = GWT.create(DiscussionListView.class);
		respond = GWT.create(RespondView.class);
	}

	public EventBus getEventBus() {
		return eventBus;
	}

	public PlaceController getPlaceController() {
		return placeController;
	}

	public SerenityLayout getLayout() {
		return layout;
	}
	
	public Sidebar getSidebar() {
		return sidebar;
	}

	public ContentListView getContentList() {
		return contentList;
	}

	public ArticleView getArticleView() {
		return article;
	}
	
	public RespondView getRespondView() {
    return respond;
  }
	
	public DiscussionListView getDiscussionListView() {
    return discussion;
  }

}

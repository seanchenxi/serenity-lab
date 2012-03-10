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

import com.seanchenxi.gwt.logging.api.Log;
import com.seanchenxi.gwt.serenity.client.SerenityFactory;
import com.seanchenxi.gwt.serenity.client.place.SearchPlace;
import com.seanchenxi.gwt.serenity.client.place.SerenityPlace;
import com.seanchenxi.gwt.serenity.client.view.ContentListView;
import com.seanchenxi.gwt.wordpress.json.api.model.PostType;

public class SearchContentsActivity extends ContentListActivity {

	public SearchContentsActivity(String slug, int page, SerenityFactory clientFactory) {
		super(slug, page, clientFactory);
	}
	
	@Override
	protected void fetchContents(String slug, int offset, int size, ContentListView view) {
		Log.finest("[SearchContentsActivity] fetchContents "+slug+", "+ offset + ", " + size);
		GetContentsResult gcr = new GetContentsResult("Search: " + slug, offset, view);
		getWPCoreService().searchPosts(slug, PostType.POST, offset, size, gcr);
	}
	
	@Override
	protected SerenityPlace nextPlace(String slug, int page) {
		return new SearchPlace(slug, page);
	}
	
}

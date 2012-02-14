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

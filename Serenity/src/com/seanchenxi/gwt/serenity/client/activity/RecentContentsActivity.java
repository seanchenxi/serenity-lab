package com.seanchenxi.gwt.serenity.client.activity;

import com.seanchenxi.gwt.logging.api.Log;
import com.seanchenxi.gwt.serenity.client.SerenityFactory;
import com.seanchenxi.gwt.serenity.client.place.HomePlace;
import com.seanchenxi.gwt.serenity.client.place.SerenityPlace;
import com.seanchenxi.gwt.serenity.client.view.ContentListView;
import com.seanchenxi.gwt.wordpress.json.api.model.PostType;

public class RecentContentsActivity extends ContentListActivity {

	public RecentContentsActivity(int page, SerenityFactory clientFactory) {
		super(null, page, clientFactory);
	}
	
	@Override
	protected void fetchContents(String slug, int offset, int size, ContentListView view) {
		Log.finest("[RecentContentsActivity] fetchContents "+ offset + ", " + size);
		GetContentsResult gcr = new GetContentsResult("Recents", offset, view);
		getWPCoreService().getRecentPosts(offset, size, PostType.POST, gcr);
	}
	
	@Override
	protected SerenityPlace nextPlace(String slug, int page) {
		return new HomePlace(page);
	}
	
}

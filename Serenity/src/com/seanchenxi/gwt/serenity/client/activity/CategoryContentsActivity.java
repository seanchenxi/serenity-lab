package com.seanchenxi.gwt.serenity.client.activity;

import com.seanchenxi.gwt.logging.api.Log;
import com.seanchenxi.gwt.serenity.client.SerenityFactory;
import com.seanchenxi.gwt.serenity.client.place.CategoryPlace;
import com.seanchenxi.gwt.serenity.client.place.SerenityPlace;
import com.seanchenxi.gwt.serenity.client.view.ContentListView;
import com.seanchenxi.gwt.wordpress.json.api.model.PostType;

public class CategoryContentsActivity extends ContentListActivity {

	public CategoryContentsActivity(String slug, int page, SerenityFactory clientFactory) {
		super(slug, page, clientFactory);
	}
	
	@Override
	protected void fetchContents(String slug, int offset, int size, ContentListView view) {
		Log.finest("[CategoryContentsActivity] fetchContents "+slug+", "+ offset + ", " + size);
		GetContentsResult gcr = new GetContentsResult("Category: " + slug, offset, view);
		getWPCoreService().getCategoryPosts(slug, PostType.POST, offset, size, gcr);
	}

	@Override
	protected SerenityPlace nextPlace(String slug, int page) {
		return new CategoryPlace(slug, page);
	}
	
}

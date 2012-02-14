package com.seanchenxi.gwt.serenity.client.activity.mapper;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.seanchenxi.gwt.logging.api.Log;
import com.seanchenxi.gwt.serenity.client.SerenityFactory;
import com.seanchenxi.gwt.serenity.client.activity.CategoryContentsActivity;
import com.seanchenxi.gwt.serenity.client.activity.ContentListActivity;
import com.seanchenxi.gwt.serenity.client.activity.RecentContentsActivity;
import com.seanchenxi.gwt.serenity.client.activity.SearchContentsActivity;
import com.seanchenxi.gwt.serenity.client.activity.TagContentsActivity;
import com.seanchenxi.gwt.serenity.client.place.ArticlePlace;
import com.seanchenxi.gwt.serenity.client.place.CategoryPlace;
import com.seanchenxi.gwt.serenity.client.place.HasPaging;
import com.seanchenxi.gwt.serenity.client.place.HomePlace;
import com.seanchenxi.gwt.serenity.client.place.SearchPlace;
import com.seanchenxi.gwt.serenity.client.place.SlugPlace;
import com.seanchenxi.gwt.serenity.client.place.TagPlace;

public class ContentListActivityMapper implements ActivityMapper {

	private SerenityFactory clientFactory;
	private ContentListActivity activity;

	public ContentListActivityMapper(SerenityFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof ArticlePlace) {
			Log.finest("[ContentListActivityMapper] getActivity from ArticlePlace");
			if (activity == null) {
				activity = new RecentContentsActivity(0, clientFactory);
			}
			activity.hilightContent(((ArticlePlace) place).getSlug());
			return activity;
		} else if (place instanceof HasPaging) {
			String slug = null;
			int page = 0;
			page = ((HasPaging) place).getPage();
			if (place instanceof SlugPlace) {
				slug = ((SlugPlace) place).getSlug();
				Log.finest("[ContentListActivityMapper] getActivity from HasPaging " + slug + ", " + page);
				if (activity != null
						&& slug.equalsIgnoreCase(activity.getSlug())
						&& activity.getPage() == page) {
					activity.hilightContent("");
					return activity;
				}
				if (place instanceof CategoryPlace) {
					return activity = new CategoryContentsActivity(slug, page,
							clientFactory);
				} else if (place instanceof TagPlace) {
					return activity = new TagContentsActivity(slug, page,
							clientFactory);
				} else if (place instanceof SearchPlace) {
					return activity = new SearchContentsActivity(slug, page,
							clientFactory);
				}
			} else if (place instanceof HomePlace) {
				Log.finest("[ContentListActivityMapper] getActivity from HomePlace " + page);
				if (activity instanceof RecentContentsActivity
						&& activity.getPage() == page) {
					activity.hilightContent("");
					return activity;
				}
				return activity = new RecentContentsActivity(page, clientFactory);
			}
		}
		return null;
	}

}

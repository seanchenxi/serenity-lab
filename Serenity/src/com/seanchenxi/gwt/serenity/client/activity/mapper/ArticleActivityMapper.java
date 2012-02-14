package com.seanchenxi.gwt.serenity.client.activity.mapper;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.seanchenxi.gwt.serenity.client.SerenityFactory;
import com.seanchenxi.gwt.serenity.client.activity.ArticleActivity;
import com.seanchenxi.gwt.serenity.client.place.ArticlePlace;

public class ArticleActivityMapper implements ActivityMapper {

	private SerenityFactory clientFactory;

	public ArticleActivityMapper(SerenityFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof ArticlePlace) {
			return new ArticleActivity((ArticlePlace) place, clientFactory);
		}
		return null;
	}

}

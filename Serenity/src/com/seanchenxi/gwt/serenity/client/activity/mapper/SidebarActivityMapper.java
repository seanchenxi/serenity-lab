package com.seanchenxi.gwt.serenity.client.activity.mapper;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.seanchenxi.gwt.serenity.client.SerenityFactory;
import com.seanchenxi.gwt.serenity.client.activity.SidebarActivity;
import com.seanchenxi.gwt.serenity.client.place.SerenityPlace;

public class SidebarActivityMapper implements ActivityMapper {

	private final SidebarActivity sidebarActivity;

	public SidebarActivityMapper(SerenityFactory clientFactory) {
		this.sidebarActivity = new SidebarActivity(clientFactory);
	}

	@Override
	public Activity getActivity(Place place) {
		if(place instanceof SerenityPlace){
			sidebarActivity.updateForPlace((SerenityPlace) place);
			return sidebarActivity;
		}
		return null;
	}

}

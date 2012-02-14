package com.seanchenxi.gwt.serenity.client.place.mapper;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.seanchenxi.gwt.serenity.client.place.SerenityPlace;
import com.seanchenxi.gwt.serenity.client.place.SerenityPlaceUtil;

public class SerenityPlaceHistoryMapper implements PlaceHistoryMapper {

	@Override
	public Place getPlace(String token) {
		return SerenityPlaceUtil.parseToken(token);
	}

	@Override
	public String getToken(Place place) {
		if(place instanceof SerenityPlace)
			return SerenityPlaceUtil.parsePlace((SerenityPlace) place);
		return null;
	}
	
}

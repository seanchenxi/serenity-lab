package com.seanchenxi.gwt.serenity.client.place;


public class SearchPlace extends SlugPlace {

	public static final String PREFIX = "search";
	
	public SearchPlace(String slug, int page) {
		super(slug, page);
	}

	@Override
	public String getPrefix() {
		return SearchPlace.PREFIX;
	}
	
}

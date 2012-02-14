package com.seanchenxi.gwt.serenity.client.place;



public class TagPlace extends SlugPlace {

	public static final String PREFIX = "tag";
	
	public TagPlace(String slug, int page) {
		super(slug, page);
	}
	
	@Override
	public String getPrefix() {
		return TagPlace.PREFIX;
	}
}


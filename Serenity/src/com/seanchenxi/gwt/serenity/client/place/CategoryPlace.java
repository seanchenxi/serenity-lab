package com.seanchenxi.gwt.serenity.client.place;


public class CategoryPlace extends SlugPlace {

	public final static String PREFIX = "category";
	
	public CategoryPlace(String slug, int page) {
		super(slug, page);
	}

	@Override
	public String getPrefix() {
		return CategoryPlace.PREFIX;
	}
}

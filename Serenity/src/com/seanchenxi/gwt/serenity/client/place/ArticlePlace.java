package com.seanchenxi.gwt.serenity.client.place;


public class ArticlePlace extends SlugPlace {
	
	public static final String PREFIX = "article";
	
	public ArticlePlace(String slug) {
		super(slug, 0);
	}
	
	@Override
	public String getPrefix() {
		return ArticlePlace.PREFIX;
	}
	
}

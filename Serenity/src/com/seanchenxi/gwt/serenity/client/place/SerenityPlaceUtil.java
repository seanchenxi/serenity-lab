/*******************************************************************************
 * Copyright 2012 Xi CHEN
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.seanchenxi.gwt.serenity.client.place;

import com.seanchenxi.gwt.serenity.share.StringPool;

public class SerenityPlaceUtil {
	
	protected static final String PRE = StringPool.EXCLAMATION + StringPool.SLASH;
	private static final String PPRE = "page";
	private static final String PREGX = PPRE+"[0-9]+";
	private static final String NBREGX = "[0-9]+";
	
	private static final int PPRE_LENGTH = PPRE.length();
	private static final int PRE_LENGTH = PRE.length();
	
	private static final HomePlace DEFAULT = new HomePlace();
	
  public static String getArticleAnchor(String slug) {
    return buildToken(ArticlePlace.PREFIX, slug, 0, true);
  }
	 
	public static String getCategoryAnchor(String slug, int page){
		return buildToken(CategoryPlace.PREFIX, slug, page, true);
	}
	
	public static String getTagAnchor(String slug, int page){
		return buildToken(TagPlace.PREFIX, slug, page, true);
	}
	
	public static String getSearchAnchor(String slug, int page){
		return buildToken(SearchPlace.PREFIX, slug, page, true);
	}
	
	public static String parsePlace(SerenityPlace place){
		String prefix = place.getPrefix();
		int page = 0;
		String slug = null;
		if(place instanceof HasPaging){
			page = ((HasPaging) place).getPage();
		}
		if(place instanceof SlugPlace){
			slug = ((SlugPlace) place).getSlug();
		}else if(place instanceof AboutPlace){
		  slug = ((AboutPlace) place).getSlug();
		}
		return buildToken(prefix, slug, page, false);
	}
	
	public static SerenityPlace parseToken(String token){
		if(token == null || !token.startsWith(PRE)) 
			return DEFAULT;
		
		int prefixIndex = token.indexOf(StringPool.SLASH, PRE_LENGTH);
		if(prefixIndex == -1)
			return DEFAULT;
		
		String prefix = token.substring(PRE_LENGTH, prefixIndex);
		PlaceBuilder builder = PlaceBuilder.parsePrefix(prefix);
		if(builder == null){
			return DEFAULT;
		}
		
		String[] tokens = token.substring(prefixIndex).split(StringPool.SLASH);
		int page = 0;
		String slug = null;
		if(tokens.length > 1){
			if(!tokens[1].matches(PREGX)){
				slug = tokens[1];
				if(tokens.length > 2){
					page = Integer.parseInt(tokens[2].substring(PPRE_LENGTH));
				}
			}else if(tokens[1].matches(NBREGX)){
			  page = Integer.parseInt(tokens[1].substring(PPRE_LENGTH));
			}
		}
		return builder.build(slug, page);
	}
	
	private static String buildToken(String prefix, String slug, int page, boolean isAnchor){
		StringBuilder sb = isAnchor ? new StringBuilder(StringPool.POUND) : new StringBuilder();
		sb.append(PRE + prefix);
		if(slug != null && !slug.isEmpty()){
			sb.append(StringPool.SLASH);
			sb.append(slug);
		}
		if(page > 0){
			sb.append(StringPool.SLASH);
			sb.append(PPRE);
			sb.append(page);
		}
		return sb.toString();
	}
	
	private enum PlaceBuilder {
		HOME(HomePlace.PREFIX) {
			@Override
			public SerenityPlace build(String slug, int page) {
				return new HomePlace(page);
			}
		},ABOUT(AboutPlace.PREFIX) {
			@Override
			public SerenityPlace build(String slug, int page) {
				return new AboutPlace(slug);
			}
		},CATEGORY(CategoryPlace.PREFIX) {
			@Override
			public SerenityPlace build(String slug, int page) {
				return new CategoryPlace(slug, page);
			}
		},TAG(TagPlace.PREFIX) {
			@Override
			public SerenityPlace build(String slug, int page) {
				return new TagPlace(slug, page);
			}
		},ARTICLE(ArticlePlace.PREFIX) {
			@Override
			public SerenityPlace build(String slug, int page) {
				return new ArticlePlace(slug);
			}
		},SEARCH(SearchPlace.PREFIX) {
			@Override
			public SerenityPlace build(String slug, int page) {
				return new SearchPlace(slug, page);
			}
		};
		
		private final String prefix;
		
		private PlaceBuilder(String prefix){
			this.prefix = prefix;
		}
		
		public abstract SerenityPlace build(String slug, int page);
		
		private static PlaceBuilder parsePrefix(String prefix){
			for(PlaceBuilder pb : values()){
				if(pb.prefix.equalsIgnoreCase(prefix)){
					return pb;
				}
			}
			return null;
		}
	}
}

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
package com.seanchenxi.gwt.serenity.client.activity.mapper;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.seanchenxi.gwt.serenity.client.SerenityFactory;
import com.seanchenxi.gwt.serenity.client.activity.ArticleActivity;
import com.seanchenxi.gwt.serenity.client.place.AboutPlace;
import com.seanchenxi.gwt.serenity.client.place.ArticlePlace;

public class ArticleActivityMapper implements ActivityMapper {

	private SerenityFactory clientFactory;

	public ArticleActivityMapper(SerenityFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public Activity getActivity(Place place) {
	  String articleSlug = null;
		if (place instanceof ArticlePlace) {
		  articleSlug = ((ArticlePlace) place).getSlug();
		}else if(place instanceof AboutPlace){
		  articleSlug = ((AboutPlace) place).getSlug();
		}
		if(articleSlug == null || articleSlug.isEmpty()){
		  return null;
		}
		return new ArticleActivity(articleSlug, clientFactory);
	}

}

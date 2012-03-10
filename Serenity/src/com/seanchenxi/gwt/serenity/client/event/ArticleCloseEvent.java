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
package com.seanchenxi.gwt.serenity.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class ArticleCloseEvent extends GwtEvent<ArticleCloseEvent.Handler> {

	public interface Handler extends EventHandler {
		void onArticleClose(ArticleCloseEvent event);
	}
	
	public interface HasArticleCloseHandlers extends HasHandlers {
		HandlerRegistration addArticleCloseHandler(ArticleCloseEvent.Handler handler);
	}
	
	public static Type<ArticleCloseEvent.Handler> TYPE;

	private final String slug;

	public ArticleCloseEvent(String slug) {
		this.slug = slug;
	}

	public String getSlug() {
		return slug;
	}

	public static Type<ArticleCloseEvent.Handler> getType() {
		if (TYPE == null) {
			TYPE = new Type<ArticleCloseEvent.Handler>();
		}
		return TYPE;
	}
	
	@Override
	public Type<ArticleCloseEvent.Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ArticleCloseEvent.Handler handler) {
		handler.onArticleClose(this);
	}
	
	public static void fire(String slug, HasArticleCloseHandlers source) {
		if (TYPE != null) {
			ArticleCloseEvent event = new ArticleCloseEvent(slug);
			source.fireEvent(event);
		}
	}
}

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
package com.seanchenxi.gwt.ui.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class SearchEvent extends GwtEvent<SearchEvent.Handler> {

	public static interface Handler extends EventHandler {
		void onSearch(SearchEvent event);
	}

	public interface HasSearchHandlers extends HasHandlers {
		HandlerRegistration addSearchHandler(SearchEvent.Handler handler);
	}

	public static Type<SearchEvent.Handler> TYPE;
	
	private final String searchValue;
	
	public SearchEvent(String searchValue) {
		this.searchValue = searchValue;
	}
	
	public String getSearchValue() {
		return searchValue;
	}

	public static Type<SearchEvent.Handler> getType() {
		if (TYPE == null) {
			TYPE = new Type<SearchEvent.Handler>();
		}
		return TYPE;
	}

	@Override
	public Type<SearchEvent.Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SearchEvent.Handler handler) {
		handler.onSearch(this);
	}

	public static void fire(String searchValue, HasSearchHandlers source) {
		if (TYPE != null) {
			SearchEvent event = new SearchEvent(searchValue);
			source.fireEvent(event);
		}
	}
}

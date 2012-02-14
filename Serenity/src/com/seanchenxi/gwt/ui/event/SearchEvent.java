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

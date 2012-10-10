package com.seanchenxi.logging.monitor.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class LogRotatedEvent extends GwtEvent<LogRotatedEvent.Handler>{

	public interface Handler extends EventHandler{
		void onLogRotated();
	}

	private final static Type<LogRotatedEvent.Handler> TYPE = new Type<LogRotatedEvent.Handler>();
	
	public static Type<LogRotatedEvent.Handler> getType() {
		return TYPE;
	}
	
	@Override
	public Type<LogRotatedEvent.Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LogRotatedEvent.Handler handler) {
		handler.onLogRotated();
	}

}

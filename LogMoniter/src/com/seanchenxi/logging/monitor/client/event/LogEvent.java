package com.seanchenxi.logging.monitor.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class LogEvent extends GwtEvent<LogEvent.Handler>{

	public interface Handler extends EventHandler{
		void onLog(String log);
	}

	private final static Type<LogEvent.Handler> TYPE = new Type<LogEvent.Handler>();
	
	public static Type<LogEvent.Handler> getType() {
		return TYPE;
	}
	
	private final String log;
	
	public LogEvent(String log){
		this.log = log;
	}
	
	@Override
	public Type<LogEvent.Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LogEvent.Handler handler) {
		handler.onLog(log);
	}

}

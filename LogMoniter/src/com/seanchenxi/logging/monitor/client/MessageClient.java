package com.seanchenxi.logging.monitor.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.seanchenxi.logging.monitor.client.event.LogEvent;
import com.seanchenxi.logging.monitor.client.event.LogRotatedEvent;
import com.seanchenxi.logging.monitor.client.service.MessageService;
import com.seanchenxi.logging.monitor.client.service.MessageServiceAsync;
import com.seanchenxi.logging.monitor.shared.LogMessage;

public class MessageClient {
	
	private final static Logger Log = Logger.getLogger("MessageClient");
	
	private class RegisterCallback implements AsyncCallback<String> {
		@Override
		public void onFailure(Throwable caught) {
			Log.log(Level.SEVERE, "register error", caught);
			isStarted = false;
		}

		@Override
		public void onSuccess(String result) {
			messageId = result;
			errorCount = 5;
			call();
		}
	}

	private class UnRegisterCallback implements AsyncCallback<Boolean> {
		@Override
		public void onFailure(Throwable caught) {
			Log.log(Level.SEVERE, "unregister error", caught);
			isStarted = false;
		}

		@Override
		public void onSuccess(Boolean result) {
			if(result) {
				messageId = null;
				isStarted = false;
			}
			Log.log(Level.INFO, "unregistered: " + result);
		}
	}
	
	private class ListenCallback implements AsyncCallback<LogMessage> {
		@Override
		public void onFailure(Throwable caught) {
			if(errorCount-- > 0){
				call();
				Log.log(Level.WARNING, "listen error, retry", caught);
			}else{
				stop();
				Log.log(Level.SEVERE, "listen error", caught);
			}
		}

		@Override
		public void onSuccess(LogMessage result) {
			errorCount = 5;
			fireEvent(result);
			call();
		}
	}
	
	private final static MessageClient INSTANCE = new MessageClient();
	
	private final MessageServiceAsync messageService = GWT.create(MessageService.class);
	private final HandlerManager handlerManager = new HandlerManager(this);
	private boolean isStarted = false;
	private String messageId;
	private int errorCount = 5;
	
	public static MessageClient getInstance() {
		return INSTANCE;
	}
	
	private MessageClient(){
	}
	
	public HandlerRegistration addLogHandler(LogEvent.Handler handler){
		try{
			return handlerManager.addHandler(LogEvent.getType(), handler);
		}finally{
			start();
		}
	}
	
	public HandlerRegistration addLogRotatedHandler(LogRotatedEvent.Handler handler){
		try{
			return handlerManager.addHandler(LogRotatedEvent.getType(), handler);
		}finally{
			start();
		}
	}
	
	private void fireEvent(final LogMessage result) {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				switch (result.getType()) {
				case LOG:
					handlerManager.fireEvent(new LogEvent(result.getMessage()));
					break;
				case ROTATED:
					handlerManager.fireEvent(new LogRotatedEvent());
					break;
				case ERROR:
				  handlerManager.fireEvent(new LogEvent(result.getMessage()));
				  break;
				default:
					break;
				}
			}
		});
	}
	
	private void start() {
		if(!isStarted && hasHanlder()){
			messageService.register(new RegisterCallback());
			isStarted = true;
		}
	}
	
	private void call(){
		if(isStarted && hasHanlder()){
			messageService.listen(messageId, new ListenCallback());
		}else{
			stop();
		}
	}

	public void stop() {
		if(isStarted){
			messageService.unregister(messageId, new UnRegisterCallback());
		}
	}
	
	public boolean hasHanlder(){
		return handlerManager.getHandlerCount(LogEvent.getType()) > 0 || handlerManager.getHandlerCount(LogRotatedEvent.getType()) > 0;
	}
}

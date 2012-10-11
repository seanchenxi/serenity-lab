package com.seanchenxi.logging.monitor.client;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
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
	
	private class ListenCallback implements AsyncCallback<ArrayList<LogMessage>> {
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
		public void onSuccess(ArrayList<LogMessage> result) {
			errorCount = 5;
			Scheduler.get().scheduleIncremental(new FireEventCommande(result));
			call();
		}
	}
	
	private class FireEventCommande implements RepeatingCommand {
		
		private final LinkedList<LogMessage> msgs;	
		
		private FireEventCommande(List<LogMessage> msgs){
			this.msgs = new LinkedList<LogMessage>(msgs);	
		}

		@Override
		public boolean execute() {
			fireEvent(msgs.poll());
			return !msgs.isEmpty();
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
	
	private void fireEvent(LogMessage lm){
		if(lm == null) return;
		switch (lm.getType()) {
		case LOG:
			handlerManager.fireEvent(new LogEvent(lm.getMessage()));
			break;
		case ROTATED:
			handlerManager.fireEvent(new LogRotatedEvent());
			break;
		case ERROR:
		  handlerManager.fireEvent(new LogEvent(lm.getMessage()));
		  break;
		default:
			break;
		}
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

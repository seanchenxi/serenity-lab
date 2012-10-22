package com.seanchenxi.gwt.gms.client;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.seanchenxi.gwt.gms.share.Message;
import com.seanchenxi.gwt.gms.share.MessageHandler;

/**
 * User: Xi
 * Date: 21/10/12
 */
public class MessageClient {

	private class SubscribeCallback implements AsyncCallback<String> {
		@Override
		public void onFailure(Throwable caught) {
			Log.log(Level.SEVERE, "register error", caught);
			setStarted(false);
			setClientId(null);
		}

		@Override
		public void onSuccess(String result) {
			setClientId(result);
			resetErrorCount();
			retrieve();
		}
	}
	
	private class UnSubscribeCallback implements AsyncCallback<Void> {
		@Override
		public void onFailure(Throwable caught) {
			Log.log(Level.SEVERE, "unregister error", caught);
			setStarted(false);
		}

		@Override
		public void onSuccess(Void result) {
			setClientId(null);
			setStarted(false);
			Log.log(Level.INFO, "unregistered");
		}
	}
	
    private class MessageCallback implements AsyncCallback<LinkedList<Message<MessageHandler>>> {
        @Override
        public void onFailure(Throwable caught) {
            if (decrementErrorCount() > 0) {
            	retrieve();
                Log.log(Level.WARNING, "listen error, retry", caught);
            } else {
            	stop();
                Log.log(Level.SEVERE, "listen error", caught);
            }
        }

		@Override
        public void onSuccess(LinkedList<Message<MessageHandler>> result) {
            resetErrorCount();
            Scheduler.get().scheduleIncremental(new SendMessageCommand(messageBus, result));
            retrieve();
        }
    }

    private final static Logger Log = Logger.getLogger(MessageClient.class.getName());
    
    private final static MessageClient INSTANCE = new MessageClient();

    private final MessageServiceAsync messageService;
    private final MessageBus messageBus;

    private boolean isStarted;
    private String clientId;
    private int errorCount;

    public static MessageClient getInstance() {
        return INSTANCE;
    }

    private MessageClient() {
    	messageService = GWT.create(MessageService.class);
    	messageBus = new MessageBus();
    	setStarted(false);
    	setClientId(null);
    	resetErrorCount();
    }

    public void start() {
    	messageService.subscribe(new SubscribeCallback());
    }

    public void stop() {
    	messageService.unSubscribe(clientId, new UnSubscribeCallback());
	}
    
    public <H extends MessageHandler> HandlerRegistration addMessageHandler(Event.Type<H> type, H handler) {
        try {
            return messageBus.addHandler(type, handler);
        } finally {
            if (messageBus.getRegistrationSize() == 1) {
                start();
            }
        }
    }

    public void removeAllHandlers() {
        try {
            messageBus.removeHandlers();
        } finally {
            stop();
        }
    }
    
    private void retrieve(){
    	if(isStarted)
    		messageService.retrieve(clientId, new MessageCallback());
    }
    
    private void setClientId(String clientId) {
		this.clientId = clientId;
	}
    
    private void setStarted(boolean isStarted) {
		this.isStarted = isStarted;
	}
    
    private void resetErrorCount() {
		this.errorCount = 5;
	}

    private int decrementErrorCount() {
		return (--this.errorCount);
	}
}

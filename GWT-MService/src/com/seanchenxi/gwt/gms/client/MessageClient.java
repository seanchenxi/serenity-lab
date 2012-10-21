package com.seanchenxi.gwt.gms.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.seanchenxi.gwt.gms.share.Message;
import com.seanchenxi.gwt.gms.share.MessageHandler;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: Xi
 * Date: 21/10/12
 */
public class MessageClient {

    private class MessageCallback implements AsyncCallback<LinkedList<Message<MessageHandler>>> {
        @Override
        public void onFailure(Throwable caught) {
            if (errorCount-- > 0) {

                Log.log(Level.WARNING, "listen error, retry", caught);
            } else {

                Log.log(Level.SEVERE, "listen error", caught);
            }
        }

        @Override
        public void onSuccess(LinkedList<Message<MessageHandler>> result) {
            errorCount = 5;
            Scheduler.get().scheduleIncremental(new SendMessageCommand(messageBus, result));
        }
    }

    private final static Logger Log = Logger.getLogger(MessageClient.class.getName());

    private final static MessageClient INSTANCE = new MessageClient();

    private final MessageServiceAsync messageService = GWT.create(MessageService.class);
    private final MessageBus messageBus = new MessageBus();

    private boolean isStarted = false;
    private String messageId;
    private int errorCount = 5;

    public static MessageClient getInstance() {
        return INSTANCE;
    }

    private MessageClient() {
    }

    public void start() {
        //TODO
    }

    public <H extends MessageHandler> HandlerRegistration addMessageHandler(Event.Type<H> type, H handler) {
        try {
            return messageBus.addHandler(type, handler);
        } finally {
            if (messageBus.getRegistrationSize() == 1) {
                //start
            }
        }
    }

    public void removeAllHandlers() {
        try {
            messageBus.removeHandlers();
        } finally {
            //stop
        }
    }

}

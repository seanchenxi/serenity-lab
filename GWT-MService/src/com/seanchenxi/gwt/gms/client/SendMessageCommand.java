package com.seanchenxi.gwt.gms.client;

import java.util.LinkedList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.seanchenxi.gwt.gms.share.Message;
import com.seanchenxi.gwt.gms.share.MessageHandler;

/**
 * User: Xi
 * Date: 22/10/12
 */
public class SendMessageCommand implements Scheduler.RepeatingCommand {

    private LinkedList<Message<MessageHandler>> messages;
    private MessageBus messageBus;

    public SendMessageCommand(MessageBus messageBus, LinkedList<Message<MessageHandler>> messages) {
        this.messages = messages;
        this.messageBus = messageBus;
        if (messageBus == null || messages == null)
            throw new NullPointerException();
    }

    @Override
    public boolean execute() {
        try {
            messageBus.fireEvent(messages.poll());
        } catch (Exception e) {
            if (GWT.getUncaughtExceptionHandler() != null) {
                GWT.getUncaughtExceptionHandler().onUncaughtException(e);
            }
        }
        return !messages.isEmpty();
    }

}

package com.seanchenxi.gwt.gms.server;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.seanchenxi.gwt.gms.share.Message;
import com.seanchenxi.gwt.gms.share.MessageHandler;

/**
 * User: Xi
 * Date: 22/10/12
 */
public class Subscriber {

    private final String id;
    private Queue<Message<MessageHandler>> messages;
    private long lastActivityTime;

    public Subscriber(String id) {
        this.id = id;
        this.messages = new ConcurrentLinkedQueue<Message<MessageHandler>>();
    }

    public String getId() {
		return id;
	}
    
    public long getLastActivityTime() {
		return lastActivityTime;
	}
    
    public void updateActivity() {
        lastActivityTime = System.currentTimeMillis();
    }

    public boolean addMessage(Message<MessageHandler> message) {
        return messages.offer(message);
    }

    public LinkedList<Message<MessageHandler>> retrieveMessage() {
        LinkedList<Message<MessageHandler>> list = new LinkedList<Message<MessageHandler>>();
        Message<MessageHandler> message = null;
        while ((message = messages.poll()) != null) {
            list.offer(message);
        }
        return list;
    }

}

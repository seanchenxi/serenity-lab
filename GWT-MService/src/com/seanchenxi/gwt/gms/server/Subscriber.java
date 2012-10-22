package com.seanchenxi.gwt.gms.server;

import com.seanchenxi.gwt.gms.share.Message;
import com.seanchenxi.gwt.gms.share.MessageHandler;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

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

    public void updateActivity() {
        lastActivityTime = System.currentTimeMillis();
    }


}

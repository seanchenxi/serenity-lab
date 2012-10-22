package com.seanchenxi.gwt.gms.server;

import com.seanchenxi.gwt.gms.share.Message;
import com.seanchenxi.gwt.gms.share.MessageHandler;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Logger;

/**
 * User: Xi
 * Date: 21/10/12
 */
public class MessageServer {

    private static class MessageServerHolder {
        private static final MessageServer INSTANCE = new MessageServer();
    }

    private static final Logger LOG = Logger.getLogger(MessageServer.class.getName());

    private final ConcurrentHashMap<String, Subscriber> USERS;

    private final static ScheduledExecutorService CLEANER = Executors.newScheduledThreadPool(0);
    private final static long CLEAN_TIME = 10 * 60 * 1000;

    public static MessageServer getInstance() {
        return MessageServerHolder.INSTANCE;
    }

    private MessageServer() {
        USERS = new ConcurrentHashMap<String, Subscriber>();
    }

    public String subscribe() {
        String id = UUID.randomUUID().toString();
        Subscriber subscriber = USERS.putIfAbsent(id, new Subscriber(id));
        if (subscriber == null)
            subscriber = USERS.get(id);
        return id;
    }

    public boolean unSubscribe(String id) {
        return USERS.remove(id) != null;
    }

    public LinkedList<Message<MessageHandler>> retrieveMessage(String id) {
        return USERS.get(id).retrieveMessage();
    }

    public void sendMessage(Message<MessageHandler> message) {
        Iterator<Subscriber> itS = USERS.values().iterator();
        while (itS.hasNext()) {
            itS.next().addMessage(message);
        }
    }

    public void sendMessage(String id, Message<MessageHandler> message) {
        Subscriber subscriber = USERS.get(id);
        if (subscriber != null)
            subscriber.addMessage(message);
    }

}

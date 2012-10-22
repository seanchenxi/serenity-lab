package com.seanchenxi.gwt.gms.server;

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

    private final Logger LOG;
    private final ConcurrentHashMap<String, Subscriber> USERS;

    private final static ScheduledExecutorService CLEANER = Executors.newScheduledThreadPool(0);
    private final static long CLEAN_TIME = 10 * 60 * 1000;

    public static MessageServer getInstance() {
        return MessageServerHolder.INSTANCE;
    }

    private MessageServer() {
        LOG = Logger.getLogger(MessageServer.class.getName());
        USERS = new ConcurrentHashMap<String, Subscriber>();
    }

    public String subscribe() {
        String id = UUID.randomUUID().toString();
        Subscriber subscriber = USERS.put(id, new Subscriber(id));
        if (subscriber == null)
            subscriber = USERS.get(id);
        return id;
    }


}

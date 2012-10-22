package com.seanchenxi.gwt.gms.server;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.seanchenxi.gwt.gms.share.Message;
import com.seanchenxi.gwt.gms.share.MessageHandler;

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
        initTimeOutCleaner();
    }

    private void initTimeOutCleaner() {
    	CLEANER.scheduleAtFixedRate(new Runnable() {			
			@Override
			public void run() {
				try{
					long now = System.currentTimeMillis();
					Iterator<Subscriber> idS = USERS.values().iterator();
					while(idS.hasNext()){
						Subscriber subscriber = idS.next();
						if((now - subscriber.getLastActivityTime()) > CLEAN_TIME){
							idS.remove();
							LOG.info(subscriber.getId() + " cleaned");
						}
					}
				}catch (Exception e) {
				  LOG.log(Level.SEVERE, "clean error", e);
				}
			}
		}, CLEAN_TIME, 10, TimeUnit.MINUTES);
	}

	public String addSubscriber() {
        String id = UUID.randomUUID().toString();
        Subscriber subscriber = USERS.putIfAbsent(id, new Subscriber(id));
        if (subscriber == null)
            subscriber = USERS.get(id);
        return id;
    }

    public boolean removeSubscriber(String id) {
        return USERS.remove(id) != null;
    }

    public LinkedList<Message<MessageHandler>> retrieveMessage(String id) {
        return USERS.get(id).retrieveMessage();
    }

    public int sendMessage(Message<MessageHandler> message) {
        Iterator<Subscriber> itS = USERS.values().iterator();
        int count = 0;
        while (itS.hasNext()) {
            if(itS.next().addMessage(message)){
            	count++;
            }
        }
        return count;
    }

    public boolean sendMessage(String id, Message<MessageHandler> message) {
        Subscriber subscriber = USERS.get(id);
        if (subscriber != null)
            return subscriber.addMessage(message);
        return false;
    }

}

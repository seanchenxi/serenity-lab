package com.seanchenxi.logging.monitor.server;

import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.seanchenxi.logging.monitor.client.service.MessageService;
import com.seanchenxi.logging.monitor.shared.LogMessage;
import com.seanchenxi.logging.monitor.shared.LogMessage.Type;

@SuppressWarnings("serial")
public class MessageServiceImpl extends RemoteServiceServlet implements MessageService, LogHandler {

	private final static Logger Log = Logger.getLogger(MessageServiceImpl.class.getName());
	private final static ConcurrentHashMap<String, ConcurrentLinkedQueue<LogMessage>> MAP = new ConcurrentHashMap<String, ConcurrentLinkedQueue<LogMessage>>();
	private final static ConcurrentHashMap<String, Long> TIME_MAP = new ConcurrentHashMap<String, Long>();
	
	private final static ScheduledExecutorService CLEANER = Executors.newScheduledThreadPool(0);	
	private final static long CLEAN_TIME = 30 * 60 * 1000;
	
	private LogReader reader;
	private String filePath;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		filePath = getServletContext().getInitParameter("logPath");
		CLEANER.scheduleAtFixedRate(new Runnable() {			
			@Override
			public void run() {
				try{
					long now = System.currentTimeMillis();
					Iterator<String> idIt = TIME_MAP.keySet().iterator();
					while(idIt.hasNext()){
						String id = idIt.next();
						if((now - TIME_MAP.get(id)) > CLEAN_TIME){
							MAP.remove(id);
							idIt.remove();
							Log.info(id + " cleaned");
						}
					}
					if(MAP.isEmpty() && reader != null){
						reader.stop();
					}
				}catch (Exception e) {
				  Log.log(Level.SEVERE, "clean error", e);
				}
			}
		}, CLEAN_TIME, 10, TimeUnit.MINUTES);
	}
	
	@Override
	public LogMessage listen(String messageId) {
		try{
			TIME_MAP.put(messageId, System.currentTimeMillis());
			LogMessage lm = poll(MAP.get(messageId));
			if(lm == null){
				int count = 0;
				do{
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					  Log.log(Level.SEVERE, "listen error", e);
					}
					lm = poll(MAP.get(messageId));
					count ++;
				}while(lm == null && count < 15);
			}
			return lm;
		}finally{
			TIME_MAP.put(messageId, System.currentTimeMillis());
		}
	}

	@Override
	public String register() {
		String id = UUID.randomUUID().toString();
		MAP.put(id, new ConcurrentLinkedQueue<LogMessage>());
		TIME_MAP.put(id, System.currentTimeMillis());
		Log.info(id + " registered");
		if(!MAP.isEmpty()){
			if(reader == null){
			  reader = new LogReader(filePath, this);
			}
			reader.start();
			Log.info("LogReader started for file " + filePath);
		}
		return id;
	}

	@Override
	public boolean unregister(String messageId) {
		try{
			return null != MAP.remove(messageId) && null != TIME_MAP.remove(messageId);
		}finally{
			Log.info(messageId + " unregistered");
			if(MAP.isEmpty() && reader != null){
				reader.stop();
				Log.info("LogReader stoped");
			}
		}
	}

	@Override
	public void onRotated() {
		LogMessage lm = new LogMessage(Type.ROTATED, null);
		Iterator<String> idIt = MAP.keySet().iterator();
		while(idIt.hasNext()){
			String id = idIt.next();
			try{
				offer(MAP.get(id), lm);
			}catch (Exception e) {
				Log.log(Level.SEVERE, "onRotated - offer " + lm + " to " + id + " error", e);
			}
		}
	}

	@Override
	public void onLog(String log) {
		LogMessage lm = new LogMessage(Type.LOG, log);
		Iterator<String> idIt = MAP.keySet().iterator();
		while(idIt.hasNext()){
			String id = idIt.next();
			try{
				offer(MAP.get(id), lm);
			}catch (Exception e) {
				Log.log(Level.SEVERE, "onLog - offer " + lm + " to " + id + " error", e);
			}
		}
	}
	
	private LogMessage poll(ConcurrentLinkedQueue<LogMessage> msgs){
		return msgs == null ? null : msgs.poll();
	}

	private void offer(ConcurrentLinkedQueue<LogMessage> msgs, LogMessage lm){
		if(msgs != null && lm != null) msgs.offer(lm);
	}

  @Override
  public void onError(String error) {
    LogMessage lm = new LogMessage(Type.ERROR, error);
    Iterator<String> idIt = MAP.keySet().iterator();
    while(idIt.hasNext()){
      String id = idIt.next();
      try{
        offer(MAP.get(id), lm);
      }catch (Exception e) {
        Log.log(Level.SEVERE, "onError - offer " + lm + " to " + id + " error", e);
      }
    }
  }
}

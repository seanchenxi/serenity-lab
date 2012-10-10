package com.seanchenxi.logging.monitor.server;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;


public class LogReader implements TailerListener {
	
	private static final Logger Log = Logger.getLogger(LogReader.class.getName());	
	private static final long DELAY = 1000;
	
	private ExecutorService executor;
	private Tailer tailer;
	
	private final LogHandler handler;
	private final String filePath;
	
	public LogReader(String filePath, LogHandler handler){
		this.handler = handler;
		this.filePath = filePath;
	}
	
	public void start(){
		if(tailer == null){
		  tailer = new Tailer(new File(filePath), this, DELAY, true);
		}else{
		  Log.info("LogReader already started.");
		}
	}
	
	public void stop(){
		if(tailer != null)
			tailer.stop();
		if(executor == null)
			return;
		if(!executor.isShutdown())
			executor.shutdown();
		while(!executor.isTerminated()){
			try {
				executor.awaitTermination(1, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
			  //do nothing
			}
		}
		tailer= null;
		executor = null;
	}

	@Override
	public final void fileNotFound() {
		Log.log(Level.SEVERE, "log file " + filePath + " not found");
		executor.submit(new Runnable() {
      @Override
      public void run() {
        try{
          handler.onError("ERROR: log file " + filePath + " not found");
        }catch(Exception e){
          handle(e);
        }
      }
    });
		stop();
	}

	@Override
	public final void fileRotated() {
		executor.submit(new Runnable() {
			@Override
			public void run() {
				try{
					handler.onRotated();
				}catch(Exception e){
					handle(e);
				}
			}
		});
	}

	@Override
	public final void handle(final String arg0) {
		if(arg0 == null || arg0.isEmpty()) return;
		executor.submit(new Runnable() {
			@Override
			public void run() {
				try{
					handler.onLog(arg0);
				}catch(Exception e){
					handle(e);
				}
			}
		});	
	}

	@Override
	public final void handle(final Exception arg0) {
		Log.log(Level.SEVERE,"handle exception",arg0);
    executor.submit(new Runnable() {
      @Override
      public void run() {
        try{
          handler.onError("ERROR: " + arg0 == null ? "unknown" : arg0.getMessage());
        }catch(Exception e){
          Log.log(Level.SEVERE,"handle exception",e);
        }
      }
    });
	}

	@Override
	public final void init(Tailer tailer) {
	  Log.info("LogReader initialized");
	  if(executor == null){
	    executor = Executors.newFixedThreadPool(2);
	  }
	  if(!executor.isShutdown()){
	    executor.execute(tailer);
	  }
	}
	
}

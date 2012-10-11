package com.seanchenxi.logging.monitor.server;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;

public class LogReader implements TailerListener {

	private static final Logger Log = Logger.getLogger(LogReader.class.getName());
	private static final ExecutorService POOL = Executors.newFixedThreadPool(2);
	private static final long DELAY = 1000;
	
	private final Object lock;
	private final LogHandler handler;
	private final String filePath;
	
	private Tailer tailer;

	public LogReader(String filePath, LogHandler handler) {
		this.lock = new Object();
		this.handler = handler;
		this.filePath = filePath;
	}

	public void start() {
		synchronized (lock) {
			File file = new File(filePath);
			if(!file.exists()){
				fileNotFound();
			}else if (tailer == null) {
				tailer = Tailer.create(file, this, DELAY, true);
			} else {
				Log.info("LogReader already started.");
			}
		}
	}

	public void stop() {
		synchronized (lock) {
			if (tailer != null)
				tailer.stop();
			tailer = null;
		}
	}

	@Override
	public final void fileNotFound() {
		Log.log(Level.SEVERE, "log file " + filePath + " not found");
		handler.onError("ERROR: log file " + filePath + " not found");
		stop();
	}

	@Override
	public final void fileRotated() {
		POOL.submit(new Runnable() {
			@Override
			public void run() {
				try {
					handler.onRotated();
				} catch (Exception e) {
					handle(e);
				}
			}
		});
	}

	@Override
	public final void handle(final String arg0) {
		if (arg0 == null || arg0.isEmpty())
			return;
		POOL.submit(new Runnable() {
			@Override
			public void run() {
				try {
					handler.onLog(arg0);
				} catch (Exception e) {
					handle(e);
				}
			}
		});
	}

	@Override
	public final void handle(final Exception arg0) {
		Log.log(Level.SEVERE, "handle exception", arg0);
		POOL.submit(new Runnable() {
			@Override
			public void run() {
				try {
					handler.onError("ERROR: " + arg0 == null ? "unknown" : arg0.getMessage());
				} catch (Exception e) {
					Log.log(Level.SEVERE, "handle exception", e);
				}
			}
		});
	}

	@Override
	public final void init(Tailer tailer) {
		Log.info("LogReader initialized");
		POOL.execute(tailer);
	}

}

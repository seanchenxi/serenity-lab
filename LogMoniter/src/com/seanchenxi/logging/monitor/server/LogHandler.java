package com.seanchenxi.logging.monitor.server;


public interface LogHandler {

	void onRotated();

	void onLog(String log);
	
	void onError(String error);
	
}

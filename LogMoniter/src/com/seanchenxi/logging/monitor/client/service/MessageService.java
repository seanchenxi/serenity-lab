package com.seanchenxi.logging.monitor.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.seanchenxi.logging.monitor.shared.LogMessage;

@RemoteServiceRelativePath("reader")
public interface MessageService extends RemoteService {
	
	LogMessage listen(String messageId);

	String register();

	boolean unregister(String messageId);
	
}

package com.seanchenxi.logging.monitor.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.seanchenxi.logging.monitor.shared.LogMessage;

public interface MessageServiceAsync {
	
	void listen(String messageId, AsyncCallback<ArrayList<LogMessage>> callback);

	void register(AsyncCallback<String> callback);

	void unregister(String messageId, AsyncCallback<Boolean> callback);
	
}

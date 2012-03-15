package com.seanchenxi.gwt.wordpress.json.api;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.seanchenxi.gwt.wordpress.json.api.model.JModel;


public interface JRequestBuilder {

	<M extends JModel> JRequest requestObject(JRequestURL url, AsyncCallback<M> callback);

	void setServicePath(String servicePath);

	void setTimeout(int timeout);
	
}

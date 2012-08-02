package com.seanchenxi.resteasy.autobean.client;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.google.web.bindery.autobean.shared.Splittable;
import com.google.web.bindery.autobean.shared.impl.StringQuoter;

class RESTCallbackAdapter<T> implements RequestCallback {

	private final AsyncCallback<T> callback;
	private final String uri;
	private final Class<T> clazz;

	public RESTCallbackAdapter(String uri, Class<T> clazz,
			AsyncCallback<T> callback) {
		assert (callback != null);
		this.callback = callback;
		this.uri = uri;
		this.clazz = clazz;
	}

	@Override
	public void onError(Request request, Throwable exception) {
		callback.onFailure(exception);
	}

	@Override
	public void onResponseReceived(Request request, Response response) {
		T result = null;
		Throwable caught = null;
		try {
			String encodedResponse = response.getText();
			int statusCode = response.getStatusCode();
			if (statusCode != Response.SC_OK) {
				if(statusCode != Response.SC_NO_CONTENT || !Void.class.equals(clazz)){
					caught = new StatusCodeException(statusCode, encodedResponse);
				}
			}else if (encodedResponse == null) {
				caught = new InvocationException("No response payload from " + uri);
			}else{
				Splittable data = StringQuoter.split(encodedResponse);
				if(REST.isThrowable(data)){
					caught = REST.decodeThrowable(data);
				}else if(data != null){
					result = (T) REST.decodeData(clazz, data);
				}
			}
		} catch (SerializationException e) {
			caught = new IncompatibleRemoteServiceException("The response: \n{"
					+ response.getText() + "}\n could not be deserialized", e);
		} catch (Throwable e) {
			caught = e;
		}
		
		if (caught != null) {
			callback.onFailure(caught);
		} else {
			callback.onSuccess(result);
		}
	}
	
}

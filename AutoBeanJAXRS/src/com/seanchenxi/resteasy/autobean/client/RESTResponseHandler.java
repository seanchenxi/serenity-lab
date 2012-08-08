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
import com.seanchenxi.resteasy.autobean.share.RESTResponse;

class RESTResponseHandler<T> implements RequestCallback {

	private final String fullServiceName;
	private final String uri;
	private final Class<T> clazz;
	private final AsyncCallback<T> callback;

	public RESTResponseHandler(String fullServiceName, String uri, Class<T> clazz,
			AsyncCallback<T> callback) {
		assert (callback != null);
		assert (clazz != null);
		this.callback = callback;
		this.uri = uri;
		this.clazz = clazz;
		this.fullServiceName = fullServiceName;
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
		  RESTResponse restResponse = REST.decodeResponse(response.getText());
			int statusCode = response.getStatusCode();
			if (statusCode != Response.SC_OK) {
				caught = new StatusCodeException(statusCode, response.getText());
			}else if (restResponse == null) {
				caught = new InvocationException("No response payload from " + uri + " of service " + fullServiceName);
			}else if(REST.isReturnValue(restResponse)){
			  if(!Void.class.equals(clazz)){
			    Splittable data = StringQuoter.split(restResponse.getPayload());
			    if(data != null)  result = (T) REST.decodeData(clazz, data);
			  }
			}else if(REST.isThrowable(restResponse)){
			  caught = REST.decodeThrowable(restResponse.getPayload());
			}else{
			  caught = new InvocationException(restResponse + " from " + uri + " of service " + fullServiceName);
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

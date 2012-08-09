package com.seanchenxi.resteasy.autobean.client;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class RESTResponseHandler<T> implements RequestCallback {
  
  private String resourceName;
  private Class<T> clazz;
  private AsyncCallback<T> callback;
  
  public final void setResourceName(String resourceName) {
    this.resourceName = resourceName;
  }
  
  public final void setCallback(Class<T> clazz, AsyncCallback<T> callback) {
    assert (clazz != null && callback != null) : "the call back and his class type shouldn't be null";
    this.callback = callback;
    this.clazz = clazz;
  }

  @Override
  public final void onResponseReceived(Request request, Response response) {
    assert (clazz != null && callback != null) : "call RESTResponseHandler<T>.setCallback(Class<T> clazz, AsyncCallback<T> callback) before send request";
    handleResponse(response, clazz, callback, resourceName);
  }

  @Override
  public final void onError(Request request, Throwable exception) {
    if(callback != null) callback.onFailure(exception);
  }
  
  protected abstract void handleResponse(Response response, Class<T> clazz, AsyncCallback<T> callback, String resourceName);
  
}

package com.seanchenxi.resteasy.autobean.client;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.InvocationException;

public abstract class RESTServiceProxy {

  protected RESTServiceProxy(){}
  
  protected <T> Request invoke(RESTRequest<T> builder, Class<T> clazz, AsyncCallback<T> callback){
    assert builder != null : "create RESTRequestBuilder at first";
    if(callback == null) throw new IllegalArgumentException("The AsyncCallback in " + builder.getResourceName() + " is null !");
    try {
      return builder.execute(clazz, callback);
    } catch (RequestException ex) {
      InvocationException iex = new InvocationException(
          "Unable to initiate the asynchronous service invocation (" +
              builder.getResourceName() + ") -- check the network connection",
          ex);
      callback.onFailure(iex);
    }
    return null;
  }
  
}

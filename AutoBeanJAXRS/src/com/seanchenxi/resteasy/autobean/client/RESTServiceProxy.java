package com.seanchenxi.resteasy.autobean.client;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.InvocationException;

public abstract class RESTServiceProxy {

  protected RESTServiceProxy(){
    
  }
  
  protected <T> Request invoke(String fullServiceName, RESTRequest builder, Class<T> clazz, AsyncCallback<T> callback){
    if(callback == null) 
      throw new IllegalArgumentException("The AsyncCallback in "+fullServiceName+" shouldn't be null !");
    assert builder != null : "create RESTRequestBuilder at first";
    try {
      return builder.execute(fullServiceName, clazz, callback);
    } catch (RequestException ex) {
      InvocationException iex = new InvocationException(
          "Unable to initiate the asynchronous service invocation (" +
              fullServiceName + ") -- check the network connection",
          ex);
      callback.onFailure(iex);
    }
    return null;
  }
  
}

package com.seanchenxi.resteasy.autobean.test.client;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.seanchenxi.resteasy.autobean.client.REST;
import com.seanchenxi.resteasy.autobean.share.RESTResponse;
import com.seanchenxi.resteasy.autobean.test.share.BeanFactory;
import com.seanchenxi.resteasy.autobean.test.share.Greeting;

public class GreetingServiceAsyncM {

  private final BeanFactory factory;
  
  public GreetingServiceAsyncM(BeanFactory factory){
    this.factory = factory;
  }
  
  void greetServerObject(String name, boolean ok, AsyncCallback<Greeting> callback){
    RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, "/rest/greetM/serverInfoObject/"+name+"-"+ok);
    builder.setHeader("Content-Type", "application/json; charset=utf-8");
    builder.setHeader("Accept", "application/json");
    builder.setCallback(new ResponseHandler(Greeting.class, callback));
    try {
      builder.send();
    } catch (RequestException ex) {
      InvocationException iex = new InvocationException(
          "Unable to initiate the asynchronous service invocation ( greetServerObject ) -- check the network connection",
          ex);
      callback.onFailure(iex);
    }
  }
  
  class ResponseHandler implements RequestCallback{
    
    private Class<Greeting> clazz;
    private AsyncCallback<Greeting> callback;
    
    public ResponseHandler(Class<Greeting> clazz, AsyncCallback<Greeting> callback) {
      this.callback = callback;
      this.clazz = clazz;
    }

    @Override
    public void onResponseReceived(Request request, Response response) {
      Greeting result = null;
      Throwable caught = null;
      try {
        RESTResponse restResponse = REST.decodeResponse(response.getText());
        int statusCode = response.getStatusCode();
        if (statusCode != Response.SC_OK) {
          caught = new StatusCodeException(statusCode, response.getText());
        }else if (restResponse == null) {
          caught = new InvocationException("No response payload ");
        }else{
          // should treat exception return and primitive types
          result = AutoBeanCodex.decode(factory, clazz, response.getText()).as();
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

    @Override
    public void onError(Request request, Throwable exception) {
      callback.onFailure(exception);
    }
    
  }
}

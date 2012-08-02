package com.seanchenxi.resteasy.autobean.client;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.SerializationException;

public class RESTRequest {
  
  public static final String CONTENT_TYPE_HEADER = "Content-Type";
  public static final String ACCEPT_HEADER = "Accept";
  private final RequestBuilder builder;
  
  public RESTRequest(String uri){
    this(RequestBuilder.GET, uri);
  }
  
  public RESTRequest(Method httpMethod, String uri){
    builder = new RequestBuilder(httpMethod, uri);
    setContentType(REST.JSON_CONTENT_TYPE_UTF8);
    setAccepts(REST.ACCEPT_JSON);
  }
  
  /**
   *  Executes the request with all the information set in the current object. The value is never returned but passed to the optional argument callback. 
   * @param clazz 
   */
  public <T> void execute(Class<T> clazz, AsyncCallback<T> callback){
    if(callback != null){
    	try {
    		builder.setCallback(new RESTCallbackAdapter<T>(builder.getUrl(), clazz, callback));
			builder.send();
		} catch (RequestException e) {
			callback.onFailure(e);
		}
    }
  }
  
  /**
   * Sets the Accept request header. Defaults to application/json.
   */
  public RESTRequest setAccepts(String acceptHeader){
    builder.setHeader(ACCEPT_HEADER, acceptHeader);
    return this;
  }
   
   /**
    * Sets the request credentials.
    */
  public RESTRequest setCredentials(String username, String password){
    builder.setUser(username);
    builder.setPassword(password);
    return this;
  }
  
  /**
   *  Sets the request entity.
   */
  public RESTRequest setRequestData(Object...requestData){
    try {
      if(requestData != null && requestData.length > 0)
      builder.setRequestData(REST.encodeRequestData(requestData));
    } catch (SerializationException e) {
      e.printStackTrace(System.err);
    }
    return this;
  }
  
  /**
   * Sets the Content-Type request header.
   */
  public RESTRequest setContentType(String contentTypeHeader){
    builder.setHeader(CONTENT_TYPE_HEADER, contentTypeHeader);
    return this;
  }  
  
  /**
   * Sets the given cookie in the current document when executing the request. Beware that this will be persistent in your browser.
   */
  public RESTRequest addCookie(String name, String value){
    Cookies.setCookie(name, value);
    return this;
  } 
  
  /**
   * Adds a query parameter to the URI query part.
   */
  public RESTRequest addQueryParameter(String name, String value){
    throw new UnsupportedOperationException();
  } 
  
  /**
   * Adds a matrix parameter (path parameter) to the last path segment of the request URI.
   */
  public RESTRequest addMatrixParameter(String name, String value){
    throw new UnsupportedOperationException();
  } 
  
  /**
   * Adds a request header. 
   */
  public RESTRequest addHeader(String name, String value){
    builder.setHeader(name, value);
    return this;
  }  
  
}

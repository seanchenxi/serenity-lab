package com.seanchenxi.resteasy.autobean.client;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;

public final class RESTRequest<T> {
  
  public static final String CONTENT_TYPE_HEADER = "Content-Type";
  public static final String ACCEPT_HEADER = "Accept";

  private final RequestBuilder builder;
  private final String resourceName;
  
  public RESTRequest(String uri, String resourceName){
    this(RequestBuilder.GET, uri, resourceName);
  }
  
  public RESTRequest(Method httpMethod, String uri, String resourceName){
    this.resourceName = resourceName;
    this.builder = new RequestBuilder(httpMethod, uri);
    setContentType(REST.JSON_CONTENT_TYPE_UTF8);
    setAccepts(REST.ACCEPT_JSON);
  }
  
  public String getResourceName() {
    return resourceName;
  }
  
  /**
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  public RESTResponseHandler<T> getResponseHandler(){
    return builder.getCallback() == null ? null : (RESTResponseHandler<T>) builder.getCallback();
  }
  
  /**
   * 
   * @param responseHandler
   * @return
   */
  public RESTRequest<T> setResponseHandler(RESTResponseHandler<T> responseHandler) {
    builder.setCallback(responseHandler);
    return this;
  }
  
  /**
   *  Executes the request with all the information set in the current object. The value is never returned but passed to the optional argument callback. 
   * @param clazz 
   * @return 
   * @throws RequestException 
   */
  public Request execute(Class<T> clazz, AsyncCallback<T> callback) throws RequestException{
    RESTResponseHandler<T> responseHandler = getResponseHandler();
    if(null == responseHandler) setResponseHandler(responseHandler = new BaseResponseHandler<T>(builder.getUrl()));
    responseHandler.setCallback(clazz, callback);
    responseHandler.setResourceName(resourceName);
    return builder.send();
  }
  
  /**
   * Sets the Accept request header. Defaults to application/json.
   */
  public RESTRequest<T> setAccepts(String acceptHeader){
    builder.setHeader(ACCEPT_HEADER, acceptHeader);
    return this;
  }
   
   /**
    * Sets the request credentials.
    */
  public RESTRequest<T> setCredentials(String username, String password){
    builder.setUser(username);
    builder.setPassword(password);
    return this;
  }
  
  /**
   *  Sets the request entity.
   */
  public RESTRequest<T> setRequestData(String requestData){
    builder.setRequestData(requestData);
    return this;
  }
  
  /**
   * Sets the Content-Type request header.
   */
  public RESTRequest<T> setContentType(String contentTypeHeader){
    builder.setHeader(CONTENT_TYPE_HEADER, contentTypeHeader);
    return this;
  }  
  
  /**
   * Sets the given cookie in the current document when executing the request. Beware that this will be persistent in your browser.
   */
  public RESTRequest<T> addCookie(String name, String value){
    Cookies.setCookie(name, value);
    return this;
  } 
  
  /**
   * Adds a query parameter to the URI query part.
   */
  public RESTRequest<T> addQueryParameter(String name, String value){
    throw new UnsupportedOperationException();
  } 
  
  /**
   * Adds a matrix parameter (path parameter) to the last path segment of the request URI.
   */
  public RESTRequest<T> addMatrixParameter(String name, String value){
    throw new UnsupportedOperationException();
  } 
  
  /**
   * Adds a request header. 
   */
  public RESTRequest<T> addHeader(String name, String value){
    builder.setHeader(name, value);
    return this;
  }
  
}

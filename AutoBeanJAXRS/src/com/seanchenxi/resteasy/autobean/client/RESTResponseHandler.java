/*******************************************************************************
 * Copyright 2012 Xi CHEN
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
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

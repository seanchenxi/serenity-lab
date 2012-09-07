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

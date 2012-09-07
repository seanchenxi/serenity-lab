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

import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.google.web.bindery.autobean.shared.Splittable;
import com.google.web.bindery.autobean.shared.impl.StringQuoter;
import com.seanchenxi.resteasy.autobean.share.RESTResponse;

class BaseResponseHandler<T> extends RESTResponseHandler<T> {

	private final String uri;

	public BaseResponseHandler(String uri) {
	  super();
		this.uri = uri;
	}
	
	@Override
	protected void handleResponse(Response response, Class<T> clazz, AsyncCallback<T> callback, String resourceName) {
    T result = null;
    Throwable caught = null;
    try {
      RESTResponse restResponse = REST.decodeResponse(response.getText());
      int statusCode = response.getStatusCode();
      if (statusCode != Response.SC_OK) {
        caught = new StatusCodeException(statusCode, response.getText());
      }else if (restResponse == null) {
        caught = new InvocationException("No response payload from " + uri + " of resource " + resourceName);
      }else if(REST.isReturnValue(restResponse)){
        if(!Void.class.equals(clazz)){
          Splittable data = StringQuoter.split(restResponse.getPayload());
          if(data != null)  result = (T) REST.decodeData(clazz, data);
        }
      }else if(REST.isThrowable(restResponse)){
        caught = REST.decodeThrowable(restResponse.getPayload());
      }else{
        caught = new InvocationException(restResponse + " from " + uri + " of resource " + resourceName);
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

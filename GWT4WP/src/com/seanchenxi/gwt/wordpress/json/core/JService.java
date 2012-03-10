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
package com.seanchenxi.gwt.wordpress.json.core;

import java.util.logging.Logger;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.jsonp.client.JsonpRequest;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.seanchenxi.gwt.wordpress.json.api.JRequest;
import com.seanchenxi.gwt.wordpress.json.api.model.JModel;

public abstract class JService {
  
  private static final Logger Log = Logger.getLogger(JService.class.getName());
  
  private Dictionary jServiceConfig;
  private JsonpRequestBuilder builder;
  private String jServicePath;

  protected JService() {
    builder = new JsonpRequestBuilder();
    initialization();
  }
  
  protected final <M extends JModel> JRequest request(RequestURL url, AsyncCallback<M> callback) {
    if (url == null || callback == null)
      throw new NullPointerException();
    JsonpRequest<JavaScriptObject> req = null;
    String urlString = url.create(jServicePath);
    Log.finest("encode " + url.getMethod() + " call: " + urlString);
    req = builder.requestObject(urlString, new JAsyncCallback<M>(callback));
    return new JRequestImpl(req);
  }
  
  private void initialization(){
    try {
      jServiceConfig = Dictionary.getDictionary("WPJsonAPIConfig");
      jServicePath = jServiceConfig.get("servicePath").trim();
      int timeout = Integer.parseInt(jServiceConfig.get("requestTimeout").trim());
      builder.setTimeout(timeout);
      Log.info("initialized the service path with WPJsonAPIConfig: " + jServicePath + ", Request Timeout: " + timeout + "ms.");
    } catch (Exception e) {
      jServiceConfig = null;
      jServicePath = "/api";
      builder.setTimeout(20000);
      Log.info("initialized the service path with defaut value: " + jServicePath + ", Request Timeout: 20000ms.");
    }
  }

  private class JAsyncCallback<M extends JModel> implements AsyncCallback<JavaScriptObject> {

    private AsyncCallback<M> callback;

    private JAsyncCallback(AsyncCallback<M> callback) {
      this.callback = callback;
    }

    @Override
    public void onFailure(Throwable caught) {
      if (callback != null)
        callback.onFailure(caught);
    }

    @Override
    public void onSuccess(JavaScriptObject result) {
      if (callback != null && result != null) {
        JResponse<M> resp = new JResponse<M>(result);
        switch (resp.getStatus()) {
          case OK:
          case PENDING:
            callback.onSuccess(resp.getResult());
            break;
          case ERROR:
            callback.onFailure(new Throwable(resp.getError()));
            break;
          case UNKNOW:
            callback.onFailure(new Throwable(resp.toString()));
            break;
        }
      } else {
        callback.onFailure(new Throwable("The returned result is null !"));
      }
    }

  }

}

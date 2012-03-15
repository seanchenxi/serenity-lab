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
package com.seanchenxi.gwt.wordpress.json.core.service;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.seanchenxi.gwt.wordpress.json.api.JRequest;
import com.seanchenxi.gwt.wordpress.json.api.JRequestBuilder;
import com.seanchenxi.gwt.wordpress.json.api.model.JModel;
import com.seanchenxi.gwt.wordpress.json.core.JRequestURLImpl;
import com.seanchenxi.gwt.wordpress.json.util.Configuration;

public abstract class JService {
  
  private static final Logger Log = Logger.getLogger(JService.class.getName());
  
  private JRequestBuilder builder = GWT.create(JRequestBuilder.class);

  protected JService() {
    String jServicePath = Configuration.getInstance().getServicePath();
    int timeout = Configuration.getInstance().getRequestTimeoutTime();
    builder.setServicePath(jServicePath);
    builder.setTimeout(timeout);
    Log.info("initialized with service path: " + jServicePath + ", and Request Timeout: " + timeout + "ms.");
  }
  
  protected final <M extends JModel> JRequest request(JRequestURLImpl url, AsyncCallback<M> callback) {
    return builder.requestObject(url, callback);
  }
  
}

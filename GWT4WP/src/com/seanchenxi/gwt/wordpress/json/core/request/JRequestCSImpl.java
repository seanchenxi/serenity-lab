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
package com.seanchenxi.gwt.wordpress.json.core.request;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.jsonp.client.JsonpRequest;
import com.seanchenxi.gwt.wordpress.json.api.JRequest;

class JRequestCSImpl implements JRequest {
	
  private JsonpRequest<JavaScriptObject> requset;

  JRequestCSImpl(JsonpRequest<JavaScriptObject> requset) {
    this.requset = requset;
  }

  @Override
  public void cancel() {
    if (requset != null)
      requset.cancel();
  }
  
}
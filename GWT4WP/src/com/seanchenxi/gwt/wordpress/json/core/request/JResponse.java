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
import com.google.gwt.json.client.JSONObject;

class JResponse<M extends JavaScriptObject> extends JSONObject {

  enum Status {

    UNKNOW(""), OK("ok"), ERROR("error"), PENDING("pending");

    private String val;

    private Status(String val) {
      this.val = val;
    }

    public static Status parseValue(String value) {
      for (Status state : values()) {
        if (state.val.equalsIgnoreCase(value)) {
          return state;
        }
      }
      return UNKNOW;
    }
  }

  JResponse(JavaScriptObject result) {
    super(result);
  }

  final Status getStatus() {
    return Status.parseValue(get("status").isString().stringValue());
  }

  final String getError() {
    return get("error").isString().stringValue();
  }

  final M getResult() {
    if (containsKey("post")) {
      return get("post").isObject().getJavaScriptObject().cast();
    }
    if (containsKey("page")) {
      return get("page").isObject().getJavaScriptObject().cast();
    }
    return getJavaScriptObject().cast();
  }

}

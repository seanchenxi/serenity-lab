package com.seanchenxi.gwt.wordpress.json.core;

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

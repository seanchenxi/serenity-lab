package com.seanchenxi.gwt.wordpress.json.core;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.jsonp.client.JsonpRequest;
import com.seanchenxi.gwt.wordpress.json.api.JRequest;

class JRequestImpl implements JRequest {
  private JsonpRequest<JavaScriptObject> requset;

  JRequestImpl(JsonpRequest<JavaScriptObject> requset) {
    this.requset = requset;
  }

  @Override
  public void cancel() {
    if (requset != null)
      requset.cancel();
  }
}
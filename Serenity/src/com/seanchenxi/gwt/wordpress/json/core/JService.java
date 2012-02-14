package com.seanchenxi.gwt.wordpress.json.core;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.jsonp.client.JsonpRequest;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.seanchenxi.gwt.logging.api.Log;
import com.seanchenxi.gwt.wordpress.json.api.model.JModel;
import com.seanchenxi.gwt.wordpress.json.api.service.JRequest;

abstract class JService {

  private Dictionary jServiceConfig;
  private JsonpRequestBuilder builder;
  private String jServicePath;

  protected JService() {
    builder = new JsonpRequestBuilder();
    builder.setTimeout(20000);
    try {
      jServiceConfig = Dictionary.getDictionary("WPJsonAPIConfig");
      jServicePath = jServiceConfig.get("servicePath").trim();
      Log.info("[JService] initialized the service path with WPJsonAPIConfig : " + jServicePath);
    } catch (Exception e) {
      jServiceConfig = null;
      jServicePath = "/api";
      Log.info("[JService] initialized the service path with defaut value : " + jServicePath);
    }
  }

  protected String getServicePath() {
    return jServicePath;
  }

  protected final <M extends JModel> JRequest request(RequestURL url, AsyncCallback<M> callback) {
    if (url == null || callback == null)
      throw new NullPointerException();
    JsonpRequest<JavaScriptObject> req = null;
    req = builder.requestObject(encode(url), new JAsyncCallback<M>(callback));
    return new JRequestImpl(req);
  }

  private String encode(RequestURL url) {
    String encodedUrl = null;
    try {
      return encodedUrl = URL.encode(url.create(getServicePath()));
    } finally {
      Log.finest("[" + this.getSimpleClassName() + "] - " + url.getMethod() + " -> " + encodedUrl);
    }
  }

  private String getSimpleClassName() {
    String[] name = this.getClass().getName().split("\\.");
    if (name != null && name.length > 0) {
      return name[name.length - 1];
    }
    return "JService";
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

  private class JRequestImpl implements JRequest {
    private JsonpRequest<JavaScriptObject> requset;

    private JRequestImpl(JsonpRequest<JavaScriptObject> requset) {
      this.requset = requset;
    }

    @Override
    public void cancel() {
      if (requset != null)
        requset.cancel();
    }
  }

}

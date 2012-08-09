package com.seanchenxi.resteasy.autobean.test.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.seanchenxi.resteasy.autobean.test.share.Greeting;

public interface GreetingServiceAsync {
  void greetServer(String name, boolean ok, AsyncCallback<String> callback);
  void greetServerObject(String name, boolean ok, AsyncCallback<Greeting> callback);
}

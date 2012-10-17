package com.seanchenxi.gwt.deobfuscator.client;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DeobfuscatorServiceAsync {
  void deobfuscate(String moduleName, String strongName, String trace, AsyncCallback<String> callback);
  void getAvailableModules(AsyncCallback<HashMap<String, HashMap<String, String>>> callback);
  void setModuleSymbolMapPath(String path, AsyncCallback<HashMap<String, HashMap<String, String>>> callback);
}

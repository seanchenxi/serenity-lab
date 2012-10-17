package com.seanchenxi.gwt.deobfuscator.client;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("deobfuscate")
public interface DeobfuscatorService extends RemoteService {
	
	String deobfuscate(String moduleName, String strongName, String trace);

	HashMap<String, HashMap<String, String>> getAvailableModules();

	HashMap<String, HashMap<String, String>> setModuleSymbolMapPath(String path);
	
}

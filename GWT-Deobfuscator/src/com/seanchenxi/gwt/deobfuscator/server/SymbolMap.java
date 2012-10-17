package com.seanchenxi.gwt.deobfuscator.server;

import java.util.HashMap;

@SuppressWarnings("serial")
class SymbolMap extends HashMap<String, String> {
	
	private final String strongName;
	private String userAgent = null;
	
	public SymbolMap(String strongName){
		this.strongName = strongName;
	}
	
	public String getStrongName() {
		return strongName;
	}
	
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	} 
	
	public String getUserAgent() {
		return userAgent;
	}
	
}
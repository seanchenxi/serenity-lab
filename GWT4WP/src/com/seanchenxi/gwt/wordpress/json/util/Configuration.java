package com.seanchenxi.gwt.wordpress.json.util;

import com.google.gwt.i18n.client.Dictionary;

/**
 * Use {@link Dictionary} to realize dynamic settings
 * @author Xi
 *
 */
public class Configuration {

	private static final String ConfigName = "WPJsonAPIConfig";
	private static final String ServicePathKey = "servicePath";
	private static final String RequestTimeoutKey = "requestTimeout";
	
	private static Dictionary jServiceConfig;
	private static Configuration instance;
	
	public static Configuration getInstance() {
		if(instance == null){
			return instance = new Configuration();
		}
		return instance;
	}
	
	public String getServicePath(){
		try{
			return jServiceConfig.get(ServicePathKey).trim();
		}catch (Exception e) {
			return "/api";
		}
	}
	
	public int getRequestTimeoutTime(){
		try{
			return Integer.parseInt(jServiceConfig.get(RequestTimeoutKey));
		}catch (Exception e) {
			return 20000;
		}
	}
	
	private Configuration(){
		try {
			jServiceConfig = Dictionary.getDictionary(ConfigName);
		} catch (Exception e) {
			jServiceConfig = null;
		}
	}
}

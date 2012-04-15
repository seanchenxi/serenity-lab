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
	
	/**
	 * Get plugin Json API's service path
	 * If it is not defined in the dictionary "WPJsonAPIConfig", "/api" is returned as default value
	 * @return the relative 
	 */
	public String getServicePath(){
		try{
			return jServiceConfig.get(ServicePathKey).trim();
		}catch (Exception e) {
			return "/api";
		}
	}
	
	/**
	 * Get HTTP Request Timeout Time, 20s by default.
	 * If it is not set in the dictionary "WPJsonAPIConfig", 20s is returned as default value
	 * 
	 * @return The defined HTTP Request Timeout Time. 
	 */
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

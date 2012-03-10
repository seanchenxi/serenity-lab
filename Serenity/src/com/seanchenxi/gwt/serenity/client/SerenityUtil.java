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
package com.seanchenxi.gwt.serenity.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.i18n.client.Dictionary;
import com.seanchenxi.gwt.logging.api.Log;
import com.seanchenxi.gwt.serenity.client.resource.message.MessageResources;
import com.seanchenxi.gwt.serenity.client.util.MD5;

public class SerenityUtil {
	
	public static final String PAGING_CONFIG_ID = "PagingConfig";
	
	public static final MessageResources CST = GWT.create(MessageResources.class);
	
	private static final DateTimeFormat DateFormatter = DateTimeFormat.getFormat(PredefinedFormat.DATE_MEDIUM);
	private static final DateTimeFormat TimerFormatter = DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM);
	
	private static final Dictionary PAGING_CONFIG = Dictionary.getDictionary(PAGING_CONFIG_ID);
	
	public static int getPageSize(){
		try{
			return Integer.parseInt(PAGING_CONFIG.get("size"));
		}catch (Exception e) {
			Log.severe("[SerenityUtil] getPageSize error", e);
			return 10;
		}
	}
	
	public static String toDateString(Date date) {
		return DateFormatter.format(date);
	}
	
	public static String toDateTimeString(Date date) {
		return TimerFormatter.format(date);
	}
	
	public static String getMD5(String string){
	  if(string == null || string.isEmpty())
	    return null;
	  return MD5.encode(string.trim().toLowerCase());
	}
	
	public static boolean isValidEmail(String email){
	  if(email == null || email.isEmpty()) return false;
	  if(email.length() < 6) return false;
	  if(email.indexOf("@") == -1) return false;
	  if(email.indexOf(".") == -1) return false;
	  return true;
	}
	
}

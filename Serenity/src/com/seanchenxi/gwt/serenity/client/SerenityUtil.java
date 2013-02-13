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

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.HeadElement;
import com.google.gwt.dom.client.MetaElement;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window.Location;
import com.seanchenxi.gwt.logging.api.Log;
import com.seanchenxi.gwt.serenity.client.place.SerenityPlaceUtil;
import com.seanchenxi.gwt.serenity.client.resource.SerenityResources;
import com.seanchenxi.gwt.serenity.share.StringPool;

public class SerenityUtil {
  
	private static final DateTimeFormat DateFormatter = DateTimeFormat.getFormat(PredefinedFormat.DATE_MEDIUM);
	private static final DateTimeFormat TimerFormatter = DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM);
	
	public static int getPageSize(){
		try{
			return Integer.parseInt(SerenityResources.PAGING_CONFIG.get(SerenityResources.SIZE));
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
	
	public static boolean isValidEmail(String email){
	  if(email == null || email.isEmpty()) return false;
	  return email.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,4}$");
	}
	
  public static String getWpNaming() {
    Element el = Document.get().getElementsByTagName(HeadElement.TAG).getItem(0);
    NodeList<Element> els = el.getElementsByTagName(MetaElement.TAG);
    for (int i = 0; i < els.getLength(); i++) {
      if (SerenityResources.GENERATOR.equalsIgnoreCase(els.getItem(i).getAttribute(SerenityResources.NAME))) {
        return els.getItem(i).getAttribute(SerenityResources.CONTENT);
      }
    }
    return SerenityResources.MSG.wordpress_Name();
  }
 
	public static String getWpBaseUrl(){
	  Element el = Document.get().getElementsByTagName(HeadElement.TAG).getItem(0);
	  NodeList<Element> els = el.getElementsByTagName(MetaElement.TAG);
	  for(int i = 0; i < els.getLength(); i++){
	    if(SerenityResources.URL.equalsIgnoreCase(els.getItem(i).getAttribute(SerenityResources.NAME))){
	      return els.getItem(i).getAttribute(SerenityResources.CONTENT);
	    }
	  }
	  return StringPool.SLASH;
	}
	
	public static boolean replaceIfIdOldRequest(){
	  String path = Location.getPath();
	  String query = Location.getQueryString();
	  if(path.endsWith(".html")){
	    try{
  	    String[] slugs = path.split(StringPool.SLASH);
  	    String token = SerenityPlaceUtil.getArticleAnchor(slugs[slugs.length - 1].replace(".html", ""));
  	    StringBuilder sb = new StringBuilder(getWpBaseUrl());
  	    if(query != null){
  	      sb.append(query);
  	    }else{
  	      sb.append(StringPool.SLASH);
  	    }
  	    sb.append(token);
  	    Log.finest("[SerenityUtil] replace to " + sb.toString());
  	    Location.replace(sb.toString());
  	    return true;
	    }catch (Exception e) {
	      Log.warn("[SerenityUtil] replace error", e);
      }
	  }
	  return false;
	}
	
	public static boolean isLoggedIn() {
    for(String name : Cookies.getCookieNames()){
      if(name != null && name.contains("logged") && name.contains("in")){
        return true;
      }
    }
    return false;
  }
	
}

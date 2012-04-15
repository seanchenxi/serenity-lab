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

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * JSON Object String parsing utility class
 * @author Xi
 *
 */
@SuppressWarnings("unchecked")
public class JUtil {

	private final static DateTimeFormat DATE_TIME_FORMATTER = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * Convert a {@link JsArray} to {@link ArrayList}
	 * @param jsList
	 * @return
	 */
	public static <M extends JavaScriptObject> ArrayList<M> convert(
			JsArray<? extends JavaScriptObject> jsList) {
		if (jsList == null || jsList.length() < 1)
			return new ArrayList<M>();
		ArrayList<M> list = new ArrayList<M>(jsList.length());
		for (int i = 0; i < jsList.length(); i++) {
			try {
				list.add(i, (M) jsList.get(i).cast());
			} catch (Exception e) {
			}
		}
		return list;
	}

	/**
	 * Convert a {@link String} in format "yyyy-MM-dd HH:mm:ss" to {@link Date} 
	 * @param dateString, date string in format "yyyy-MM-dd HH:mm:ss"
	 * @return date
	 */
	public static Date parse(String dateString) {
		if (dateString == null || dateString.isEmpty())
			return null;
		return DATE_TIME_FORMATTER.parse(dateString);
	}

	/**
	 * Convert a {@link Date} to {@link String} "yyyy-MM-dd HH:mm:ss"
	 * @param date
	 * @return a "yyyy-MM-dd HH:mm:ss" formatted date string
	 */
	public static String format(Date date) {
		if (date == null)
			return null;
		return DATE_TIME_FORMATTER.format(date);
	}

}

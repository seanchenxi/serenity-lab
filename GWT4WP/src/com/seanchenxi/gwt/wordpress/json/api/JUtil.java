package com.seanchenxi.gwt.wordpress.json.api;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.i18n.client.DateTimeFormat;

@SuppressWarnings("unchecked")
public class JUtil {

	private final static DateTimeFormat DATE_TIME_FORMATTER = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");

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

	public static Date parse(String dateString) {
		if (dateString == null || dateString.isEmpty())
			return null;
		return DATE_TIME_FORMATTER.parse(dateString);
	}

	public static String format(Date date) {
		if (date == null)
			return null;
		return DATE_TIME_FORMATTER.format(date);
	}

}

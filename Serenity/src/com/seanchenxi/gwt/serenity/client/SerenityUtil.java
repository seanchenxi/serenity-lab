package com.seanchenxi.gwt.serenity.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.i18n.client.Dictionary;
import com.seanchenxi.gwt.logging.api.Log;
import com.seanchenxi.gwt.serenity.client.resource.message.MessageResources;

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
	
}

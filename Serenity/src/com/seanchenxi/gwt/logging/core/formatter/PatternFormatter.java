
package com.seanchenxi.gwt.logging.core.formatter;

import java.util.Date;
import java.util.logging.LogRecord;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.logging.impl.FormatterImpl;

public class PatternFormatter extends FormatterImpl {
	
	private static DateTimeFormat dateTimerFormatter = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private boolean showStackTraces = true;

	@Override
	protected String getRecordInfo(LogRecord record, String newline) {
		Date date = new Date(record.getMillis());
		String formattedTime = dateTimerFormatter.format(date);
		StringBuilder s = new StringBuilder();
		s.append(formattedTime);
		s.append(" ");
//		s.append(record.getLoggerName());
//		s.append(newline);
		s.append("[");
		s.append(record.getLevel().getName());
		s.append("]");
		s.append(" ");
		return s.toString();
	}

	@Override
	public String format(LogRecord record) {		
		if(dateTimerFormatter == null){
			dateTimerFormatter = DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_LONG);
		}	
		StringBuilder message = new StringBuilder();
		message.append(getRecordInfo(record, "\n"));
		message.append(record.getMessage());
		if (showStackTraces) {
		    message.append(getStackTraceAsString(record.getThrown(), "\n", "\t"));
		}
		return message.toString();
	}
	
	public void setShowStackTraces(boolean showStackTraces) {
		this.showStackTraces = showStackTraces;
	}

}
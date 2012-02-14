package com.seanchenxi.gwt.logging.api;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.logging.client.LogConfiguration;

public class Log {

	private static Logger LOGGER;
	static{
		if (LogConfiguration.loggingIsEnabled()){
			LOGGER = Logger.getLogger("SCX-LOG");
		}
	}
	
	public static void setLevel(Level level){
		if (LogConfiguration.loggingIsEnabled())
			LOGGER.setLevel(level);
	}

	public static void all(String message) {
		if (LogConfiguration.loggingIsEnabled())
			LOGGER.log(Level.ALL, message);
	}

	public static void all(String message, Throwable e) {
		if (LogConfiguration.loggingIsEnabled())
			LOGGER.log(Level.ALL, message, e);
	}

	public static void finest(String message) {
		if (LogConfiguration.loggingIsEnabled())
			LOGGER.finest(message);
	}

	public static void finest(String message, Throwable e) {
		if (LogConfiguration.loggingIsEnabled())
			LOGGER.log(Level.FINEST, message, e);
	}

	public static void finer(String message) {
		if (LogConfiguration.loggingIsEnabled())
			LOGGER.finer(message);
	}

	public static void finer(String message, Throwable e) {
		if (LogConfiguration.loggingIsEnabled())
			LOGGER.log(Level.FINER, message, e);
	}

	public static void fine(String message) {
		if (LogConfiguration.loggingIsEnabled())
			LOGGER.fine(message);
	}

	public static void fine(String message, Throwable e) {
		if (LogConfiguration.loggingIsEnabled())
			LOGGER.log(Level.FINE, message, e);
	}

	public static void config(String message) {
		if (LogConfiguration.loggingIsEnabled())
			LOGGER.config(message);
	}

	public static void config(String message, Throwable e) {
		if (LogConfiguration.loggingIsEnabled())
			LOGGER.log(Level.CONFIG, message, e);
	}

	public static void info(String message) {
		if (LogConfiguration.loggingIsEnabled())
			LOGGER.info(message);
	}

	public static void info(String message, Throwable e) {
		if (LogConfiguration.loggingIsEnabled())
			LOGGER.log(Level.INFO, message, e);
	}

	public static void warn(String message) {
		if (LogConfiguration.loggingIsEnabled())
			LOGGER.warning(message);
	}

	public static void warn(String message, Throwable e) {
		if (LogConfiguration.loggingIsEnabled())
			LOGGER.log(Level.WARNING, message, e);
	}

	public static void severe(String message) {
		if (LogConfiguration.loggingIsEnabled())
			LOGGER.severe(message);
	}

	public static void severe(String message, Throwable e) {
		if (LogConfiguration.loggingIsEnabled())
			LOGGER.log(Level.SEVERE, message, e);
	}
}

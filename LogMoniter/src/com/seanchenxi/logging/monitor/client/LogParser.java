package com.seanchenxi.logging.monitor.client;

import java.util.logging.Level;

public class LogParser {

  private static final String WARN = "WARN";
  private static final String[] SEVERE = {"ERROR", "SEVERE", "EXCEPTION"};
  private static final String[] SEVERE_TRACE = {"AT", ".JAVA:", "(", "]"};

  private static final String STDOUT = "[STDOUT]";
  private static final String INFO = "INFO";
  private static final String GWT_LOG = "[GWT-LOG]";

  public static Level getLevel(String log) {
    String upperLog = log.toUpperCase().trim();

    for (String str : SEVERE) {
      if (upperLog.indexOf(str) != -1) {
        return Level.SEVERE;
      }
    }

    if(isTraceLine(upperLog)){
      return Level.SEVERE;
    }

    if (upperLog.indexOf(GWT_LOG) != -1 && upperLog.indexOf(INFO) != -1) {
      return Level.INFO;
    } else if (upperLog.indexOf(STDOUT) != -1 && upperLog.indexOf(INFO) != -1) {
      return Level.OFF;
    } else if (upperLog.indexOf(WARN) != -1) {
      return Level.WARNING;
    }

    return null;
  }

  public static boolean isTraceLine(String log) {
    String upperLog = log.toUpperCase().trim();
    for (String str : SEVERE_TRACE) {
      if (upperLog.indexOf(str) == -1) {
        return false;
      }
    }
    return true;
  }
  
  public static String getColor(Level logLevel) {
    if (logLevel == Level.OFF) {
      return "#888"; // grey
    }
    if (logLevel == Level.SEVERE) {
      return "#F00"; // bright red
    }
    if (logLevel == Level.WARNING) {
      return "#E56717"; // dark orange
    }
    if (logLevel == Level.INFO) {
      return "#20b000"; // green
    }
    if (logLevel == Level.CONFIG) {
      return "#2B60DE"; // blue
    }
    if (logLevel == Level.FINE) {
      return "#F0F"; // purple
    }
    if (logLevel == Level.FINER) {
      return "#F0F"; // purple
    }
    if (logLevel == Level.FINEST) {
      return "#F0F"; // purple
    }
    return "#333"; // deep grey
  }

}

package com.seanchenxi.logging.monitor.client;


public class LogParser {
  
  private static final String STDOUT = "[STDOUT]";
  private static final String GWT_LOG = "[GWT-LOG]";
  private static final String INFO = " INFO ";
  private static final String CONFIG = " CONFIG ";
  private static final String FINE = " FINE ";
  private static final String FINER = " FINER ";
  private static final String FINEST = " FINEST ";
  private static final String DEBUG = " DEBUG ";
  
  private static final String[] SEVERE = {"ERROR", "SEVERE", "EXCEPTION"};
  private static final String[] WARNING = {" WARN ", " WARNING "};
  private static final String[] SEVERE_TRACE = {"AT", ".JAVA:", "("};
  private static final String[] SQL_TRACE = {STDOUT, INFO, "HIBERNATE"};
  

  public static ExtLevel getLevel(String log) {
    String upperLog = log.toUpperCase().trim();

    if(isTraceLine(upperLog)){
        return ExtLevel.SEVERE;
    }
    
    for (String str : SEVERE) {
      if (upperLog.indexOf(str) != -1) {
        return ExtLevel.SEVERE;
      }
    }
    
    for (String str : WARNING) {
      if (upperLog.indexOf(str) != -1) {
        return ExtLevel.WARNING;
      }
    }
    
    if(isSQLTrace(upperLog)){
      return ExtLevel.SQL;
    }
    
    if (upperLog.indexOf(INFO) != -1) {
      return upperLog.indexOf(GWT_LOG) != -1 ? ExtLevel.GREEN_INFO : ExtLevel.INFO;
    }

    if (upperLog.indexOf(DEBUG) != -1) {
      return ExtLevel.DEBUG;
    }
    
    if (upperLog.indexOf(CONFIG) != -1) {
      return ExtLevel.CONFIG;
    }
    if (upperLog.indexOf(FINE) != -1) {
      return ExtLevel.FINE;
    }
    if (upperLog.indexOf(FINER) != -1) {
      return ExtLevel.FINER;
    }    
    if (upperLog.indexOf(FINEST) != -1) {
      return ExtLevel.FINEST;
    }
    
    return ExtLevel.ALL;
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
  
  public static boolean isSQLTrace(String log) {
    String upperLog = log.toUpperCase().trim();
    for (String str : SQL_TRACE) {
      if (upperLog.indexOf(str) == -1) {
        return false;
      }
    }
    return true;
  }
  
  public static String getColor(ExtLevel logLevel) {
    if (logLevel == ExtLevel.OFF || logLevel == ExtLevel.SQL) {
      return "#888"; // grey
    }
    if (logLevel == ExtLevel.SEVERE) {
      return "#F00"; // bright red
    }
    if (logLevel == ExtLevel.WARNING) {
      return "#E56717"; // dark orange
    }
    if (logLevel == ExtLevel.GREEN_INFO) {
      return "#20b000"; // green
    }
    if (logLevel == ExtLevel.CONFIG) {
      return "#2B60DE"; // blue
    }
    if (logLevel == ExtLevel.FINE) {
      return "#F0F"; // purple
    }
    if (logLevel == ExtLevel.FINER) {
      return "#F0F"; // purple
    }
    if (logLevel == ExtLevel.FINEST) {
      return "#F0F"; // purple
    }
    return "#333"; // deep grey
  }

}

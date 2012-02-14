package com.seanchenxi.gwt.logging.core.handler;

import java.util.logging.Level;

import com.google.gwt.logging.client.FirebugLogHandler;
import com.seanchenxi.gwt.logging.core.formatter.PatternFormatter;

public class SCXFirebugLogHandler extends FirebugLogHandler {
  
  public SCXFirebugLogHandler() {
    setFormatter(new PatternFormatter());
    setLevel(Level.ALL);
  }
  
}

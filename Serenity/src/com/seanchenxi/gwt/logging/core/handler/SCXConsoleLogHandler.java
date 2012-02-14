package com.seanchenxi.gwt.logging.core.handler;

import java.util.logging.Level;

import com.google.gwt.logging.client.ConsoleLogHandler;
import com.seanchenxi.gwt.logging.core.formatter.PatternFormatter;

public class SCXConsoleLogHandler extends ConsoleLogHandler {

  public SCXConsoleLogHandler(){
    setFormatter(new PatternFormatter());
    setLevel(Level.ALL);
  }
  
}

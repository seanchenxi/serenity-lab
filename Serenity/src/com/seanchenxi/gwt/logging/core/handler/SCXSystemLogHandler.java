package com.seanchenxi.gwt.logging.core.handler;

import java.util.logging.Level;

import com.google.gwt.logging.client.SystemLogHandler;
import com.seanchenxi.gwt.logging.core.formatter.PatternFormatter;

public class SCXSystemLogHandler extends SystemLogHandler {

  public SCXSystemLogHandler(){
    setFormatter(new PatternFormatter());
    setLevel(Level.ALL);
  }
  
}

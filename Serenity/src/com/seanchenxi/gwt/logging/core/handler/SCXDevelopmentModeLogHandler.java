package com.seanchenxi.gwt.logging.core.handler;

import java.util.logging.Level;

import com.google.gwt.logging.client.DevelopmentModeLogHandler;
import com.seanchenxi.gwt.logging.core.formatter.PatternFormatter;

public class SCXDevelopmentModeLogHandler extends DevelopmentModeLogHandler {

  public SCXDevelopmentModeLogHandler(){
    setFormatter(new PatternFormatter());
    setLevel(Level.ALL);
  }
}

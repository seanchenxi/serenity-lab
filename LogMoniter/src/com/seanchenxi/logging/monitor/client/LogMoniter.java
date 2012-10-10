package com.seanchenxi.logging.monitor.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.seanchenxi.logging.monitor.client.event.LogEvent;
import com.seanchenxi.logging.monitor.client.event.LogRotatedEvent;

public class LogMoniter implements EntryPoint, UncaughtExceptionHandler, LogEvent.Handler,
    LogRotatedEvent.Handler, CloseHandler<Window> {

  private static final Unit PX = Unit.PX;
  private static final Logger Log = Logger.getLogger("LogMoniter");
  private static final int DEFAULT_LIMIT = 1000;

  private ScrollPanel scroller;
  private FlexTable table;
  private CellFormatter cellFormatter;
  
  private TextBox rowLimit;
  private Button clear;
  
  private HandlerRegistration logHander;
  private HandlerRegistration logRotatedHander;
  private HandlerRegistration windowCloseHandler;

  @Override
  public void onClose(CloseEvent<Window> event) {
    MessageClient.getInstance().stop();
    clearHandlers();
  }

  @Override
  public void onLog(final String log) {
    final int row = getRowCount();
    table.setHTML(row, 0, log);
    updateCellStyle(row, LogParser.getLevel(log), LogParser.isTraceLine(log));
    scroller.scrollToBottom();
  }

  @Override
  public void onLogRotated() {
    table.removeAllRows();
    scroller.scrollToBottom();
  }

  public void onModuleLoad() {
    GWT.setUncaughtExceptionHandler(this);
    initGUI();
    initHandlers();
  }

  @Override
  public void onUncaughtException(Throwable e) {
    Log.log(Level.SEVERE, "onUncaughtException", e);
  }

  private void clearHandlers() {
    if(windowCloseHandler != null){
      windowCloseHandler.removeHandler();
    }
    if (logHander != null) {
      logHander.removeHandler();
    }
    if (logRotatedHander != null) {
      logRotatedHander.removeHandler();
    }
  }

  private int getRowCount() {
    int row = table.getRowCount();
    if (row > getRowLimit()) {
      table.removeAllRows();
      return 0;
    }
    return row;
  }
  
  private int getRowLimit() {
    try {
      return Integer.parseInt(rowLimit.getValue());
    } catch (Exception e) {
      return DEFAULT_LIMIT;
    }
  }

  private void initGUI() {
    Label label = new Label("Row Limit: ");
    label.getElement().getStyle().setFontWeight(FontWeight.BOLD);
    label.getElement().getStyle().setProperty("lineHeight", "25px");
    RootLayoutPanel.get().add(label);
    RootLayoutPanel.get().setWidgetTopHeight(label, 10, PX, 25, PX);
    RootLayoutPanel.get().setWidgetLeftWidth(label, 10, PX, 80, PX);
    
    rowLimit = new TextBox();
    rowLimit.setWidth("60px");
    rowLimit.setText(String.valueOf(DEFAULT_LIMIT));
    rowLimit.setAlignment(TextAlignment.RIGHT);
    RootLayoutPanel.get().add(rowLimit);
    RootLayoutPanel.get().setWidgetTopHeight(rowLimit, 10, PX, 25, PX);
    RootLayoutPanel.get().setWidgetLeftWidth(rowLimit, 90, PX, 70, PX);
    
    clear = new Button("Clear Log");
    clear.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        table.removeAllRows();
      }
    });
    RootLayoutPanel.get().add(clear);
    RootLayoutPanel.get().setWidgetTopHeight(clear, 10, PX, 25, PX);
    RootLayoutPanel.get().setWidgetLeftWidth(clear, 170, PX, 120, PX);
    
    table = new FlexTable();
    table.setSize("100%", "auto");
    table.setCellSpacing(2);
    cellFormatter = table.getCellFormatter();
    scroller = new ScrollPanel(table);
    scroller.getElement().getStyle().setProperty("borderTop", "1px solid #333333");
    RootLayoutPanel.get().add(scroller);
    RootLayoutPanel.get().setWidgetTopBottom(scroller, 45, PX, 0, PX);
    RootLayoutPanel.get().setWidgetLeftRight(scroller, 0, PX, 0, PX);
  }

  private void initHandlers() {
    clearHandlers();
    windowCloseHandler = Window.addCloseHandler(this);
    logHander = MessageClient.getInstance().addLogHandler(this);
    logRotatedHander = MessageClient.getInstance().addLogRotatedHandler(this);
  }

  private void updateCellStyle(int row, Level level, boolean isTraceLine) {
    Element cell = cellFormatter.getElement(row, 0);
    cell.getStyle().setColor(LogParser.getColor(level));
    cell.getStyle().setPaddingLeft(isTraceLine ? 20 : 2, PX);
    cell.setClassName(level == null ? "" : level.getName());   
  }
  
  

}

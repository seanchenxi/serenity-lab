package com.seanchenxi.logging.monitor.client;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.seanchenxi.logging.monitor.client.event.LogEvent;
import com.seanchenxi.logging.monitor.client.event.LogRotatedEvent;

public class Viewer implements IsWidget, LogEvent.Handler, LogRotatedEvent.Handler {

  private static final Logger Log = Logger.getLogger("Viewer");
  
  private final ScrollPanel scroller;
  private final FlexTable table;
  private final CellFormatter cellFormatter;
  private final RowFormatter rowFormatter;
  
  private boolean isTemplePaused;
  private boolean isPaused;
  private int rowLimit;
  private int lastPosition = 0;
  private HandlerRegistration scrollHandler;

  private Set<String> showNames;
  
  public Viewer(int rowLimit, Set<String> showNames){
    table = new FlexTable();
    table.setSize("100%", "auto");
    table.setCellSpacing(2);
    cellFormatter = table.getCellFormatter();
    rowFormatter = table.getRowFormatter();
    scroller = new ScrollPanel(table);
    scroller.getElement().getStyle().setProperty("borderTop", "1px solid #333333");
    setPause(false);
    setRowLimit(rowLimit);
    updateRowVisibility(showNames);
  }
  
  public void clear() {
    table.removeAllRows();
    Log.log(Level.INFO, "removeAllRows");
  }

  public void setPause(boolean value) {
    this.isPaused = value;
    Log.log(Level.INFO, "setPause = " + value);
    resetScrollHandler();
  }
  
  public void setRowLimit(int rowLimit) {
    this.rowLimit = rowLimit;
    Log.log(Level.INFO, "setRowLimit = " + rowLimit);
  }
  
  @Override
  public void onLog(final String log) {
    final int row = getRowCount();
    table.setHTML(row, 0, log);
    updateCellStyle(row, LogParser.getLevel(log), LogParser.isTraceLine(log));
    if (!isPaused && !isTemplePaused)
      scroller.scrollToBottom();
  }

  @Override
  public void onLogRotated() {
    table.removeAllRows();
  }
  
  @Override
  public Widget asWidget() {
    return scroller;
  }
  
  private void updateCellStyle(int row, ExtLevel level, boolean isTraceLine) {
    rowFormatter.getElement(row).getStyle().setColor(LogParser.getColor(level));
    rowFormatter.setStyleName(row, level == null ? "" : level.getName());
    rowFormatter.setVisible(row, level == null ? true : showNames.contains(level.getName()));
    cellFormatter.getElement(row, 0).getStyle().setPaddingLeft(isTraceLine ? 20 : 2, Unit.PX);
  }
  
  public void updateRowVisibility(Set<String> names) {
    if(names.equals(showNames))
      return;
    this.showNames = names;
    int row = 0;
    while(row < table.getRowCount()){
      Element rowEl = rowFormatter.getElement(row);
      String className = rowEl.getClassName().trim();
      rowFormatter.setVisible(row, className.isEmpty() || showNames.contains(className));
      row++;
    }
  }

  private int getRowCount() {
    int row = table.getRowCount();
    int diff = row - rowLimit;
    if(diff > 0){
      int i = 0;
      while(i < diff){
        table.removeRow(i);
        i++;
      }
      return table.getRowCount();
    }else{
      return row;
    }
  }
  
  private void resetScrollHandler() {
    if(isPaused){
      if(scrollHandler != null)
        scrollHandler.removeHandler();
      scrollHandler = null;
      return;
    }else if(scrollHandler != null){
      return;
    }
    scrollHandler = scroller.addScrollHandler(new ScrollHandler() {
      @Override
      public void onScroll(ScrollEvent event) {
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
          @Override
          public void execute() {
            if(isPaused) return;
            int newPosition = scroller.getVerticalScrollPosition();
            if(lastPosition > newPosition){
              isTemplePaused = true;
            }else if(newPosition == scroller.getMaximumVerticalScrollPosition()){
              isTemplePaused = false;
            }
            lastPosition = newPosition;
          }
        });
      }
    });
  }
  
}

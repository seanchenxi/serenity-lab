package com.seanchenxi.logging.monitor.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class LogMoniter implements EntryPoint, UncaughtExceptionHandler, CloseHandler<Window> {

  private static final Logger Log = Logger.getLogger("LogMoniter");
  private static final Unit PX = Unit.PX;

  private SettingBar bar;
  private Viewer viewer;

  private HandlerRegistration logHander;
  private HandlerRegistration logRotatedHander;
  private HandlerRegistration windowCloseHandler;

  @Override
  public void onClose(CloseEvent<Window> event) {
    MessageClient.getInstance().stop();
    clearHandlers();
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
    if (windowCloseHandler != null) {
      windowCloseHandler.removeHandler();
    }
    if (logHander != null) {
      logHander.removeHandler();
    }
    if (logRotatedHander != null) {
      logRotatedHander.removeHandler();
    }
  }

  private void initGUI() {
    RootLayoutPanel.get().add(bar = new SettingBar());
    RootLayoutPanel.get().setWidgetTopHeight(bar, 10, PX, 40, PX);
    RootLayoutPanel.get().setWidgetLeftRight(bar, 0, PX, 0, PX);
    RootLayoutPanel.get().add(viewer = new Viewer(bar.getRowLimit()));
    RootLayoutPanel.get().setWidgetTopBottom(viewer, 55, PX, 0, PX);
    RootLayoutPanel.get().setWidgetLeftRight(viewer, 0, PX, 0, PX);
  }

  private void initHandlers() {
    bar.getClearBtn().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        viewer.clear();
      }
    });

    bar.getConfirmeRowLimit().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        viewer.setRowLimit(bar.getRowLimit());
        bar.getConfirmeRowLimit().setEnabled(false);
      }
    });

    bar.getPauseBtn().addValueChangeHandler(new ValueChangeHandler<Boolean>() {
      @Override
      public void onValueChange(ValueChangeEvent<Boolean> event) {
        viewer.setPause(event.getValue());
      }
    });

    clearHandlers();
    windowCloseHandler = Window.addCloseHandler(this);
    logHander = MessageClient.getInstance().addLogHandler(viewer);
    logRotatedHander = MessageClient.getInstance().addLogRotatedHandler(viewer);
  }

}

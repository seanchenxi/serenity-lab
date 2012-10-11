package com.seanchenxi.logging.monitor.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;

public class SettingBar extends HorizontalPanel {

  private static final String NBSPx4 = "&nbsp;&nbsp;|&nbsp;&nbsp;";
  private static final int DEFAULT_LIMIT = 1000;
  
  private Label label;
  private TextBox rowLimit;
  private Button confirmeRowLimit;
  private Button clear;
  private ToggleButton pause;
  
  public SettingBar(){
    super();
    setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
    setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
    setSpacing(5);
    injectElements();
    initHandlers();
  }

  private void injectElements() {
    label = new Label("Row Limit: ");
    label.getElement().getStyle().setFontWeight(FontWeight.BOLD);
    rowLimit = new TextBox();
    rowLimit.setWidth("60px");
    rowLimit.setText(String.valueOf(DEFAULT_LIMIT));
    rowLimit.setAlignment(TextAlignment.RIGHT);
    confirmeRowLimit = new Button("Ok");
    confirmeRowLimit.setEnabled(false);
    clear = new Button("Clear Log");
    pause = new ToggleButton("Pause", "Unpause");
    pause.setValue(false);

    add(label);
    add(rowLimit);
    add(confirmeRowLimit);
    add(createSeparator());
    add(clear);
    add(createSeparator());
    add(pause);
    add(createSeparator());
  }

  private void initHandlers() {
    rowLimit.addValueChangeHandler(new ValueChangeHandler<String>() {      
      @Override
      public void onValueChange(ValueChangeEvent<String> event) {
        final String val = event.getValue();
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
          @Override
          public void execute() {
            confirmeRowLimit.setEnabled(val != null && !val.trim().isEmpty());
          }
        });
      }
    });
    confirmeRowLimit.addClickHandler(new ClickHandler() { 
      @Override
      public void onClick(ClickEvent event) {
        confirmeRowLimit.setEnabled(false);
      }
    });
  }
  
  public Button getClearBtn() {
    return clear;
  }
  
  public ToggleButton getPauseBtn() {
    return pause;
  }

  public Button getConfirmeRowLimit() {
    return confirmeRowLimit;
  }

  public int getRowLimit() {
    try{
      return Integer.parseInt(rowLimit.getValue());
    }catch (Exception e) {
      return DEFAULT_LIMIT;
    }
  }
  
  private HTML createSeparator() {
    return new HTML(NBSPx4);
  }
  
}

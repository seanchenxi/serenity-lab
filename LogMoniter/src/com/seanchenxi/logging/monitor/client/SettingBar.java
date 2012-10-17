package com.seanchenxi.logging.monitor.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
import com.google.web.bindery.event.shared.HandlerRegistration;

public class SettingBar extends HorizontalPanel {

  private static final String NBSPx4 = "&nbsp;&nbsp;|&nbsp;&nbsp;";
  private static final int DEFAULT_LIMIT = 1000;
  
  private Label label;
  private TextBox rowLimit;
  private Button confirmeRowLimit;
  private Button clear;
  private ToggleButton pause;
  
  private ArrayList<ToggleButton> nameBtns;
  
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
    
    nameBtns = new ArrayList<ToggleButton>();
    nameBtns.add(new ToggleButton(ExtLevel.INFO.getName()));
    nameBtns.add(new ToggleButton(ExtLevel.GREEN_INFO.getName()));
    nameBtns.add(new ToggleButton(ExtLevel.SQL.getName()));
    nameBtns.add(new ToggleButton(ExtLevel.DEBUG.getName()));
    nameBtns.add(new ToggleButton(ExtLevel.WARNING.getName()));
    nameBtns.add(new ToggleButton(ExtLevel.SEVERE.getName()));
    
    add(label);
    add(rowLimit);
    add(confirmeRowLimit);
    add(createSeparator());
    add(clear);
    add(createSeparator());
    add(pause);
    add(createSeparator());
    for(ToggleButton tb : nameBtns){
      tb.setValue(true);
      add(tb);
    }
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
  
  public HandlerRegistration addShowNameValueChangeHandler(ValueChangeHandler<Boolean> handler){
    final ArrayList<HandlerRegistration> list = new ArrayList<HandlerRegistration>();
    for(ToggleButton tb : nameBtns){
      list.add(tb.addValueChangeHandler(handler));
    }
    return new HandlerRegistration() {
      @Override
      public void removeHandler() {
        for(HandlerRegistration hr : list){
          hr.removeHandler();
        }
      }
    };
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

  public Set<String> getShowNames() {
    Set<String> set = new HashSet<String>();
    for(ToggleButton tb : nameBtns){
      if(tb.getValue())
        set.add(tb.getText());
    }
    return set;
  }
  
}

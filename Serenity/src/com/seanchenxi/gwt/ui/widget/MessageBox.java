/*******************************************************************************
 * Copyright 2012 Xi CHEN
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.seanchenxi.gwt.ui.widget;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.Image;
import com.seanchenxi.gwt.ui.resource.UIResources;

public class MessageBox {
  
  private final DialogBox dialog;
  private final FlexTable container;
  private final FlowPanel buttons;
  
  private MessageBox(){
    buttons = new FlowPanel();
    buttons.setSize("100%", "100%");
    
    container = new FlexTable();
    container.setSize("100%", "100%");
    container.setCellPadding(5);
    container.setCellSpacing(5);
    container.setHTML(0, 0, "");
    container.setHTML(0, 1, "");
    container.setWidget(1, 0, buttons);
    container.getFlexCellFormatter().setColSpan(1, 0, 2);  
    CellFormatter cellFormatter = container.getCellFormatter();   
    cellFormatter.setAlignment(0, 0, HasAlignment.ALIGN_CENTER, HasAlignment.ALIGN_MIDDLE);
    cellFormatter.setWidth(0, 0, "50px");
    cellFormatter.setAlignment(1, 0, HasAlignment.ALIGN_CENTER, HasAlignment.ALIGN_BOTTOM);
    
    dialog = new DialogBox(false, true);
    dialog.setStyleName("MessageBox");
    dialog.setWidget(container);
  }
  
  private void setTitle(String title){
    dialog.setText(title);
  }
 
  private void setMessage(String message){
    container.setHTML(0, 1, message);
  }
  
  private void setIcon(ImageResource icon){
    container.setWidget(0, 0, new Image(icon));
  }
  
  private void addButton(Button btn){
    btn.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        hide();
      }
    });
    buttons.add(btn);
  }
  
  public static void alert(String title, String message, final ScheduledCommand cmd) {   
    MessageBox box = new MessageBox();
    box.setTitle(title);
    box.setMessage(message);
    box.setIcon(UIResources.IMG.icon_MsgBox_warning());
    box.setSize("250px", "120px");
    if(cmd != null){
      box.addButton(new Button("Ok", new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
          Scheduler.get().scheduleDeferred(cmd);
        }
      }));
    }
    box.show();
  }

  private void setSize(String width, String height) {
    dialog.setSize(width, height);
  }

  public void show(){
    dialog.show();
    dialog.center();
  }
  
  public void hide(){
    dialog.hide();
  }
  
}

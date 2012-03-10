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
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.seanchenxi.gwt.ui.event.SearchEvent;
import com.seanchenxi.gwt.ui.event.SearchEvent.HasSearchHandlers;

public class SearchBox extends PopupPanel implements FocusHandler,
		KeyUpHandler, ClickHandler, HasSearchHandlers, SearchEvent.Handler {

	private FlowPanel container;

	private TextBox field;
	private Button button;

	public SearchBox() {
		super(true, true);
		setWidget(container = new FlowPanel());
		setStyleName("popSearchBox");
		
		container.add(field = new TextBox());
		container.add(button = new Button("Search"));
		
		field.getElement().getStyle().setMarginRight(1, Unit.EM);
		container.getElement().getStyle().setPadding(1, Unit.EM);
		initAllHandlers();
	}
	
	public String getSeachValue() {
		return field.getValue();
	}
	
	public void setFieldWidth(String width){
		field.setWidth(width);
	}

	public void setSeachValue(String slug) {
		field.setText(slug);
	}

	public void setFocus(boolean focused) {
		field.setFocus(focused);
		if(focused)
			field.selectAll();
	}

	private void initAllHandlers() {
		field.addFocusHandler(this);
		field.addKeyUpHandler(this);
		button.addClickHandler(this);
		addSearchHandler(this);
	}

	@Override
	public void onClick(final ClickEvent event) {
		Object obj = event.getSource();
		if (obj == button) {
			fireSearchEvent(field.getText());
		}
	}
	
	private void fireSearchEvent(final String keywords){
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				SearchEvent.fire(keywords, SearchBox.this);
			}
		});
	}
	
	@Override
	public void onSearch(SearchEvent event) {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				field.selectAll();
			}
		});
	}

	@Override
	public void onKeyUp(KeyUpEvent event) {
	  switch (event.getNativeKeyCode()) {
      case KeyCodes.KEY_ENTER:
        if(field.getValue() != null && !field.getValue().isEmpty()){
          button.click();
        }
        break;
      case KeyCodes.KEY_ESCAPE:
        hide();
        break;
    }
	}

	@Override
	public void onFocus(FocusEvent event) {
		field.selectAll();
	}

	@Override
	public HandlerRegistration addSearchHandler(SearchEvent.Handler handler) {
		return addHandler(handler, SearchEvent.getType());
	}
	
	@Override
	public void show() {
	  super.show();
	  setFocus(true);
	}

}

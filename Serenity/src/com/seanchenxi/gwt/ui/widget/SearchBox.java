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

import com.google.gwt.core.client.GWT;
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
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.seanchenxi.gwt.ui.event.SearchEvent;
import com.seanchenxi.gwt.ui.event.SearchEvent.HasSearchHandlers;

public class SearchBox extends PopupPanel implements FocusHandler,
		KeyUpHandler, ClickHandler, HasSearchHandlers, SearchEvent.Handler {

  public interface Resources extends ClientBundle {

    public interface Style extends CssResource {
      final static String DEFAULT_CSS = "com/seanchenxi/gwt/ui/widget/SearchBox.css";
      String popSearchBox();
      String popSearchField();
      String popSearchButton();
      String popSearchContainer();
    }

    @ClientBundle.Source(Style.DEFAULT_CSS)
    Style style();
  }

  private static Resources resources;

  private TextBox field;
	private Button button;

  public static Resources getResources(){
    if(resources == null){
      resources = GWT.create(Resources.class);
      resources.style().ensureInjected();
    }
    return resources;
  }

	public SearchBox() {
		super(true, true);
    HorizontalPanel container = new HorizontalPanel();
    setWidget(container);
		setStyleName(getResources().style().popSearchBox());
		container.setVerticalAlignment(HasAlignment.ALIGN_MIDDLE);
		container.add(field = new TextBox());
		container.add(button = new Button("Search"));
    container.setStyleName(getResources().style().popSearchContainer());
    field.setStyleName(getResources().style().popSearchField());
    button.setStyleName(getResources().style().popSearchButton());
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
          fireSearchEvent(field.getText());
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

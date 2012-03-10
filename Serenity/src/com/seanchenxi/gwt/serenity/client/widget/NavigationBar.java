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
package com.seanchenxi.gwt.serenity.client.widget;

import java.util.ArrayList;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class NavigationBar extends Composite implements ClickHandler,
		HasSelectionHandlers<NavigationItem>, HasKeyUpHandlers {

	private FlowPanel panel;

	private ArrayList<NavigationItem> items;
	private NavigationItem selectedItem = null;

	public NavigationBar() {
		items = new ArrayList<NavigationItem>();
		initWidget(panel = new FlowPanel());
		setSize("100%", "100%");
		sinkEvents(Event.KEYEVENTS);
	}

	public void addItem(final NavigationItem item) {
		if (item == null) {
			return;
		}

		int oldIndex = panel.getWidgetIndex(item);
		if (oldIndex != -1) {
			panel.remove(oldIndex);
			items.remove(oldIndex);
		} else {
			item.addClickHandler(this);
		}

		int beforeIndex = panel.getWidgetCount();
		item.setNavigationBar(this);
		panel.add(item);
		items.add(beforeIndex, item);
	}

	public NavigationItem findItem(String itemId) {
		if (itemId == null)
			return null;
		for (NavigationItem item : items) {
			if (itemId.equalsIgnoreCase(item.getId())) {
				return item;
			}
		}
		return null;
	}
	
	public void clearSelection() {
		select(null);
	}
	
	public boolean select(NavigationItem item){
		if(item == selectedItem){
			return false;
		}
		if(item != null && item.getLabelBox() != null && item.getLabelBox().getSelectedLabel() == null){
			return false;
		}
		if(selectedItem != null){
			selectedItem.hideLabelBox();
			selectedItem.setSelectionStyle(false);
		}
		selectedItem = item;
		if(selectedItem != null){
			selectedItem.setSelectionStyle(true);
		}
		return true;
	}

	@Override
	public HandlerRegistration addSelectionHandler(
			SelectionHandler<NavigationItem> handler) {
		return addHandler(handler, SelectionEvent.getType());
	}

  @Override
  public HandlerRegistration addKeyUpHandler(KeyUpHandler handler) {
    return addDomHandler(handler, KeyUpEvent.getType());
  }
  
	@Override
	public void onClick(ClickEvent event) {
		final Object o = event.getSource();
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				if (o instanceof NavigationItem) {
					NavigationItem item = (NavigationItem)o;
					if(item.getLabelBox() == null)
						fireSelectionEvent(item);
				}
			}
		});
	}
	
	void fireSelectionEvent(NavigationItem item){
		if(select(item)){
			SelectionEvent.fire(NavigationBar.this, selectedItem);
		}
	}


}

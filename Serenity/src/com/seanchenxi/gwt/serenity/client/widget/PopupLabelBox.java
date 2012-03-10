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

import java.util.Iterator;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class PopupLabelBox extends PopupPanel implements ClickHandler,
		HasSelectionHandlers<Label> {

	private FlexTable table;
	private int row = 0, col = 0;
	private int columns;

	private Label selectedLabel;
	private String preSelection;

	public PopupLabelBox(int columns) {
		super(true, true);
		this.columns = columns;
		this.table = new FlexTable();
		this.table.setSize("100%", "auto");
		setWidget(new ScrollPanel(table));
		setGlassEnabled(false);
		setAutoHideOnHistoryEventsEnabled(true);
		setStyleName("popLabelBox");
	}

	public void showRelativeTo(Widget w) {
		show();
		int top = Math.max(w.getParent().getAbsoluteTop(), w.getAbsoluteTop()
				+ ((w.getOffsetHeight() - getOffsetHeight()) >> 1));
		int left = w.getAbsoluteLeft() + w.getOffsetWidth() - 5;
		setPopupPosition(left, top);
	}

	public void addLabel(String id, String text) {
		Label label = new Label(text);
		label.getElement().setId(id);
		label.setStyleName("popLabelBox-label");
		label.addClickHandler(this);
		table.setWidget(row, col, label);
		table.getCellFormatter().setVerticalAlignment(row, col, HasAlignment.ALIGN_MIDDLE);
		col++;
		if (col >= columns) {
			col = 0;
			row++;
		}
	}
	
	public Label findLabel(String id){
		if(id == null || id.isEmpty()) return null;
		for(Iterator<Widget> it = table.iterator(); it.hasNext();){
			Widget w = it.next();
			if(w instanceof Label && ((Label) w).getElement().getId().equalsIgnoreCase(id)){
				return (Label) w;
			}
		}
		return null;
	}
	
	public void clearSelection() {
		select(null);
	}
	
	public boolean select(Label label) {
		if(label == selectedLabel) 
			return false;
		if (selectedLabel != null) {
			selectedLabel.removeStyleName("selected");
		}
		selectedLabel = label;
		if(selectedLabel != null){
			selectedLabel.addStyleName("selected");
		}
		return true;
	}
	
	public String getSelectedLabel() {
		return selectedLabel == null ? null : selectedLabel.getElement().getId();
	}
	
	public void clearLabels() {
		selectedLabel = null;
		table.clear();
		row = 0;
		col = 0;
	}
	
	public void preSelectLabel(String slug) {
		preSelection = slug;
	}
	
	public String getPreSelection() {
		return preSelection;
	}

	@Override
	public void onClick(ClickEvent event) {
		final Object o = event.getSource();
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				if (o instanceof Label) {
					fireSelectionEvent((Label) o);
				}
			}
		});
	}

	@Override
	public HandlerRegistration addSelectionHandler(
			SelectionHandler<Label> handler) {
		return addHandler(handler, SelectionEvent.getType());
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
	}
	
	private void fireSelectionEvent(final Label label) {
		if(select(label)){
			SelectionEvent.fire(PopupLabelBox.this, label);
		}
	}
}

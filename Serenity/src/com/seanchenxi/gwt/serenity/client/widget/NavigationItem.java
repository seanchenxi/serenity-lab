package com.seanchenxi.gwt.serenity.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.seanchenxi.gwt.logging.api.Log;

public class NavigationItem extends Composite implements HasClickHandlers {

	private static final String DEPENDENT_STYLENAME_SELECTED_ITEM = "selected";

	private String id;
	private Image img;
	private PopupLabelBox labelBox;
	private boolean autoOpen;
	
	private HandlerRegistration mouseOverHR;
	private HandlerRegistration mouseOutHR;

	private NavigationItemHandler handler;
	private NavigationBar navBar;

	public NavigationItem(ImageResource imgSrc) {
		handler = new NavigationItemHandler();
		img = new Image(imgSrc);

		initWidget(new SimplePanel(img));
		setStyleName("navItem");
		addClickHandler(handler);
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setAutoOpen(boolean autoOpen) {
		this.autoOpen = autoOpen;
		if(autoOpen){
			if(mouseOutHR == null && mouseOverHR == null){
				mouseOutHR = img.addMouseOutHandler(handler);
				mouseOverHR = img.addMouseOverHandler(handler);
			}
		}else{
			if(mouseOutHR != null && mouseOverHR != null){
				mouseOutHR.removeHandler();
				mouseOverHR.removeHandler();
			}
		}
	}

	public void setLabelBox(PopupLabelBox popup) {
		if (popup == null)
			return;
		this.labelBox = popup;
		this.labelBox.addSelectionHandler(handler);
		this.labelBox.addCloseHandler(handler);
	}
	
	public PopupLabelBox getLabelBox() {
		return labelBox;
	}

	public void showLabelBox() {
		if (labelBox != null) {
			labelBox.showRelativeTo(this);
			setSelectionStyle(true);
		}
	}

	public void hideLabelBox() {
		if (labelBox != null) {
			labelBox.hide();
		}
	}

	public void setSelectedLabel(String slug) {
		if(labelBox != null){
			labelBox.preSelectLabel(slug);
		}
	}
	
	void hideLabelBox(boolean clearSelection) {
		if(labelBox != null){
			if(clearSelection) 
				labelBox.clearSelection();
			labelBox.hide(false);
		}	
	}
	
	void setNavigationBar(NavigationBar navBar) {
		this.navBar = navBar;
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return addDomHandler(handler, ClickEvent.getType());
	}

	public void setSelectionStyle(boolean selected) {
		if (selected) {
			addStyleDependentName(DEPENDENT_STYLENAME_SELECTED_ITEM);
		} else {
			removeStyleDependentName(DEPENDENT_STYLENAME_SELECTED_ITEM);
		}
	}

	private class NavigationItemHandler implements ClickHandler,
			MouseOutHandler, MouseOverHandler, SelectionHandler<Label>, CloseHandler<PopupPanel> {

		@Override
		public void onMouseOver(MouseOverEvent event) {
			if (autoOpen)
				showLabelBox();
		}

		@Override
		public void onMouseOut(MouseOutEvent event) {
			if (autoOpen)
				hideLabelBox();
		}

		@Override
		public void onClick(ClickEvent event) {
			if (!autoOpen)
				showLabelBox();
		}

		@Override
		public void onSelection(SelectionEvent<Label> event) {
			if(navBar != null && event.getSelectedItem() != null){
				labelBox.hide();
				Log.finest("[NavigationItem] onSelection - fire selection");
				navBar.select(NavigationItem.this);
				SelectionEvent.fire(navBar, NavigationItem.this);
			}
		}

		@Override
		public void onClose(CloseEvent<PopupPanel> event) {
			if(labelBox.getSelectedLabel() == null 
					|| labelBox.getSelectedLabel().isEmpty())
				setSelectionStyle(false);
		}

	}

}

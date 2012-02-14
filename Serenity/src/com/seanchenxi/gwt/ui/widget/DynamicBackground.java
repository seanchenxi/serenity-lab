package com.seanchenxi.gwt.ui.widget;

import java.util.ArrayList;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.seanchenxi.gwt.effect.core.Fade;

public class DynamicBackground extends Composite implements ResizeHandler {

	private Image img = null;
	private RepeatingCommand showCmd = null;
	private int delayMs = -1;

	public DynamicBackground(String url) {
		super();
		initWidget(img = new Image(url));
		initGUI();
	}

	private void initGUI() {
		img.addStyleName("bg-img");
		Style style = img.getElement().getStyle();
		style.setDisplay(Display.BLOCK);
		style.setZIndex(0);
		style.setPosition(Position.FIXED);
		style.setLeft(0, Unit.PX);
		style.setTop(0, Unit.PX);
		style.setOverflow(Overflow.HIDDEN);
		
		setPixelSize(Window.getClientWidth(), Window.getClientHeight());
	}

	public void setImageResources(
			final ArrayList<ImageResource> imageResources, int delayMs) {
		this.showCmd = new RepeatingCommand() {
			private int i = 0;

			@Override
			public boolean execute() {
				if (i < imageResources.size()) {
					img.setResource(imageResources.get(i));
					i++;
				} else {
					i = 0;
				}
				return true;
			}
		};
		this.delayMs = delayMs;
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		new Fade(this, 1800).fadeIn();
		if (showCmd != null && delayMs > 0) {
			Scheduler.get().scheduleFixedDelay(showCmd, delayMs);
		}
	}

	@Override
	public void setPixelSize(int width, int height) {
		super.setPixelSize(width, height);
		img.setPixelSize(width, height);
	}

	@Override
	public void onResize(ResizeEvent event) {
		setPixelSize(event.getWidth(), event.getHeight());
	}

}

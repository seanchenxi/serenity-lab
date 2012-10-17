package com.seanchenxi.gwt.deobfuscator.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;

public class Viewer extends SplitLayoutPanel implements AsyncCallback<String>{

	private static final String COPY_YOUR_TRACE_HERE = "Copy your trace here...";
	private final TextArea origin;
	private final HTML result;
	private final ScrollPanel scroller;
	
	public Viewer(){
		super();
		origin = new TextArea();
		origin.setText(COPY_YOUR_TRACE_HERE);
		scroller = new ScrollPanel(result = new HTML());		
		initGUI();
	}

	private void initGUI() {
		result.setSize("auto", "auto");
		result.getElement().getStyle().setPadding(5, Unit.PX);
		origin.getElement().getStyle().setMargin(5, Unit.PX);
		getElement().getStyle().setProperty("borderTop", "1px solid #333");
		addWest(origin, (Window.getClientWidth() >> 1) - 50);
		add(scroller);
		initHandlers();
	}

	public String getOriginalTrace() {
		String val = origin.getValue().trim();
		return COPY_YOUR_TRACE_HERE.equals(val) ? "" : val;
	}

	public boolean validTrace() {
		String val = getOriginalTrace();
		if(val.isEmpty()){
			result.setHTML("");
			Window.alert("No trace");
			return false;
		}
		return true;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		GWT.getUncaughtExceptionHandler().onUncaughtException(caught);
	}

	@Override
	public void onSuccess(String text) {
		result.setHTML(text);
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
		updateSize();
	}
	
	@Override
	public void onResize() {
		super.onResize();
		updateSize();
	}
	
	private String format(String input){
		if(COPY_YOUR_TRACE_HERE.equals(input) || input.isEmpty()) return input;
		String newString = input.replaceAll("[\\r\\n\\t]", "").replace("at Unknown.", "\n\tat Unknown.").replace("Caused by:", "\nCaused by:").trim();
		origin.setText(newString);
		return newString;
	}
	
	private void updateSize(){
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				Element el = getWidgetContainerElement(origin);
				origin.setPixelSize(el.getClientWidth() - 15, el.getClientHeight() - 15);
			}
		});
	}

	private void initHandlers() {
		origin.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				if(COPY_YOUR_TRACE_HERE.equalsIgnoreCase(origin.getValue().trim())){
					origin.setText("");
					origin.selectAll();
				}
			}
		});
		origin.addBlurHandler(new BlurHandler() {		
			@Override
			public void onBlur(BlurEvent event) {
				if(origin.getValue().isEmpty()){
					origin.setText(COPY_YOUR_TRACE_HERE);
				}else{
					format(origin.getValue());
				}
			}
		});
	}
}

package com.seanchenxi.gwt.deobfuscator.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class Deobfuscator implements EntryPoint, UncaughtExceptionHandler {

	private final DeobfuscatorServiceAsync deobfuscateService = GWT.create(DeobfuscatorService.class);

	private TopBar bar = new TopBar();
	private Viewer viewer = new Viewer();

	public void onModuleLoad() {
		GWT.setUncaughtExceptionHandler(this);
		
		RootLayoutPanel.get().add(bar);
		RootLayoutPanel.get().setWidgetTopHeight(bar, 5, Unit.PX, 30, Unit.PX);
		RootLayoutPanel.get().setWidgetLeftRight(bar, 0, Unit.PX, 0, Unit.PX);

		RootLayoutPanel.get().add(viewer);	
		RootLayoutPanel.get().setWidgetTopBottom(viewer, 40, Unit.PX, 0, Unit.PX);
		RootLayoutPanel.get().setWidgetLeftRight(viewer, 0, Unit.PX, 0, Unit.PCT);
		
		RootLayoutPanel.get().getElement().getStyle().setProperty("minWidth", "860px");
		RootLayoutPanel.get().getElement().getStyle().setProperty("minHeight", "430px");
		
		initHandlers();
		//loadData
		deobfuscateService.getAvailableModules(bar);
	}

	private void initHandlers() {
		bar.getDeobfuscateBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {				
					@Override
					public void execute() {
						if(!viewer.validTrace() || !bar.validSelection()){
							return;
						}
						String trace = viewer.getOriginalTrace();
						String moduleName = bar.getModule();
						String strongName = bar.getPermutation();
						deobfuscateService.deobfuscate(moduleName, strongName, trace, viewer);
					}
				});
			}
		});
		bar.getChange().addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {				
					@Override
					public void execute() {
						deobfuscateService.setModuleSymbolMapPath(bar.getSymbolMapsPath(), bar);
					}
				});
			}
		});
	}

	@Override
	public void onUncaughtException(Throwable e) {
		e.printStackTrace(System.err);
	}
}

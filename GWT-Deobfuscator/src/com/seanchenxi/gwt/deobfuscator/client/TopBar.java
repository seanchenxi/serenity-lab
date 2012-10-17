package com.seanchenxi.gwt.deobfuscator.client;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

public class TopBar extends HorizontalPanel implements AsyncCallback<HashMap<String, HashMap<String, String>>> {

	private static final String NBSPx4 = "&nbsp;&nbsp;|&nbsp;&nbsp;";
	private final ListBox modules;
	private final ListBox permuation;
	private final Button deobfuscateBtn;
	private final TextBox path;
	private final Button change;
	
	private final HashMap<String, HashMap<String, String>> map;

	public TopBar(){
		super();
		modules = new ListBox(false);
		permuation = new ListBox(false);
		deobfuscateBtn = new Button("Deobfuscate");
		path = new TextBox();
		change = new Button("Reload Symbol Maps");
		change.setEnabled(false);
		map = new HashMap<String, HashMap<String,String>>();		
		initGUI();
		initHandlers();
	}

	public String getModule(){
		return modules.getItemText(modules.getSelectedIndex()).trim();
	}
	
	public String getPermutation(){
		return permuation.getValue(permuation.getSelectedIndex()).trim();
	}
	
	public String getSymbolMapsPath(){
		return path.getValue();
	}
	
	public Button getDeobfuscateBtn() {
		return deobfuscateBtn;
	}
	
	public Button getChange() {
		return change;
	}
	
	private void injectData(HashMap<String, HashMap<String, String>> result){
		modules.clear();
		permuation.clear();
		map.clear();
		map.putAll(result);
		for (String module : map.keySet())
			modules.addItem(module);
		if (!map.isEmpty())
			modules.setSelectedIndex(0);
		updatePermutationSelector();
	}

	private void initHandlers() {
		modules.addChangeHandler(new ChangeHandler() {		
			@Override
			public void onChange(ChangeEvent event) {
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
					@Override
					public void execute() {
						updatePermutationSelector();
					}
				});
			}
		});
		path.addValueChangeHandler(new ValueChangeHandler<String>() {	
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
					@Override
					public void execute() {
						change.setEnabled(!path.getValue().isEmpty());
					}
				});
			}
		});
	}

	private void initGUI() {
		setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		setSpacing(2);
		add(new HTML("<b>Module:&nbsp;</b>"));
		add(modules);
		add(new HTML(NBSPx4));
		add(new HTML("<b>Permuation:&nbsp;</b>"));
		add(permuation);
		add(new HTML(NBSPx4));
		add(deobfuscateBtn);
		add(new HTML(NBSPx4));
		add(path);
		add(change);
	}
	
	private void updatePermutationSelector() {
		String t = modules.getItemText(modules.getSelectedIndex());
		HashMap<String, String> perm = map.get(t);
		for(String strongName : perm.keySet()){
			permuation.addItem(perm.get(strongName), strongName);
		}
		permuation.setSelectedIndex(0);
	}

	@Override
	public void onFailure(Throwable caught) {
		GWT.getUncaughtExceptionHandler().onUncaughtException(caught);
	}

	@Override
	public void onSuccess(HashMap<String, HashMap<String, String>> result) {
		injectData(result);
	}

	public boolean validSelection() {
		if(getModule() == null){
			Window.alert("No module");
			return false;
		}else if(getPermutation() == null){
			Window.alert("No permutation");
			return false;
		}
		return true;
	}
	
}

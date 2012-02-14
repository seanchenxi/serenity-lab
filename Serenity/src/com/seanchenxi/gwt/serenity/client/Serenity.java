package com.seanchenxi.gwt.serenity.client;

import java.util.logging.Level;

import com.google.gwt.core.client.EntryPoint;
import com.seanchenxi.gwt.logging.api.Log;

public class Serenity implements EntryPoint {

	public void onModuleLoad() {
		Log.setLevel(Level.FINEST);
		SerenityWeb.getInstance().run();
	}

}

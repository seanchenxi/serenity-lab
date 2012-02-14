package com.seanchenxi.gwt.wordpress.json.api.model;

public class IndexModel<T extends JModel> extends JModel {

	protected IndexModel() {

	}

	public final native int getCount() /*-{
		return this.count != null ? this.count : -1;
	}-*/;

}
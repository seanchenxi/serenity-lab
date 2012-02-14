package com.seanchenxi.gwt.wordpress.json.api.model;

import java.util.ArrayList;

import com.google.gwt.core.client.JsArray;
import com.seanchenxi.gwt.wordpress.json.util.JsUtil;

public class CategoryIndex extends IndexModel<Category> {
	
	protected CategoryIndex(){
		
	}

	protected final native JsArray<Category> getList() /*-{
		return this.categories;
	}-*/;
	
	public final ArrayList<Category> getCagetories(){
		return JsUtil.convert(getList());
	}

}

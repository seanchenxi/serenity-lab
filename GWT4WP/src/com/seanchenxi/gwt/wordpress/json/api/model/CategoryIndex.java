package com.seanchenxi.gwt.wordpress.json.api.model;

import java.util.ArrayList;

import com.google.gwt.core.client.JsArray;
import com.seanchenxi.gwt.wordpress.json.util.JUtil;

public class CategoryIndex extends IndexModel<Category> {
	
	protected CategoryIndex(){
		
	}

	protected final native JsArray<Category> getList() /*-{
		return this.categories;
	}-*/;
	
	public final ArrayList<Category> getCagetories(){
		return JUtil.convert(getList());
	}

}

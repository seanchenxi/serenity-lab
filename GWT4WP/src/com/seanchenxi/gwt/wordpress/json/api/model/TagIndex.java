package com.seanchenxi.gwt.wordpress.json.api.model;

import java.util.ArrayList;

import com.google.gwt.core.client.JsArray;
import com.seanchenxi.gwt.wordpress.json.util.JsUtil;

public class TagIndex extends IndexModel<Tag>{

	protected TagIndex(){
		
	}
	
	protected final native JsArray<Tag> getList() /*-{
		return this.tags;
	}-*/;
	
	public final ArrayList<Tag> getTags(){
		return JsUtil.convert(getList());
	}

}

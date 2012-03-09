package com.seanchenxi.gwt.wordpress.json.api.model;

import java.util.ArrayList;

import com.google.gwt.core.client.JsArray;
import com.seanchenxi.gwt.wordpress.json.util.JUtil;

public class PagingModel<M extends JModel> extends JModel {

  protected PagingModel(){
    
  }
  
  public final ArrayList<M> getList(){
    return JUtil.convert(getData());
  }
  
  public final native JsArray<M> getData() /*-{
    return this.posts;
  }-*/;

  public final native int getCount() /*-{
    return this.count != null ? this.count : -1;
  }-*/;

  public final native int getTotal() /*-{
    return this.count_total != null ? this.count_total : (this.count != null) ? this.count : -1;
  }-*/;

  public final native int getPageNumber() /*-{
    return this.pages != null ? this.pages : -1;
  }-*/;
  
}

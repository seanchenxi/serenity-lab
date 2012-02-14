package com.seanchenxi.gwt.wordpress.json.api.model;

public class WpImage extends JModel {

  protected WpImage(){
    
  }
  
  public final native String getURL() /*-{
		return this.url;
  }-*/;

  public final native void setURL(String url)/*-{
		this.url = url;
  }-*/;

  public final native int getWidth() /*-{
		return this.width;
  }-*/;

  public final native void setWidth(int width)/*-{
		this.width = width;
  }-*/;

  public final native int getHeight() /*-{
		return this.url;
  }-*/;

  public final native void setHeight(int height)/*-{
		this.height = height;
  }-*/;

}

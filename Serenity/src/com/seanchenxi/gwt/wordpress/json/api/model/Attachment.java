package com.seanchenxi.gwt.wordpress.json.api.model;

import java.util.ArrayList;

import com.google.gwt.core.client.JsArray;
import com.seanchenxi.gwt.wordpress.json.api.JUtil;

public class Attachment extends JModel {

  protected Attachment() {
  }

  public final native int getId() /*-{
		return this.id;
  }-*/;

  public final native void setId(int id) /*-{
		this.id = id;
  }-*/;

  public final native String getURL() /*-{
		return this.url;
  }-*/;

  public final native void setURL(String url)/*-{
		this.url = url;
  }-*/;

  public final native String getSlug()/*-{
		return this.slug;
  }-*/;

  public final native void setSlug(String slug)/*-{
		this.slug = slug;
  }-*/;

  public final native String getTitle()/*-{
		return this.title;
  }-*/;

  public final native void setTitle(String title)/*-{
		this.title = title;
  }-*/;

  public final native String getDescription() /*-{
		return this.description;
  }-*/;

  public final native void setDescription(String description) /*-{
		this.description = description;
  }-*/;

  public final native String getCaption() /*-{
		return this.caption;
  }-*/;

  public final native void setCaption(String caption) /*-{
		this.caption = caption;
  }-*/;

  public final native int getParentId() /*-{
		return this.parent;
  }-*/;

  public final native void setParentId(int parentId) /*-{
		this.parent = parentId;
  }-*/;

  public final native String getMimeType() /*-{
		return this.mime_type;
  }-*/;

  public final native void setMimeType(String mimeType) /*-{
		this.mime_type = mimeType;
  }-*/;

  private final native JsArray<WpImage> getJsArrayImages() /*-{
		return this.images;
  }-*/;

  private final native void setJsArrayImages(JsArray<WpImage> images) /*-{
		this.parent = images;
  }-*/;

  public final ArrayList<WpImage> getImages() {
    return JUtil.convert(getJsArrayImages());
  }

  public final void setImages(ArrayList<WpImage> images) {

  }

}

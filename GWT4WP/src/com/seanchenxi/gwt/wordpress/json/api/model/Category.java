package com.seanchenxi.gwt.wordpress.json.api.model;

public class Category extends JModel {

  protected Category() {
  }

  public final native int getId() /*-{
		return this.id;
  }-*/;

  public final native void setId(int id) /*-{
		this.id = id;
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

  public final native int getParentId() /*-{
		return this.parent;
  }-*/;

  public final native void setParentId(int parentId) /*-{
		this.parent = parentId;
  }-*/;

  public final native int getPostCount() /*-{
		return this.post_count;
  }-*/;
}

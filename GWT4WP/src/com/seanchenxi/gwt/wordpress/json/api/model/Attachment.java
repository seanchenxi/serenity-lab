/*******************************************************************************
 * Copyright 2012 Xi CHEN
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.seanchenxi.gwt.wordpress.json.api.model;

import java.util.ArrayList;

import com.google.gwt.core.client.JsArray;
import com.seanchenxi.gwt.wordpress.json.util.JUtil;

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

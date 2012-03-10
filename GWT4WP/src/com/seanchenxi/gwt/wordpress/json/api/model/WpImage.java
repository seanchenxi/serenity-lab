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

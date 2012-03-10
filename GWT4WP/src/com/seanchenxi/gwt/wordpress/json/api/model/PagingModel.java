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

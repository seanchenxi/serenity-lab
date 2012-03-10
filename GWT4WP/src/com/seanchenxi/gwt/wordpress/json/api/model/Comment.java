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

import java.util.Date;

import com.seanchenxi.gwt.wordpress.json.util.JUtil;

public class Comment extends JModel {
  
  protected Comment() {
  }

  public final native int getId() /*-{
		return this.id;
  }-*/;

  public final native void setId(int id) /*-{
		this.id = id;
  }-*/;

  public final native String getName() /*-{
		return this.name;
  }-*/;

  public final native void setName(String name)/*-{
		this.name = name;
  }-*/;

  public final native String getURL() /*-{
		return this.url;
  }-*/;

  public final native void setURL(String url)/*-{
		this.url = url;
  }-*/;
  
  public final native String getGravatarURL() /*-{
    return this.gravatar;
  }-*/;

  public final native String getContent()/*-{
		return this.content;
  }-*/;

  public final native void setContent(String content)/*-{
		this.content = content;
  }-*/;

  public final native String getDateString()/*-{
		return this.date;
  }-*/;

  public final Date getDate() {
    return JUtil.parse(getDateString());
  }

  public final native void setDateString(String dateString)/*-{
		this.date = date;
  }-*/;

  public final void setDate(Date date) {
    setDateString(JUtil.format(date));
  }

  public final native int getParentId() /*-{
		return this.parent;
  }-*/;

  public final native void setParentId(int parentId) /*-{
		this.parent = parentId;
  }-*/;

  public final native Author getAuthor()/*-{
		return this.author;
  }-*/;

  public final native void setAuthor(Author author)/*-{
		this.author = author;
  }-*/;

  public final native String getStatusString()/*-{
    return this.status;
  }-*/;

  public final native void setStatusString(String status)/*-{
    this.status = status;
  }-*/;
  
  public final CommentStatus getStatus(){
    return CommentStatus.parseValue(getStatusString());
  }
  
  public final void setStatus(CommentStatus status){
    setStatusString(status.getValue());
  }

}

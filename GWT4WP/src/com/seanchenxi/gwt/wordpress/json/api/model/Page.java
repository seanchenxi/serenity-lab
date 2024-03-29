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
import java.util.Date;

import com.google.gwt.core.client.JsArray;
import com.seanchenxi.gwt.wordpress.json.util.JUtil;

public class Page extends JModel {

  protected Page() {
  }

  public final native int getId() /*-{
		return this.id;
  }-*/;

  public final native void setId(int id) /*-{
		this.id = id;
  }-*/;

  public final native String getType() /*-{
		return this.type;
  }-*/;

  public final native void setType(String type)/*-{
		this.type = type;
  }-*/;

  public final native String getSlug()/*-{
		return this.slug;
  }-*/;

  public final native void setSlug(String slug)/*-{
		this.slug = slug;
  }-*/;

  public final native String getURL() /*-{
		return this.url;
  }-*/;

  public final native void setURL(String url)/*-{
		this.url = url;
  }-*/;

  public final native String getStatus() /*-{
		return this.status;
  }-*/;

  public final native void setStatus(String status)/*-{
		this.status = status;
  }-*/;

  public final native String getTitle()/*-{
		return this.title;
  }-*/;

  public final native void setTitle(String title)/*-{
		this.title = title;
  }-*/;

  public final native String getTitlePlain()/*-{
		return this.title_plain;
  }-*/;

  public final native void setTitlePlain(String titlePlain)/*-{
		this.titlePlain = titlePlain;
  }-*/;

  public final native String getContent()/*-{
		return this.content;
  }-*/;

  public final native void setContent(String content)/*-{
		this.content = content;
  }-*/;

  public final native String getExcerpt()/*-{
		return this.excerpt;
  }-*/;

  public final native void setExcerpt(String excerpt)/*-{
		this.excerpt = excerpt;
  }-*/;

  public final native String getCreatedDateString()/*-{
		return this.date;
  }-*/;

  public final native void setCreatedDateString(String createdDate)/*-{
		this.date = createdDate;
  }-*/;

  public final Date getCreatedDate() {
    return JUtil.parse(getCreatedDateString());
  }

  public final void setCreatedDate(Date createdDate) {
    setCreatedDateString(JUtil.format(createdDate));
  }

  public final native String getModifiedDateString()/*-{
		return this.modified;
  }-*/;

  public final native void setModifiedDateString(String modifiedDate)/*-{
		this.modified = modifiedDate;
  }-*/;

  public final Date getModifiedDate() {
    return JUtil.parse(getModifiedDateString());
  }

  public final void setModifiedDate(Date modifiedDate) {
    setModifiedDateString(JUtil.format(modifiedDate));
  }

  public final native Author getAuthor()/*-{
		return this.author;
  }-*/;

  public final native void setAuthor(Author author)/*-{
		this.author = author;
  }-*/;

  public final ArrayList<Comment> getComments() {
    return JUtil.convert(getJsArrayComments());
  }

  public final void setComments(ArrayList<Comment> comments) {
    throw new UnsupportedOperationException();
  }

  public final ArrayList<Attachment> getAttachments() {
    return JUtil.convert(getJsArrayAttachments());
  }

  public final void setAttachments(ArrayList<Attachment> attachments) {
    // TODO
    throw new UnsupportedOperationException();
  }

  public final native int getCommentCount()/*-{
		return this.comment_count;
  }-*/;

  public final native void setCommentCount(int number)/*-{
		this.comment_count = number;
  }-*/;
  
  public final PostCommentStatus getCommentStatus(){
    return PostCommentStatus.parseValue(getCommentStatusString());
  }

  public final void setPostCommentStatus(PostCommentStatus status){
    setCommentStatusString(status.getValue());
  }
  
  protected final native JsArray<Category> getJsArrayCategories()/*-{
    return this.categories;
  }-*/;
  
  protected final native void setJsArrayCategories(JsArray<Category> categories)/*-{
    this.categories = categories;
  }-*/;
  
  protected final native JsArray<Tag> getJsArrayTags()/*-{
    return this.tags;
  }-*/;
  
  protected final native void setJsArrayTags(JsArray<Tag> tags)/*-{
    this.tags = tags;
  }-*/;
  
  protected final native JsArray<Comment> getJsArrayComments() /*-{
    return this.comments;
  }-*/;
  
  protected final native void setJsArrayComments(JsArray<Comment> comments)/*-{
    this.comments = comments;
  }-*/;
  
  protected final native JsArray<Attachment> getJsArrayAttachments() /*-{
    return this.attachments;
  }-*/;
  
  protected final native void setJsArrayAttachments(JsArray<Attachment> attachments)/*-{
    this.attachments = attachments;
  }-*/;

  protected final native String getCommentStatusString()/*-{
    return this.comment_status;
  }-*/;
  
  protected final native void setCommentStatusString(String commentStatus)/*-{
    this.comment_status = commentStatus;
  }-*/;
  
}

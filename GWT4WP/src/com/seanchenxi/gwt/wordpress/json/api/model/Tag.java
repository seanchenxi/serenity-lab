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


public class Tag extends JModel {

	protected Tag() {
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


	public final native int getPostCount() /*-{
		return this.post_count;
	}-*/;

}

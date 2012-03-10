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

public class Author extends JModel {

  protected Author() {
  }

  public final native int getId() /*-{
		return this.id;
  }-*/;

  public final native void setId(int id) /*-{
		this.id = id;
  }-*/;

  public final native String getlogin() /*-{
		return this.slug;
  }-*/;

  public final native void setLogin(String login) /*-{
		this.slug = login;
  }-*/;

  public final native String getDisplayName() /*-{
		return this.name;
  }-*/;

  public final native void setDisplayName(String displayName) /*-{
		this.name = displayName;
  }-*/;

  public final native String getFirstName() /*-{
		return this.first_name;
  }-*/;

  public final native void setFirstName(String firstName) /*-{
		this.first_name = firstName;
  }-*/;

  public final native String getLastName() /*-{
		return this.last_name;
  }-*/;

  public final native void setLastName(String lastName) /*-{
		this.last_name = lastName;
  }-*/;

  public final native String getNickName() /*-{
		return this.nickname;
  }-*/;

  public final native void setNickName(String nickName) /*-{
		this.nickname = nickName;
  }-*/;

  public final native String getURL() /*-{
		return this.url;
  }-*/;

  public final native void setURL(String url) /*-{
		this.url = url;
  }-*/;

  public final native String getDescription() /*-{
		return this.description;
  }-*/;

  public final native void setDescription(String description) /*-{
		this.description = description;
  }-*/;

}

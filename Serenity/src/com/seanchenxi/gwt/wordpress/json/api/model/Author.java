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

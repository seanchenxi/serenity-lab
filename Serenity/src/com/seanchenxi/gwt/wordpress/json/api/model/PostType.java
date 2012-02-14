package com.seanchenxi.gwt.wordpress.json.api.model;

public enum PostType {
	
	POST("post"),
	PAGE("page");
	
	private String value;

	private PostType(){}
	
	private PostType(String value){
		this.value  = value;
	}
	
	public static PostType parseValue(String value){
		if("post".equalsIgnoreCase(value)) return POST;
		if("page".equalsIgnoreCase(value)) return PAGE;
		return null;
	}
	
	public String getValue() {
    return value;
  }
	
	@Override
	public String toString() {
		return (this.value);
	}
}

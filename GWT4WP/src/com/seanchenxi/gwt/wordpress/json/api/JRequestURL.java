package com.seanchenxi.gwt.wordpress.json.api;

public interface JRequestURL {
	JRequestURL setParameter(String name, Object value);

	void setEncode(boolean encode);

	boolean isEncode();

	String setPrefix(String urlPrefix);

	String getMethodName();
}

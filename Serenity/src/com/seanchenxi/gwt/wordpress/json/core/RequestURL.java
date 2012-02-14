package com.seanchenxi.gwt.wordpress.json.core;

import java.util.Date;
import java.util.HashMap;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.seanchenxi.gwt.wordpress.json.api.JMethod;
import com.seanchenxi.gwt.wordpress.json.api.JParameter;

public class RequestURL {

  protected final static DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat("yyy-MM-d");
  protected final static String START = "?";
  protected final static String AND = "&";
  protected final static String SLASH = "/";
  protected final static String EQUAL = "=";
  protected final static String AT = "%40";

  private JMethod method;
  private HashMap<String, Object> params;

  private String request;

  public RequestURL(JMethod submitcomment) {
    if (submitcomment == null)
      throw new NullPointerException();
    this.method = submitcomment;
    this.params = new HashMap<String, Object>();
    this.request = null;
  }

  public JMethod getMethod() {
    return method;
  }

  public RequestURL setParameter(String name, Object value) {
    if (name == null || name.trim().isEmpty() || value == null)
      throw new NullPointerException();
    this.params.put(name.trim(), value);
    if (request != null)
      this.request = null;
    return this;
  }

  public RequestURL setParameter(JParameter param, Object value) {
    if (param == null || value == null)
      throw new NullPointerException();
    this.params.put(param.toString(), value);
    if (request != null)
      this.request = null;
    return this;
  }

  public String create(String servicePath) {
    if (request == null) {
      StringBuilder sb = new StringBuilder(servicePath);
      sb.append(SLASH);
      sb.append(method);
      sb.append(SLASH);
      if (!params.isEmpty()) {
        sb.append(START);
        for (String name : params.keySet()) {
          sb.append(name);
          sb.append(EQUAL);
          sb.append(convert(params.get(name)));
          sb.append(AND);
        }
        sb.deleteCharAt(sb.lastIndexOf(AND));
      }
      request = sb.toString();
    }
    return request;
  }

  @Override
  public String toString() {
    return request;
  }

  private String convert(Object object) {
    if (object instanceof Date) {
      return DATE_FORMAT.format((Date) object);
    }
    return String.valueOf(object);
  }
}

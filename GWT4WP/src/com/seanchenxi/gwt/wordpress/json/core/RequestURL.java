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
package com.seanchenxi.gwt.wordpress.json.core;

import java.util.Date;
import java.util.HashMap;

import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.DateTimeFormat;

public class RequestURL {

  protected final static DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat("yyy-MM-d");
  protected final static String START = "?";
  protected final static String AND = "&";
  protected final static String SLASH = "/";
  protected final static String EQUAL = "=";
  protected final static String AT = "%40";

  private JMethod method;
  private HashMap<String, Object> params;

  private boolean encode;
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

  public void setEncode(boolean encode) {
    this.encode = encode;
  }
  
  public boolean isEncode() {
    return encode;
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
      if(encode){
        request = URL.encode(request);
      }
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

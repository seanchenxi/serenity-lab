package com.seanchenxi.resteasy.autobean.share;

import com.google.web.bindery.autobean.shared.AutoBean.PropertyName;

public interface RESTResponse {
  
  public enum Type {
    OK, EX
  }
  
  static final String PAYLOAD = "P";
  static final String TYPE = "T";

  @PropertyName(TYPE) Type getType();
  @PropertyName(TYPE) void setType(Type type);
  
  @PropertyName(PAYLOAD) String getPayload();
  @PropertyName(PAYLOAD) void setPayload(String type);
  
}

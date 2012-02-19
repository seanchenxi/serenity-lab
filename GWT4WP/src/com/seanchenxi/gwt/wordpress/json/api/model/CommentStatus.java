package com.seanchenxi.gwt.wordpress.json.api.model;

public enum CommentStatus {
  
  UNKNOW(""), PENDING("pending");
  
  private String value;

  private CommentStatus(String value) {
    this.value = value;
  }

  public static CommentStatus parseValue(String value) {
    for (CommentStatus cs : values()) {
      if (cs.value.equalsIgnoreCase(value)) {
        return cs;
      }
    }
    return UNKNOW;
  }

  public String getValue() {
    return value;
  }
  
  @Override
  public String toString() {
    return value;
  }

}

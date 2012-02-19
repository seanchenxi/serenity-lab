package com.seanchenxi.gwt.wordpress.json.api.model;

public enum PostCommentStatus {
  UNKNOW(""), OPEN("open"), CLOSED("closed");
  
  private String value;

  private PostCommentStatus(String value) {
    this.value = value;
  }

  public static PostCommentStatus parseValue(String value) {
    for (PostCommentStatus cs : values()) {
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

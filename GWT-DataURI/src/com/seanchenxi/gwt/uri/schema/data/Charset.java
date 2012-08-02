package com.seanchenxi.gwt.uri.schema.data;

public enum Charset {
  ISO_8859_1 {
    @Override
    public String getValue() {
      return "iso-8859-1";
    }
  },
  US_ASCII {
    @Override
    public String getValue() {
      return "us-ascii";
    }
  },
  UTF_8 {
    @Override
    public String getValue() {
      return "utf-8";
    }
  };

  public abstract String getValue();
}
package com.seanchenxi.gwt.wordpress.json.api;

public enum JParameter {
  POST_TYPE {
    @Override
    public String toString() {
      return "post_type";
    }
  }, 
  COUNT {
    @Override
    public String toString() {
      return "count";
    }
  }, 
  PAGE {
    @Override
    public String toString() {
      return "page";
    }
  }, 
  ID {
    @Override
    public String toString() {
      return "id";
    }
  }, 
  SLUG {
    @Override
    public String toString() {
      return "slug";
    }
  }, 
  DATE {
    @Override
    public String toString() {
      return "date";
    }
  }, 
  SEARCH {
    @Override
    public String toString() {
      return "search";
    }
  }, 
  NAME {
    @Override
    public String toString() {
      return "name";
    }
  }, 
  EMAIL {
    @Override
    public String toString() {
      return "email";
    }
  }, 
  CONTENT {
    @Override
    public String toString() {
      return "content";
    }
  }, 
  POST_ID {
    @Override
    public String toString() {
      return "post_id";
    }
  };
  
  public abstract String toString();
}

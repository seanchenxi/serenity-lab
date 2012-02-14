package com.seanchenxi.gwt.wordpress.json.api.model;

import java.util.ArrayList;

import com.seanchenxi.gwt.wordpress.json.api.JUtil;

public class Post extends Page {

  protected Post() {
  }

  public final ArrayList<Category> getCategories() {
    return JUtil.convert(getJsArrayCategories());
  }

  public final void setCategories(ArrayList<Category> categories) {
    // TODO
    throw new UnsupportedOperationException();
  }

  public final ArrayList<Tag> getTags() {
    return JUtil.convert(getJsArrayTags());
  }

  public final void setTags(ArrayList<Tag> tags) {
    // TODO
    throw new UnsupportedOperationException();
  }
}

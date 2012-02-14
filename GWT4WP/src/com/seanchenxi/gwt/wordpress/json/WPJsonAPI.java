package com.seanchenxi.gwt.wordpress.json;

import com.google.gwt.core.client.GWT;
import com.seanchenxi.gwt.wordpress.json.api.service.JCoreService;
import com.seanchenxi.gwt.wordpress.json.api.service.JPostsService;
import com.seanchenxi.gwt.wordpress.json.api.service.JRespondService;
import com.seanchenxi.gwt.wordpress.json.core.JCoreServiceImpl;
import com.seanchenxi.gwt.wordpress.json.core.JPostsServiceImpl;
import com.seanchenxi.gwt.wordpress.json.core.JRespondServiceImpl;

public class WPJsonAPI {
  
  private JCoreService coreService;
  private JPostsService postsService;
  private JRespondService respondService;
  
  public static WPJsonAPI get() {
    return JsonAPIHolder.INSTANCE;
  }
  
  public JCoreService getCoreService(){
    return coreService == null ? coreService = GWT.create(JCoreServiceImpl.class) : coreService;
  }
  
  public JPostsService getPostsService(){
    return postsService == null ? postsService = GWT.create(JPostsServiceImpl.class) : postsService;
  }
  
  public JRespondService getRespondService(){
    return respondService == null ? respondService = GWT.create(JRespondServiceImpl.class) : respondService;
  }
  
  private static class JsonAPIHolder {
    private static final WPJsonAPI INSTANCE = new WPJsonAPI();
  }
  
}

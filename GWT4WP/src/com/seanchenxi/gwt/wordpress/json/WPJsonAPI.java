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
package com.seanchenxi.gwt.wordpress.json;

import com.google.gwt.core.client.GWT;
import com.seanchenxi.gwt.wordpress.json.api.service.JCoreService;
import com.seanchenxi.gwt.wordpress.json.api.service.JPostsService;
import com.seanchenxi.gwt.wordpress.json.api.service.JRespondService;
import com.seanchenxi.gwt.wordpress.json.core.service.JCoreServiceImpl;
import com.seanchenxi.gwt.wordpress.json.core.service.JPostsServiceImpl;
import com.seanchenxi.gwt.wordpress.json.core.service.JRespondServiceImpl;

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

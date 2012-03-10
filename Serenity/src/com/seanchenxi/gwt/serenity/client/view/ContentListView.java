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
package com.seanchenxi.gwt.serenity.client.view;

import com.google.gwt.user.client.ui.IsWidget;

public interface ContentListView extends IsWidget {

  void bindPresenter(Presenter presenter);
  void clearContentList();
  void setPagingInfo(String title, int offset, int count, int total);
  void addContent(String slug, String title, String excerpt, String meta);
  void highlightContentItem(String contentSlug);
  
  public interface Presenter {
    void goForArticle(String slug);
    void nextPage();
    void prevPage();
  }
  
}

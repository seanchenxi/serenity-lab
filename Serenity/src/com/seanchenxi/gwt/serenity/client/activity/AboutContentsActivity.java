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
package com.seanchenxi.gwt.serenity.client.activity;

import com.seanchenxi.gwt.serenity.client.SerenityFactory;
import com.seanchenxi.gwt.serenity.client.place.AboutPlace;
import com.seanchenxi.gwt.serenity.client.place.SerenityPlace;
import com.seanchenxi.gwt.serenity.client.view.ContentListView;

public class AboutContentsActivity extends ContentListActivity {

  public AboutContentsActivity(String slug, int page, SerenityFactory clientFactory) {
    super(slug, page, clientFactory);
  }

  @Override
  protected void fetchContents(String slug, int offset, int size, final ContentListView view) {
    view.clearContentList();
    view.setPagingInfo("About",  0 , 3, 3);
    view.addContent("xi-chen", "Xi CHEN", "About me...", "");
    view.addContent("seanchenxi-com", "SeanChenXi.com", "About SeanChenXi.com", "-- Since 2007");
    view.addContent("theme", "Theme", "About SeanChenXi.com's Theme", "-- in 2012");
    if(slug != null && !slug.isEmpty()){
      view.highlightContentItem(slug);
    }
  }

  @Override
  public void goForArticle(String articleSlug) {
    clientFactory.getPlaceController().goTo(new AboutPlace(articleSlug));
  }
  
  @Override
  protected SerenityPlace nextPlace(String slug, int page) {
    return new AboutPlace("");
  }

}

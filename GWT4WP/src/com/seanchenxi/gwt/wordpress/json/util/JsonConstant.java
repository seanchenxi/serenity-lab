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
package com.seanchenxi.gwt.wordpress.json.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Constants;

public class JsonConstant {
  public final static OperationName OPERATION_NAME = GWT.create(OperationName.class);

  public interface OperationName extends Constants {

    @DefaultStringValue("get_post")
    String GetPost();

    @DefaultStringValue("get_page")
    String GetPage();

    @DefaultStringValue("get_recent_posts")
    String GetRecentPosts();

    @DefaultStringValue("get_date_posts")
    String GetDatePosts();

    @DefaultStringValue("get_category_posts")
    String GetCategoryPosts();

    @DefaultStringValue("get_tag_posts")
    String GetTagPosts();

    @DefaultStringValue("get_author_posts")
    String GetAuthorPosts();

    @DefaultStringValue("get_search_posts")
    String GetSearchPosts();

    @DefaultStringValue("respond/submit_comment")
    String SubmitComment();
  }
}

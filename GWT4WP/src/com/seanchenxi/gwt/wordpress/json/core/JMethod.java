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
package com.seanchenxi.gwt.wordpress.json.core;

public enum JMethod {
  
  GET_POST {
    @Override
    public String toString() {
      return "get_post";
    }
  }, 
  GET_PAGE {
    @Override
    public String toString() {
      return "get_page";
    }
  }, 
  GET_RECENT_POSTS {
    @Override
    public String toString() {
      return "get_recent_posts";
    }
  }, 
  GET_DATE_POSTS {
    @Override
    public String toString() {
      return "get_date_posts";
    }
  }, 
  GET_CAT_POSTS {
    @Override
    public String toString() {
      return "get_category_posts";
    }
  }, 
  GET_TAG_POSTS {
    @Override
    public String toString() {
      return "get_tag_posts";
    }
  }, 
  GET_AUTHOR_POSTS {
    @Override
    public String toString() {
      return "get_author_posts";
    }
  }, 
  GET_SEARCH_RESULTS {
    @Override
    public String toString() {
      return "get_search_results";
    }
  }, 
  SubmitComment {
    @Override
    public String toString() {
      return "submit_comment";
    }
  },
  GET_DATE_INDEX{
    @Override
    public String toString() {
      return "get_date_index";
    }
  },
  GET_CATEGORY_INDEX{
    @Override
    public String toString() {
      return "get_category_index";
    }
  },
  GET_TAG_INDEX{
    @Override
    public String toString() {
      return "get_tag_index";
    }
  },
  GET_AUTHOR_INDEX{
    @Override
    public String toString() {
      return "get_author_index";
    }
  },
  GET_PAGE_INDEX{
    @Override
    public String toString() {
      return "get_page_index";
    }
  },
  GET_NONCE{
    @Override
    public String toString() {
      return "get_nonce";
    }
  };
  
  public abstract String toString();
}

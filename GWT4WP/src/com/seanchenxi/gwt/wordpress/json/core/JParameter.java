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
  URL {
    @Override
    public String toString() {
      return "url";
    }
  }, 
  CONTENT {
    @Override
    public String toString() {
      return "content";
    }
  }, 
  PARENT {
    @Override
    public String toString() {
      return "parent";
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

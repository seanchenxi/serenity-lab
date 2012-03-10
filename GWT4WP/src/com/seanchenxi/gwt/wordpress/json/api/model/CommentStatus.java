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
package com.seanchenxi.gwt.wordpress.json.api.model;

public enum CommentStatus {
  
  UNKNOW(""), PENDING("pending"), OK("ok");
  
  private String value;

  private CommentStatus(String value) {
    this.value = value;
  }

  public static CommentStatus parseValue(String value) {
    for (CommentStatus cs : values()) {
      if (cs.value.equalsIgnoreCase(value)) {
        return cs;
      }
    }
    return UNKNOW;
  }

  public String getValue() {
    return value;
  }
  
  @Override
  public String toString() {
    return value;
  }

}

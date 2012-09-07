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
package com.seanchenxi.resteasy.autobean.share;

import com.google.web.bindery.autobean.shared.AutoBean.PropertyName;

public interface RESTResponse {
  
  public enum Type {
    OK, EX
  }
  
  static final String PAYLOAD = "P";
  static final String TYPE = "T";

  @PropertyName(TYPE) Type getType();
  @PropertyName(TYPE) void setType(Type type);
  
  @PropertyName(PAYLOAD) String getPayload();
  @PropertyName(PAYLOAD) void setPayload(String type);
  
}

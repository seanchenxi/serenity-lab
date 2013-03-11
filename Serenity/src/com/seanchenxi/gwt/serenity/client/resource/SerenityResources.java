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
package com.seanchenxi.gwt.serenity.client.resource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Dictionary;
import com.seanchenxi.gwt.serenity.client.resource.common.CommonResource;
import com.seanchenxi.gwt.serenity.client.resource.message.MessageResources;

public class SerenityResources {
  
  public static final String PAGING_CONFIG_ID = "PagingConfig";
  public static final String NAME = "name";
  public static final String CONTENT = "content";
  public static final String URL = "url";
  public static final String GENERATOR = "generator";
  public static final String SIZE = "size";

	public static final MessageResources MSG = GWT.create(MessageResources.class);
  
  public static final CommonResource COMMON = GWT.create(CommonResource.class);
  
	public static final Dictionary PAGING_CONFIG = Dictionary.getDictionary(PAGING_CONFIG_ID);
}

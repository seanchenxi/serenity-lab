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
package com.seanchenxi.gwt.serenity.client.resource.message;

import com.google.gwt.i18n.client.Constants;

public interface MessageResources extends Constants {
	
	@DefaultStringValue("Submit")
	String btn_submit();
	
	@DefaultStringValue("Name:")
	String lbl_name();

	@DefaultStringValue("Email:")
	String lbl_email();
	
	@DefaultStringValue("Content:")
	String lbl_content();
	
	@DefaultStringValue("http://wordpress.org")
  String wordpress_URL();
	
	@DefaultStringValue("Wordpress")
  String wordpress_Name();

	@DefaultStringValue("Serenity")
  String page_Title();

	@DefaultStringValue("非淡泊无以明志，非宁静无以致远")
  String page_subTitle();

}

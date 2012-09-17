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
package com.seanchenxi.gwt.serenity.client.view.impl;

import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;

public interface FooterTemplate extends SafeHtmlTemplates {

  @Template("<p>Copyright &copy; 2007 - {0} <a href=\"{1}\" title=\"{2}\">{1}</a></p>")
  SafeHtml copyright(String year, String url, String title);
  
  @Template("<p>Proudly powered by <a target=\"_blank\" href=\"{0}\" title=\"{1}\">{2}</a></p>")
  SafeHtml poweredBy(String url, String title, String name);
  
  @Template("<p>Theme by <a target=\"_blank\" href=\"{0}\" title=\"{1}\">{2}</a></p>")
  SafeHtml themeBy(String url, String title, String name);
}

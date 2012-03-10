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
package com.seanchenxi.gwt.ui.resource.image;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ImgSources extends ClientBundle {
	
  @Source("window/icon-error.gif")
  ImageResource icon_MsgBox_error();

  @Source("window/icon-info.gif")
  ImageResource icon_MsgBox_info();

  @Source("window/icon-question.gif")
  ImageResource icon_MsgBox_question();

  @Source("window/icon-warning.gif")
  ImageResource icon_MsgBox_warning();
}

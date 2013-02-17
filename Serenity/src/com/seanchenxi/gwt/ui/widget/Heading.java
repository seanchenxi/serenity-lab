/*
 * Copyright 2013 Xi CHEN
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.seanchenxi.gwt.ui.widget;

import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * Created by: Xi
 */
public class Heading extends Widget {

  private final static int LVL_MIN = 1;
  private final static int LVL_MAX = 6;
  private final Element heading;
  private Element small;

  @UiConstructor
  public Heading(int level){
    if (level < LVL_MIN || level > LVL_MAX) {
      throw new IllegalArgumentException("The level of the header must be between " + LVL_MIN + " and " + LVL_MAX + ".");
    }
    setElement(DOM.createDiv());
    DOM.appendChild(getElement(), heading = DOM.createElement("h" + level));
    setStyleName("heading");
  }

  public String getText() {
    return DOM.getInnerText(heading);
  }

  public void setText(String text) {
    DOM.setInnerText(heading, text);
  }

  public void setSubText(String subText) {
    if(subText != null && !subText.isEmpty()){
      DOM.appendChild(heading, small = DOM.createElement("small"));
      DOM.setInnerText(small, subText);
    }else if(small != null && heading.isOrHasChild(small)){
      DOM.removeChild(heading, small);
      small = null;
    }
  }

  public String getSubText(){
    return small == null ? null : DOM.getInnerText(small);
  }
}
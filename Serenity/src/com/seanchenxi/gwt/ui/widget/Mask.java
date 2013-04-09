/*
 * Copyright 2012 Xi CHEN
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.seanchenxi.gwt.ui.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class Mask {

  public interface Resources extends ClientBundle {

    public interface Style extends CssResource {
      final static String DEFAULT_CSS = "com/seanchenxi/gwt/ui/widget/Mask.css";
      String mask();
      String loaderIcon();
    }

    @ClientBundle.Source(Style.DEFAULT_CSS)
    Style style();

    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None, height = 42, width = 42)
    @ClientBundle.Source("com/seanchenxi/gwt/ui/resource/image/mask/ajax-loader.gif")
    ImageResource loaderIcon();
  }

  private static HandlerRegistration handlerRegistration;
  private static final HTML MASK;
  private static final Resources RESOURCES;

  static{
    RESOURCES = GWT.create(Resources.class);
    MASK = new HTML();
    MASK.getElement().setId("Mask");
    MASK.setStyleName(RESOURCES.style().mask());
  }

  public static boolean isShowing(){
    return MASK.isAttached();
  }

  public static void hideMask(){
    RootPanel.get().remove(MASK);
    MASK.setStyleName(RESOURCES.style().mask());
  }

  public static void showMaskFor(final Widget widget){
    clearHandler();
    if(widget.isAttached()){
      RESOURCES.style().ensureInjected();
      MASK.addStyleName(RESOURCES.style().loaderIcon());
      Element element = widget.getElement();
      MASK.setPixelSize(element.getOffsetWidth(), element.getOffsetHeight());
      RootPanel.get().add(MASK, DOM.getAbsoluteLeft(element), DOM.getAbsoluteTop(element));
    }else{
      delayHandler(widget);
    }
  }

  private static void delayHandler(final Widget widget){
    handlerRegistration = widget.addAttachHandler(new AttachEvent.Handler() {
      @Override
      public void onAttachOrDetach(AttachEvent event){
        Scheduler.get().scheduleDeferred(new Command() {
          @Override
          public void execute(){
            showMaskFor(widget);
          }
        });
      }
    });
  }

  private static void clearHandler(){
    if(handlerRegistration != null){
      handlerRegistration.removeHandler();
      handlerRegistration = null;
    }
  }


}

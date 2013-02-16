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

import com.google.gwt.animation.client.Animation;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.seanchenxi.gwt.serenity.client.resource.SerenityResources;
import com.seanchenxi.gwt.serenity.client.view.SerenityLayout;
import com.seanchenxi.gwt.ui.widget.Heading;

public class SerenityLayoutImpl extends Composite implements SerenityLayout {

  public interface Resources extends ClientBundle {

    public interface Style extends CssResource {
      final static String DEFAULT_CSS = "com/seanchenxi/gwt/serenity/client/resource/view/SerenityLayout.css";
      String headerContainer();
      String sidebarContainer();
      String contentListContainer();
      String footerContainer();
      String container();
      String layout();
    }

    @ClientBundle.Source(Style.DEFAULT_CSS)
    Style style();
    
  }
  
  interface SerenityLayoutImplUiBinder extends UiBinder<Widget, SerenityLayoutImpl> {}

  private static SerenityLayoutImplUiBinder UIBINDER = GWT.create(SerenityLayoutImplUiBinder.class);
  
  @UiField
  Resources resource;
  @UiField
  SimplePanel sidebarContainer;
  @UiField
  SimplePanel contentListContainer;
  @UiField
  SimplePanel footer;
  @UiField
  Heading header;

  private static ArticleViewContainer articleViewContainer;
	
	public SerenityLayoutImpl() {
		initWidget(UIBINDER.createAndBindUi(this));
    resource.style().ensureInjected();
    header.setText(SerenityResources.MSG.page_Title());
    header.setSubText(SerenityResources.MSG.page_subTitle());
	}

  @Override
  public void show() {
    RootPanel.get().insert(this, 0);
  }
  
  @Override
	public AcceptsOneWidget getSidebarContainer() {
		return sidebarContainer;
	}

	@Override
	public AcceptsOneWidget getContentListContainer() {
		return contentListContainer;
	}

  @Override
  public AcceptsOneWidget getArticleContainer() {
    if(articleViewContainer == null){
      Window.addResizeHandler(articleViewContainer = new ArticleViewContainer());
    }
    return articleViewContainer;
  }

  private class ArticleViewContainer implements AcceptsOneWidget, ResizeHandler {

    private final static int VIEW_MARGIN = 35;
    private final static int MAX_WIDTH = 1000;
    
    private double width;
    private double height;
    private double top;
    private double left;
    private IsWidget article;
    
    private ArticleViewContainer(){
      resetCoordinates();
    }
    
    @Override
    public void setWidget(final IsWidget w) {
      Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
        @Override
        public void execute() {
          if(article != null){
            removeAndShow(w);
          }else{
            show(w);
          }
        }
      });      
    }

    private void show(IsWidget w) {
      if(!(w instanceof ArticleViewImpl)){
          removeAndShow(null);
        return;
      }
      w.asWidget().setVisible(true);
      
      if(!(article = w).asWidget().isAttached())
        RootPanel.get().add(article);
      final Style style = article.asWidget().getElement().getStyle();
      final int distance = Window.getClientHeight() - VIEW_MARGIN;
      new ShowArticleAnimation(top, left, width, height, distance, style).run(300);
    }

    private void removeAndShow(final IsWidget w) {
      final Widget oldWidget = Widget.asWidgetOrNull(article);
      if(oldWidget == null){
          if(w != null) show(w);
          return;
      }
      new CloseArticleAnimation(oldWidget.getElement().getStyle()){
          @Override
          protected void onComplete(){
              super.onComplete();
              oldWidget.removeFromParent();
              article = null;
              if(w != null) show(w);
          }
      }.run(200);
    }
    
    @Override
    public void onResize(ResizeEvent event) {
      Scheduler.get().scheduleDeferred(new Command() {
        @Override
        public void execute() {
          resetCoordinates();
        }
      });
    }

    private void resetCoordinates() {
      width = Math.min(Window.getClientWidth() - (VIEW_MARGIN * 2), MAX_WIDTH);
      left = ((int)(Window.getClientWidth() - width)) >> 1;

      top = Window.getClientHeight();
      height = top - (VIEW_MARGIN * 2);
    }
  }
  
  private class ShowArticleAnimation extends Animation{

    private final Style articleStyle;
    private final double distance;
    
    private ShowArticleAnimation(double startTop, double startLeft, double startWidth, double startHeight, double distance, Style articleStyle){
      this.articleStyle = articleStyle;
      this.distance = distance;
      this.articleStyle.setTop(startTop, Style.Unit.PX);
      this.articleStyle.setLeft(startLeft, Style.Unit.PX);
      this.articleStyle.setWidth(startWidth, Style.Unit.PX);
      this.articleStyle.setHeight(startHeight, Style.Unit.PX);
    }

      @Override
      protected void onStart(){
          articleStyle.clearOpacity();
          super.onStart();
      }

      @Override
    protected void onUpdate(double progress) {
      final double top = progress == 1 ? ArticleViewContainer.VIEW_MARGIN : (Window.getClientHeight() - (distance * progress));
      articleStyle.setTop(top, Style.Unit.PX);  
    }
  }

  private class CloseArticleAnimation extends Animation{

        private final Style articleStyle;

        private CloseArticleAnimation(Style articleStyle){
            this.articleStyle = articleStyle;
        }

        @Override
        protected void onUpdate(double progress) {
            articleStyle.setOpacity(Math.max(0, 1 - progress));
        }
    }
}

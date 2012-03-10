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

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.layout.client.Layout.AnimationCallback;
import com.google.gwt.layout.client.Layout.Layer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.seanchenxi.gwt.serenity.client.view.SerenityLayout;

public class SerenityLayoutImpl extends LayoutPanel implements SerenityLayout {
	
	private static final Unit UNIT = Unit.PX;
	private static final int MIN_WIDTH = 1024;
	private static final int MIN_HEIGHT = 700;
	private static final int HEADER_HEIGHT = 70;
	private static final int MENU_WIDTH = 66;
	private static final int CONTENT_LIST_WIDTH = 650;
	private static final int CONTENT_WIDTH = 650;
	
	private LayoutPanel contentwrap = null;
	private AcceptsOneWidget sidebarContainer;
	private AcceptsOneWidget contentListContainer;
	private AcceptsOneWidget contentContainer;
	
	public SerenityLayoutImpl() {
		super();
		initGUI();
	}

	private void initGUI() {
		HTML title = new HTML("<h1 class=\"title\">Serenity</h1>");
		add(title);
		setWidgetTopHeight(title, 0, UNIT, HEADER_HEIGHT, UNIT);
		
		sidebarContainer = new PositionViewInjector(0, MENU_WIDTH);
		contentListContainer = new PositionViewInjector(MENU_WIDTH, CONTENT_LIST_WIDTH);
		contentContainer = new ArticleViewInjector();
	}

	@Override
	public void show(){
		RootLayoutPanel root = RootLayoutPanel.get();
		Style rootStyle = root.getElement().getStyle();
		rootStyle.setProperty("minWidth", MIN_WIDTH, UNIT);
		rootStyle.setProperty("minHeight", MIN_HEIGHT, UNIT);
		root.add(this);
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
		return contentContainer;
	}
	
	private class PositionViewInjector implements AcceptsOneWidget {

		private IsWidget widget;
		private double left, width;
		
		public PositionViewInjector(double left, double width){
			this.left = left;
			this.width = width;
		}
		
		@Override
		public void setWidget(IsWidget w) {
			if(w != null && w.asWidget().getParent() != SerenityLayoutImpl.this){
				add(widget = w);
				setWidgetLeftWidth(widget, left, UNIT, width, UNIT);
				setWidgetTopBottom(widget, HEADER_HEIGHT, UNIT, 0, UNIT);
			}
		}
	}
	
	private class ArticleViewInjector implements AcceptsOneWidget {	
		
		private IsWidget article;
		
		@Override
		public void setWidget(final IsWidget w) {
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {	
				@Override
				public void execute() {
					ensureAnimateWrap();
					if(article != null){
						removedAndShow(w);
					}else{
						show(w);
					}
				}
			});
		}
		
		private void ensureAnimateWrap(){
			if(contentwrap == null){
				add(contentwrap = new LayoutPanel());
				setWidgetLeftRight(contentwrap, (MENU_WIDTH + CONTENT_LIST_WIDTH), UNIT, 0, UNIT);
				setWidgetTopBottom(contentwrap, (HEADER_HEIGHT >> 1), UNIT, 25, UNIT);
			}
		}
		
		private void removedAndShow(final IsWidget w){
			contentwrap.setWidgetLeftWidth(article, contentwrap.getOffsetWidth() + 60, UNIT, CONTENT_WIDTH, UNIT);
			contentwrap.animate(384, new AnimationCallback() {
				@Override
				public void onAnimationComplete() {
					if(w == null){
						contentwrap.clear();
						setWidgetLeftRight(contentwrap, (MENU_WIDTH + CONTENT_LIST_WIDTH), UNIT, 0, UNIT);
						article = null;
					}else{
						if(article != null)
							article.asWidget().setVisible(false);
						Scheduler.get().scheduleDeferred(new ScheduledCommand() {
							@Override
							public void execute() {
								show(w);
							}
						});
					}
				}
				
				@Override
				public void onLayout(Layer layer, double progress) {}
			});
		}
		
		private void show(IsWidget w){
		  if(w == null) return;
			w.asWidget().setVisible(true);
			if(!(article = w).asWidget().isAttached())
				contentwrap.add(article);
			contentwrap.setWidgetLeftWidth(article, contentwrap.getOffsetWidth() + 50, UNIT, CONTENT_WIDTH, UNIT);
			contentwrap.forceLayout();
			setWidgetLeftRight(contentwrap, getLeftMargin() - 5, UNIT, 0, UNIT);
			contentwrap.setWidgetLeftWidth(article, 5, UNIT, CONTENT_WIDTH, UNIT);
			contentwrap.animate(640);
		}
		
		private double getLeftMargin(){
			int clientWidth = Math.max(MIN_WIDTH, Window.getClientWidth());
			if(clientWidth > 1500){
				return CONTENT_LIST_WIDTH + MENU_WIDTH - 22;
			}else{
				return (clientWidth - CONTENT_WIDTH) >> 1;
			}
		}
	}
}

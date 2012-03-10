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
package com.seanchenxi.gwt.effect.core;

import com.google.gwt.user.client.ui.Widget;
import com.seanchenxi.gwt.effect.api.Effect;
import com.seanchenxi.gwt.effect.api.EffectCallback;
import com.seanchenxi.gwt.effect.api.Parallelizable;

public class Blind extends Effect implements Parallelizable {
	
	final static String STYLE_CLIP = "clip";
	final static String AUTO_CLIP = "rect(auto, auto, auto, auto)";
	final static String ZERO_CLIP = "rect(0px, 0px, 0px, 0px)";
	
	public enum BlindDirection {
		UP, DOWN, LEFT, RIGHT, CENTER;
	}
	
	private int offsetWidth, offsetHeight;	
	private BlindDirection direction = BlindDirection.CENTER;
	private boolean isShowing = true;
	
	public Blind(Widget widget, int duration, BlindDirection direction) {
		super(widget, duration);
		setDirection(direction);
	}
	
	public Blind(Widget widget, BlindDirection direction){
		this(widget, 700, direction);
	}
	
	public Blind(Widget widget) {
		this(widget, BlindDirection.CENTER);
	}
	
	public void blind(){
		blind(true);
	}
	
	public void blind(boolean isShowing){
		blind(isShowing, null);
	}
	
	public void blind(EffectCallback callback){		
		blind(true, callback);
	}
	
	public void blind(boolean isShowing, EffectCallback callback){
		setShowing(isShowing);
		setCallback(callback);
		this.play();
	}
	
	public void setShowing(boolean isShowing) {
		this.isShowing = isShowing;
	}
	
	public void setDirection(BlindDirection direction) {
		this.direction = direction;
	}
	
	@Override
	public boolean doPrepare() {
		offsetWidth = getWidget().getOffsetWidth();
		offsetHeight = getWidget().getOffsetHeight();
		getStyle().setProperty(STYLE_CLIP, ZERO_CLIP);
		return true;
	}

	@Override
	public void doUpdate(double progress) {
		
		final double current_progress = isShowing ? progress : (1.0 - progress);
		
		// Determine the clipping size
		int top = 0;
		int left = 0;
		int right = 0;
		int bottom = 0;
		int height = (int) (current_progress * offsetHeight);
		int width = (int) (current_progress * offsetWidth);
		switch (direction) {
		case UP:
			top = offsetHeight - height;
			right = offsetWidth;
			bottom = offsetHeight;
			break;
		case DOWN:
			right = offsetWidth;
			bottom = height;
			break;
		case LEFT:
			left = offsetWidth - width;
			right = offsetWidth;
			bottom = offsetHeight;
			break;
		case RIGHT:
			right = width;
			bottom = offsetHeight;
			break;
		case CENTER:
			top = (offsetHeight - height) >> 1;
			left = (offsetWidth - width) >> 1;
			right = left + width;
			bottom = top + height;			
			break;
		}
		
		getStyle().setProperty(STYLE_CLIP, getRectString(top, right, bottom, left));
		
	}
	
	@Override
	protected void play() {
		if(doPrepare()){
			super.play();
		}
	}
	
	@Override
	protected void onUpdate(double progress) {
		doUpdate(progress);
	}
	
	private String getRectString(int top, int right, int bottom, int left) {
		return "rect(" + top + "px, " + right + "px, " + bottom + "px, " + left + "px)";
	}

}

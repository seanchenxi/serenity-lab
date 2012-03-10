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

public class Fade extends Effect implements Parallelizable {
	
	private double startOpacity, targetOpacity = 1;
	private double diff = 1;

	public Fade(Widget widget, int duration) {
		super(widget, duration);
	}

	public Fade(Widget widget){
		this(widget, 500);
	}
	
	public void setOpacity(double targetOpacity) {
		this.targetOpacity = targetOpacity;
	}
	
	public void fadeIn() {
		fadeTo(1);
	}
	
	public void fadeOut() {
		fadeTo(0);
	}
	
	public void fadeTo(double targetOpacity){
		setOpacity(targetOpacity);
		play();
	}
	
	public void fadeIn(EffectCallback callback) {
		setCallback(callback);
		fadeIn();
	}
	
	public void fadeOut(EffectCallback callback) {
		setCallback(callback);
		fadeOut();
	}
		
	public void fadeTo(double targetOpacity, EffectCallback callback) {
		setCallback(callback);
		fadeTo(targetOpacity);
	}
	
	@Override
	protected void play(){
		if(doPrepare()){
			super.play();
		}
	}
	
	protected double getOpacity(){
		String opacity = getStyle().getOpacity();
		if(opacity == null || opacity.trim().isEmpty()){
			getStyle().setOpacity(1);
			return 1;
		}
		return Double.valueOf(opacity).doubleValue();
	}

	@Override
	protected void onUpdate(double progress) {
		doUpdate(progress);
	}

	@Override
	public boolean doPrepare() {
		startOpacity = getOpacity();
		if(targetOpacity == 1 && startOpacity == 1){
			getStyle().setOpacity(0);
			startOpacity = 0;
		}
		diff = targetOpacity - startOpacity;
		return (diff!=0);
	}

	@Override
	public void doUpdate(double progress) {
		double val;
		getStyle().setOpacity(val = progress * diff + startOpacity);
		if(val == 1){
			getStyle().clearOpacity();
		}
	}

}

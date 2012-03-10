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
package com.seanchenxi.gwt.effect.api;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Widget;

public abstract class Effect extends Animation {
	
	protected int duration = 200;
	protected Widget widget = null;
	protected EffectCallback callback = null;

	public Effect(Widget widget, int duration) {
		this(widget, duration, null);
	}

	public Effect(Widget widget, int duration, EffectCallback callback) {
		super();
		setWidget(widget);
		setDuration(duration);
		this.callback = callback;
	}
	
	/**
     * @deprecated Use {@link #play} instead
     */
	@Deprecated
	public final void run(int duration) {
		super.run(duration);
	}
	
	/**
     * @deprecated Use {@link #play} instead
     */
	@Deprecated
	public final void run(int duration, double startTime) {
		super.run(duration, startTime);
	}

	public final void setCallback(EffectCallback callback) {
		this.callback = callback;
	}
	
	protected void play(){
		if(duration > 0){
			super.run(duration);
		}
	}
	
	protected final Widget getWidget() {
		return this.widget;
	}
	
	protected final void setWidget(Widget widget) {
		if(widget == null){
			throw new NullPointerException();
		}
		this.widget = widget;
	}
	
	protected final void setDuration(int duration) {
		if(duration <= 0){
			throw new IllegalStateException();
		}
		this.duration = duration;
	}
	
	@Override
	protected final void onCancel() {
		super.onCancel();
		if(callback != null)
			callback.onEffectCancel();
	}
	
	@Override
	protected final void onComplete() {
		super.onComplete();
		if(callback != null)
			callback.onEffectComplete();
	}

	protected final Element getElement(){
		return widget.getElement();
	}
	
	protected final Style getStyle(){
		return getElement().getStyle();
	}
}

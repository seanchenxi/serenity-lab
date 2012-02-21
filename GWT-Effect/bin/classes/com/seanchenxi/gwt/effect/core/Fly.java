package com.seanchenxi.gwt.effect.core;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.seanchenxi.gwt.effect.api.EffectCallback;
import com.seanchenxi.gwt.effect.api.Effect;
import com.seanchenxi.gwt.effect.api.Parallelizable;

public class Fly extends Effect implements Parallelizable {
	
	private int startLeft, startTop, targetLeft, targetTop = -1;
	private double distL, distT;

	public Fly(Widget widget, int duration) {
		super(widget, duration);
	}
	
	public Fly(Widget widget){
		this(widget, 700);
	}
	
	public void setTarget(int left, int top){
		this.targetLeft = Math.max(left, 0);
		this.targetTop = Math.max(top, 0);
	}
	
	public void setTargetRelativeToCenter(int left, int top){
		this.targetLeft = left + (Window.getClientWidth() - getWidget().getOffsetWidth()) >> 1;
		this.targetTop = top + (Window.getClientHeight() - getWidget().getOffsetHeight()) >> 1;
	}
	
	public void flyTo(int left, int top){
		setTarget(left, top);
		this.play();
	}
	
	public void flyTo(int left, int top, EffectCallback callback){
		setCallback(callback);		
		flyTo(left, top);
	}

	public void center(){
		flyTo(-1,-1);
	}
	
	public void center(EffectCallback callback){
		flyTo(-1, -1, callback);
	}
	
	@Override
	protected void play() {
		if(doPrepare())
			super.play();
	}

	@Override
	public void doUpdate(double progress) {
		getStyle().setTop(startTop + progress * distT, Unit.PX);
		getStyle().setLeft(startLeft + progress * distL, Unit.PX);
	}
	
	@Override
	public boolean doPrepare() {
		getStyle().setPosition(Position.ABSOLUTE);
		if(targetTop == -1){
			targetLeft = (Window.getClientWidth() - getWidget().getOffsetWidth()) >> 1;
			targetTop = (Window.getClientHeight() - getWidget().getOffsetHeight()) >> 1;
		}
		startLeft = getWidget().getAbsoluteLeft();
		startTop = getWidget().getAbsoluteTop();
		distL = targetLeft - startLeft;
		distT = targetTop - startTop;
		return true;
	}

	@Override
	protected void onUpdate(double progress) {
		doUpdate(progress);		
	}

}

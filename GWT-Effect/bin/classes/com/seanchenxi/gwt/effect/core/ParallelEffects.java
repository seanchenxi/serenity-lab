package com.seanchenxi.gwt.effect.core;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.seanchenxi.gwt.effect.api.EffectCallback;
import com.seanchenxi.gwt.effect.api.Effect;
import com.seanchenxi.gwt.effect.api.Parallelizable;

public class ParallelEffects extends Effect implements Parallelizable{
	
	private List<Parallelizable> effects = new ArrayList<Parallelizable>();
	
	public ParallelEffects(Widget widget, int duration) {
		super(widget, duration);
	}
	
	public ParallelEffects(Widget widget, int duration, EffectCallback callback) {
		super(widget, duration, callback);
	}
	
	public ParallelEffects(Widget widget, int duration, List<Parallelizable> effects, EffectCallback callback) {
		this(widget, duration, callback);
		setEffects(effects);
	}
	
	public void setEffects(List<Parallelizable> effects) {
		this.effects = effects;
	}
	
	public List<Parallelizable> getEffects() {
		return effects;
	}
	
	public boolean addEffect(Parallelizable effect){
		if(effects == null){
			effects = new ArrayList<Parallelizable>();
		}
		return effects.add(effect);
	}
	
	public void clearAll(){
		effects.clear();
	}
	
	public void playAll(){
		this.play();
	}
	
	@Override
	public boolean doPrepare() {
		if(effects == null || effects.isEmpty()){
			return false;
		}		
		for(Parallelizable p : effects){
			if(!p.doPrepare()){
				return false;
			}
		}
		return true;
	}

	@Override
	public void doUpdate(double progress) {
		for(Parallelizable p : effects){
			p.doUpdate(progress);
		}
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

}

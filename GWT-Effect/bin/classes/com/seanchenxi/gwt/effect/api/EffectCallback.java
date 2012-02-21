package com.seanchenxi.gwt.effect.api;

/**
 * The effect call back interface 
 * Used to integrate necessary treatment in effect 
 * 
 * @author Xi
 *
 */
public interface EffectCallback {
	
	/**
	 * Will be called after the effect animation was done
	 */
	void onEffectComplete();
	
	/**
	 * Will be called after the effect animation was canceled
	 */
	void onEffectCancel();
}

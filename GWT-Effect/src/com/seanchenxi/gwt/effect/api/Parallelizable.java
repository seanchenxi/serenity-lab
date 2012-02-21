package com.seanchenxi.gwt.effect.api;

/**
 * The Interface Parallelizable.
 * All parallelizable effects must implement this interface 
 */
public interface Parallelizable {
	
	/**
	 * doPrepare.
	 *
	 * @return true, if ready to do update
	 */
	boolean doPrepare();
	
	/**
	 * Do update.
	 *
	 * @param progress the progress
	 */
	void doUpdate(double progress);
	
}

package com.seanchenxi.resteasy.autobean.server;

import com.google.web.bindery.autobean.vm.AutoBeanFactorySource;
import com.seanchenxi.resteasy.autobean.share.RestAutoBeanFactory;

public class AutoBeanFactoryHolder {

	private static RestAutoBeanFactory FACTORY = null;
	private static String factoryClassName = null;

	public synchronized static void setFactoryClassName(String factoryClassName) {
		AutoBeanFactoryHolder.factoryClassName = factoryClassName;
	}

	@SuppressWarnings("unchecked")
	private synchronized static void maybeInitializeFactory() {
		try {
			if (AutoBeanFactoryHolder.FACTORY == null
					&& factoryClassName != null)
				AutoBeanFactoryHolder.FACTORY = AutoBeanFactorySource
						.create((Class<? extends RestAutoBeanFactory>) Class
								.forName(factoryClassName));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends RestAutoBeanFactory> T getFactory() {
		maybeInitializeFactory();
		return (T) FACTORY;
	}

}

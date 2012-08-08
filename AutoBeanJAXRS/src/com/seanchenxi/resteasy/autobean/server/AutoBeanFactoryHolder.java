package com.seanchenxi.resteasy.autobean.server;

import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.google.web.bindery.autobean.vm.AutoBeanFactorySource;

public class AutoBeanFactoryHolder {

	private static AutoBeanFactory FACTORY = null;
	private static String factoryClassName = null;

	public synchronized static void setFactoryClassName(String factoryClassName) {
		AutoBeanFactoryHolder.factoryClassName = factoryClassName;
	}

	@SuppressWarnings("unchecked")
	private synchronized static void maybeInitializeFactory() {
		try {
			if (AutoBeanFactoryHolder.FACTORY == null && factoryClassName != null)
				AutoBeanFactoryHolder.FACTORY = AutoBeanFactorySource.create((Class<? extends AutoBeanFactory>) Class.forName(factoryClassName));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends AutoBeanFactory> T getFactory() {
		maybeInitializeFactory();
		return (T) FACTORY;
	}

}

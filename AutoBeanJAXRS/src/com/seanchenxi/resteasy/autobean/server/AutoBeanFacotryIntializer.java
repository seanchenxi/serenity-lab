package com.seanchenxi.resteasy.autobean.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AutoBeanFacotryIntializer implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String className = sce.getServletContext().getInitParameter("restautobean.factory");
		AutoBeanFactoryHolder.setFactoryClassName(className);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}

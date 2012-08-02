package com.seanchenxi.resteasy.autobean.share;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

public interface RestAutoBeanFactory extends AutoBeanFactory {
	AutoBean<ThrowableBean> throwable();
	AutoBean<StackTraceElementBean> stackTraceElement();
}

package com.seanchenxi.resteasy.autobean.share;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

public interface RESTBeanFactory extends AutoBeanFactory {

  AutoBean<RESTResponse> resposne();

  AutoBean<RESTResponse> resposne(RESTResponse toWrap);

  AutoBean<ThrowableBean> throwable();

  AutoBean<ThrowableBean> throwable(ThrowableBean toWrap);

  AutoBean<StackTraceElementBean> stackTraceElement();

  AutoBean<StackTraceElementBean> stackTraceElement(StackTraceElementBean toWrap);
}

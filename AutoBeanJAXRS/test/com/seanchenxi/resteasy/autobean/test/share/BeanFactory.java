package com.seanchenxi.resteasy.autobean.test.share;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

public interface BeanFactory extends AutoBeanFactory {
  AutoBean<Greeting> greeting();
}

package com.seanchenxi.resteasy.autobean.test.share;

import com.google.web.bindery.autobean.shared.AutoBean.PropertyName;

public interface Greeting {

  @PropertyName("n") String getUserName();
  @PropertyName("n") void setUserName(String name);
  
  @PropertyName("o") boolean getOK();
  @PropertyName("o") void setOK(boolean ok);
  
  @PropertyName("m") String getMessage();
  @PropertyName("m") void setMessage(String mesage);
  
}

package com.seanchenxi.resteasy.autobean.test.server;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.vm.AutoBeanFactorySource;
import com.seanchenxi.resteasy.autobean.test.client.GreetingServiceM;
import com.seanchenxi.resteasy.autobean.test.share.BeanFactory;
import com.seanchenxi.resteasy.autobean.test.share.FieldVerifier;
import com.seanchenxi.resteasy.autobean.test.share.Greeting;


public class GreetingServiceMImpl implements GreetingServiceM {

  private final static BeanFactory FACTORY = AutoBeanFactorySource.create(BeanFactory.class);
  
  public String greetServer(String input, boolean ok) throws IllegalArgumentException {
    
    if (!FieldVerifier.isValidName(input)) {
      throw new IllegalArgumentException("Name must be at least 4 characters long");
    }

    input = escapeHtml(input);
    
    return "Hello, " + input + "-" + ok + "!<br><br>I am running " + "serverInfo"
        + ".<br><br>It looks like you are using:<br>" + "userAgent";
  }

  public Response greetServerObject(String input, boolean ok) throws IllegalArgumentException {
    
    if (!FieldVerifier.isValidName(input)) {
      throw new IllegalArgumentException("Name must be at least 4 characters long");
    }

    input = escapeHtml(input);

    AutoBean<Greeting> bean = FACTORY.greeting();
    Greeting g = bean.as();
    g.setUserName(input);
    g.setOK(ok);
    g.setMessage("Hello, " + input + "-" + ok + "!<br><br>I am running " + "serverInfo"
        + ".<br><br>It looks like you are using:<br>" + "userAgent");
    return Response.ok(AutoBeanCodex.encode(bean).getPayload()).type(MediaType.TEXT_PLAIN).build();
  }

  private String escapeHtml(String html) {
    if (html == null) {
      return null;
    }
    return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
  }
}

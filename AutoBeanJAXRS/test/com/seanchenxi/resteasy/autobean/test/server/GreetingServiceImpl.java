package com.seanchenxi.resteasy.autobean.test.server;

import com.google.web.bindery.autobean.vm.AutoBeanFactorySource;
import com.seanchenxi.resteasy.autobean.test.client.GreetingService;
import com.seanchenxi.resteasy.autobean.test.share.BeanFactory;
import com.seanchenxi.resteasy.autobean.test.share.FieldVerifier;
import com.seanchenxi.resteasy.autobean.test.share.Greeting;


public class GreetingServiceImpl implements GreetingService {

  private final static BeanFactory FACTORY = AutoBeanFactorySource.create(BeanFactory.class);
  
  public String greetServer(String input, boolean ok) throws IllegalArgumentException {
    // Verify that the input is valid. 
    if (!FieldVerifier.isValidName(input)) {
      // If the input is not valid, throw an IllegalArgumentException back to
      // the client.
      throw new IllegalArgumentException("Name must be at least 4 characters long");
    }

//    String serverInfo = getServletContext().getServerInfo();
//    String userAgent = getThreadLocalRequest().getHeader("User-Agent");

    // Escape data from the client to avoid cross-site script vulnerabilities.
    input = escapeHtml(input);
//    userAgent = escapeHtml(userAgent);

    return "Hello, " + input + "-" + ok + "!<br><br>I am running " + "serverInfo"
        + ".<br><br>It looks like you are using:<br>" + "userAgent";
  }

  public Greeting greetServerObject(String input, boolean ok) throws IllegalArgumentException {
    // Verify that the input is valid. 
    if (!FieldVerifier.isValidName(input)) {
      // If the input is not valid, throw an IllegalArgumentException back to
      // the client.
      throw new IllegalArgumentException("Name must be at least 4 characters long");
    }

//    String serverInfo = getServletContext().getServerInfo();
//    String userAgent = getThreadLocalRequest().getHeader("User-Agent");

    // Escape data from the client to avoid cross-site script vulnerabilities.
    input = escapeHtml(input);
//    userAgent = escapeHtml(userAgent);

    Greeting g = FACTORY.greeting().as();
    g.setUserName(input);
    g.setOK(ok);
    g.setMessage("Hello, " + input + "-" + ok + "!<br><br>I am running " + "serverInfo"
        + ".<br><br>It looks like you are using:<br>" + "userAgent");
    return g;
  }
  
  /**
   * Escape an html string. Escaping data received from the client helps to
   * prevent cross-site script vulnerabilities.
   * 
   * @param html the html string to escape
   * @return the escaped string
   */
  private String escapeHtml(String html) {
    if (html == null) {
      return null;
    }
    return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
  }
}

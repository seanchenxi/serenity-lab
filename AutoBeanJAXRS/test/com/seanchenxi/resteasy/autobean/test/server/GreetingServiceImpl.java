/*******************************************************************************
 * Copyright 2012 Xi CHEN
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.seanchenxi.resteasy.autobean.test.server;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import com.google.web.bindery.autobean.vm.AutoBeanFactorySource;
import com.seanchenxi.resteasy.autobean.test.client.GreetingService;
import com.seanchenxi.resteasy.autobean.test.share.BeanFactory;
import com.seanchenxi.resteasy.autobean.test.share.FieldVerifier;
import com.seanchenxi.resteasy.autobean.test.share.Greeting;


public class GreetingServiceImpl implements GreetingService {

  private final static BeanFactory FACTORY = AutoBeanFactorySource.create(BeanFactory.class);
  
  private @Context ServletContext servletContext;
  private @Context HttpServletRequest servletRequset;
  
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

    String serverInfo = servletContext.getServerInfo();
    String userAgent = servletRequset.getHeader("User-Agent");

    // Escape data from the client to avoid cross-site script vulnerabilities.
    input = escapeHtml(input);
    userAgent = escapeHtml(userAgent);

    Greeting g = FACTORY.greeting().as();
    g.setUserName(input);
    g.setOK(ok);
    g.setMessage("Hello, " + input + "-" + ok + "!<br><br>I am running " + serverInfo
        + ".<br><br>It looks like you are using:<br>" + userAgent);
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

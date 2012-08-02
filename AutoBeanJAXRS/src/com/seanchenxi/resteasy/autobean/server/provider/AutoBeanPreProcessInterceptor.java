package com.seanchenxi.resteasy.autobean.server.provider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;

@Provider
@ServerInterceptor
public class AutoBeanPreProcessInterceptor implements PreProcessInterceptor {

	@Override
	public ServerResponse preProcess(HttpRequest request, ResourceMethod method)
			throws Failure, WebApplicationException {
		InputStreamReader reader = new InputStreamReader(request.getInputStream());
		BufferedReader input = new BufferedReader(reader);
		StringBuilder sb = new StringBuilder();
		String readed;
		try {
			while ((readed = input.readLine()) != null) {
				sb.append(readed);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String payload = sb.toString();
		System.out.println("readFrom for payload: " + payload);
		return null;
	}

}

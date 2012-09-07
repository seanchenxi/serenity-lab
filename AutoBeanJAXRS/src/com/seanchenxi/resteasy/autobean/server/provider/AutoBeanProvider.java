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
package com.seanchenxi.resteasy.autobean.server.provider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;
import com.google.web.bindery.autobean.vm.AutoBeanFactorySource;
import com.seanchenxi.resteasy.autobean.share.RESTBeanFactory;
import com.seanchenxi.resteasy.autobean.share.RESTResponse;
import com.seanchenxi.resteasy.autobean.share.StackTraceElementBean;
import com.seanchenxi.resteasy.autobean.share.ThrowableBean;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AutoBeanProvider implements MessageBodyReader<Object>, MessageBodyWriter<Object>, ExceptionMapper<Throwable> {

	protected final static String CONTENT_ENCODING = "Content-Encoding";
	protected final static String ACCEPT_ENCODING = "Accept-Encoding";
	protected final static String CONTENT_ENCODING_GZIP = "gzip";
	
	protected final static int BUFFER_SIZE = 4096;
	protected final static String CHARSET_UTF8 = "UTF-8";
	protected final static String GENERIC_FAILURE_MSG = "The call failed on the server; see server log for details";
	
	private final static Logger Log = Logger.getLogger(AutoBeanProvider.class.getName());
	
	private final static RESTBeanFactory REST_BEAN_FACTORY = AutoBeanFactorySource.create(RESTBeanFactory.class);

	protected final static HashSet<Class<?>> untouchables = new HashSet<Class<?>>();
	static {
		untouchables.add(Boolean.class);
		untouchables.add(Character.class);
		untouchables.add(Byte.class);
		untouchables.add(Short.class);
		untouchables.add(Integer.class);
		untouchables.add(Long.class);
		untouchables.add(Float.class);
		untouchables.add(Double.class);
		untouchables.add(Void.class);
		untouchables.add(String.class);
	}
	
	@Override
	public long getSize(Object arg0, Class<?> type, Type arg2, Annotation[] arg3, MediaType mediaType) {
		return -1;
	}

	@Override
	public boolean isWriteable(Class<?> type, Type arg1, Annotation[] arg2,
			MediaType mediaType) {
		return !Throwable.class.isAssignableFrom(type) && isJsonType(mediaType);
	}

	@Override
	public void writeTo(Object arg0, Class<?> type, Type arg2,
			Annotation[] arg3, MediaType arg4,
			MultivaluedMap<String, Object> arg5, OutputStream out)
			throws IOException, WebApplicationException {
	  AutoBean<RESTResponse> responseBean = REST_BEAN_FACTORY.resposne();
	  responseBean.as().setType(arg0 instanceof ThrowableBean ? RESTResponse.Type.EX : RESTResponse.Type.OK);
	  if(untouchables.contains(type)){
	    responseBean.as().setPayload("\"" + arg0 != null ? String.valueOf(arg0) : arg0 + "\"");
	  }else{
	    responseBean.as().setPayload(AutoBeanCodex.encode(AutoBeanUtils.getAutoBean(arg0)).getPayload());
	  }
		out.write(AutoBeanCodex.encode(responseBean).getPayload().getBytes(CHARSET_UTF8));
	}

	@Override
	public boolean isReadable(Class<?> type, Type arg1, Annotation[] arg2, MediaType mediaType) {
		return true;
	}

	@Override
	public Object readFrom(Class<Object> type, Type arg1, Annotation[] arg2,
			MediaType arg3, MultivaluedMap<String, String> arg4, InputStream in)
			throws IOException, WebApplicationException {
		InputStreamReader reader = new InputStreamReader(in);
		BufferedReader input = new BufferedReader(reader);
		StringBuilder sb = new StringBuilder();
		String readed;
		while ((readed = input.readLine()) != null) {
			sb.append(readed);
		}
		//TODO need to deserialize
		return sb.toString();
	}
	
	protected boolean isJsonType(MediaType mediaType){
        if (mediaType != null) {
            String subtype = mediaType.getSubtype();
            return "json".equalsIgnoreCase(subtype) || subtype.endsWith("+json");
        }
        return true;
    }

	@Override
	public Response toResponse(Throwable failure) {
		Log.log(Level.SEVERE, failure.getMessage(), failure);
		return Response.status(Response.Status.OK).entity(convert(failure)).type(MediaType.APPLICATION_JSON).build();
	}

	private ThrowableBean convert(Throwable failure){
		ThrowableBean tb = REST_BEAN_FACTORY.throwable().as();
		tb.setExceptionType(failure.getClass().getName());
		tb.setMessage(failure.getMessage());
		List<StackTraceElementBean> stackTraceBeans = new ArrayList<StackTraceElementBean>();
		for(StackTraceElement ste : failure.getStackTrace()){
			StackTraceElementBean steb = REST_BEAN_FACTORY.stackTraceElement().as();
			steb.setClassName(ste.getClassName());
			steb.setFileName(ste.getFileName());
			steb.setLineNumber(ste.getLineNumber());
			steb.setMethodName(ste.getMethodName());
			stackTraceBeans.add(steb);
		}
		tb.setStackTrace(stackTraceBeans);
		if(failure.getCause() != null){
			tb.setCause(convert(failure.getCause()));
		}
		return tb;
	}
}

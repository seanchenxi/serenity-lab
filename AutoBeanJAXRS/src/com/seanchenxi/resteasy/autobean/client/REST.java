package com.seanchenxi.resteasy.autobean.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.google.web.bindery.autobean.shared.Splittable;
import com.seanchenxi.resteasy.autobean.share.RESTBeanFactory;
import com.seanchenxi.resteasy.autobean.share.RESTResponse;
import com.seanchenxi.resteasy.autobean.share.StackTraceElementBean;
import com.seanchenxi.resteasy.autobean.share.ThrowableBean;

public class REST {
	
	public static final String JSON_CONTENT_TYPE_UTF8 = "application/json; charset=utf-8";
	public static final String ACCEPT_JSON = "application/json";
	public static final String ACCEPT_ALL = "*/*";

	private static final ThrowableFactory THROWABLE_FACTORY = GWT.create(ThrowableFactory.class);
	private static final RESTBeanFactory REST_BEAN_FACTORY = GWT.create(RESTBeanFactory.class);
	
	private static AutoBeanFactory FACTORY;
	
	public static <T extends AutoBeanFactory> void registerFactory(T factory){
		REST.FACTORY = factory;
	}
	
	@SuppressWarnings("unchecked")
	public static <U> U decodeData(Class<U> clazz, Splittable data) throws SerializationException {
	  assert FACTORY != null : "REST.register(factory) to register your autobean factory class";
		try {
			if(data.isBoolean()){
				return (U) Boolean.valueOf(data.asBoolean());
			}else if(data.isString()){
				return (U) String.valueOf(data.asString());
			}else if(data.isNumber()){
				Double number = Double.valueOf(data.asNumber());
				if(Integer.class.equals(clazz)){
					return (U) Integer.valueOf(number.intValue());
				}else if(Float.class.equals(clazz)){
					return (U) Float.valueOf(number.floatValue());
				}else if(Long.class.equals(clazz)){
					return (U) Long.valueOf(number.longValue());
				}else{
					return (U) number;
				}
			}else{
			  AutoBean<U> bean = AutoBeanCodex.decode(FACTORY, clazz, data);
				return bean != null ? bean.as() : null;
			}
		} catch (Exception e) {
			throw new SerializationException(e);
		}
	}

	public static <T, U extends T> AutoBean<T> createAutoBean(Class<T> clazz, U toWrap) throws SerializationException {
	  assert FACTORY != null : "REST.register(factory) to register your autobean factory class";
	  try {
			AutoBean<T> bean = FACTORY.create(clazz, toWrap);
			return bean;
		} catch (Exception e) {
			throw new SerializationException(e);
		}
	}
	
	public static boolean isReturnValue(RESTResponse restResponse) {
    return restResponse != null && RESTResponse.Type.OK.equals(restResponse.getType());
  }
	
	public static boolean isThrowable(RESTResponse restResponse){
	  return restResponse != null && RESTResponse.Type.EX.equals(restResponse.getType());
	}
	
	public static RESTResponse decodeResponse(String encodedResponse) throws SerializationException {
	  if(encodedResponse == null || encodedResponse.trim().isEmpty()) return null;
    try {
      AutoBean<RESTResponse> bean = AutoBeanCodex.decode(REST_BEAN_FACTORY, RESTResponse.class, encodedResponse);
      return bean.as();
    } catch (Exception e) {
      throw new SerializationException(e);
    }
  }
	
	public static Throwable decodeThrowable(String throwablePayload) throws SerializationException {
		try {
			AutoBean<ThrowableBean> bean = AutoBeanCodex.decode(REST_BEAN_FACTORY, ThrowableBean.class, throwablePayload);
			return convert(bean.as());
		} catch (Exception e) {
			throw new SerializationException(e);
		}
	}
	
	private static Throwable convert(ThrowableBean bean){
		Throwable caught = THROWABLE_FACTORY.create(bean.getExceptionType(), bean.getMessage());
		if(caught == null) return new Throwable(bean.getExceptionType() + " (please to see server log): ");
		ThrowableBean causeBean = bean.getCause();
		if(causeBean != null) caught.initCause(convert(causeBean));
		List<StackTraceElementBean> stebs = bean.getStackTrace();
		if(stebs != null && !stebs.isEmpty()){
			StackTraceElement[] stackTrace = new StackTraceElement[stebs.size()];
			for(int i = 0 ; i < stebs.size() ; i ++){
				StackTraceElementBean steb = stebs.get(i);
				if(steb != null)
					stackTrace[i] = new StackTraceElement(steb.getClassName(), steb.getMethodName(), steb.getFileName(), steb.getLineNumber());
			}
			caught.setStackTrace(stackTrace);
		}
		return caught;
	}

  
}

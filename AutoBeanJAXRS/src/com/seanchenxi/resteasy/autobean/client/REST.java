package com.seanchenxi.resteasy.autobean.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;
import com.google.web.bindery.autobean.shared.Splittable;
import com.seanchenxi.resteasy.autobean.share.RestAutoBeanFactory;
import com.seanchenxi.resteasy.autobean.share.StackTraceElementBean;
import com.seanchenxi.resteasy.autobean.share.ThrowableBean;

public class REST {
	
	public static final String JSON_CONTENT_TYPE_UTF8 = "application/json; charset=utf-8";
	public static final String ACCEPT_JSON = "application/json";
	public static final String ACCEPT_ALL = "*/*";

	private static final ThrowableFactory THROWABLE_FACTORY = GWT.create(ThrowableFactory.class);
	
	private static RestAutoBeanFactory FACTORY;
	
	public static <T extends RestAutoBeanFactory> void registerFactory(T factory){
		REST.FACTORY = factory;
	}

	public static <T, U extends T> String encodeRequestData(U...delegates) throws SerializationException {
		if (delegates == null) return null;
		StringBuilder sb = new StringBuilder("{");
		for(U delegate : delegates){
			if (delegate instanceof String){
				sb.append("i:\""+String.valueOf(delegate) + "\", ");
			}else if (delegate instanceof Number){
				sb.append("n:"+String.valueOf(delegate) + ", ");
			}else if (delegate instanceof Boolean){
				sb.append("b:"+String.valueOf(delegate));
			}else{
				try {
					@SuppressWarnings("unchecked")
					AutoBean<T> bean = (AutoBean<T>) AutoBeanUtils.getAutoBean(delegate);
					if (bean == null) {
						throw new IllegalStateException("The given " + delegate
								+ " is not wrapped by an AutoBean !");
					}
					sb.append(AutoBeanCodex.encode(bean).getPayload());
				} catch (Exception e) {
					throw new SerializationException(e);
				}
			}
		}
		sb.append("}");
		System.out.println(sb);
		return sb.toString();
	}

	public static <U> U decodePayload(Class<U> clazz, String payload) throws SerializationException {
		try {
			AutoBean<U> bean = AutoBeanCodex.decode(FACTORY, clazz, payload);
			return bean.as();
		} catch (Exception e) {
			throw new SerializationException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <U> U decodeData(Class<U> clazz, Splittable data) throws SerializationException {
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

	public static <T, U extends T> AutoBean<T> createAutoBean(Class<T> clazz,
			U toWrap) throws SerializationException {
		try {
			AutoBean<T> bean = FACTORY.create(clazz, toWrap);
			return bean;
		} catch (Exception e) {
			throw new SerializationException(e);
		}
	}
	
	public static boolean isThrowable(Splittable data){
		try{
			return data != null && !data.isNull("exceptionType");
		}catch (Exception e) {
			return false;
		}
	}
	
	public static Throwable decodeThrowable(Splittable data) throws SerializationException {
		try {
			AutoBean<ThrowableBean> bean = AutoBeanCodex.decode(FACTORY, ThrowableBean.class, data);
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

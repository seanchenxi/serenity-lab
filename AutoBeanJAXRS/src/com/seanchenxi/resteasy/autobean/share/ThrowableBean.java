package com.seanchenxi.resteasy.autobean.share;

import java.util.List;

public interface ThrowableBean {
	
	ThrowableBean getCause();
	void setCause(ThrowableBean cause);
	
	List<StackTraceElementBean> getStackTrace();
	void setStackTrace(List<StackTraceElementBean> stackTrace);
	
	String getMessage();
	void setMessage(String message);
	
	String getExceptionType();
	void setExceptionType(String exceptionType);

}

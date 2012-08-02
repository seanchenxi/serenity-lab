package com.seanchenxi.resteasy.autobean.share;

public interface StackTraceElementBean {
	
	String getMethodName();
	void setMethodName(String methodName);
	
	String getFileName();
	void setFileName(String fileName);
	
	int getLineNumber();
	void setLineNumber(int lineNumber);
	
	String getClassName();
	void setClassName(String className);
	
}

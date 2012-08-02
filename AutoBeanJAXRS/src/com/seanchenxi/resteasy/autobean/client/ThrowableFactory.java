package com.seanchenxi.resteasy.autobean.client;

public interface ThrowableFactory {
	Throwable create(String className, String message);
}

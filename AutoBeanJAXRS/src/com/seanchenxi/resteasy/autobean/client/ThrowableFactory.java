package com.seanchenxi.resteasy.autobean.client;

interface ThrowableFactory {
	Throwable create(String className, String message);
}

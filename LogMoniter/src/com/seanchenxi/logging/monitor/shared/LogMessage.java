package com.seanchenxi.logging.monitor.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LogMessage implements Serializable {

	public enum Type{
		ROTATED, LOG, ERROR
	}
	
	private Type type;
	private String message;
	
	protected LogMessage(){
		
	}

	public LogMessage(Type type, String message) {
		this.type = type;
		this.message = message;
	}

	public Type getType() {
		return type;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "LogMessage [type=" + type + ", message=" + message + "]";
	}
}

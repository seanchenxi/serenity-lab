package com.seanchenxi.gwt.serenity.client.action;

public interface Action<R extends Response> {
	
	void execute(R reponse);
	
}

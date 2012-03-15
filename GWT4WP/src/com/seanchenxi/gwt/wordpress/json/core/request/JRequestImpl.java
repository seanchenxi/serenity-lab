package com.seanchenxi.gwt.wordpress.json.core.request;

import com.google.gwt.http.client.Request;
import com.seanchenxi.gwt.wordpress.json.api.JRequest;

class JRequestImpl implements JRequest {

	private final Request request;
	
	JRequestImpl(Request request){
		this.request = request;
	}
	
	@Override
	public void cancel() {
		request.cancel();
	}

}

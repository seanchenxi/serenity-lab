package com.seanchenxi.gwt.wordpress.json.core.request;

import java.util.logging.Logger;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.seanchenxi.gwt.wordpress.json.api.JRequest;
import com.seanchenxi.gwt.wordpress.json.api.JRequestBuilder;
import com.seanchenxi.gwt.wordpress.json.api.JRequestURL;
import com.seanchenxi.gwt.wordpress.json.api.model.JModel;

/**
 * Use {@link RequestBuilder} make site request
 * @author Xi
 *
 */
public class JRequestBuilderImpl implements JRequestBuilder {

	private static final Logger Log = Logger.getLogger(JRequestBuilderCSImpl.class.getName());
	
	private String jServicePath;
	private int timeout;
	
	public JRequestBuilderImpl(){
	}
	
	@Override
	public <M extends JModel> JRequest requestObject(JRequestURL url, AsyncCallback<M> callback) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url.setPrefix(jServicePath));
		builder.setTimeoutMillis(timeout);
		try {
			Log.finest("call " + url.getMethodName() + " by: " + builder.getUrl());
			Request request = builder.sendRequest(null, new JAsyncCallback<M>(callback));
			return new JRequestImpl(request);
		} catch (RequestException e) {
			if(callback != null)
				callback.onFailure(e);
		}
		return null;
	}

	@Override
	public void setServicePath(String jServicePath) {
		this.jServicePath = jServicePath;
	}

	@Override
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	private class JAsyncCallback<M extends JModel> implements RequestCallback {

		private AsyncCallback<M> callback;

		private JAsyncCallback(AsyncCallback<M> callback) {
			this.callback = callback;
		}

		@Override
		public void onResponseReceived(Request request, Response response) {
			if (200 == response.getStatusCode()) {
				JavaScriptObject result = null;
				try{
					result = JSONParser.parseLenient(response.getText()).isObject().getJavaScriptObject();
				}catch (Exception e) {
				}
				if (callback == null || result == null) return;
				JResponse<M> resp = new JResponse<M>(result);
				switch (resp.getStatus()) {
				case OK:
				case PENDING:
					callback.onSuccess(resp.getResult());
					break;
				case ERROR:
					callback.onFailure(new Throwable(resp.getError()));
					break;
				case UNKNOW:
					callback.onFailure(new Throwable(resp.toString()));
					break;
				}
			} else {
				callback.onFailure(new Throwable("Couldn't retrieve JSON"));
			}
		}

		@Override
		public void onError(Request request, Throwable exception) {
			callback.onFailure(exception);
		}

	}
}

package com.seanchenxi.gwt.wordpress.json.core.request;

import java.util.logging.Logger;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.jsonp.client.JsonpRequest;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.seanchenxi.gwt.wordpress.json.api.JRequest;
import com.seanchenxi.gwt.wordpress.json.api.JRequestBuilder;
import com.seanchenxi.gwt.wordpress.json.api.JRequestURL;
import com.seanchenxi.gwt.wordpress.json.api.model.JModel;

/**
 * Use {@link JsonpRequestBuilder} make cross site request
 * @author Xi
 */
public class JRequestBuilderCSImpl implements JRequestBuilder {

	private static final Logger Log = Logger.getLogger(JRequestBuilderCSImpl.class.getName());
	
	private final JsonpRequestBuilder builder;
	private String jServicePath;

	public JRequestBuilderCSImpl() {
		builder = new JsonpRequestBuilder();
	}

	@Override
	public void setServicePath(String servicePath) {
		this.jServicePath = servicePath;
	}

	@Override
	public void setTimeout(int timeout) {
		builder.setTimeout(timeout);
	}
	
	@Override
	public <M extends JModel> JRequest requestObject(JRequestURL url, AsyncCallback<M> callback) {
		if (url == null || callback == null)
			throw new NullPointerException();
		String urlString = url.setPrefix(jServicePath);
		Log.finest("call " + url.getMethodName() + " by: " + urlString);
		JsonpRequest<JavaScriptObject> req = builder.requestObject(url.toString(), new JAsyncCallback<M>(callback));
		return new JRequestCSImpl(req);
	}

	private class JAsyncCallback<M extends JModel> implements AsyncCallback<JavaScriptObject> {

		private AsyncCallback<M> callback;

		private JAsyncCallback(AsyncCallback<M> callback) {
			this.callback = callback;
		}

		@Override
		public void onFailure(Throwable caught) {
			if (callback != null)
				callback.onFailure(caught);
		}

		@Override
		public void onSuccess(JavaScriptObject result) {
			if (callback != null && result != null) {
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
				callback.onFailure(new Throwable("The returned result is null !"));
			}
		}

	}

	
}

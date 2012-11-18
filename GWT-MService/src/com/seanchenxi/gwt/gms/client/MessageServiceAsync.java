package com.seanchenxi.gwt.gms.client;

import java.util.LinkedList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.seanchenxi.gwt.gms.share.Message;
import com.seanchenxi.gwt.gms.share.MessageHandler;

public interface MessageServiceAsync {

    void subscribe(AsyncCallback<String> async);

    void unSubscribe(String id, AsyncCallback<Void> async);

    void retrieve(String id, AsyncCallback<LinkedList<Message<MessageHandler>>> async);

}

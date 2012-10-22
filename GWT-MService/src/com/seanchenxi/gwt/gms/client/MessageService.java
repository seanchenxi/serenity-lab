package com.seanchenxi.gwt.gms.client;

import java.util.LinkedList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.seanchenxi.gwt.gms.share.Message;
import com.seanchenxi.gwt.gms.share.MessageHandler;

@RemoteServiceRelativePath("gms")
public interface MessageService extends RemoteService {

    String subscribe();

    void unSubscribe(String id);

    LinkedList<Message<MessageHandler>> retrieve(String id);

}

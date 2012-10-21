package com.seanchenxi.gwt.gms.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.seanchenxi.gwt.gms.share.Message;
import com.seanchenxi.gwt.gms.share.MessageHandler;

import java.util.LinkedList;

@RemoteServiceRelativePath("gms")
public interface MessageService extends RemoteService {

    String register();

    void unregister();

    LinkedList<Message<MessageHandler>> retrieve(String id);

}

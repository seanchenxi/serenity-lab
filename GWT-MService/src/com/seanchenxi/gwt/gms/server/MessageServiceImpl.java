package com.seanchenxi.gwt.gms.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.seanchenxi.gwt.gms.client.MessageService;
import com.seanchenxi.gwt.gms.share.Message;
import com.seanchenxi.gwt.gms.share.MessageHandler;

import java.util.LinkedList;

public class MessageServiceImpl extends RemoteServiceServlet implements MessageService {

    @Override
    public String register() {
        return MessageServer.getInstance().subscribe();
    }

    @Override
    public void unregister(String id) {

    }

    public LinkedList<Message<MessageHandler>> retrieve(String id) {
        return new LinkedList<Message<MessageHandler>>();
    }
}
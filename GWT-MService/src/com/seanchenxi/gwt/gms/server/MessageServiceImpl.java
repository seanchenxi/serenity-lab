package com.seanchenxi.gwt.gms.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.seanchenxi.gwt.gms.client.MessageService;
import com.seanchenxi.gwt.gms.share.Message;
import com.seanchenxi.gwt.gms.share.MessageHandler;

import java.util.LinkedList;

public class MessageServiceImpl extends RemoteServiceServlet implements MessageService {

    @Override
    public String subscribe() {
        return MessageServer.getInstance().subscribe();
    }

    @Override
    public void unSubscribe(String id) {
        MessageServer.getInstance().unSubscribe(id);
    }

    public LinkedList<Message<MessageHandler>> retrieve(String id) {
        return MessageServer.getInstance().retrieveMessage(id);
    }
}
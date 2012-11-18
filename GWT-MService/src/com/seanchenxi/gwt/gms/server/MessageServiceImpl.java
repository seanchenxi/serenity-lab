package com.seanchenxi.gwt.gms.server;

import java.util.LinkedList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.seanchenxi.gwt.gms.client.MessageService;
import com.seanchenxi.gwt.gms.share.Message;
import com.seanchenxi.gwt.gms.share.MessageHandler;

@SuppressWarnings("serial")
public class MessageServiceImpl extends RemoteServiceServlet implements MessageService {

    @Override
    public String subscribe() {
        return MessageServer.getInstance().addSubscriber();
    }

    @Override
    public void unSubscribe(String id) {
        MessageServer.getInstance().removeSubscriber(id);
    }

    public LinkedList<Message<MessageHandler>> retrieve(String id) {
        return MessageServer.getInstance().retrieveMessage(id);
    }
}
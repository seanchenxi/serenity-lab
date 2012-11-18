package com.seanchenxi.gwt.gms.client;

import com.google.web.bindery.event.shared.ResettableEventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

/**
 * User: Xi
 * Date: 22/10/12
 */
public class MessageBus extends ResettableEventBus {

    public MessageBus() {
        super(new SimpleEventBus());
    }

    @Override
    public int getRegistrationSize() {
        return super.getRegistrationSize();
    }
}

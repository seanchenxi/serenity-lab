package com.seanchenxi.gwt.gms.share;

import com.google.web.bindery.event.shared.Event;

import java.io.Serializable;

/**
 * User: Xi
 * Date: 21/10/12
 */
@SuppressWarnings("serial")
public abstract class Message<H extends MessageHandler> extends Event<H> implements Serializable {

    protected Message() {
    }

}

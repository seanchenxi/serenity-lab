package com.seanchenxi.gwt.gms.share;

/**
 * User: Xi
 * Date: 22/10/12
 */
public class LogMessage extends Message<LogMessageHandler> {

    private static final transient Type<LogMessageHandler> TYPE = new Type<LogMessageHandler>();

    public static Type<LogMessageHandler> getType() {
        return TYPE;
    }

    private String content;

    public LogMessage() {
    }

    public LogMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public Type<LogMessageHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(LogMessageHandler handler) {
        handler.onMessageReceived(this);
    }
}

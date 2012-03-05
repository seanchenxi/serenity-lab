package com.seanchenxi.gwt.serenity.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class ReplyDiscussionEvent extends GwtEvent<ReplyDiscussionEvent.Handler> {

	public interface Handler extends EventHandler {
		void onReply(ReplyDiscussionEvent event);
		void onCancel(ReplyDiscussionEvent event);
	}
	
	public interface HasReplyDiscussionHandlers extends HasHandlers {
		HandlerRegistration addReplyDiscussionHandler(ReplyDiscussionEvent.Handler handler);
	}
	
	public static Type<ReplyDiscussionEvent.Handler> TYPE;

	private final boolean isCancelled;
	private final int discussionId;

	public ReplyDiscussionEvent(int discussionId, boolean isCancelled) {
		this.isCancelled = isCancelled;
		this.discussionId = discussionId;
	}

	public boolean isCancelled() {
    return isCancelled;
  }
	
	public int getDiscussionId() {
    return discussionId;
  }

	public static Type<ReplyDiscussionEvent.Handler> getType() {
		if (TYPE == null) {
			TYPE = new Type<ReplyDiscussionEvent.Handler>();
		}
		return TYPE;
	}
	
	@Override
	public Type<ReplyDiscussionEvent.Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ReplyDiscussionEvent.Handler handler) {
	  if(isCancelled){
	    handler.onCancel(this);
	  }else{
	    handler.onReply(this);
	  }
	}
	
	public static void fireCancel(int discussionId, HasReplyDiscussionHandlers source) {
		if (TYPE != null) {
			ReplyDiscussionEvent event = new ReplyDiscussionEvent(discussionId, true);
			source.fireEvent(event);
		}
	}
	
	public static void fireReply(int discussionId, HasReplyDiscussionHandlers source) {
    if (TYPE != null) {
      ReplyDiscussionEvent event = new ReplyDiscussionEvent(discussionId, false);
      source.fireEvent(event);
    }
  }
	
}

/*******************************************************************************
 * Copyright 2012 Xi CHEN
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.seanchenxi.gwt.serenity.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class ReplyDiscussionEvent extends GwtEvent<ReplyDiscussionEvent.Handler> {

	public interface Handler extends EventHandler {
		void onReplyDiscussion(ReplyDiscussionEvent event);
	}
	
	public interface HasReplyDiscussionHandlers extends HasHandlers {
		HandlerRegistration addReplyDiscussionHandler(ReplyDiscussionEvent.Handler handler);
	}
	
	public static Type<ReplyDiscussionEvent.Handler> TYPE;

	private final int discussionId;

	public ReplyDiscussionEvent(int discussionId) {
		this.discussionId = discussionId;
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
	  handler.onReplyDiscussion(this);
	}
	
	public static void fire(int discussionId, HasReplyDiscussionHandlers source) {
    if (TYPE != null) {
      ReplyDiscussionEvent event = new ReplyDiscussionEvent(discussionId);
      source.fireEvent(event);
    }
  }
	
}

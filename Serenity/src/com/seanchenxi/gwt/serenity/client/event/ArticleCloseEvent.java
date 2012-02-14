package com.seanchenxi.gwt.serenity.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class ArticleCloseEvent extends GwtEvent<ArticleCloseEvent.Handler> {

	public interface Handler extends EventHandler {
		void onArticleClose(ArticleCloseEvent event);
	}
	
	public interface HasArticleCloseHandlers extends HasHandlers {
		HandlerRegistration addArticleCloseHandler(ArticleCloseEvent.Handler handler);
	}
	
	public static Type<ArticleCloseEvent.Handler> TYPE;

	private final String slug;

	public ArticleCloseEvent(String slug) {
		this.slug = slug;
	}

	public String getSlug() {
		return slug;
	}

	public static Type<ArticleCloseEvent.Handler> getType() {
		if (TYPE == null) {
			TYPE = new Type<ArticleCloseEvent.Handler>();
		}
		return TYPE;
	}
	
	@Override
	public Type<ArticleCloseEvent.Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ArticleCloseEvent.Handler handler) {
		handler.onArticleClose(this);
	}
	
	public static void fire(String slug, HasArticleCloseHandlers source) {
		if (TYPE != null) {
			ArticleCloseEvent event = new ArticleCloseEvent(slug);
			source.fireEvent(event);
		}
	}
}

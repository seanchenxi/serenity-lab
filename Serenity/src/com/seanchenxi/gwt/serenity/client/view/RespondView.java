package com.seanchenxi.gwt.serenity.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;

public interface RespondView extends IsWidget {

	HasValue<String> getAuthorField();

	HasValue<String> getEmailField();

	HasValue<String> getCommentField();

	HasClickHandlers getSubmitButton();

	void reset();
	
}

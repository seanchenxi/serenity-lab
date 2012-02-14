package com.seanchenxi.gwt.serenity.client.view;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

public interface SerenityLayout extends IsWidget {

	AcceptsOneWidget getSidebarContainer();

	AcceptsOneWidget getContentListContainer();

	AcceptsOneWidget getArticleContainer();
	
	void show();
	
}

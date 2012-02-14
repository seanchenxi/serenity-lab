package com.seanchenxi.gwt.serenity.client.view;

import com.google.gwt.user.client.ui.IsWidget;

public interface Sidebar extends IsWidget {

	void bindPresenter(Presenter presenter);
	
	void clearSelection();
	
	void updateSelectionForPlace(String itemId, String slug);
	
	void setSeachValue(String slug);
	
	void addCategory(String slug, String title);
	
	public interface Presenter {
		
		void goToHome();
		
		void goToAbout();

		void goToCategory(String slug);

		void search(String searchValue);
		
	}

}

package com.seanchenxi.gwt.serenity.client.resource.message;

import com.google.gwt.i18n.client.Constants;

public interface MessageResources extends Constants {
	
	@DefaultStringValue("Copyright © 2007-2011 Serenity - is proudly powered by WordPress")
	String footer_info();
	
	@DefaultStringValue("Submit")
	String btn_submit();
	
	@DefaultStringValue("Name:")
	String lbl_name();

	@DefaultStringValue("Email:")
	String lbl_email();
	
	@DefaultStringValue("Content:")
	String lbl_content();
	
}

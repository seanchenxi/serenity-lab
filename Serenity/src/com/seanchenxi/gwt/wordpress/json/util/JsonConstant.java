package com.seanchenxi.gwt.wordpress.json.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Constants;

public class JsonConstant {
	public final static OperationName OPERATION_NAME = GWT.create(OperationName.class);
	
	public interface OperationName extends Constants {
		
		@DefaultStringValue("get_post")
		String GetPost();
		
		@DefaultStringValue("get_page")
		String GetPage();
		
		@DefaultStringValue("get_recent_posts")
		String GetRecentPosts();
		
		@DefaultStringValue("get_date_posts")
		String GetDatePosts();
		
		@DefaultStringValue("get_category_posts")
		String GetCategoryPosts();

		@DefaultStringValue("get_tag_posts")
		String GetTagPosts();

		@DefaultStringValue("get_author_posts")
		String GetAuthorPosts();
		
		@DefaultStringValue("get_search_posts")
		String GetSearchPosts();
		
		@DefaultStringValue("respond/submit_comment")
		String SubmitComment();
	}
}

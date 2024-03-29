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
package com.seanchenxi.gwt.wordpress.json.api.service;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.seanchenxi.gwt.wordpress.json.api.JRequest;
import com.seanchenxi.gwt.wordpress.json.api.model.CategoryIndex;
import com.seanchenxi.gwt.wordpress.json.api.model.Page;
import com.seanchenxi.gwt.wordpress.json.api.model.PagingPostList;
import com.seanchenxi.gwt.wordpress.json.api.model.Post;
import com.seanchenxi.gwt.wordpress.json.api.model.PostType;
import com.seanchenxi.gwt.wordpress.json.api.model.TagIndex;

public interface JCoreService {
	
  JRequest getPost(int postId, AsyncCallback<Post> callback);
	
  JRequest getPost(String postSlug, AsyncCallback<Post> callback);
  
  JRequest getPage(int postId, AsyncCallback<Page> callback);
  
  JRequest getPage(String pageSlug, AsyncCallback<Page> callback);
  
  JRequest getRecentPosts(AsyncCallback<PagingPostList> callback);

  JRequest getRecentPosts(int offset, int size, PostType type, AsyncCallback<PagingPostList> callback);

  JRequest getDatePosts(Date date, AsyncCallback<PagingPostList> callback);
  
  JRequest getDatePosts(Date date, PostType type, int offset, int size, AsyncCallback<PagingPostList> callback);

  JRequest getCategoryPosts(int categoryId, AsyncCallback<PagingPostList> callback);

  JRequest getCategoryPosts(String categorySlug, AsyncCallback<PagingPostList> callback);
  
  JRequest getCategoryPosts(int categoryId, PostType type, int offset, int size, AsyncCallback<PagingPostList> callback);

  JRequest getCategoryPosts(String categorySlug, PostType type, int offset, int size, AsyncCallback<PagingPostList> callback);

  JRequest getTagPosts(int tagId, AsyncCallback<PagingPostList> callback);

  JRequest getTagPosts(String tagSlug, AsyncCallback<PagingPostList> callback);
  
  JRequest getTagPosts(int tagId, PostType type, int offset, int size, AsyncCallback<PagingPostList> callback);

  JRequest getTagPosts(String tagSlug, PostType type, int offset, int size, AsyncCallback<PagingPostList> callback);
  
  JRequest getAuthorPosts(int authorId, AsyncCallback<PagingPostList> callback);
  
  JRequest getAuthorPosts(String authorSlug, AsyncCallback<PagingPostList> callback);

  JRequest getAuthorPosts(int authorId, PostType type, int offset, int size, AsyncCallback<PagingPostList> callback);

  JRequest getAuthorPosts(String authorSlug, PostType type, int offset, int size, AsyncCallback<PagingPostList> callback);

  JRequest searchPosts(String value, AsyncCallback<PagingPostList> callback);
  
  JRequest searchPosts(String value, PostType type, int offset, int size, AsyncCallback<PagingPostList> callback);

  JRequest getCategoryIndex(AsyncCallback<CategoryIndex> callback);

  JRequest getTagIndex(AsyncCallback<TagIndex> callback);
  
}

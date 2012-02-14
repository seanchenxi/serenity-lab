package com.seanchenxi.gwt.wordpress.json.core;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.seanchenxi.gwt.wordpress.json.api.JMethod;
import com.seanchenxi.gwt.wordpress.json.api.JParameter;
import com.seanchenxi.gwt.wordpress.json.api.model.CategoryIndex;
import com.seanchenxi.gwt.wordpress.json.api.model.Page;
import com.seanchenxi.gwt.wordpress.json.api.model.PagingPostList;
import com.seanchenxi.gwt.wordpress.json.api.model.Post;
import com.seanchenxi.gwt.wordpress.json.api.model.PostType;
import com.seanchenxi.gwt.wordpress.json.api.model.TagIndex;
import com.seanchenxi.gwt.wordpress.json.api.service.JCoreService;
import com.seanchenxi.gwt.wordpress.json.api.service.JRequest;

public class JCoreServiceImpl extends JService implements JCoreService {

  @Override
  public JRequest getPost(int postId, AsyncCallback<Post> callback) {
    RequestURL url = new RequestURL(JMethod.GET_POST);
    url.setParameter(JParameter.POST_ID, postId);
    return request(url, callback);
  }

  @Override
  public JRequest getPost(String postSlug, AsyncCallback<Post> callback) {
    RequestURL url = new RequestURL(JMethod.GET_POST);
    url.setParameter(JParameter.SLUG, postSlug);
    return request(url, callback);
  }

  @Override
  public JRequest getPage(int postId, AsyncCallback<Page> callback) {
    RequestURL url = new RequestURL(JMethod.GET_PAGE);
    url.setParameter(JParameter.ID, postId);
    return request(url, callback);
  }

  @Override
  public JRequest getPage(String pageSlug, AsyncCallback<Page> callback) {
    RequestURL url = new RequestURL(JMethod.GET_PAGE);
    url.setParameter(JParameter.SLUG, pageSlug);
    return request(url, callback);
  }

  @Override
  public JRequest getRecentPosts(AsyncCallback<PagingPostList> callback) {
    return getRecentPosts(-1, -1, PostType.POST, callback);
  }

  @Override
  public JRequest getRecentPosts(int offset, int size, PostType type, AsyncCallback<PagingPostList> callback) {
    RequestURL url = new RequestURL(JMethod.GET_RECENT_POSTS);
    addPagingParameter(url, offset, size, type);
    return request(url, callback);
  }

  @Override
  public JRequest getDatePosts(Date date, AsyncCallback<PagingPostList> callback) {
    return getDatePosts(date, PostType.POST, -1, -1, callback);
  }

  @Override
  public JRequest getDatePosts(Date date, PostType type, int offset, int size, AsyncCallback<PagingPostList> callback) {
    RequestURL url = new RequestURL(JMethod.GET_DATE_POSTS);
    url.setParameter(JParameter.DATE, date);
    addPagingParameter(url, offset, size, type);
    return request(url, callback);
  }

  @Override
  public JRequest getCategoryPosts(int categoryId, AsyncCallback<PagingPostList> callback) {
    return getCategoryPosts(categoryId, PostType.POST, -1, -1, callback);
  }

  @Override
  public JRequest getCategoryPosts(String categorySlug, AsyncCallback<PagingPostList> callback) {
    return getCategoryPosts(categorySlug, PostType.POST, -1, -1, callback);
  }

  @Override
  public JRequest getCategoryPosts(int categoryId, PostType type, int offset, int size, AsyncCallback<PagingPostList> callback) {
    RequestURL url = new RequestURL(JMethod.GET_CAT_POSTS);
    url.setParameter(JParameter.ID, categoryId);
    addPagingParameter(url, offset, size, type);
    return request(url, callback);
  }

  @Override
  public JRequest getCategoryPosts(String categorySlug, PostType type, int offset, int size, AsyncCallback<PagingPostList> callback) {
    RequestURL url = new RequestURL(JMethod.GET_CAT_POSTS);
    url.setParameter(JParameter.SLUG, categorySlug);
    addPagingParameter(url, offset, size, type);
    return request(url, callback);
  }

  @Override
  public JRequest getTagPosts(int tagId, AsyncCallback<PagingPostList> callback) {
    return getTagPosts(tagId, PostType.POST, -1, -1, callback);
  }

  @Override
  public JRequest getTagPosts(String tagSlug, AsyncCallback<PagingPostList> callback) {
    return getTagPosts(tagSlug, PostType.POST, -1, -1, callback);
  }

  @Override
  public JRequest getTagPosts(int tagId, PostType type, int offset, int size, AsyncCallback<PagingPostList> callback) {
    RequestURL url = new RequestURL(JMethod.GET_TAG_POSTS);
    url.setParameter(JParameter.ID, tagId);
    addPagingParameter(url, offset, size, type);
    return request(url, callback);
  }

  @Override
  public JRequest getTagPosts(String tagSlug, PostType type, int offset, int size, AsyncCallback<PagingPostList> callback) {
    RequestURL url = new RequestURL(JMethod.GET_TAG_POSTS);
    url.setParameter(JParameter.SLUG, tagSlug);
    addPagingParameter(url, offset, size, type);
    return request(url, callback);
  }

  @Override
  public JRequest getAuthorPosts(int authorId, AsyncCallback<PagingPostList> callback) {
    return getAuthorPosts(authorId, PostType.POST, -1, -1, callback);
  }

  @Override
  public JRequest getAuthorPosts(String authorSlug, AsyncCallback<PagingPostList> callback) {
    return getAuthorPosts(authorSlug, PostType.POST, -1, -1, callback);
  }

  @Override
  public JRequest getAuthorPosts(int authorId, PostType type, int offset, int size, AsyncCallback<PagingPostList> callback) {
    RequestURL url = new RequestURL(JMethod.GET_AUTHOR_POSTS);
    url.setParameter(JParameter.ID, authorId);
    addPagingParameter(url, offset, size, type);
    return request(url, callback);
  }

  @Override
  public JRequest getAuthorPosts(String authorSlug, PostType type, int offset, int size, AsyncCallback<PagingPostList> callback) {
    RequestURL url = new RequestURL(JMethod.GET_AUTHOR_POSTS);
    url.setParameter(JParameter.SLUG, authorSlug);
    addPagingParameter(url, offset, size, type);
    return request(url, callback);
  }

  @Override
  public JRequest searchPosts(String value, AsyncCallback<PagingPostList> callback) {
    return searchPosts(value, PostType.POST, -1, -1, callback);
  }

  @Override
  public JRequest searchPosts(String value, PostType type, int offset, int size, AsyncCallback<PagingPostList> callback) {
    RequestURL url = new RequestURL(JMethod.GET_SEARCH_RESULTS);
    url.setParameter(JParameter.SEARCH, value);
    addPagingParameter(url, offset, size, type);
    return request(url, callback);
  }

  @Override
  public JRequest getCategoryIndex(AsyncCallback<CategoryIndex> callback) {
    RequestURL url = new RequestURL(JMethod.GET_CATEGORY_INDEX);
    return request(url, callback);
  }

  @Override
  public JRequest getTagIndex(AsyncCallback<TagIndex> callback) {
    RequestURL url = new RequestURL(JMethod.GET_TAG_INDEX);
    return request(url, callback);
  }

  protected void addPagingParameter(RequestURL url, int offset, int size, PostType type) {
    url.setParameter(JParameter.POST_TYPE, type == null ? PostType.POST : type);
    if (size > 0) {
      url.setParameter(JParameter.COUNT, size);
      if (offset >= 0) {
        url.setParameter(JParameter.PAGE, ((offset / size) + 1));
      }
    }
  }

}

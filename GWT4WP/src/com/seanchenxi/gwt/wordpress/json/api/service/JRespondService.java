package com.seanchenxi.gwt.wordpress.json.api.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.seanchenxi.gwt.wordpress.json.api.JRequest;
import com.seanchenxi.gwt.wordpress.json.api.model.Comment;

public interface JRespondService {
  
  JRequest submitComment(int postId, String name, String email, String url, String content, AsyncCallback<Comment> callback);
  JRequest submitComment(int postId, String name, String email, String url, String content, int parentId, AsyncCallback<Comment> callback);
}

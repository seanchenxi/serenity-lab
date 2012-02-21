package com.seanchenxi.gwt.wordpress.json.core;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.seanchenxi.gwt.wordpress.json.api.JMethod;
import com.seanchenxi.gwt.wordpress.json.api.JParameter;
import com.seanchenxi.gwt.wordpress.json.api.model.Comment;
import com.seanchenxi.gwt.wordpress.json.api.service.JRequest;
import com.seanchenxi.gwt.wordpress.json.api.service.JRespondService;

public class JRespondServiceImpl extends JService implements JRespondService {

  @Override
  public JRequest submitComment(int postId, String name, String email, String url, String content, AsyncCallback<Comment> callback) {
    RequestURL requrl = new RequestURL(JMethod.SubmitComment);
    requrl.setParameter(JParameter.POST_ID, postId).setParameter(JParameter.NAME, name)
        .setParameter(JParameter.EMAIL, email).setParameter(JParameter.URL, url).setParameter(
            JParameter.CONTENT, content).setEncode(true);
    return request(requrl, callback);
  }

  @Override
  public JRequest submitComment(int postId, String name, String email, String url, String content, int parentId, AsyncCallback<Comment> callback) {
    RequestURL requrl = new RequestURL(JMethod.SubmitComment);
    requrl.setParameter(JParameter.POST_ID, postId).setParameter(JParameter.NAME, name)
        .setParameter(JParameter.EMAIL, email).setParameter(JParameter.URL, url).setParameter(
            JParameter.CONTENT, content).setParameter(JParameter.PARENT, parentId).setEncode(true);
    return request(requrl, callback);
  }

}

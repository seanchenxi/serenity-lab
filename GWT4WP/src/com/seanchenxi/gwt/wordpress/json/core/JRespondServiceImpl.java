package com.seanchenxi.gwt.wordpress.json.core;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.seanchenxi.gwt.wordpress.json.api.JMethod;
import com.seanchenxi.gwt.wordpress.json.api.JParameter;
import com.seanchenxi.gwt.wordpress.json.api.model.Comment;
import com.seanchenxi.gwt.wordpress.json.api.service.JRequest;
import com.seanchenxi.gwt.wordpress.json.api.service.JRespondService;

public class JRespondServiceImpl extends JService implements JRespondService {

  @Override
  public JRequest submitComment(int postId, String name, String email, String content, AsyncCallback<Comment> callback) {
    RequestURL url = new RequestURL(JMethod.SubmitComment);
    url.setParameter(JParameter.POST_ID, postId).setParameter(JParameter.NAME, name).setParameter(
        JParameter.EMAIL, email).setParameter(JParameter.CONTENT, content).setEncode(true);
    return request(url, callback);
  }

}

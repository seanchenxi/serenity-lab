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
package com.seanchenxi.gwt.wordpress.json.core.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.seanchenxi.gwt.wordpress.json.api.JRequest;
import com.seanchenxi.gwt.wordpress.json.api.model.Comment;
import com.seanchenxi.gwt.wordpress.json.api.service.JRespondService;
import com.seanchenxi.gwt.wordpress.json.core.JMethod;
import com.seanchenxi.gwt.wordpress.json.core.JParameter;
import com.seanchenxi.gwt.wordpress.json.core.JService;
import com.seanchenxi.gwt.wordpress.json.core.RequestURL;

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

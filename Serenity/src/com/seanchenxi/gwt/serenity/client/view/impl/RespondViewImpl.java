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
package com.seanchenxi.gwt.serenity.client.view.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.seanchenxi.gwt.serenity.client.SerenityUtil;
import com.seanchenxi.gwt.serenity.client.view.RespondView;
import com.seanchenxi.gwt.serenity.share.StringPool;
import com.seanchenxi.gwt.ui.widget.MessageBox;

public class RespondViewImpl extends Composite implements RespondView {

	interface RespondViewImplUiBinder extends UiBinder<Widget, RespondViewImpl>{}
	
	private static RespondViewImplUiBinder uiBinder = GWT.create(RespondViewImplUiBinder.class);

	@UiField TextBox authorField;
	@UiField TextBox emailField;
	@UiField TextBox urlField;
	@UiField TextArea commentField;
	@UiField Anchor cancel;
	
	private int articleId;
	private int discussionId;
	
	private Presenter presenter;
	
	public RespondViewImpl(){
		initWidget(uiBinder.createAndBindUi(this));
		getElement().setId("respond");
	}
	
	@Override
	public void setArticleId(int articleId) {
    this.articleId = articleId;
  }
	
	@Override
	public void setDiscussionId(int discussionId) {
    this.discussionId = discussionId;
    this.cancel.setVisible(discussionId != 0);
  }
	 
	@Override
	public void reset() {
	  clear();
	  cancel.setVisible(false);
		articleId = -1;
		discussionId = 0;
	}

  @Override
  public void bindPresenter(Presenter presenter) {
    this.presenter = presenter;
  }
  
  @UiHandler("submit")
  void onSubmintResponse(ClickEvent event){
    if(presenter != null && articleId != -1 && isVisible()){
      if(validate()){
        presenter.reply(articleId, authorField.getValue(), emailField.getValue(), urlField.getValue(), commentField.getValue(), discussionId);
      }
    }
  }
  
  @UiHandler("cancel")
  void onCancel(ClickEvent event){
    clear();
    if(presenter != null){
      presenter.cancel(articleId, 0);
    }
  }
  
  private boolean validate(){
    final String name = authorField.getValue();
    final String emailAdr = emailField.getValue();
    final String content = commentField.getValue();
    if(name.isEmpty() || content.isEmpty() || !SerenityUtil.isValidEmail(emailAdr)){
      MessageBox.alert(
          "Requires Information", 
          "Please correctly fill the required fields (name, email, message)", 
          new ScheduledCommand() { 
            @Override
            public void execute() {
              if(name.isEmpty()) {
                authorField.setFocus(true);
              } else if(!SerenityUtil.isValidEmail(emailAdr)) {
                emailField.setFocus(true);
                emailField.selectAll();
              } else if(content.isEmpty()) {
                commentField.setFocus(true);
              } 
            }
      });
      return false;
    }else{
      return true;
    }
  }
  
  private void clear(){
    authorField.setValue(StringPool.BLANK);
    emailField.setValue(StringPool.BLANK);
    commentField.setValue(StringPool.BLANK);
  }

}

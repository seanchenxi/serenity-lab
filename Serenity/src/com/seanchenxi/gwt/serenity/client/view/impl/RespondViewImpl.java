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
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.seanchenxi.gwt.serenity.client.SerenityUtil;
import com.seanchenxi.gwt.serenity.client.resource.SerenityResources;
import com.seanchenxi.gwt.serenity.client.view.RespondView;
import com.seanchenxi.gwt.serenity.share.StringPool;
import com.seanchenxi.gwt.ui.widget.MessageBox;

public class RespondViewImpl extends Composite implements RespondView {

  public interface Resources extends ClientBundle {

    public interface Style extends CssResource {
      final static String DEFAULT_CSS = "com/seanchenxi/gwt/serenity/client/resource/view/RespondView.css";
      String respond();
      String respondCancel();
      String respondDescription();
      String respondLbl();
      String respondInput();
      String respondTextarea();
      String respondButton();
      String required();
    }

    @ClientBundle.Source(Style.DEFAULT_CSS)
    Style style();
  }

	interface RespondViewImplUiBinder extends UiBinder<Widget, RespondViewImpl>{}
	
	private static RespondViewImplUiBinder uiBinder = GWT.create(RespondViewImplUiBinder.class);

  @UiField Resources resource;

  @UiField SpanElement titleLbl;
  @UiField Anchor cancel;

  @UiField ParagraphElement description;

  @UiField LabelElement nameLbl;
  @UiField TextBox authorField;

  @UiField LabelElement mailLbl;
  @UiField InputElement emailField;

  @UiField LabelElement siteLbl;
  @UiField InputElement urlField;

  @UiField LabelElement msgLbl;
  @UiField TextArea commentField;

  @UiField Button submit;
	
	private int articleId;
	private int discussionId;
	
	private Presenter presenter;
	
	public RespondViewImpl(){
    SerenityResources.COMMON.formCSS().ensureInjected();
		initWidget(uiBinder.createAndBindUi(this));
    resource.style().ensureInjected();
    titleLbl.setInnerHTML(SerenityResources.MSG.lbl_respondTitle());
    cancel.setHTML(SerenityResources.MSG.anchor_CancelReply());
    description.setInnerHTML(SerenityResources.MSG.lbl_respondDescription());
    nameLbl.setInnerHTML(asRequired(SerenityResources.MSG.lbl_respondYourName()));
    mailLbl.setInnerHTML(asRequired(SerenityResources.MSG.lbl_respondYourMail()));
    siteLbl.setInnerHTML(SerenityResources.MSG.lbl_respondYourWebsite());
    msgLbl.setInnerHTML(asRequired(SerenityResources.MSG.lbl_respondYourMessage()));
    submit.setText(SerenityResources.MSG.btn_respondSubmit());
    getElement().setId(resource.style().respond());
    setStyleName(resource.style().respond());
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
  void onSubmitResponse(ClickEvent event){
    if(presenter != null && articleId != -1 && isVisible()){
      if(validate()){
        presenter.reply(articleId, authorField.getValue(), emailField.getValue(), urlField.getValue(), commentField.getValue(), discussionId);
        clear();
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
          SerenityResources.MSG.msg_requiresInformation(),
          SerenityResources.MSG.msg_pleaseFillRequiredFields(),
          new ScheduledCommand() { 
            @Override
            public void execute() {
              if(name.isEmpty()) {
                authorField.setFocus(true);
              } else if(!SerenityUtil.isValidEmail(emailAdr)) {
                emailField.focus();
                emailField.select();
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
    urlField.setValue(StringPool.BLANK);
  }

  private String asRequired(String label) {
    return label + "<span class=\""+resource.style().required()+"\">*</span>";
  }

}

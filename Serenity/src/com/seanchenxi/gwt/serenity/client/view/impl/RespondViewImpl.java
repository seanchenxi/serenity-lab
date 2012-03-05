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
	  cancel.setVisible(false);
		authorField.setValue(StringPool.BLANK);
		emailField.setValue(StringPool.BLANK);
		commentField.setValue(StringPool.BLANK);
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
        presenter.sendResponse(articleId, authorField.getValue(), emailField.getValue(), urlField.getValue(), commentField.getValue(), discussionId);
      }
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

}

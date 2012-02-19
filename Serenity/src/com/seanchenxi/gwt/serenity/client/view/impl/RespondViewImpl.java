package com.seanchenxi.gwt.serenity.client.view.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.seanchenxi.gwt.serenity.client.view.RespondView;
import com.seanchenxi.gwt.serenity.share.StringPool;

public class RespondViewImpl extends Composite implements RespondView {

	interface RespondViewImplUiBinder extends UiBinder<Widget, RespondViewImpl>{}
	
	private static RespondViewImplUiBinder uiBinder = GWT.create(RespondViewImplUiBinder.class);

	@UiField TextBox authorField;
	@UiField TextBox emailField;
	@UiField TextArea commentField;
	
	private int articleId;
	
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
	public void reset() {
		authorField.setValue(StringPool.BLANK);
		emailField.setValue(StringPool.BLANK);
		commentField.setValue(StringPool.BLANK);
		articleId = -1;
	}

  @Override
  public void bindPresenter(Presenter presenter) {
    this.presenter = presenter;
  }
  
  @UiHandler("submit")
  void onSubmintResponse(ClickEvent event){
    if(presenter != null && articleId != -1 && isVisible()){
      String name = authorField.getValue();
      String emailAdr = emailField.getValue();
      String content = commentField.getValue();
      if(name.isEmpty() || emailAdr.isEmpty() || content.isEmpty()){
        return;
      }
      presenter.submitResponse(articleId, name, emailAdr, content);
    }
  }
	
}

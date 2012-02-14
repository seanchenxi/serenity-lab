package com.seanchenxi.gwt.serenity.client.view.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
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
	@UiField Button submit;
	
	public RespondViewImpl(){
		initWidget(uiBinder.createAndBindUi(this));
		getElement().setId("respond");
	}
	
	@Override
	public HasValue<String> getAuthorField() {
		return authorField;
	}
	
	@Override
	public HasValue<String> getEmailField() {
		return emailField;
	}
	
	@Override
	public HasValue<String> getCommentField() {
		return commentField;
	}
	
	@Override
	public HasClickHandlers getSubmitButton() {
		return submit;
	}

	@Override
	public void reset() {
		authorField.setValue(StringPool.BLANK);
		emailField.setValue(StringPool.BLANK);
		commentField.setValue(StringPool.BLANK);
	}
	
}

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
package com.seanchenxi.resteasy.autobean.test.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.seanchenxi.resteasy.autobean.client.REST;
import com.seanchenxi.resteasy.autobean.test.share.BeanFactory;
import com.seanchenxi.resteasy.autobean.test.share.FieldVerifier;
import com.seanchenxi.resteasy.autobean.test.share.Greeting;

public class Test implements EntryPoint {

  private final static BeanFactory FACTORY = GWT.create(BeanFactory.class);
  private final static GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
  
  private final static GreetingServiceAsyncM greetingServiceM = new GreetingServiceAsyncM(FACTORY);
  
  boolean returnObject = true;
  boolean useM = false;
  
  @Override
  public void onModuleLoad() {
    REST.registerFactory(FACTORY);
    initView();
  }
  
  private void callManualServiceObjectReturned(){
    greetingServiceM.greetServerObject(nameField.getText(), false, new AsyncCallback<Greeting>() {
      public void onFailure(Throwable caught) {
        onRESTFailure(caught);
      }

      public void onSuccess(Greeting result) {
        dialogBox.setText("Remote Procedure Call");
        serverResponseLabel.removeStyleName("serverResponseLabelError");
        serverResponseLabel.setHTML(result.getMessage());
        dialogBox.center();
        closeButton.setFocus(true);
      }
    });
  }
  
  private void callGeneratedServiceStringReturned(){
    greetingService.greetServer(nameField.getText(), false, new AsyncCallback<String>() {
      public void onFailure(Throwable caught) {
        onRESTFailure(caught);
      }
      
      public void onSuccess(String result) {
        dialogBox.setText("Remote Procedure Call");
        serverResponseLabel.removeStyleName("serverResponseLabelError");
        serverResponseLabel.setHTML(result);
        dialogBox.center();
        closeButton.setFocus(true);
      }
    });
  }
  
  private void callGeneratedServiceObjectReturned(){
    greetingService.greetServerObject(nameField.getText(), false, new AsyncCallback<Greeting>() {
      public void onFailure(Throwable caught) {
        onRESTFailure(caught);
      }

      public void onSuccess(Greeting result) {
        dialogBox.setText("Remote Procedure Call");
        serverResponseLabel.removeStyleName("serverResponseLabelError");
        serverResponseLabel.setHTML(result.getMessage());
        dialogBox.center();
        closeButton.setFocus(true);
      }
    });
  }
  
  /**
   * Send the name from the nameField to the server and wait for a response.
   */
  private void sendNameToServer() {
    validate();
    if(useM){
      callManualServiceObjectReturned();
    }else if(!returnObject){
      callGeneratedServiceStringReturned();
    }else{
      callGeneratedServiceObjectReturned();
    }
  }
  
  private void onRESTFailure(Throwable caught) {
    caught.printStackTrace(System.err);
    // Show the RPC error message to the user
    dialogBox.setText("Remote Procedure Call - Failure");
    serverResponseLabel.addStyleName("serverResponseLabelError");
    serverResponseLabel.setHTML(SERVER_ERROR);
    dialogBox.center();
    closeButton.setFocus(true);
  }

  private static final String SERVER_ERROR = "An error occurred while "
      + "attempting to contact the server. Please check your network "
      + "connection and try again.";
  
  Button sendButton = new Button("Send");
  TextBox nameField = new TextBox();
  Label errorLabel = new Label();
  DialogBox dialogBox = new DialogBox();
  Button closeButton = new Button("Close");
  Label textToServerLabel = new Label();
  HTML serverResponseLabel = new HTML();
  
  private void validate(){
    // First, we validate the input.
    errorLabel.setText("");
    String textToServer = nameField.getText();
    if (!FieldVerifier.isValidName(textToServer)) {
      errorLabel.setText("Please enter at least four characters");
      return;
    }

    // Then, we send the input to the server.
    sendButton.setEnabled(false);
    textToServerLabel.setText(textToServer);
    serverResponseLabel.setText("");
  }
  
  private void initView() {
    nameField.setText("GWT User");

    // We can add style names to widgets
    sendButton.addStyleName("sendButton");

    // Add the nameField and sendButton to the RootPanel
    // Use RootPanel.get() to get the entire body element
    RootPanel.get("nameFieldContainer").add(nameField);
    RootPanel.get("sendButtonContainer").add(sendButton);
    RootPanel.get("errorLabelContainer").add(errorLabel);

    // Focus the cursor on the name field when the app loads
    nameField.setFocus(true);
    nameField.selectAll();

    // Create the popup dialog box
    dialogBox.setText("Remote Procedure Call");
    dialogBox.setAnimationEnabled(true);
    // We can set the id of a widget by accessing its Element
    closeButton.getElement().setId("closeButton");
   
    VerticalPanel dialogVPanel = new VerticalPanel();
    dialogVPanel.addStyleName("dialogVPanel");
    dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
    dialogVPanel.add(textToServerLabel);
    dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
    dialogVPanel.add(serverResponseLabel);
    dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
    dialogVPanel.add(closeButton);
    dialogBox.setWidget(dialogVPanel);

    // Add a handler to close the DialogBox
    closeButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        dialogBox.hide();
        sendButton.setEnabled(true);
        sendButton.setFocus(true);
      }
    });
    
    // Create a handler for the sendButton and nameField
    class MyHandler implements ClickHandler, KeyUpHandler {
      /**
       * Fired when the user clicks on the sendButton.
       */
      public void onClick(ClickEvent event) {
        sendNameToServer();
      }
      /**
       * Fired when the user types in the nameField.
       */
      public void onKeyUp(KeyUpEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
          sendNameToServer();
        }
      }
      
    }
    // Add a handler to send the name to the server
    MyHandler handler = new MyHandler();
    sendButton.addClickHandler(handler);
    nameField.addKeyUpHandler(handler);
  }

}

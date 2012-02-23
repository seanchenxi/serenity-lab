package com.seanchenxi.gwt.ui.resource.image;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ImgSources extends ClientBundle {
	
  @Source("window/icon-error.gif")
  ImageResource icon_MsgBox_error();

  @Source("window/icon-info.gif")
  ImageResource icon_MsgBox_info();

  @Source("window/icon-question.gif")
  ImageResource icon_MsgBox_question();

  @Source("window/icon-warning.gif")
  ImageResource icon_MsgBox_warning();
}

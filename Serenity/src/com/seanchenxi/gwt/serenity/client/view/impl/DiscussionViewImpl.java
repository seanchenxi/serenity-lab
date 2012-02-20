package com.seanchenxi.gwt.serenity.client.view.impl;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.seanchenxi.gwt.serenity.client.SerenityUtil;
import com.seanchenxi.gwt.serenity.client.view.DiscussionView;

public class DiscussionViewImpl extends Composite implements DiscussionView {

	interface Template extends SafeHtmlTemplates {
	  
	  static final String PREFIX = "discussion";
	  
		@SafeHtmlTemplates.Template(
		    "<div class=\"" + PREFIX + "-meta\">" +
				"<img class=\"avatar\" alt=\"\" src=\"{0}\" width=\"{1}\" height=\"{1}\" />" +
				"<h4 class=\"" + PREFIX + "-author\">{2}</h4>" +
				"<span class=\"" + PREFIX + "-date\">{3}</span>" +
				"</div>")
		SafeHtml discussionAuthorMeta(SafeUri gravatar, int size, String name, String dateString);
		
		@SafeHtmlTemplates.Template("<span class=\"" + PREFIX + "-counter\">{0}</span>")
    SafeHtml discussionCounter(int counter);
	}
	
	private static final Template TEMPLATE = GWT.create(Template.class);
	
  //Mystery Man
  protected static final String DEFAULT_GRAVATAR = "http://www.gravatar.com/avatar/ad516503a11cd5ca435acc9bb6523536";
  protected static final int AVATAR_SIZE = 32;
  
	
	private FlowPanel main;
	private HTML title;

	public DiscussionViewImpl() {
		initWidget(main = new FlowPanel());
		main.add(title = new HTML());
		title.setStyleName(Template.PREFIX + "s-title");
		getElement().setId(Template.PREFIX + "s");
	}
	
  @Override
  public void setDiscussionsCount(int count) {
    title.setText(count + " Comments:");
  }

	@Override
	public void addDiscussion(int id, String gravatar, String name, Date date, String content) {
	  int counter = main.getWidgetCount();
		SafeHtmlBuilder shb = new SafeHtmlBuilder();
		shb.append(TEMPLATE.discussionAuthorMeta(getGravatarUri(gravatar, AVATAR_SIZE), AVATAR_SIZE, name, SerenityUtil.toDateTimeString(date)));
		shb.appendHtmlConstant("<div class=\"" + Template.PREFIX + "-content "+ (counter % 2 == 0 ? "odd" : "even") +"\">");
		shb.appendHtmlConstant(content);
		shb.append(TEMPLATE.discussionCounter(counter));
		shb.appendHtmlConstant("</div>");
		HTML discussion = new HTML(shb.toSafeHtml());
		discussion.setStyleName(Template.PREFIX);
		discussion.getElement().setId(Template.PREFIX + "-" + id);
		main.add(discussion);
	}
	
	@Override
	public void clearAll() {
		main.clear();
		main.add(title);
	}
	
	private SafeUri getGravatarUri(String gravatar, int size){
    return UriUtils.fromString(gravatar+"?s="+size+"&d=" + DEFAULT_GRAVATAR + "?s="+size);
  }
	
}

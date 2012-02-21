package com.seanchenxi.gwt.serenity.client.view.impl;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.seanchenxi.gwt.serenity.client.SerenityUtil;
import com.seanchenxi.gwt.serenity.client.view.DiscussionView;

public class DiscussionViewImpl extends Composite implements DiscussionView {

	interface Template extends SafeHtmlTemplates {
		@Template("<div class=\"authormeta\"><h4 class=\"comment-author\">{0}</h4><span class=\"comment-date\">{1}</span></div>")
		SafeHtml discussionAuthorMeta(String name, String dateString);
		@Template("<h3 style=\"border-bottom:1px solid #CCC;border-bottom:1px solid rgba(0, 0, 0, 0.1);padding-bottom:0.1em;\">{0} Comments:</h3>")
    SafeHtml discussionTitle(int count);
		@Template("<span style=\"position:absolute;right:10px;top:10px;color:#F0F0F0; color:rgba(0, 0, 0, 0.05);font-size:3.5em;\">{0}</span>")
    SafeHtml discussionCounter(int count);
	}
	
	private static final Template TEMPLATE = GWT.create(Template.class);
	
	private FlowPanel main;
	private HTML title;

	public DiscussionViewImpl() {
		initWidget(main = new FlowPanel());
		main.add(title = new HTML());
		getElement().setId("discussion");
	}
	
  @Override
  public void setDiscussionsCount(int count) {
    title.setHTML(TEMPLATE.discussionTitle(count));
  }

	@Override
	public void addDiscussion(int id, String name, Date date, String content) {
	  int counter = main.getWidgetCount();
		SafeHtmlBuilder shb = new SafeHtmlBuilder();
		shb.append(TEMPLATE.discussionAuthorMeta(name, SerenityUtil.toDateTimeString(date)));
		shb.appendHtmlConstant("<div class=\"comment-content "+ (counter % 2 == 0 ? "odd" : "even") +"\">");
		shb.appendHtmlConstant(content);
		shb.append(TEMPLATE.discussionCounter(counter));
		shb.appendHtmlConstant("</div>");
		HTML discussion = new HTML(shb.toSafeHtml());
		discussion.getElement().setId("discussion-" + id);
		discussion.getElement().getStyle().setPosition(Position.RELATIVE);
		main.add(discussion);
	}

	@Override
	public void clearAll() {
		main.clear();
		main.add(title);
	}
	
}

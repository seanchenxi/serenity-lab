package com.seanchenxi.gwt.serenity.client.view.impl;

import java.util.Date;

import com.google.gwt.core.client.GWT;
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
		SafeHtml discussion(String name, String dateString);
	}
	
	private static final Template TEMPLATE = GWT.create(Template.class);
	
	private FlowPanel main;

	public DiscussionViewImpl() {
		initWidget(main = new FlowPanel());
		getElement().setId("discussion");
	}

	@Override
	public void addDiscussion(int id, String name, Date date, String content) {
		SafeHtmlBuilder shb = new SafeHtmlBuilder();
		shb.append(TEMPLATE.discussion(name, SerenityUtil.toDateTimeString(date)));
		shb.appendHtmlConstant("<div class=\"comment-content\">");
		shb.appendHtmlConstant(content);
		shb.appendHtmlConstant("</div>");
		HTML discussion = new HTML(shb.toSafeHtml());
		discussion.getElement().setId("discussion-" + id);
		main.add(discussion);
	}

	@Override
	public void clearAll() {
		main.clear();
	}
	
}

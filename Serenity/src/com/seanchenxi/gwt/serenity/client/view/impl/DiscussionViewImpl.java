package com.seanchenxi.gwt.serenity.client.view.impl;

import java.util.Date;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.seanchenxi.gwt.serenity.client.SerenityUtil;
import com.seanchenxi.gwt.serenity.client.resource.SerenityResources;
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
		SafeHtml AuthorMeta(SafeUri gravatar, int size, String name, String dateString);
		
		@SafeHtmlTemplates.Template(
        "<div class=\"" + PREFIX + "-meta\">" +
          "<img class=\"avatar\" alt=\"\" src=\"{0}\" width=\"{1}\" height=\"{1}\" />" +
          "<h4 class=\"" + PREFIX + "-author\"><a target=\"_balnk\" href=\"{4}\" title=\"{2}'s website\">{2}</a></h4>" +
          "<span class=\"" + PREFIX + "-date\">{3}</span>" +
        "</div>")
    SafeHtml AuthorMetaWithUrl(SafeUri gravatar, int size, String name, String dateString, SafeUri url);
		
		@SafeHtmlTemplates.Template(
		    "<div class=\"" + PREFIX + "-content {0}\">" +
		    		"{1}<span class=\"" + PREFIX + "-counter\">{2}</span>" +
		    "</div>")
    SafeHtml Content(String style, SafeHtml content, int index);
	}
	
	private static final Template TEMPLATE = GWT.create(Template.class);
	
	protected static final String SUB_PREFIX = "child-";
  protected static final int AVATAR_SIZE = 32;
	
	private FlowPanel main;
	private HTML title;
	private HashMap<Integer,Integer> idIdex;

	public DiscussionViewImpl() {
	  idIdex = new HashMap<Integer, Integer>();
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
	public void addDiscussion(int id, String gravatar, String name, String url, String content, Date date, int parentId) {
	  int index = main.getWidgetCount();
		SafeHtmlBuilder shb = new SafeHtmlBuilder();
		if(url != null && !url.isEmpty()){
      shb.append(TEMPLATE.AuthorMetaWithUrl(getGravatarUri(gravatar), AVATAR_SIZE, name, SerenityUtil.toDateTimeString(date), UriUtils.fromString(url)));		  
		}else{
	    shb.append(TEMPLATE.AuthorMeta(getGravatarUri(gravatar), AVATAR_SIZE, name, SerenityUtil.toDateTimeString(date)));
		}
		shb.append(TEMPLATE.Content(index % 2 == 0 ? "odd" : "even", SafeHtmlUtils.fromTrustedString(content), index));
		HTML discussion = new HTML(shb.toSafeHtml());
		discussion.setStyleName(Template.PREFIX);
		discussion.getElement().setId(Template.PREFIX + "-" + id);
		Integer parentIndex = null;
		if(parentId > 0 && (parentIndex = idIdex.get(parentId)) != null){
		  String parentStyleName = main.getWidget(parentIndex).getStyleName();
		  int level = 0;
		  if(parentStyleName.contains(SUB_PREFIX)){
		    level = Integer.parseInt(parentStyleName.split(SUB_PREFIX)[1]);
		  }
		  discussion.addStyleName(SUB_PREFIX + (++level));
		  main.insert(discussion, parentIndex.intValue() + 1);
		}else{
		  main.add(discussion);
		}
		idIdex.put(id, main.getWidgetIndex(discussion));
	}
	
	@Override
	public void clearAll() {
	  idIdex.clear();
		main.clear();
		main.add(title);
	}
	
	private SafeUri getGravatarUri(String gravatar){
	  if(gravatar != null && !gravatar.isEmpty())
	    return UriUtils.fromString(gravatar+"?s=" + AVATAR_SIZE + "&d=mm");
	  else
	    return SerenityResources.IMG.avatar32().getSafeUri();
  }
	
}

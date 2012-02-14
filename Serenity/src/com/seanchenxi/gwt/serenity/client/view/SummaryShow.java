package com.seanchenxi.gwt.serenity.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.google.gwt.user.client.ui.HasAlignment;

public class SummaryShow extends Composite implements HasClickHandlers {
	
	private static final String POST_TITLE_PREFIX = "<span class=\"unSelectable\">&Xi;&nbsp;</span>";
	
	private Grid grid;
	private String id;
	
	public SummaryShow(){
		initWidget(grid = new Grid(3, 1));
		grid.setStyleName("summaryTable");
		grid.setCellPadding(5);
		CellFormatter cellFormatter = grid.getCellFormatter();
		cellFormatter.addStyleName(0, 0, "summaryTitle");
		cellFormatter.addStyleName(1, 0, "summaryContent");
		cellFormatter.addStyleName(2, 0, "summaryMeta");
		cellFormatter.setAlignment(0, 0, HasAlignment.ALIGN_LEFT, HasAlignment.ALIGN_TOP);
		cellFormatter.setAlignment(1, 0, HasAlignment.ALIGN_LEFT, HasAlignment.ALIGN_MIDDLE);
		cellFormatter.setAlignment(2, 0, HasAlignment.ALIGN_RIGHT, HasAlignment.ALIGN_BOTTOM);
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void setPostTitle(String postTitle){
		grid.setHTML(0, 0, "<h2>" + POST_TITLE_PREFIX + postTitle + "</h2>");
	}
	
	public void setPostSummary(String summary){
		grid.setHTML(1, 0, shorten(summary, 200));
	}
	
	public void setPostMetaInfo(String metaInfo){
		grid.setHTML(2, 0, metaInfo);
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return addDomHandler(handler, ClickEvent.getType());
	}

	public boolean isHighlighted() {
		return getStyleName().indexOf("hilight") != -1;
	}
	
	public void setHighlight(boolean hilight){
		if(hilight){
			addStyleName("hilight");
		}else{
			removeStyleName("hilight");
		}
	}
	
	private String shorten(String str, int length){
		if(str.length() > length){
			return str.substring(0,length)+"...";
		}
		return str+"...";
	}
	
}

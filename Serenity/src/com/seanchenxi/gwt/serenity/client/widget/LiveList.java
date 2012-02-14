package com.seanchenxi.gwt.serenity.client.widget;

import java.util.Iterator;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.layout.client.Layout.AnimationCallback;
import com.google.gwt.user.client.ui.AnimatedLayout;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.seanchenxi.gwt.serenity.client.view.SummaryShow;

public class LiveList extends ResizeComposite implements AnimatedLayout, ClickHandler {
	
	public interface PagingController {
		void newerPage();
		void olderPage();
	}
	
	private final static Unit UNIT = Unit.PX;
	
	private LayoutPanel lp;
	
	private HTML title;
	private HorizontalPanel paginControlZone;
	
	private ScrollPanel csp;
	private VerticalPanel vp;
	
	private Label pagingInfo;
	private Label newerBtn;
	private Label olderBtn;

	private PagingController pagingController;
	
	public LiveList(){
		initWidget(lp = new LayoutPanel());
		
		title =  new HTML("");
		title.setStyleName("contentList-title");
		lp.add(title);
		lp.setWidgetTopHeight(title, 0, UNIT, 32, UNIT);
		lp.setWidgetLeftRight(title, 0, UNIT, 0, UNIT);
		
		paginControlZone = new HorizontalPanel();
		paginControlZone.setSize("auto", "auto");
		paginControlZone.setSpacing(2);
		paginControlZone.setVerticalAlignment(HasAlignment.ALIGN_MIDDLE);
		paginControlZone.setHorizontalAlignment(HasAlignment.ALIGN_RIGHT);
		pagingInfo = new Label();
		pagingInfo.setStyleName("paging-info-lbl");
		newerBtn = new Label(" < ");
		newerBtn.setStyleName("newer-lbl");
		newerBtn.setTitle("newer");
		newerBtn.addClickHandler(this);
		olderBtn = new Label(" > ");
		olderBtn.setTitle("older");
		olderBtn.setStyleName("older-lbl");
		olderBtn.addClickHandler(this);
		paginControlZone.add(pagingInfo);
		paginControlZone.add(newerBtn);
		paginControlZone.add(olderBtn);
		lp.add(paginControlZone);
		lp.setWidgetTopHeight(paginControlZone, 0, UNIT, 32, UNIT);
		lp.setWidgetRightWidth(paginControlZone, 0, UNIT, 160, UNIT);
		
		vp = new VerticalPanel();
		vp.setWidth("100%");
		csp = new ScrollPanel(vp);
		lp.add(csp);
	}
	
	public void setPagingInfo(String titleHtml, int offset, int size, int total){
		title.setHTML(titleHtml);
		int end = Math.min(offset + size, total);
		pagingInfo.setText(offset + "-" + end + " of " + total);
		enablePagingBtn(newerBtn, offset > 0);
		enablePagingBtn(olderBtn, end < total);
	}

	public void renderUI(){
		lp.setWidgetBottomHeight(csp, 0, UNIT, 0, UNIT);
		lp.forceLayout();
		lp.setWidgetTopBottom(csp, 32, UNIT, 0, UNIT);
		lp.setWidgetLeftRight(csp, 0, UNIT, 0, UNIT);
		lp.animate(1000);
	}
	
	public void add(Widget widget){
		vp.add(widget);
	}
	
	public Label getLeft() {
		return newerBtn;
	}
	
	public Label getRight() {
		return olderBtn;
	}
	
	public void clearAllContent() {
		vp.clear();
	}

	@Override
	public void animate(int duration) {
		lp.animate(duration);
	}

	@Override
	public void animate(int duration, AnimationCallback callback) {
		lp.animate(duration, callback);
	}

	@Override
	public void forceLayout() {
		lp.forceLayout();
	}
	
	private void enablePagingBtn(Label l, boolean enable){
		if(enable){
			l.removeStyleDependentName("disable");
		}else{
			l.addStyleDependentName("disable");
		}
	}

	public void setPagingController(PagingController controller) {
		this.pagingController = controller;
	}

	@Override
	public void onClick(ClickEvent event) {
		final Object o = event.getSource();
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				if(o instanceof Widget){
					if(o == newerBtn){
						pagingController.newerPage();
					}else if(o == olderBtn){
						pagingController.olderPage();
					}
				}
			}
		});
	}

	public void highlightItem(String contentSlug) {
		Iterator<Widget> itW = vp.iterator();
		while(itW.hasNext()){
			Widget w = itW.next();
			if(w instanceof SummaryShow){
				if(((SummaryShow) w).getId().equalsIgnoreCase(contentSlug)){
					((SummaryShow) w).setHighlight(true);
				}else{
					((SummaryShow) w).setHighlight(false);
				}
			}
		}
	}

}

package com.seanchenxi.gwt.serenity.client.place;

public class HomePlace extends SerenityPlace implements HasPaging {

	public static final String PREFIX = "home";
	
	private int page;

	public HomePlace() {
		this(0);
	}
	
	public HomePlace(int page) {
		this.page = page;
	}
	
	@Override
	public String getPrefix() {
		return HomePlace.PREFIX;
	}
	
	@Override
	public int getPage() {
		return page;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + page;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		HomePlace other = (HomePlace) obj;
		if (page != other.page)
			return false;
		return true;
	}
	  
}

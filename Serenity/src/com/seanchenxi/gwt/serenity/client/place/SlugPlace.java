package com.seanchenxi.gwt.serenity.client.place;



public abstract class SlugPlace extends SerenityPlace implements HasPaging {

	private final String slug;
	private final int page;
	
	protected SlugPlace(String slug, int page){
		this.slug = slug.toLowerCase();
		this.page = page;
	}
	
	public String getSlug(){
		return this.slug;
	}
	
	@Override
	public int getPage(){
		return this.page;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + page;
		result = prime * result + ((slug == null) ? 0 : slug.hashCode());
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
		SlugPlace other = (SlugPlace) obj;
		if (page != other.page)
			return false;
		if (slug == null) {
			if (other.slug != null)
				return false;
		} else if (!slug.equals(other.slug))
			return false;
		return true;
	}
}

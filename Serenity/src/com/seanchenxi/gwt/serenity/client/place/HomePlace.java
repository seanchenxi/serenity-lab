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

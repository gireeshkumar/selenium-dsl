package com.giri.target.dsl.keywords;

import com.giri.target.dsl.ifc.IDataKeyword;

public class KeywordFlex implements IDataKeyword {

	private String name;
	private String locator;
	
	
	public KeywordFlex(String name, String locator) {
		super();
		this.name = name;
		this.locator = locator;
	}

	

	@Override
	public String name() {
		return name;
	}



	@Override
	public String value() {
		return locator;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((locator == null) ? 0 : locator.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KeywordFlex other = (KeywordFlex) obj;
		if (locator == null) {
			if (other.locator != null)
				return false;
		} else if (!locator.equals(other.locator))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
}

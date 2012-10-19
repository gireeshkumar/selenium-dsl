package com.giri.target.dsl.keywords;

import com.giri.target.dsl.ifc.ILocatorKeyword;

public class LocatorKeyword implements ILocatorKeyword {

	private final String locator;
	private final String name;

	public LocatorKeyword(final String l_name, final String l_locator) {
		name = l_name;
		locator = l_locator;
	}

	@Override
	public String locator() {
		return locator;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public String value() {
		return locator();
	}
}

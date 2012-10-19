package com.giri.target;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class jQueryBy extends By {

	private String locator;

	public jQueryBy(String locator) {
		this.locator = locator;
	}

	public static jQueryBy jqueryBy(String locator){
		return new jQueryBy(locator);
	}
	
	@Override
	public List<WebElement> findElements(SearchContext arg0) {
		return null;
	}

}

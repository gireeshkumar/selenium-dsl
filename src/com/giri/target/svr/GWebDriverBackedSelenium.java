package com.giri.target.svr;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;

import com.thoughtworks.selenium.CommandProcessor;

public class GWebDriverBackedSelenium extends WebDriverBackedSelenium {

	public GWebDriverBackedSelenium(WebDriver baseDriver, String baseUrl) {
		super(baseDriver, baseUrl);
		
	}

	public CommandProcessor getCommandProcessor(){
		return this.commandProcessor;
	}
}

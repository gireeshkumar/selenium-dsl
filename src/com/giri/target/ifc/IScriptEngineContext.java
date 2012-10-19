package com.giri.target.ifc;

import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.thoughtworks.selenium.Selenium;

public interface IScriptEngineContext {

	public abstract void command(ICommand cmd);

	public abstract void testRunner(ITestCommandRunner testRunner);

	public abstract void data(Properties data);

	// optional
	public abstract void selenium(Selenium selenium);
	
	public abstract void webdriver(WebDriver driver);

	public Object put(String key, Object value);
	
	public Object get(String key);
}
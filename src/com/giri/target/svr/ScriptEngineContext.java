package com.giri.target.svr;

import java.util.HashMap;
import java.util.Properties;

import net.sourceforge.seleniumflexapi.cal.TARGetPageDriver;

import org.jdom.Element;
import org.openqa.selenium.WebDriver;

import com.giri.target.common.Utils;
import com.giri.target.ifc.ICommand;
import com.giri.target.ifc.IScriptEngineContext;
import com.giri.target.ifc.ITestCommandRunner;
import com.giri.target.ifc.ITestDataStore;
import com.thoughtworks.selenium.Selenium;

public class ScriptEngineContext extends HashMap<String, Object> implements IScriptEngineContext{

	private static final long serialVersionUID = 1L;

	public ScriptEngineContext(ITestDataStore testDataStore){
		put("testDataStore", testDataStore);
		put("utils", Utils.INSTANCE);
	}
	
	
	/* (non-Javadoc)
	 * @see com.giri.target.svr.IScriptEngineContext#command(com.giri.target.ifc.ICommand)
	 */
	public void command(ICommand cmd){
		put("command", cmd);
	}

	@Override
	public Object get(String key) {
		return super.get(key);
	}
	
	/* (non-Javadoc)
	 * @see com.giri.target.svr.IScriptEngineContext#testRunner(com.giri.target.ifc.ITestCommandRunner)
	 */
	public void testRunner(ITestCommandRunner testRunner) {
		put("testRunner", testRunner);
	}

	/* (non-Javadoc)
	 * @see com.giri.target.svr.IScriptEngineContext#data(java.util.Properties)
	 */
	public void data(Properties data) {
		put("data", data);
	}
	
	@Override
	public void selenium(Selenium selenium) {
		put("selenium", selenium);
	}
	
	@Override
	public void webdriver(WebDriver driver) {
		put("webdriver", driver);
	}

	// optional
	public void driver(TARGetPageDriver driver) {
		put("driver", driver);
	}
	// optional
	public void element(Element element) {
		put("element", element);
	}
	
	
}

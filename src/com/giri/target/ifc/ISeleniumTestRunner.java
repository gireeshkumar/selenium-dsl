package com.giri.target.ifc;

import com.giri.target.core.TestFailedException;

public interface ISeleniumTestRunner extends ITestCommandRunner {

	

	public abstract void stop();

	public abstract void start(String server, String port, String browser,
			String url, String appname) throws Exception;
	
	public void open(String url);

	public abstract void setDataCollector(ITestDataCollector dataCollector);

	public abstract boolean isStarted();

	public abstract String getCurrentURL();

	public abstract String executeScript(String type, String flexAppName, String script) throws Exception;

	public abstract String execute(ICommand cmd) throws TestFailedException;

	public abstract void addTestListener(ITestListener listener);

	public ITestDataCollector getDataCollector();

	

}

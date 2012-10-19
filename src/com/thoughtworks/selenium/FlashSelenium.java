package com.thoughtworks.selenium;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/*
 * The FlashSelenium is the component adding Flash 
 * communication capabilities to the Selenium framework. 
 * Basically, the FlashSelenium is a Selenium RC Client 
 * driver extension for helping exercise the tests 
 * against the Flash component.  
 **/
public class FlashSelenium implements IFlashSelenium {
	private Selenium selenium;
	private String flashJSStringPrefix;
	private String flashObjectId;
	private WebDriver webdriver;

	public FlashSelenium(Selenium selenium, WebDriver webdriver, String flashObjectId) {
		this.selenium = selenium;
		this.webdriver = webdriver;
		this.flashObjectId = flashObjectId;
		generateCommand();
	}
	
	public void setFlashObjectID(String objectid){
		this.flashObjectId = objectid;
		generateCommand();
	}
	
	/**
	 * 
	 */
	private void generateCommand() {
		// verify the browser type
		String appName = selenium.getEval("navigator.userAgent");
		if (appName.contains(com.thoughtworks.selenium.BrowserConstants.FIREFOX3) || appName.contains(com.thoughtworks.selenium.BrowserConstants.IE)) {
			flashJSStringPrefix = createJSPrefix_window_document(flashObjectId);		
		}
		else {
			flashJSStringPrefix = createJSPrefix_document(flashObjectId);	
		}
	}

	// constructor used for test purpose
	FlashSelenium(Selenium browser, String flashObjectId, String flashJSStringPrefix) {
		this.selenium = browser;
		this.flashObjectId = flashObjectId;
		this.flashJSStringPrefix = flashJSStringPrefix;
	}

	
	// creational method used for test purpose
	static FlashSelenium createFlashSeleniumFlashObjAsDocument(Selenium browser, String flashObjectId){
		return new FlashSelenium(browser, flashObjectId, createJSPrefix_document(flashObjectId));
	}
	
	static FlashSelenium createFlashSeleniumFlashObjAsWindowDocument(Selenium browser, String flashObjectId){
		return new FlashSelenium(browser, flashObjectId, createJSPrefix_window_document(flashObjectId));
	}
	
	static String createJSPrefix_window_document(String flashObjectId) {
		return "window.document['"
			+ flashObjectId + "'].";	
	}

	static String createJSPrefix_document(String flashObjectId) {
		return "document['"
			+ flashObjectId + "'].";
	}

	public String call(String functionName, String ... args) {
		final String evalString = "return "+this.jsForFunction(functionName, args);
//		final String rslt = selenium.getEval(evalString);
		
		if(webdriver == null){
			throw new RuntimeException("WebDriver is null");
		}
		
		Object jsResult = ((JavascriptExecutor)webdriver).executeScript(evalString);
		
		return (jsResult == null ? null : jsResult.toString());
	}	

	String flashJSStringPrefix(){
		return this.flashJSStringPrefix;
	}

	String jsForFunction(String functionName, String ... args) {
		String functionArgs = "";
		if (args.length>0){;
	      for (int i=0;i < args.length; i++) {
	    	  functionArgs = functionArgs + "'" + args[i] + "',";
	      }
	      // remove last comma
	      functionArgs = functionArgs.substring(0, functionArgs.length() -1);
		}
		return flashJSStringPrefix + functionName + "(" + functionArgs + ");";
	}	

	public String GetVariable(String varName) {
		return this.call("GetVariable", varName);
	}
	
	public void GotoFrame(int frameNumber) {
		this.call("GotoFrame", Integer.toString(frameNumber));
	}
	
	public boolean IsPlaying() {
		return "true".equals(this.call("IsPlaying"));
	}
	
	public void LoadMovie(int layerNumber, String url ) {
		this.call("LoadMovie", Integer.toString(layerNumber), url );
	}
	
	public void Pan(int x,int y,int mode) {
		this.call("Pan", Integer.toString(x), Integer.toString(y), Integer.toString(mode));
	}
	
	public int PercentLoaded() {
		return new Integer(this.call("PercentLoaded")).intValue();
	}
	
	public void Play() {
		this.call("Play");
	}
	
	public void Rewind() {
		this.call("Rewind");
	}
	
	public void SetVariable(String varName, String varValue) {
		this.call("SetVariable", varName,  varValue);
	}
	
	public void SetZoomRect(int left,int top,int right,int bottom ) {
		this.call("SetZoomRect", Integer.toString(left), Integer.toString(top), Integer.toString(right), Integer.toString(bottom));
	}	
	
	public void StopPlay() {
		this.call("StopPlay");
	}
	
	public int TotalFrames() {
		return new Integer(this.call("TotalFrames")).intValue();
	}

	public void Zoom(int percent) {
		this.call("Zoom", Integer.toString(percent));
	}	
	
	public void TCallFrame (String target, int frameNumber) {
		this.call("TCallFrame", target, Integer.toString(frameNumber));
	}	
	
	public void TCallLabel (String target, String label ) {
		this.call("TCallLabel", target, label );
	}
	
	public int TCurrentFrame(String target) {
		return new Integer(this.call("TCurrentFrame", target)).intValue();
	}	
	
	public String TCurrentLabel(String target) {
		return this.call("TCurrentLabel", target);
	}	

	public String TGetProperty(String target, String property) {
		return this.call("TGetProperty", target, property);
	}	
	
	public int TGetPropertyAsNumber(String target, String property) {
		return new Integer(this.call("TGetPropertyAsNumber", target, property)).intValue();
	}	
	
	public void TGotoFrame (String target, int frameNumber) {
		this.call("TGotoFrame", target, Integer.toString(frameNumber));
	}	
	
	public void TGotoLabel (String target, String label ) {
		this.call("TGotoLabel", target, label );
	}
	
	public void TPlay (String target ) {
		this.call("TPlay", target );
	}
	
	public void TSetProperty(String target, String property, String value) {
		this.call("TSetProperty", target, property, value);
	}	
	
	public void TStopPlay(String target) {
		this.call("TStopPlay", target);
	}
	
	public void OnProgress(int percent) {
		this.call("OnProgress", Integer.toString(percent));
	}	
	
	public void OnReadyStateChange(int state) {
		this.call("OnReadyStateChange", Integer.toString(state));
	}
	
	public String FSCommand(String command, String ... args) {
		String[] newArgs = new String[args.length+1];
		newArgs[0] = command;
	    for (int i=0;i < args.length; i++) {
	    	newArgs[i+1] = args[i];
	    }
		return this.call("FSCommand", newArgs);
	}	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((selenium == null) ? 0 : selenium.hashCode());
		result = prime
				* result
				+ ((flashJSStringPrefix == null) ? 0 : flashJSStringPrefix
						.hashCode());
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
		final FlashSelenium other = (FlashSelenium) obj;
		if (selenium == null) {
			if (other.selenium != null)
				return false;
		} else if (!selenium.equals(other.selenium))
			return false;
		if (flashJSStringPrefix == null) {
			if (other.flashJSStringPrefix != null)
				return false;
		} else if (!flashJSStringPrefix.equals(other.flashJSStringPrefix))
			return false;
		return true;
	}



	
	

}

/*	
 *	License
 *	
 *	This file is part of The TARGet framework
 * 
 *   	/__  ___/ // | |     //   ) )  //   ) )
 *   	  / /    //__| |    //___/ /  //         ___    __  ___
 *   	 / /    / ___  |   / ___ (   //  ____  //___) )  / /
 *   	/ /    //    | |  //   | |  //    / / //        / /
 *      / /    //     | | //    | | ((____/ / ((____    / /
 *   	 
 *	    ______     __,             _ ___              ,____                                                   
 *      (  /       /  |            ( /   )              /   )                                            
 *	     /       /-.-|             /-.-<              /  __                                  
 *  Web _/est   _/    |_utomation f/     \_amework by (___/iri
 *  -.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.
 *	

 *  TARGet is free software: you can redistribute it and/or
 *  modify it  under  the  terms  of  the  GNU  General Public License as 
 *  published  by  the  Free  Software Foundation,  either  version  3 of 
 *  the License, or any later version.
 *
 *  TARGet is distributed in the hope that it will be useful,
 *  but  WITHOUT  ANY  WARRANTY;  without  even the  implied  warranty  of
 *  MERCHANTABILITY   or   FITNESS   FOR  A  PARTICULAR  PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with The SeleniumFlex-API.
 *  If not, see http://www.gnu.org/licenses/
 *  
 * 
 *  @Author	Gireesh Kumar G - gireeshkumar.g@gmail.com
 *  @Date 	July 2010
 *
 */
package com.giri.target.svr;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

import junit.framework.Assert;
import net.sourceforge.seleniumflexapi.FlexSelenium;
import net.sourceforge.seleniumflexapi.cal.TARGetPageDriver;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.giri.target.CommandsClassLoader;
import com.giri.target.core.Application;
import com.giri.target.core.TestFailedException;
import com.giri.target.ifc.ICommand;
import com.giri.target.ifc.ICommandProcessor;
import com.giri.target.ifc.IFlexCommandProcessor;
import com.giri.target.ifc.IHTMLCommandProcessor;
import com.giri.target.ifc.IScriptEngineContext;
import com.giri.target.ifc.ISeleniumTestRunner;
import com.giri.target.ifc.ITestDataCollector;
import com.giri.target.ifc.ITestListener;
import com.giri.target.impl.Assertion;

public class SeleniumTestRunner implements ISeleniumTestRunner {
	
	private static final String SYSTEM_OVERRIDE = "system.override.";
	private static final String SYSTEM = "system.";
	
	static{
		try {
			CommandsClassLoader.loadCommandClasses();
			// Load browser driver class properties
			try {
				final InputStream stream = CommandsClassLoader.class.getClassLoader().getResourceAsStream("system.properties");
				Properties props = new Properties();
				props.load(stream);
				
				Enumeration<Object> keys = props.keys();
				String key;
				if(keys != null){
					while(keys.hasMoreElements()){
						String txt = keys.nextElement().toString();
						if(txt.startsWith(SYSTEM_OVERRIDE)){
							key = txt.substring(SYSTEM_OVERRIDE.length(), txt.length());
							// override and set the property
							System.setProperty(key, props.getProperty(txt));
						}
						else if(txt.startsWith(SYSTEM)){
							key = txt.substring(SYSTEM.length(), txt.length());
							// check exist, if not set
							String existing = System.getProperty(key);
							if(existing == null || (existing = existing.trim()).length() == 0){
								System.setProperty(key, props.getProperty(txt));
							}
						}
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static Properties loadDriverClasses() {
		try {
			final InputStream stream = CommandsClassLoader.class.getClassLoader().getResourceAsStream("browsertypes.properties");
			Properties props = new Properties();
			props.load(stream);
			return props;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static final String ACTION_SUCCESS = "true";
	private static final Properties driverClasses = loadDriverClasses();
	private static final SeleniumTestRunner SELF = new SeleniumTestRunner();

	
	public static final SeleniumTestRunner getInstance() {
		return SELF;
	}
	
	protected WebDriver webdriver;
	private FlexSelenium flexSelenium;
	private TARGetPageDriver driver;
	private String applicationName;
	private ArrayList<ITestListener> listeners;
	private int listenerCount = -1;
	private String currentURL;

	private ITestDataCollector dataCollector;

	private final ITestListener testListenerDelegate = new ITestListener() {

		@Override
		public void asserting(String message, TEST_STATUS status) {
			if (listenerCount > 0) {
				if (listenerCount == 1) {
					listeners.get(0).asserting(message, status);
				} else {
					for (int i = 0; i < listenerCount; i++) {
						listeners.get(i).asserting(message, status);
					}
				}
			}
		}

		@Override
		public void endTest(String message) {
			if (listenerCount > 0) {
				if (listenerCount == 1) {
					listeners.get(0).endTest(message);
				} else {
					for (int i = 0; i < listenerCount; i++) {
						listeners.get(i).endTest(message);
					}
				}
			}

		}

		public void log(String message) {
			if (listenerCount > 0) {
				if (listenerCount == 1) {
					listeners.get(0).log(message);
				} else {
					for (int i = 0; i < listenerCount; i++) {
						listeners.get(i).log(message);
					}
				}
			}
		}

		@Override
		public void logError(String message) {
			if (listenerCount > 0) {
				if (listenerCount == 1) {
					listeners.get(0).logError(message);
				} else {
					for (int i = 0; i < listenerCount; i++) {
						listeners.get(i).logError(message);
					}
				}
			}
		}

		@Override
		public void logError(Throwable ex) {
			// TODO Auto-generated method stub

		}

		@Override
		public void startTest(String message) {
			if (listenerCount > 0) {
				if (listenerCount == 1) {
					listeners.get(0).startTest(message);
				} else {
					for (int i = 0; i < listenerCount; i++) {
						listeners.get(i).startTest(message);
					}
				}
			}
		}

		@Override
		public void testing(String message, TEST_STATUS status) {
			if (listenerCount > 0) {
				if (listenerCount == 1) {
					listeners.get(0).testing(message, status);
				} else {
					for (int i = 0; i < listenerCount; i++) {
						listeners.get(i).testing(message, status);
					}
				}
			}
		};

	};

	private SeleniumTestRunner() {
		
	}

	
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.giri.gwat.ifc.ITestCommandRunner#addTestListener(com.giri.gwat.ifc
	 * .ITestListener)
	 */
	@Override
	public void addTestListener(ITestListener listener) {
		if (listener != null) {
			if (this.listeners == null) {
				this.listeners = new ArrayList<ITestListener>();
			}
			if (!this.listeners.contains(listener)) {
				this.listeners.add(listener);
			}
		}
		listenerCount = (listeners == null ? 0 : listeners.size());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.giri.gwat.ifc.ITestCommandRunner#execute(net.sourceforge.seleniumflexapi
	 * .cal.Command)
	 */
	@Override
	public String execute(ICommand cmd) throws TestFailedException {
		
		ThreadContext.set("cmd", cmd);
		
		//TODO workaround, some time click/type actions doesn't work 
		// this to make focus to the existing window and then run the commands
//		webdriver.switchTo().window(webdriver.getWindowHandle());
		
		execute(cmd.getPreProcessingCommands());
		String result = null;
		try {
			if(dataCollector != null)
				dataCollector.executingCommand(cmd);
			if (cmd.getCommand().equals("sleep")) {
				String args = cmd.getArgs();
				try {
					int waitt = Integer.parseInt(args);
					Application.getInstance().getLogger().log(
							"Testing waiting for page loading, sleeping for "
									+ waitt + " milliseconds");
					Thread.sleep(waitt);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return result;
			}
			
			// TODO handle error condition
			Assertion assertion = null;
			if (cmd instanceof Assertion) {
				assertion = (Assertion) cmd;
				testListenerDelegate.asserting(assertion.getLocator(),
						ITestListener.TEST_STATUS.RUNNING);
			} else {
				testListenerDelegate.testing(cmd.getLocator(),
						ITestListener.TEST_STATUS.RUNNING);
			}
			Application.getInstance().getLogger().log(
					"Running command : " + cmd.getCommand() + ","
							+ cmd.getLocator() + "," + cmd.getArgs());
			
			
			final Class<? extends ICommandProcessor> targetType = (cmd.isFlex() ? IFlexCommandProcessor.TYPE : IHTMLCommandProcessor.TYPE);
			ICommandProcessor cmdProcessor = CommandRegistry.getInstance().getProcessor(targetType, cmd.getCommand());
			
			if(cmdProcessor == null){ // try for generic 'get' methods
				if(cmd.getCommand().startsWith("get")){
					cmdProcessor = CommandRegistry.getInstance().getProcessor(targetType, "get");
				}
			}
			
			
			if(cmdProcessor == null){
				if(targetType == IFlexCommandProcessor.TYPE){
//					final String evalString = getEvalString(cmd);
//					if (cmd.isEnsureVisible()) {
//						driver.ensureWidgetVisibility(cmd.getLocator(), true);
//						driver.ensureWidgetEnabled(cmd.getLocator(), true);
//					}
//					Assert.assertEquals(ACTION_SUCCESS, getEval(evalString));
					
					cmdProcessor =  CommandRegistry.getGenericFlexCommandProcessor();
					
				}else{
					cmdProcessor = CommandRegistry.getGenericHTMLCommandProcessor();
				}
			}
			
			if(cmdProcessor == null){
				throw new Exception("No command processor found for command :"+cmd.getCommand());
			}
			
			result = cmdProcessor.process(this, webdriver, driver, cmd);
			
			boolean success = true;
			if(result != null){
				if("false".equals(result)){
					success = false;
				}
			}
			
			Application.getInstance().getLogger().log("Command '" + cmd.getCommand()+"@"+cmd.getLocator()  + (success ? "' successful":"' failed"));
			
			if (assertion != null) {
				testListenerDelegate.asserting(assertion.getLabel() + "/"
												+ cmd.getLocator(),
												(success ? ITestListener.TEST_STATUS.SUCCESS : ITestListener.TEST_STATUS.FAIL));
			} else {
				testListenerDelegate.testing(cmd.getLocator(), (success ? ITestListener.TEST_STATUS.SUCCESS : ITestListener.TEST_STATUS.FAIL));
			}
			if(dataCollector != null){
				if(success){
					dataCollector.executingCommandSuccess(cmd);
				}else{
					dataCollector.executingCommandFaild(cmd);
				}
			}
			
			
			execute(cmd.getPostProcessingCommands());
			
		} catch (Throwable e) {
			testListenerDelegate.logError("Test Failed");
			if(e.getMessage() != null){
				testListenerDelegate.logError(e.getMessage());
			}
			Application.getInstance().getLogger().log("Command failed");
			Application.getInstance().getLogger().log(e);
			
			final String path = captureScreen(driver);
			if(dataCollector != null){
				dataCollector.executingCommandFaild(cmd);
				dataCollector.setScreenshot(path);
			}
			
			throw new TestFailedException(e.getMessage(), e);
		}
		return result;
	}

	



	private void execute(List<ICommand> cmds) throws TestFailedException {
		if(cmds == null){
			return;
		}
		for (ICommand iCommand : cmds) {
			execute(iCommand);
		}
	}



	/**
	 * @param driver2
	 * @return 
	 */
	private String captureScreen(TARGetPageDriver driver2) {

		String outputpath = "Screenshot not catpured";

		if(webdriver instanceof TakesScreenshot){
			File out = ((TakesScreenshot)webdriver).getScreenshotAs(OutputType.FILE);
			
			final String fileid = UUID.randomUUID().toString();
			final File file = new File("./",fileid+".png");
			
			out.renameTo(file);
			
			outputpath = out.getAbsolutePath();
			
		}else{
			outputpath = "Screenshot not supported:["+webdriver.getClass()+"]";
		}
		
		return outputpath;
	}

	/*
	 * (non-Javadoc)
	 * @see com.giri.gwat.ifc.ITestCommandRunner#executeScript(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String executeScript(String type, String flexAppName, String script) throws Exception{
		String result = null;
		if (script == null || (script = script.trim()).length() == 0) {
			return result;
		}
//		System.out.println("Executing script ["+type+"]:"+script);
		testListenerDelegate.log("Script :\n"+script);
		Exception exception = null;
		try {
			if (type.equals("AS3")) {
				driver.getFlexSelenium().setFlashObjectID(flexAppName);
				final String b64script = toB64Text(script, true);
				result = driver.executeAS3Script(b64script);
				result = flexSelenium.call("getLastAS3ScriptResult",
						new String[] {});
			} else if (type.equals("JS") || type.equals("JQ")) {
				
//				result = selenium.getEval(script);
				
				result = getEval(script).toString();
				
			}
			//else if (type.equals("JQ")) {
				//result = selenium.getCommandProcessor().getString("getJQEval", new String[] {script,});
//			}
			
			testListenerDelegate.log("Script execution result :"+result);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			testListenerDelegate.logError("Test Failed");
			testListenerDelegate.logError(e.getMessage());
			exception = e;
			throw e;
		} finally {
			if(dataCollector != null)
			dataCollector.executingCustomScript(type, flexAppName, script,
					result, exception);
		}
		return result;
	}

	private Object getEval(String evalString) throws Exception {
		return driver.executeScript("return "+evalString);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.giri.gwat.ifc.ITestCommandRunner#getCurrentURL()
	 */
	@Override
	public String getCurrentURL() {
		return currentURL;
	}

	private String getEvalString(ICommand cmd) {
		// window.document['IGECalendar'].doFlexClick('2052009','')
		StringBuffer sbr = new StringBuffer();
		sbr.append("window.document");
		sbr.append("['").append(cmd.getFlexAppName()).append("']");
		sbr.append(".");
		sbr.append(cmd.getCommand()).append("(");
		sbr.append("'").append(cmd.getLocator()).append("'");
		sbr.append(",");
		sbr.append("'").append((cmd.getArgs() == null ? "" : cmd.getArgs()))
				.append("'");
		sbr.append(")");
		return sbr.toString();
	}

	@Override
	public boolean isStarted() {
		return (webdriver != null);
	}

	@Override
	public void setDataCollector(ITestDataCollector dataCollector) {
		this.dataCollector = dataCollector;
	}
	
	@Override
	public ITestDataCollector getDataCollector(){
		return dataCollector;
	}

	@Override
	public void start(String server, String port, String browser, String url,
			String appname) throws Exception {
//		selenium = new GSelenium(server, Integer.valueOf(port),
//				(browser == null ? BrowserTypes.IE.command() : browser), url);
		
		webdriver = newWebDriver(browser);
		GWebDriverBackedSelenium selenium = new GWebDriverBackedSelenium(webdriver, url);
		
		this.applicationName = appname;
		testListenerDelegate.startTest("Starting browser " + url);
		//selenium.start();

		flexSelenium = new FlexSelenium(selenium, webdriver, appname);
//		selenium.windowMaximize();
//		selenium.open(url);
		webdriver.get(url);
		
		
//		selenium.windowFocus();
		testListenerDelegate.log("Open/Maximize/Focus test browser window");
		// selenium.waitForPageToLoad("5000");
		driver = new TARGetPageDriver(selenium, flexSelenium, webdriver);
		this.currentURL = url;
		
	}
	
	private WebDriver newWebDriver(String browser) throws Exception {
		String driverclassStr = null;
		if(driverClasses != null){
			driverclassStr = (String) driverClasses.get(browser);
		}
		final Class<? extends WebDriver> driverClass;
		if(driverclassStr == null){
			// Default is Firefox, possibility of available in ALL OS
			driverClass = FirefoxDriver.class;
		}else{
			driverClass = (Class<? extends WebDriver>) Class.forName(driverclassStr);
		}
		return driverClass.newInstance();
	}



	public void open(String url){
		webdriver.get("about:blank");
		webdriver.get(url);
	}
	
	@Override
	public void stop() {
		webdriver.close();
		webdriver = null;
		driver = null;
		flexSelenium = null;
	}

	private String toB64Text(final String text, final boolean compress)
			throws Exception {
		final byte[] inputbs = text.getBytes(Charset.forName("UTF-8"));

		final byte[] bytesToConvert;
		if (compress) {
			final ByteArrayOutputStream bout = new ByteArrayOutputStream();
			final Deflater d = new Deflater();
			final DeflaterOutputStream dout = new DeflaterOutputStream(bout, d);
			dout.write(inputbs);
			dout.close();
			bout.flush();
			bytesToConvert = bout.toByteArray();
		} else {
			bytesToConvert = inputbs;
		}

		final byte[] s64encBts = Base64.encodeBase64(bytesToConvert);

		return new String(s64encBts);
	}

	@Override
	public void updateContext(IScriptEngineContext context) {
		context.testRunner(this);
		context.webdriver(webdriver);
		context.selenium(driver.getWebDriverBackedSelenium());
		context.put("driver", driver);
		context.put("applicationname",applicationName);
		context.put("dataCollector", dataCollector);
	}
	
	public boolean hasMatchingMethod(String name){
		//TODO fix , find matching selenium methods
		return true;
	}
}

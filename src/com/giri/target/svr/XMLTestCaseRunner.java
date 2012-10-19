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

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.openqa.selenium.UnsupportedCommandException;

import com.giri.target.core.Application;
import com.giri.target.core.BrowserTypes;
import com.giri.target.core.TestFailedException;
import com.giri.target.ifc.ICommand;
import com.giri.target.ifc.IServerInfo;
import com.giri.target.ifc.ITestCommandRunner;
import com.giri.target.impl.Assertion;
import com.giri.target.impl.Command;
import com.giri.target.ui.view.XMLTestListener;

/**
 * @author Gireesh Kumar G
 * @Created Jul 31, 2009
 */
public class XMLTestCaseRunner extends BaseTestCaseRunner{

	// ///////////////
	static class TCXMLFileFilter implements FilenameFilter {
		@Override
		public boolean accept(File dir, String name) {
			return (name.endsWith("tc.xml"));
		}
	}

	public boolean executing = false;

	public XMLTestCaseRunner() {
		super(null, new Properties(), null, null);
	}

	public XMLTestCaseRunner(BaseTestCaseRunner parent) {
		super(parent, parent.data, parent.listener, new XMLTestListener());
		
	}

	protected Object execute(final ITestCommandRunner testRunner, final Element element)
			throws Exception {
		Object result = null;
		
		try{
			TestDataStore.getInstance().setCurrentTestInputData(data);
			preProcess(element);
			final ICommand cmd = toCommand(testRunner, element);
			if (element.getName().equals("context")) {
				if (element.getAttribute("isFlex") != null) {
					isFlex = Boolean.parseBoolean(element
							.getAttributeValue("isFlex"));
				}
				if (element.getAttribute("isEnsureVisible") != null) {
					isEnsureVisible = Boolean.parseBoolean(element
							.getAttributeValue("isEnsureVisible"));
				}
				if (element.getAttribute("flexAppName") != null) {
					flexAppName = element.getAttributeValue("flexAppName");
				}
			} else if (element.getName().equals("command")
					|| element.getName().equals("assertion")) {
				result = testRunner.execute(cmd);
			} else if (element.getName().equals("script")) {
				// <script type="AS3" target="client" flexAppName="IGECalendar" >
				String target = element.getAttributeValue("target");
				String scriptType = element.getAttributeValue("type");
				if(target == null){
					// hardcoded script engine target
					// JQ (JQuery) - only client
					// groovy = only server
					if("jq".equalsIgnoreCase(scriptType)){
						target = "client";
					}else if("groovy".equalsIgnoreCase(scriptType)){
						target = "server";
					}
				}
				
				if (target == null || ("client").equalsIgnoreCase(target)) {
					result = testRunner.executeScript(scriptType, 
														element.getAttributeValue("flexAppName"), 
							processValue(element.getValue(), element));
					
					
				}else if ("server".equalsIgnoreCase(target)) {
					try{
						 final String scriptToExecute = trimLines(processValue(element.getValue(), new Object[]{element}).trim());
						
						 final ScriptEngineContext context = new ScriptEngineContext(TestDataStore.getInstance());
						context.element(element);
						context.command(cmd);
						context.data(data);
						
						testRunner.updateContext(context);
						
						result = ExternalScriptEngine.execute(scriptType, scriptToExecute, context);
					}catch(Exception e){
						System.err.println("Error in execting server side script");
						e.printStackTrace();
						throw e;
					}
				}
			} else if (element.getName().equals("include")) {
				String testcase = element.getAttributeValue("testcase");
				if (testcase != null && (testcase = testcase.trim()).length() > 0) {
					new XMLTestCaseRunner(this).runTest(new File(getCurrentDir(),
							testcase));
				}
			} else if (element.getName().equals("properties")) {
				final String url = element.getAttributeValue("file");
				final FileInputStream stream = (url != null && url.trim().length() > 0) ? new FileInputStream(new File(getCurrentDir(), url)) : null;
				loadData(stream, element);
			}
			
			System.out.println("result :"+result);
			final String storeName = element.getAttributeValue("store");
			if(storeName != null && result != null){
				TestDataStore.getInstance().put(storeName, result);
			}
			
			final String isthread = element.getAttributeValue("thread");
			if("true".equalsIgnoreCase(isthread)){
				
				final String target = element.getAttributeValue("target");
				final String scriptType = element.getAttributeValue("type");
				final String resultKey = processValue(element.getAttributeValue("result"), element);
				final String assertStr = processValue(element.getAttributeValue("assert"), element);
				final String nameLbl = processValue(element.getAttributeValue("name"), element);
				
				final Command cmdL = new Command(nameLbl, "");
				cmdL.setLabel(nameLbl);
				// push to worker threads to collect data
				// TODO move to thread pool
				Thread t = new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							String internal;
							while(true){
								Thread.sleep(1500);
								dataCollector.executingCommand(cmdL);
								if(executing){
									continue;
								}
								executing = true;
								if(scriptType.equals("AS3")){
//									getTextContextValue
									
									final String script = "window.document['"+element.getAttributeValue("flexAppName")+"'].getTextContextValue('"+resultKey+"', true)";
									
									try {
										internal = testRunner.executeScript("JS",null, script);
									} catch (UnsupportedCommandException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
										internal = "~[404]~";
									}
									
									//System.out.println("Data found for event result - ["+resultKey+":"+internal+"]");
									if(!"~[404]~".equalsIgnoreCase(internal)){
										break;
									}
								}
								executing = false;
							}
						
							System.out.println("Checking '"+nameLbl+"'");
							System.out.println("Running assertion - "+assertStr);
							
							final String scriptToExecute = assertStr;
							
							final Properties newdata = new Properties(data);
							newdata.put(resultKey, internal);
							
							 final ScriptEngineContext context = new ScriptEngineContext(TestDataStore.getInstance());
							context.element(element);
							context.data(newdata);
							
							context.put(resultKey, internal);
							
							testRunner.updateContext(context);
							
							final Object scrRslt = ExternalScriptEngine.execute("groovy", scriptToExecute, context);
							
							if(scrRslt == null || !scrRslt.toString().equalsIgnoreCase("true")){
								TestFailedException th;
								System.out.println(nameLbl+" - Failed");
								th = new TestFailedException("Assertion '"+scriptToExecute+"' failed. ");
								dataCollector.testFailed(th);
								
								throw th;
							}
							
							
							
							dataCollector.executingCommandSuccess(cmdL);
							System.out.println("Assertion ["+assertStr+"], validated successfully");
							System.out.println(nameLbl+" - Success");
						
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							executing = false;
						}
					}
				});
				t.start();
				
				ArrayList<Thread> arr = THREADLOCALE.get();
				if(arr == null){
					arr = new ArrayList<Thread>();
				}
				arr.add(t);
				THREADLOCALE.set(arr);
			}
		}finally{
			
		}
		
		
		return result;
	}

	public static final ThreadLocal<ArrayList<Thread>> THREADLOCALE = new ThreadLocal<ArrayList<Thread>>();
	
	/**
	 * @param fileInputStream
	 */
	protected void loadData(InputStream fileInputStream, Element element) throws Exception{
		Properties loadedProps = new Properties();
		if(fileInputStream != null){
			loadedProps.load(fileInputStream);
		}
		final List list = element == null ? null : element.getContent();
		final int len = list == null ? 0 : list.size();
		Object content;
		for (int i = 0; i < len; i++) {
			content = list.get(i);
			if (content instanceof Element) {
				Element contentEle = (Element) content;
				if (contentEle.getName().equals("property")) {
					loadedProps.setProperty(contentEle
							.getAttributeValue("key"), contentEle
							.getAttributeValue("value"));
				}
			}
		}
		mergeProperties(loadedProps);
	}

	/*
	 * Run a preprocessor on the passed element which will make the element
	 * ready for testing which includes 1. replace default/context values 2.
	 * process macros and variables for each attributes
	 */
	private void preProcess(Element element) throws Exception {

		// update the element with default values (if not available with xml)
		if (element.getAttribute("isFlex") == null) {
			element.setAttribute("isFlex", isFlex + "");
		}
		if (element.getAttribute("isEnsureVisible") == null) {
			element.setAttribute("isEnsureVisible", isEnsureVisible + "");
		}
		if (element.getAttribute("flexAppName") == null && flexAppName != null) {
			element.setAttribute("flexAppName", flexAppName);
		}

		if (element.getName().equals("properties")) {
			return;
		}

		final List attrList = element.getAttributes();
		final int size = (attrList == null ? 0 : attrList.size());
		Attribute attribute;
		for (int i = 0; i < size; i++) {
			attribute = (Attribute) attrList.get(i);
			attribute.setValue(processValue(attribute.getValue(), element));
		}
	}

	

	
	/**
	 * @param rootElement
	 */
	private void runPreprocessors(Element rootElement) {

	}


	
	/**
	 * @param build
	 * @throws Exception
	 */
	private void runTest(Document document) throws Exception {

		dataCollector.startTest(document);

		try {
			ITestCommandRunner testRunner = SeleniumTestRunner.getInstance();
			testRunner.setDataCollector(dataCollector);
			testRunner.addTestListener(listener);
			testRunner.addTestListener(xmlTestReporter);
			IServerInfo sinfo = Application.getInstance().getModel().getServerInfo();
			if (testRunner != null) {
				Element rootElement = document.getRootElement();
				runPreprocessors(rootElement);
				String url = rootElement.getAttributeValue("url");
				url = processValue(url, rootElement);
				if (!url.equals(testRunner.getCurrentURL())) {
					String btype = rootElement.getAttributeValue("browser");
					if (btype == null || (btype = btype.trim()).length() == 0) {
						btype = data.getProperty("browser.type");
					}
					btype = (btype == null || (btype = btype.trim()).length() == 0) ? BrowserTypes.IE
							.command()
							: btype;
					testRunner.start(sinfo.getServerHost(), ""
							+ sinfo.getServerPort(), btype, url, null);
					dataCollector.setTestURL(url);
				}

				List contents = rootElement.getContent();
				int len = contents == null ? 0 : contents.size();
				Object content;
				for (int i = 0; i < len; i++) {
					content = contents.get(i);
					if (content instanceof Element) {
						execute(testRunner, (Element) content);
					}
				}
			}
		} catch (Exception th) {
			dataCollector.testFailed(th);
			throw th;
		} finally {
			dataCollector.endTest();
		}
	}

	/**
	 * RUN Test case from XML Input Stream (input stream should be created from
	 * an XML file)
	 * 
	 * @param input
	 * @throws IOException
	 * @throws JDOMException
	 */
	public void runTest(InputStream inputStream,  String extension) throws Exception {
		runTest(new SAXBuilder().build(inputStream));
	}

	public void runTest(Reader reader,  String extension) throws Exception {
		runTest(new SAXBuilder().build(reader));
	}

	private ICommand toCommand(ITestCommandRunner testRunner, Element element)
			throws Exception {
		final ICommand cmd;
		if (element.getName().equals("assertion")) {
			Assertion assertion = new Assertion(element
					.getAttributeValue("command"), element
					.getAttributeValue("locator"));
			
			assertion.setOrginalData(element);
			
			assertion.setExpectedValue(processValue(element
					.getAttributeValue("expectedValue"), element));
			assertion.setActualValue(processValue(element
					.getAttributeValue("actualValue"), element));

			final String bool = processValue(element.getAttributeValue("abortOnError"), element);
			assertion.setAbortOnError((bool == null ? true : Boolean.getBoolean(bool)));
			// go for nested actual, expectedValue

			final Element actValEle = element.getChild("actualValue");
			final Element expValEle = element.getChild("expectedValue");
			if (actValEle != null) {
				final List list = actValEle.getChildren();
				if (list != null && list.size() > 0) {
					final Object rslt = execute(testRunner, (Element) list
							.get(0));
					if (rslt != null) {
						assertion.setActualValue(rslt.toString());
					}
				}
			}

			if (expValEle != null) {
				final List list = expValEle.getChildren();
				if (list != null && list.size() > 0) {
					final Object rslt = execute(testRunner, (Element) list
							.get(0));
					if (rslt != null) {
						assertion.setExpectedValue(rslt.toString());
					}
				}
			}

			cmd = assertion;
		} else {
			cmd = new Command(element.getAttributeValue("command"), element.getAttributeValue("locator"));
			((Command)cmd).setOrginalData(element);
		}

		if (element.getAttribute("label") != null) {
			cmd.setLabel(processValue(element.getAttributeValue("label"), new Object[]{cmd, element}));
		}
		
		if (element.getAttribute("options") != null) {
			cmd.setOptions(processValue(element.getAttributeValue("options"), new Object[]{cmd, element}));
		}

		if (element.getAttribute("args") != null) {
			cmd.setArgs(processValue(element.getAttributeValue("args"), new Object[]{cmd, element}));
		}
		if (element.getAttribute("isFlex") != null) {
			cmd.setFlex(Boolean.parseBoolean(processValue(element
					.getAttributeValue("isFlex"), new Object[]{cmd, element})));
		}

		if (element.getAttribute("isEnsureVisible") != null) {
			cmd.setEnsureVisible(Boolean.parseBoolean(processValue(element
					.getAttributeValue("isEnsureVisible"), new Object[]{cmd, element})));
		}

		if (element.getAttribute("message") != null) {
			cmd.setMessage(processValue(element.getAttributeValue("message"), new Object[]{cmd, element}));
		}

		if (element.getAttribute("flexAppName") != null) {
			cmd.setFlexAppName(processValue(element
					.getAttributeValue("flexAppName"), element));
		}

		return cmd;
	}

	
}

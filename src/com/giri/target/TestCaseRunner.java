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
package com.giri.target;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Properties;

import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.giri.target.core.Application;
import com.giri.target.dsl.DSLTestCaseRunner;
import com.giri.target.ifc.ITestDataCollector;
import com.giri.target.ifc.ITestListener;
import com.giri.target.svr.BaseTestCaseRunner;
import com.giri.target.svr.ExternalScriptEngine;
import com.giri.target.svr.ScriptEngineContext;
import com.giri.target.svr.TestDataStore;
import com.giri.target.svr.XMLTestCaseRunner;
import com.giri.target.svr.XMLWriter;
import com.giri.target.ui.view.XMLTestListener;

public class TestCaseRunner {

	public TestCaseRunner() throws Exception{
		
	}

	public static void main(String[] args) throws Exception {
		TARGet.print();

		CommandsClassLoader.loadCommandClasses();
		
		System.out.println("Starting  TARGet - Web test automation server and runner");
		if(args.length == 0){
			System.out.println("Missing Testcase file");
			System.out.println("Usage : ");
			System.out.println("TestCaseRunner <test case xml file>");
			return;
		}
		
		final boolean localServer = isLocalServer();
		System.out.println("Localserver ? :"+localServer);
		
		if(localServer){
			System.out.println("Run test case using embeded Selenium Server");
			try {
//				Application.getInstance().getAutomationTestServer().stop();
//				Application.getInstance().getAutomationTestServer().start();
				runTest(args[0]);
			} catch (Exception e1) {
				Application.getInstance().getLogger().log(e1);
			}
		}else{
			System.out.println("Run test case using Remote Selenium Server");
			try {
				runTest(args[0]);
			} catch (Exception e1) {
				Application.getInstance().getLogger().log(e1);
			}
		}
	}

	private static boolean isLocalServer() {
		boolean localserver = true;

		String override = System.getProperty("target.rc.override");
		String tserver = System.getProperty("target.server");
		if(override != null && "true".equals((override = override.trim()))){
			localserver = false;
			return localserver;
		}
		if (tserver != null && (tserver = tserver.trim()).length() > 0) {
			try {
				final InetAddress targetServerAddr = InetAddress.getByName(tserver);
				final InetAddress local = InetAddress.getLocalHost();

				final InetAddress[] all = InetAddress.getAllByName(local.getHostName());
				boolean found = false;
				for (int i = 0; i < all.length; i++) {
					if(targetServerAddr.getHostAddress().equals(all[i].getHostAddress())){
						found = true;
						break;
					}
				}
				if(!found){
					localserver = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return localserver;
	}
	public static void runTest(String location) {
		final File tsFile = new File(location);
		if(!tsFile.exists()){
			System.out.println("Test case file '"+tsFile.getAbsolutePath()+"' doesnt exit");
			return;
		}
		runTest(tsFile);
	}
	private static Reader groovyPreScript = null;
	public static void runTest(File curFile) {
		try {
			// support groovy script
			if(curFile.getName().lastIndexOf(".groovy") != -1){

				if(groovyPreScript == null){
					final InputStream stream = TestCaseRunner.class.getClassLoader().getResourceAsStream("scripts/basescripts.groovy.txt");
					if(stream != null){
						groovyPreScript = new InputStreamReader(stream);
					}
					
				}
				
				System.out.println("Executing external groovy script -> "+curFile);
				final ScriptEngineContext context = new ScriptEngineContext(TestDataStore.getInstance());
				
				TestCaseRunner trunner = new TestCaseRunner();
				
				context.put("source",curFile);
				context.put("dsltestcaserunner",new DSLTestCaseRunner());
				context.put("xmltestcaserunner",new XMLTestCaseRunner());
				context.put("testcaserunner",trunner);
				
				Object result = ExternalScriptEngine.execute("groovy", new FileReader(curFile), context, groovyPreScript);
				System.out.println("Script executed - "+result);
				return;
			}
					
			//final XMLTestCaseRunner runner = new XMLTestCaseRunner();
			final BaseTestCaseRunner runner;
			final Boolean istsl = (curFile.getName().lastIndexOf("tsl.xls") != -1 || 
									curFile.getName().lastIndexOf(".tsl") != -1 || 
									"tsl".equalsIgnoreCase(System.getProperty("type"))) ? true : false;
			if(istsl){
				runner = new DSLTestCaseRunner();
			}else{
				runner = new XMLTestCaseRunner();
			}
			try {
				runner.setListener(testListner);
				runner.runTest(curFile);
				testListner.log("Test case execution completed successfully");
			} catch (Throwable e) {
				e.printStackTrace();
			}
			
			
			// wait for other threads
			ArrayList<Thread> threads = XMLTestCaseRunner.THREADLOCALE.get();
			for (int i = 0; i < threads.size(); i++){
				Thread t = threads.get(i);
				if(t.isAlive()){
					t.join();
				}
			}
			
			
			try {
				// Test completed, generate reports
				XMLTestListener xlistener = runner.getXmlTestReporter();
				org.jdom.Document doc = xlistener.getDocument();
				XMLOutputter outputter = new org.jdom.output.XMLOutputter(Format.getPrettyFormat());
				outputter.output(doc, System.out);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				ITestDataCollector dataCollector = runner.getDataCollector();
				File reportfile = new File(curFile.getParentFile(), curFile.getName()+"report.xml");
				System.out.println("Report generated @ "+reportfile.getAbsolutePath());
				FileWriter fw = new FileWriter(reportfile);
				XMLWriter.write(dataCollector, reportfile.getParentFile(), fw);
				fw.flush();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void datafile(String file) throws Exception{
		Properties props = new Properties();
		props.load(new FileReader(new File(file)));
		TestDataStore.getInstance().put(props);
	}
	public void data(String key, Object value){
		TestDataStore.getInstance().put(key, value);
	}

	/// ITest Listener
	protected static ITestListener testListner = new ITestListener(){

		@Override
		public void asserting(String message, TEST_STATUS status) {
			setText("\nAsserting :"+message+"["+status+"]");  //  @jve:decl-index=0:
		}

		@Override
		public void endTest(String message) {
			setText("\nTest Completed :"+message);
		}

		@Override
		public void logError(String message) {
			setText("\nERROR :"+message);
		}

		@Override
		public void logError(Throwable ex) {
			// TODO Auto-generated method stub

		}

		@Override
		public void startTest(String message) {
			setText("\nTest started :"+message);
		}

		@Override
		public void log(String message) {
			setText("\nTest started :"+message);
		}

		@Override
		public void testing(String message, TEST_STATUS status) {
			setText("\nTesting :"+message+"["+status+"]");
		}

		private void setText(String text){
			System.out.println(text);
		}

	};
}

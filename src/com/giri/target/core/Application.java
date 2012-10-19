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
package com.giri.target.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.giri.target.dsl.DSLTestCaseRunner;
import com.giri.target.ifc.ICommand;
import com.giri.target.ifc.ICommandPrinter;
import com.giri.target.ifc.ILogger;
import com.giri.target.ifc.IModel;
import com.giri.target.ifc.IServerInfo;
import com.giri.target.ifc.IStatusMonitor;
import com.giri.target.ifc.ITestCommandRunner;
import com.giri.target.svr.SeleniumTestRunner;
import com.giri.target.svr.ServerWatcher;
import com.giri.target.ui.view.Listener;

/**
 * @author Gireesh Kumar G
 * @Created Jul 2, 2009
 */
public final class Application {

	private static final Application SELF = new Application();

	private static final ILogger getDefaultLogger() {
		return new ILogger() {
			@Override
			public void log(String message) {
				System.err.println(message);
			}

			@Override
			public void log(Throwable error) {
				error.printStackTrace(System.err);
			}
		};
	}

	public static final Application getInstance() {
		return SELF;
	}

	private final IModel model = new Model();
	private ILogger logger;
	private ArrayList<ICommandPrinter> cprinters;
	private ITestCommandRunner testCommandRunner = SeleniumTestRunner
			.getInstance();
	private HashMap<String, List<Listener>> eventListenerMap;
	private IStatusMonitor statusMonitor;
	private DSLTestCaseRunner testCaseRunner;

	private Application() {

	}

	public void addPrinters(ICommandPrinter cprinter) {
		if (this.cprinters == null) {
			this.cprinters = new ArrayList<ICommandPrinter>();
		}
		this.cprinters.add(cprinter);
	}

	public ILogger getLogger() {
		if (logger == null) {
			setLogger(getDefaultLogger());
		}
		return logger;
	}

	public IModel getModel() {
		return model;
	}

	public void printCommand(ICommand command) {
		final int len = (cprinters == null ? 0 : cprinters.size());
		for (int i = 0; i < len; i++) {
			cprinters.get(i).print(command);
		}
	}

	public void setLogger(ILogger logger) {
		this.logger = logger;
	}


	public ITestCommandRunner getTestCommandRunner() {
		return testCommandRunner;
	}

	public void setTestCommandRunner(ITestCommandRunner testCommandRunner) {
		this.testCommandRunner = testCommandRunner;
	}

	/**
	 * @param string
	 * @throws Exception
	 */
	public void dispatchEvent(String event) throws Exception {
		final List<Listener> existingList = (eventListenerMap == null ? null
				: eventListenerMap.get(event));
		final int len = (existingList == null ? 0 : existingList.size());
		for (int i = 0; i < len; i++) {
			existingList.get(i).invoke(null);
		}
	}

	/**
	 * 
	 */
	public void addEventListener(String event, Listener listener) {
		if (eventListenerMap == null) {
			eventListenerMap = new HashMap<String, List<Listener>>();
		}
		List<Listener> existingList = eventListenerMap.get(event);
		if (existingList == null) {
			existingList = new ArrayList<Listener>();
			eventListenerMap.put(event, existingList);
		}
		existingList.add(listener);
	}

	public IStatusMonitor getStatusMonitor() {
		return statusMonitor;
	}

	public void setStatusMonitor(IStatusMonitor statusMonitor) {
		this.statusMonitor = statusMonitor;
	}

	private static final ServerWatcher serverWatcher = new ServerWatcher();

	/**
	 * 
	 */
	public void startServerWatcher() {
		serverWatcher.start();
	}

	/**
	 * @param string
	 * @param text
	 * @param text2
	 * @throws Exception 
	 */
	public void start(String browser, String url, String appName) throws Exception {
		IServerInfo serverinfo = Application.getInstance().getModel()
				.getServerInfo();
		testCommandRunner.start(serverinfo.getServerHost(), serverinfo
				.getServerPort()
				+ "", browser, url, appName);
	}

}

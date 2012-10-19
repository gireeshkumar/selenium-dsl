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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.jdom.Document;

import com.giri.target.ifc.ICommand;
import com.giri.target.ifc.ICommandExeStatus;
import com.giri.target.ifc.ICommandExeStatus.ExeStatus;
import com.giri.target.ifc.ITestDataCollector;

public class TestDataCollector implements ITestDataCollector {

	public static class CommandExeStatus implements ICommandExeStatus {

		public ICommand command;
		public ExeStatus status;
		public Throwable error;
		public boolean abort;
		public int order;

		public CommandExeStatus(ICommand cmd) {
			this.command = cmd;
		}

		public ICommand getCommand() {
			return command;
		}

		public ExeStatus getStatus() {
			return status;
		}

		public void setStatus(ExeStatus status2) {
			this.status = status2;
		}
		
		/* (non-Javadoc)
		 * @see com.giri.target.ifc.ICommandExeStatus#getError()
		 */
		@Override
		public Throwable getError() {
			return error;
		}
		
		/* (non-Javadoc)
		 * @see com.giri.target.ifc.ICommandExeStatus#abortOnError()
		 */
		@Override
		public boolean abortOnError() {
			return abort;
		}
		
		/**
		 * @return the order
		 */
		public int executionOrder() {
			return order;
		}

		/* (non-Javadoc)
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		@Override
		public int compareTo(ICommandExeStatus o) {
			final int thisVal = this.executionOrder();
			final int anotherVal = o.executionOrder();
			return (thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1));
		}
	}

	private ArrayList<TestDataCollector> nestedCollectors;
	public final ITestDataCollector parent;
	public Throwable testFailedException;
	public Document testDocument;
	public String testURL;
	public long startTime;
	public long testEndTime;
	public HashMap<String, CommandExeStatus> commandMap;
	public int executionOrder = 1;

	private String sourceFile;
	private String screenShotPath;

	public TestDataCollector() {
		parent = null;
	}

	private TestDataCollector(ITestDataCollector collector) {
		this.parent = collector;
	}

	@Override
	public void endTest() {
		this.testEndTime = System.currentTimeMillis();
	}

	@Override
	public void executingCommand(ICommand cmd) {
		CommandExeStatus ies = new CommandExeStatus(cmd);
		ies.order = executionOrder++;
		if (commandMap == null) {
			commandMap = new HashMap<String, CommandExeStatus>();
		}
		commandMap.put(cmd.getCommandID(), ies);
	}

	@Override
	public void executingCommandFaild(ICommand cmd) {
		setStatus(cmd, ICommandExeStatus.ExeStatus.FAILED);
	}

	/* (non-Javadoc)
	 * @see com.giri.target.ifc.ITestDataCollector#executingCommandFaild(com.giri.target.ifc.ICommand, java.lang.Throwable, boolean)
	 */
	@Override
	public void executingCommandFaild(ICommand cmd, Throwable error, boolean abort) {
		CommandExeStatus ies = setStatus(cmd, ICommandExeStatus.ExeStatus.FAILED);
		ies.abort = abort;
		ies.error = error;
	}
	
	@Override
	public void executingCommandSuccess(ICommand cmd) {
		setStatus(cmd, ICommandExeStatus.ExeStatus.SUCCESS);
	}

	@Override
	public void executingCustomScript(String type, String flexAppName,
			String script, String result, Exception exception) {
		// TODO Auto-generated method stub

	}

	public Collection<? extends ICommandExeStatus> getCommandExeStatus() {
		return (commandMap == null ? null : commandMap.values());
	}

	public ITestDataCollector getParent() {
		return parent;
	}

	@Override
	public String getTestCaseSourceFile() {
		return sourceFile;
	}

	public Document getTestDocument() {
		return testDocument;
	}

	public long getTestEndTime() {
		return testEndTime;
	}

	public Throwable getTestFailedException() {
		return testFailedException;
	}

	public long getTestStartTime() {
		return startTime;
	}

	public String getTestURL() {
		return testURL;
	}

	@Override
	public ITestDataCollector newNestedCollector() {
		if (nestedCollectors == null) {
			nestedCollectors = new ArrayList<TestDataCollector>();
		}
		final TestDataCollector dataCollector = new TestDataCollector(this);
		nestedCollectors.add(dataCollector);
		return dataCollector;
	}

	private CommandExeStatus setStatus(ICommand cmd, ExeStatus status) {
		CommandExeStatus ies = commandMap.get(cmd.getCommandID());
		if (ies != null) {
			ies.setStatus(status);
		}
		return ies;
	}

	@Override
	public void setTestCaseSourceFile(String filepath) {
		this.sourceFile = filepath;
	}

	@Override
	public void setTestURL(String url) {
		testURL = url;
	}

	@Override
	public void startTest(Document document) {
		testDocument = document;
		startTime = System.currentTimeMillis();
	}

	@Override
	public void testFailed(Throwable th) {
		testFailedException = th;
	}

	/* (non-Javadoc)
	 * @see com.giri.target.ifc.ITestDataCollector#setScreenshot(java.lang.String)
	 */
	public void setScreenshot(String path) {
		this.screenShotPath = path;
	}
	/* (non-Javadoc)
	 * @see com.giri.target.ifc.ITestDataCollector#getScreenshot()
	 */
	public String getScreenshot() {
		return screenShotPath;
	}
}

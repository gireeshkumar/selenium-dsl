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
package com.giri.target.ifc;

import java.util.Collection;

import org.jdom.Document;

public interface ITestDataCollector {

	void endTest();

	void executingCommand(ICommand cmd);

	void executingCommandFaild(ICommand cmd);
	
	void executingCommandFaild(ICommand cmd, Throwable error, boolean abort);

	void executingCommandSuccess(ICommand cmd);

	void executingCustomScript(String type, String flexAppName, String script,
			String result, Exception exception);

	public Collection<? extends ICommandExeStatus> getCommandExeStatus();

	public ITestDataCollector getParent();

	String getTestCaseSourceFile();

	public Document getTestDocument();

	public long getTestEndTime();

	public Throwable getTestFailedException();

	public long getTestStartTime();

	public String getTestURL();

	ITestDataCollector newNestedCollector();

	void setTestCaseSourceFile(String filepath);

	void setTestURL(String url);

	void startTest(Document document);

	void testFailed(Throwable th);

	/**
	 * @param path
	 */
	void setScreenshot(String path);
	String getScreenshot();
}

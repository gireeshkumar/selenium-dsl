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

import com.giri.target.core.TestFailedException;

/**
 * @author Gireesh Kumar G
 * @Created Jul 2, 2009
 */
public interface ITestCommandRunner {

	/**
	 * @param listener
	 */
	public void addTestListener(ITestListener listener);

	public String execute(ICommand command) throws TestFailedException;

	/**
	 * Execute script at client side (browser / SWF)
	 * 
	 * @param type
	 *            - script language, AS3 or JS
	 * @param flexAppName
	 *            = Flex/Flash application identifier, not required if 'type' is
	 *            JS
	 * @param value
	 *            - script text
	 * @throws Exception 
	 */
	public String executeScript(String type, String flexAppName, String value) throws Exception;

	public String getCurrentURL();

	public void setDataCollector(ITestDataCollector dataCollector);

	public void start(String server, String port, String browser, String url, String appname) throws Exception;

	public void updateContext(IScriptEngineContext context);
}

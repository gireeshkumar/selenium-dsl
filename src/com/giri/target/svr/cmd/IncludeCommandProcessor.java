/**
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
**/
package com.giri.target.svr.cmd;

import net.sourceforge.seleniumflexapi.cal.TARGetPageDriver;

import org.openqa.selenium.WebDriver;

import com.giri.target.ifc.IAssertionProcessor;
import com.giri.target.ifc.ICommand;
import com.giri.target.ifc.IHTMLCommandProcessor;
import com.giri.target.ifc.ISeleniumTestRunner;
import com.giri.target.svr.CommandRegistry;

/**
 * @author U20463
 *
 */
public class IncludeCommandProcessor implements IAssertionProcessor, IHTMLCommandProcessor {

	
	public static final String INCLUDE_CMD = "include";
	
	static{
		new IncludeCommandProcessor();
	}
	
	private IncludeCommandProcessor() {
		CommandRegistry.register(this);
	}


	/* (non-Javadoc)
	 * @see com.giri.target.ifc.ICommandProcessor#supportedCommands()
	 */
	@Override
	public String[] supportedCommands() {
		return (new String[]{INCLUDE_CMD});
	}
	
	/* (non-Javadoc)
	 * @see com.giri.target.ifc.ICommandProcessor#process(net.sourceforge.seleniumflexapi.cal.TARGetPageDriver, com.giri.target.ifc.ICommand)
	 */
	@Override
	public String process(ISeleniumTestRunner testRunner, final WebDriver webdriver, final TARGetPageDriver driver, final ICommand command) throws Exception {


		return "true";
	}


	
	
}

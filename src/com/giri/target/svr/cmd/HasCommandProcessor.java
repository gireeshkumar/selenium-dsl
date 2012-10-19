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

import java.util.List;

import junit.framework.Assert;
import net.sourceforge.seleniumflexapi.cal.TARGetPageDriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.giri.target.core.Application;
import com.giri.target.ifc.IAssertionProcessor;
import com.giri.target.ifc.ICommand;
import com.giri.target.ifc.IHTMLCommandProcessor;
import com.giri.target.ifc.ISeleniumTestRunner;
import com.giri.target.svr.CommandRegistry;

/**
 * @author U20463
 *
 */
public class HasCommandProcessor implements IAssertionProcessor, IHTMLCommandProcessor {

	public static final String COPEN_CMD = "has";
	
	static{
		new HasCommandProcessor();
	}
	
	private HasCommandProcessor() {
		CommandRegistry.register(this);
	}

	/* (non-Javadoc)
	 * @see com.giri.target.ifc.ICommandProcessor#supportedCommands()
	 */
	@Override
	public String[] supportedCommands() {
		return (new String[]{COPEN_CMD});
	}
	
	/* (non-Javadoc)
	 * @see com.giri.target.ifc.ICommandProcessor#process(net.sourceforge.seleniumflexapi.cal.TARGetPageDriver, com.giri.target.ifc.ICommand)
	 */
	@Override
	public String process(ISeleniumTestRunner testRunner, final WebDriver webdriver, final TARGetPageDriver driver, final ICommand command) throws Exception {
//		final IAssertion assertion = (IAssertion) command;
		
		final String args = command.getArgs();
		boolean assertSuccess = false;
		try{
			//TODO generic finder script
			List<WebElement> elements = TARGetPageDriver.findElements(webdriver, command.getLocator());
			boolean rslt = (elements != null && elements.size() > 0);
			Assert.assertEquals(true, rslt);
			assertSuccess = true;
		}catch(AssertionError ae){
			assertSuccess = false;
			Application.getInstance().getLogger().log(ae);
			throw ae;
//			if(assertion.abortOnError()){// should we abort (thorw exception)
//				throw ae;
//			}else{
//				Application.getInstance().getLogger().log("Assertion failed, 'abortOnError' is set to false. Continue");
//				// log and continue
//				testRunner.getDataCollector().executingCommandFaild(assertion, ae, false);
//			}
		}
		
		return assertSuccess+"";
	}

}

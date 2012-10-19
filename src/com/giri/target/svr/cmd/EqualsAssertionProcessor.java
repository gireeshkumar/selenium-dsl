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

import junit.framework.Assert;
import net.sourceforge.seleniumflexapi.cal.TARGetPageDriver;

import org.openqa.selenium.WebDriver;

import com.giri.target.core.Application;
import com.giri.target.ifc.IAssertion;
import com.giri.target.ifc.IAssertionProcessor;
import com.giri.target.ifc.ICommand;
import com.giri.target.ifc.IFlexCommandProcessor;
import com.giri.target.ifc.IHTMLCommandProcessor;
import com.giri.target.ifc.ISeleniumTestRunner;
import com.giri.target.svr.CommandRegistry;
import com.giri.target.svr.Utils;

/**
 * @author U20463
 *
 */
public class EqualsAssertionProcessor implements IAssertionProcessor , IFlexCommandProcessor, IHTMLCommandProcessor{

	public static final String EQUALS_CMD = "equals";
	
	static{
		new EqualsAssertionProcessor();
	}
	
	private EqualsAssertionProcessor(){
		CommandRegistry.register(this);
	}
	
	/* (non-Javadoc)
	 * @see com.giri.target.ifc.ICommandProcessor#process(net.sourceforge.seleniumflexapi.cal.TARGetPageDriver, com.giri.target.ifc.ICommand)
	 */
	@Override
	public String process(ISeleniumTestRunner testRunner, final WebDriver webdriver, final TARGetPageDriver driver, final ICommand command) {
		final IAssertion assertion = (IAssertion) command;
		
		Application.getInstance().getLogger()
				.log( "Running assert Equals : ("+ assertion.getExpectedValue() + " == "+ assertion.getActualValue() + ")");
		
		String message = assertion.getMessage();
		if(assertion.getMessage() != null){
			try {
				message = Utils.processValue(message, null, assertion);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		String expValue = assertion.getExpectedValue();
		String actValue = assertion.getActualValue();
		
		final String options = assertion.getOptions();
		
		if(options != null){
			// equals ignore case		
			if(options.indexOf("ignorecase") != -1){
				expValue = expValue.toLowerCase();
				actValue = actValue.toLowerCase();
			}
		}

		boolean assertSuccess = true;
		
		try{
			Assert.assertEquals(message, expValue , actValue);
			assertSuccess = true;
		}catch(AssertionError ae){
			assertSuccess = false;
			Application.getInstance().getLogger().log(ae);
			if(assertion.abortOnError()){// should we abort (thorw exception)
				throw ae;
			}else{
				Application.getInstance().getLogger().log("Assertion failed, 'abortOnError' is set to false. Continue");
				// log and continue
				testRunner.getDataCollector().executingCommandFaild(assertion, ae, false);
			}
		}
		
		return assertSuccess+"";
	}

	/* (non-Javadoc)
	 * @see com.giri.target.ifc.ICommandProcessor#supportedCommands()
	 */
	@Override
	public String[] supportedCommands() {
		return new String[]{EQUALS_CMD};
	}

}

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

import java.io.File;
import java.util.UUID;

import net.sourceforge.seleniumflexapi.cal.TARGetPageDriver;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.giri.target.ifc.ICommand;
import com.giri.target.ifc.ICommandProcessor;
import com.giri.target.ifc.IHTMLCommandProcessor;
import com.giri.target.ifc.ISeleniumTestRunner;
import com.giri.target.svr.CommandRegistry;

/**
 * @author U20463
 *
 */
public class ScreenshotCommandProcessor implements ICommandProcessor, IHTMLCommandProcessor {

	public static final String CLICK_CMD = "screenshot";
	
	static{
		new ScreenshotCommandProcessor();
	}
	
	private ScreenshotCommandProcessor() {
		CommandRegistry.register(this);
	}

	/* (non-Javadoc)
	 * @see com.giri.target.ifc.ICommandProcessor#supportedCommands()
	 */
	@Override
	public String[] supportedCommands() {
		return (new String[]{CLICK_CMD});
	}
	
	/* (non-Javadoc)
	 * @see com.giri.target.ifc.ICommandProcessor#process(net.sourceforge.seleniumflexapi.cal.TARGetPageDriver, com.giri.target.ifc.ICommand)
	 */
	@Override
	public String process(ISeleniumTestRunner testRunner, final WebDriver webdriver, final TARGetPageDriver driver, final ICommand command) {
		String output = captureScreen(webdriver, driver);
		System.out.println("Screenshot : "+output);
		return null;
	}
	
	private String captureScreen(WebDriver webdriver, TARGetPageDriver driver2) {

		String outputpath = "Screenshot not catpured";
		
		if(webdriver instanceof TakesScreenshot){
			File out = ((TakesScreenshot)webdriver).getScreenshotAs(OutputType.FILE);
			
			final String fileid = UUID.randomUUID().toString();
			final File file = new File("./",fileid+".png");
			
			out.renameTo(file);
			
			outputpath = file.getAbsolutePath();
			
		}else{
			outputpath = "Screenshot not supported:["+webdriver.getClass()+"]";
		}
		
		return outputpath;
	}

}

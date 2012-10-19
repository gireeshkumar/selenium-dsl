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
import org.openqa.selenium.WebElement;

import com.giri.target.ifc.ICommand;
import com.giri.target.ifc.IHTMLCommandProcessor;
import com.giri.target.ifc.ISeleniumTestRunner;
import com.giri.target.svr.CommandRegistry;
import com.thoughtworks.selenium.CommandProcessor;

/**
 * @author U20463
 *
 */
public class GenericHTMLCommandProcessor implements IHTMLCommandProcessor{

	public static final String CMD = "genericHTMLCommand";
	
	static{
		new GenericHTMLCommandProcessor();
	}
	
	private GenericHTMLCommandProcessor(){
		CommandRegistry.register(this);
	}

	/* (non-Javadoc)
	 * @see com.giri.target.ifc.ICommandProcessor#supportedCommands()
	 */
	@Override
	public String[] supportedCommands() {
		return new String[]{"genericHTMLCommand"};
	}

	/**
	 * @param webdriver
	 * @param cmd2
	 * @return 
	 * @throws Exception 
	 */
	public String process(ISeleniumTestRunner testRunner, final WebDriver webdriver,  final TARGetPageDriver driver, ICommand cmd) throws Exception {
		//TODO migrate to webdriver
		
		//http://seleniumhq.org/docs/04_selenese_commands.html
		//TODO fix if conditions
		final String result;
		if("click".equals(cmd.getCommand())){
			// TODO, duplicate code
			// for some reason  if I use  "TARGetPageDriver.findElement(s)" the client event is not really happening 
			// hence move the code directly here to test

			final WebElement element = (WebElement) TARGetPageDriver.findElement(driver, cmd.getLocator());
			
			//TODO workaround, some time client is not working, try selecting the parent element?
/*			element.findElement(By.xpath("..")).click();
			
			element.click();*/
			element.sendKeys("\n");
			
//			final WebElement element;
//			String locator = cmd.getLocator();
//			if((locator != null && (locator = locator.trim()).length() > 0)){
//				final By finder;
//				if(locator.startsWith("#")){
//					element = webdriver.findElement(By.id(locator));
//				}
//				else if(locator.startsWith(".")){
//					element = webdriver.findElement(By.className(locator));
//				}
//				else if(locator.startsWith("css=")){
//					final String loc = locator.substring("css=".length(), locator.length());
//					element = webdriver.findElement(By.cssSelector(loc));
//				}
//				else if(locator.startsWith("link=")){
//					final String loc = locator.substring("link=".length(), locator.length());
//					element = webdriver.findElement(By.linkText(loc));
//				}
//				else if(locator.startsWith("name=")){
//					final String loc = locator.substring("name=".length(), locator.length());
//					element = webdriver.findElement(By.name(loc));
//				}else{
//					if(locator.startsWith("jq=")){
//						final String loc = locator.substring("js=".length(), locator.length());
//						final String js = "return TSLFINDERS.toDOMElement($(\""+loc+"\"))";
//						final String jss = "$(\""+loc+"\")";
//						final Object rslt = ((JavascriptExecutor)webdriver).executeScript(jss);
//						final List<WebElement> elements = (List<WebElement>) ((JavascriptExecutor)webdriver).executeScript("$(\""+loc+"\")");
//						element = (elements != null && elements.size() > 0 ? elements.get(0) : null);
//					}
//					else if(locator.startsWith("js=")){
//						final String loc = locator.substring("js=".length(), locator.length());
//						final String js = "return "+loc;
//						final List<WebElement> elements = (List<WebElement>) ((JavascriptExecutor)webdriver).executeScript(js);
//						element = (elements != null && elements.size() > 0 ? elements.get(0) : null);
//					}else{
//						throw new RuntimeException("Unknown locator pattern: '"+cmd.getLocator()+"'");
//					}
//				}
//			}else{
//				element = null;
//			}
//			//			final WebElement element = (WebElement) TARGetPageDriver.findElement(webdriver, cmd.getLocator());
//			if(element != null){
//				element.click();
//			}


			result = "true";
			//			final WebElement element = TARGetPageDriver.findElement(webdriver, cmd.getLocator());
			//			if(element != null){
			//				element.click();
			//				result = "true";
			//			}else{
			//				throw new RuntimeException("Element not found, Locator :"+cmd.getLocator());
			//			}
		}else if("type".equals(cmd.getCommand())){
			final WebElement element = TARGetPageDriver.findElement(driver, cmd.getLocator());
			if(element != null){
				element.sendKeys(cmd.getArgs());
				result = "true";
			}else{
				throw new RuntimeException("Element not found, Locator :"+cmd.getLocator());
			}
		}else{
			CommandProcessor commandProcessor = driver.getWebDriverBackedSelenium().getCommandProcessor();
			
			final String[] args;
			if (cmd.getArgs() != null) {
				args = new String[] { cmd.getLocator(), cmd.getArgs() };
			} else {
				args = new String[] { cmd.getLocator() };
			}

			result = commandProcessor.doCommand(cmd.getCommand(), args);
		}
		
		
		return result;
	}

}

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

import java.lang.reflect.Method;

import net.sourceforge.seleniumflexapi.cal.TARGetPageDriver;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.giri.target.ifc.IAssertionProcessor;
import com.giri.target.ifc.ICommand;
import com.giri.target.ifc.IHTMLCommandProcessor;
import com.giri.target.ifc.ISeleniumTestRunner;
import com.giri.target.svr.CommandRegistry;

/**
 * @author U20463
 *
 */
public class KeyCommandProcessor implements IAssertionProcessor, IHTMLCommandProcessor {

	
	public static final String RUN_CMD = "key";
	
	static{
		new KeyCommandProcessor();
	}
	
	private KeyCommandProcessor() {
		CommandRegistry.register(this);
	}


	/* (non-Javadoc)
	 * @see com.giri.target.ifc.ICommandProcessor#supportedCommands()
	 */
	@Override
	public String[] supportedCommands() {
		return (new String[]{RUN_CMD});
	}
	
	/* (non-Javadoc)
	 * @see com.giri.target.ifc.ICommandProcessor#process(net.sourceforge.seleniumflexapi.cal.TARGetPageDriver, com.giri.target.ifc.ICommand)
	 */
	@Override
	public String process(ISeleniumTestRunner testRunner, final WebDriver webdriver, final TARGetPageDriver driver, final ICommand command) throws Exception {
		String keystr = command.getArgs();

		CharSequence keysToSend = getKey(keystr);
		
		String locator = command.getLocator();
		final WebElement element;
		if(locator != null && locator.length() > 0){
			element = (WebElement) TARGetPageDriver.findElement(driver, locator);
		}else{
			element = (WebElement) driver.executeScript("return window.document;");
		}
		
		if(element != null){
			element.sendKeys(keysToSend);
		}
		
		return "true";
	}
	
	
	public CharSequence getKey(String keystr) throws Exception{
		CharSequence keySeq = keystr;
		try {
			keySeq = Keys.valueOf(keystr);
		} catch (IllegalArgumentException e) {
			// looking for some short keys, 
			// CTRL - CONTROL
			// DEL - DELETE
			//ESCAPE
			if(keystr.indexOf('+') != -1 && keystr.length() > 1){
				String[] keyStrs = keystr.split("\\+");
				StringBuilder sb = new StringBuilder();
				if(keyStrs != null && keyStrs.length > 0){
					// pass for number check
					
					CharSequence[] kf = new CharSequence[keyStrs.length];
					int cnt = 0;
					for (String string : keyStrs) {
						CharSequence keys = getKey(string);
						keys = (keys == null ? string : keys);
						kf[cnt] = keys;
						
						if(cnt != 0){
							sb.append(" + ");
						}
//						if(keys == null){
//							throw new Exception("No such key '"+string+"'");
//						}
						sb.append(keys);
						
						cnt++;
					}
					keySeq = Keys.chord(kf);
				}
				
				System.out.println("Multikeys ["+sb.toString()+"]: "+keySeq);
			}
			else if("CTRL".equalsIgnoreCase(keystr)){
				keySeq = Keys.CONTROL;
			}else if("DEL".equalsIgnoreCase(keystr)){
				keySeq = Keys.DELETE;
			}else if("EQ".equalsIgnoreCase(keystr)){
				keySeq = Keys.EQUALS;
			}else if("ESC".equalsIgnoreCase(keystr)){
				keySeq = Keys.ESCAPE;
			}else{
				// check if its number key
				try{
					int num = Integer.parseInt(keystr);
					if(num >= 0 && num <= 9){
						keySeq = Keys.valueOf("NUMPAD"+num);
					}
				}catch(Exception e1){
				}
			}
		}
		return keySeq;
	}
	
	private static String toString(CharSequence keys) {
		try {
			Method namem = keys.getClass().getMethod("name", null);
			return (namem == null ? keys.toString() : namem.invoke(keys, null).toString());
		} catch (Exception e) {
		}
		return keys.toString();
	}
	
	public static void main(String[] args) throws Exception {
		
//		System.out.println(Keys.chord(Keys.SHIFT, Keys.CONTROL));
		
		String keystr = args[0].trim();
		
		CharSequence keys = new KeyCommandProcessor().getKey(keystr);
		if(keys == null){
			System.err.println("No such Key '"+args[0]+"'");
		}else{
			System.out.println(toString(keys));
		}
		
		
	}
}

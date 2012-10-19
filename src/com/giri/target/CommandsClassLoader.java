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
package com.giri.target;

import java.io.InputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;


/**
 * @author U20463
 *
 */
public class CommandsClassLoader {

	public static void loadCommandClasses() throws Exception{
		//commandslist.xml
		final InputStream xmlres = CommandsClassLoader.class.getClassLoader().getResourceAsStream("commandslist.xml");
		final Document document = new SAXBuilder().build(xmlres);
		
		Element rootElement = document.getRootElement();
		List<Element> children = rootElement.getChildren("class");
		for(Element ele: children){
			Class.forName(ele.getAttributeValue("fqn"));
		}
		
//		Class cls = com.giri.target.svr.cmd.ClickAlertCommandProcessor.class;
//		
//		cls = com.giri.target.svr.cmd.ClickCommandProcessor.class;
//		cls = com.giri.target.svr.cmd.EqualsAssertionProcessor.class;
//		cls = com.giri.target.svr.cmd.GenericHTMLCommandProcessor.class;
//		cls = com.giri.target.svr.cmd.GetFlexVisibleAssertionProcessor.class;
//		cls = com.giri.target.svr.cmd.IsAlertVisibleAssertionProcessor.class;
//		cls = com.giri.target.svr.cmd.TypeCommandProcessor.class;
//		cls = com.giri.target.svr.cmd.GetCommandProcessor.class;
//		cls = com.giri.target.svr.cmd.OpenCommandProcessor.class;
//		cls = com.giri.target.svr.cmd.ScriptCommandProcessor.class;
	}
	
	public static void main(String[] args) throws Exception {
		loadCommandClasses();
	}
}

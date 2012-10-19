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
package com.giri.target.core.cmdparse;

import java.io.File;
import java.io.StringWriter;

import com.giri.target.core.CommandPattern;
import com.giri.target.core.CommandPatterns;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * @author Gireesh Kumar G
 * @Created Jul 28, 2009
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		XStream stream = new XStream(new XppDriver());
		stream.alias("command", CommandPattern.class);
		Object xml = stream.fromXML(Test.class.getClassLoader().getResourceAsStream("commands.xml"));
		System.out.println(xml);
		
		File file = new File("testout.xml");
		
		StringWriter sw = new StringWriter();
		stream.toXML(CommandPatterns.AVAILABLE_COMMANDS[0], sw);
		sw.flush();
		System.out.println(sw.getBuffer());
		
		System.out.println(file.getAbsolutePath());
	}

}

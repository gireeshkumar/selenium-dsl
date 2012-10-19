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
package com.giri.target.core;

import java.util.List;

import com.giri.target.core.cmdparse.Test;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * @author Gireesh Kumar G
 * @Created Jul 6, 2009
 */
public interface CommandPatterns {
	// command patterns to display in the UI
	// public static final CommandPattern[] AVAILABLE_COMMANDS = new
	// CommandPattern[]{
	// new
	// CommandPattern("Flex Click","doFlexClick","<object id>","<label - optional>",
	// Help.to("Click on Flex components, Provide Flex component 'id' and label ('for tabs')")).toFlex(),
	// new
	// CommandPattern("Flex Component Visible","getFlexVisible","<object id>",
	// Help.to("Check if the Flex component is visible, Provide flex component id")).toFlex(),
	// new
	// CommandPattern("Flex type","doFlexType","<object id>","<text to enter>",
	// Help.to("Type/Insert text into flex component, Provide flex text input component id and text to enter")).toFlex()
	//		
	// };

	public static final CommandPattern[] AVAILABLE_COMMANDS = Loader
			.loadCommands();
	public static final AssertionPattern[] AVAILABLE_ASSERTIONS = Loader
			.loadAssertions();

	final class Loader {

		@SuppressWarnings("unchecked")
		public static CommandPattern[] loadCommands() {
			XStream stream = new XStream(new XppDriver());
			stream.alias("command", CommandPattern.class);
			List<CommandPattern> cmdPatterns = (List<CommandPattern>) stream
					.fromXML(Test.class.getClassLoader().getResourceAsStream(
							"commands.xml"));
			if (cmdPatterns != null && cmdPatterns.size() > 0) {
				CommandPattern[] patterns = new CommandPattern[cmdPatterns
						.size()];
				cmdPatterns.toArray(patterns);
				return patterns;
			} else {
				return null;
			}
		}

		@SuppressWarnings("unchecked")
		public static AssertionPattern[] loadAssertions() {
			XStream stream = new XStream(new XppDriver());
			stream.alias("assertion", AssertionPattern.class);
			List<AssertionPattern> cmdPatterns = (List<AssertionPattern>) stream
					.fromXML(Test.class.getClassLoader().getResourceAsStream(
							"asserts.xml"));
			if (cmdPatterns != null && cmdPatterns.size() > 0) {
				AssertionPattern[] patterns = new AssertionPattern[cmdPatterns
						.size()];
				cmdPatterns.toArray(patterns);
				return patterns;
			} else {
				return null;
			}
		}
	}
}

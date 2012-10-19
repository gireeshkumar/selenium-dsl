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
package com.giri.target;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TARGet {

	public static final String BUILD = "Build 0.16 [15122011]"; //ddmmyyyy
	
	private final static StringBuffer NAME_TEXT = readNameText();
	
	private static StringBuffer readNameText(){
		StringBuffer textout = null;
		try {
			InputStream inStream = TARGet.class.getResourceAsStream("target.name");
			textout = new StringBuffer();
			final BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
			String line;
			while((line = reader.readLine()) != null){
				textout.append(line).append("\n");
			}
			textout.append("\n").append(BUILD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return textout;
	}
	
	public static void print() {
		System.out.println(NAME_TEXT);
	}

}

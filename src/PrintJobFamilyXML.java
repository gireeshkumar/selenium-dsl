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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * @author Gireesh Kumar G
 * @Created Sep 9, 2009
 */
public class PrintJobFamilyXML {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		BufferedReader breader = new BufferedReader(
				new FileReader(
						new File(
								"C:\\Documents and Settings\\U20463\\My Documents\\Flex Builder 3\\careerPath\\src\\data\\allJobFamiliesFlat.txt")));
		String line;
		int idx = 0;
		while ((line = breader.readLine()) != null) {
			System.out.println("<jobfamily id=\"jf" + idx + "\">");
			System.out
					.println("\t<name><![CDATA[" + line.trim() + "]]></name>");
			System.out.println("</jobfamily>");
			idx++;
		}
	}

}

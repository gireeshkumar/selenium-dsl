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
package com.giri.target.svr;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

/**
 * @author U20463
 *
 */
public class Utils {
	/**
	 * @param macroid
	 * @return
	 * @throws Exception
	 */
	private static final String TODAY = "TODAY";
	/**
	 * @param attributeValue
	 * @return
	 * @throws Exception
	 */
	public static String processValue(
										String attributeValue,
										Properties properties, 
										Object context) 
										throws Exception {
		if (attributeValue == null || attributeValue.length() == 0) {
			return attributeValue;
		}
		// ${property}
		// $_MACRO_ID_
		// if(attributeValue.startsWith("${") && attributeValue.endsWith("}")){
		// String prop = attributeValue.substring(2, attributeValue.length()-1);
		// String value = (data == null ? null : data.getProperty(prop));
		// attributeValue = value == null ? attributeValue : value;
		// }

		int startloc = 0;
		int sindex = -1, eindex = 0;
		String prop, value;

		String orginalProp = null;
		do {
			sindex = attributeValue.indexOf("${", eindex);
			if (sindex == -1) {
				break;
			} else {
				eindex = attributeValue.indexOf('}', sindex);
				if (eindex != -1) {
					orginalProp = attributeValue.substring(sindex, eindex+1);
					prop = attributeValue.substring(sindex + 2, eindex);
					value = (properties == null ? null : properties.getProperty(prop));
					if(value == null){
						// check in data store
						value = (String) TestDataStore.getInstance().get(prop);
					}
					if(value != null){
						value = processValue(value, properties, context);
					}else{
						// not able to process ? leave it as it is
						value = orginalProp;
					}
					
					attributeValue = attributeValue.substring(0, sindex)
							+ value
							+ attributeValue.substring(eindex + 1,
									attributeValue.length());
					// System.out.println(attributeValue);
				}
				startloc = eindex;
			}
		} while (true);

		// process macro
		String macroid = null;
		startloc = 0;
		sindex = -1;
		eindex = -1;
		value = null;
		do {
			sindex = attributeValue.indexOf("$~");
			if (sindex == -1) {
				break;
			} else {
				eindex = attributeValue.indexOf('~', (sindex + 2));
				if (eindex != -1) {
					macroid = attributeValue.substring(sindex + 2, eindex);
					value = resolveMacro(macroid);// (properties == null ? null
					// :
					// properties.getProperty(prop));
					attributeValue = attributeValue.substring(0, sindex)
							+ value
							+ attributeValue.substring(eindex + 1,
									attributeValue.length());
				}
				startloc = eindex;
			}
		} while (true);

		// if(attributeValue.startsWith("$~") && attributeValue.endsWith("~")){
		// String macroid = attributeValue.substring(2,
		// attributeValue.length()-1);
		// attributeValue = resolveMacro(macroid);
		// }
		return attributeValue;
	}
	

	public static String resolveMacro(String macroid) throws Exception {
		final String value;
		if (macroid.startsWith(TODAY)) {
			final Calendar calendar = Calendar.getInstance();
			final SimpleDateFormat dateformat = new SimpleDateFormat();

			// TODAY_{<pattern>}
			String pattern = macroid.substring(macroid.indexOf(TODAY)
					+ TODAY.length() + 1, macroid.length());
			if (pattern.startsWith("{")) {
				pattern = pattern.substring(1, pattern.length());
			}
			if (pattern.endsWith("}")) {
				pattern = pattern.substring(0, pattern.length() - 1);
			}
			dateformat.applyPattern(pattern);
			value = dateformat.format(calendar.getTime());
			;
		} else {
			throw new Exception("Unknown macro '" + macroid + "'");
		}

		return value;
	}
}

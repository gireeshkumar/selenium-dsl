package com.giri.target.common;

import java.util.ArrayList;

import com.giri.target.dsl.ifc.IKeyword;
import com.giri.target.dsl.ifc.ILocatorKeyword;
import com.giri.target.dsl.ifc.IParameter;
import com.giri.target.dsl.ifc.IStatement;
import com.giri.target.dsl.keywords.html.IHTMLTags;

public class Utils {
	
	public static final Utils INSTANCE = new Utils();
	
//	public static String findLocatorString(IStatement stmt) {
//		return findLocatorString(stmt.getParameters(), stmt, false);
//	}
	public static String findLocatorString(IParameter[] parameters, IStatement stmt, boolean isFlex) {

		String locatorString = null;

		ArrayList<IKeyword> oKeywords = stmt.getOtherKeywords();
		// click command needs one location, no parameters needed
		// How to find locator -> keyword, parameters, tokens
		IHTMLTags htmlTag = null;
		if(oKeywords != null){
			for(IKeyword kword:oKeywords){
				if(kword instanceof ILocatorKeyword){
					ILocatorKeyword iloc = (ILocatorKeyword) kword;
					locatorString = iloc.locator();
					break;
				}else{
					if(htmlTag == null && (kword instanceof IHTMLTags)){
						// check what HTML tag it could be
						htmlTag = (IHTMLTags) kword;
					}
				}
			}
		}

		if(locatorString == null){
			// search for parameters

			if(parameters != null){
				// create JQuery locator , find any component contains text
				String pvalue = null;
				for(IParameter param:parameters){
					// take only the first parameter
					pvalue = param.value();
					if(pvalue == null){
						continue;
					}

					if(pvalue.startsWith("#")){
						locatorString = "jq="+pvalue;
					}else if(pvalue.startsWith("jq=")){
						locatorString = pvalue;
					}
					else if(isFlex){
						locatorString = pvalue;
					}else{
						locatorString = FinderString.anyTagContains(pvalue);
						if(htmlTag != null){// do a contains search by this tag
							if(htmlTag.getTag() == IHTMLTags.TAG.BUTTON){
								//jQuery("button:contains('Search')")
								locatorString = FinderString.anyButtonContains(pvalue);
								//								cmd.setLocator("jq=button[value='"+param.value()+"']");
							}else if(htmlTag.getTag() == IHTMLTags.TAG.LINK){
								locatorString = "link="+pvalue;//FinderString.findLink(pvalue);
							}
						}else{

						}
					}
					break;
				}
			}
		}

		// now needs to try other parts of string
		if(locatorString == null){
			IParameter[] otherWrds = stmt.getOtherParameters();
			if(otherWrds != null && otherWrds.length > 0){
				// let me get the first one
				// assume its a id lookup
				if(htmlTag != null){
					if(htmlTag.getTag() == IHTMLTags.TAG.INPUT){
						locatorString = "jq=input[id="+otherWrds[0].value()+"]";
					}else if(htmlTag.getTag() == IHTMLTags.TAG.BUTTON){
						locatorString = "jq=button[id="+otherWrds[0].value()+"]";
					}
				}
				else if(!isFlex){
					locatorString = "jq=#"+otherWrds[0].value();
				}
			}
		}
		return locatorString;
	}

}

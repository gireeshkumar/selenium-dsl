package com.giri.target.dsl.keywords;

import com.giri.target.common.Utils;
import com.giri.target.dsl.ifc.IActionKeyword;
import com.giri.target.dsl.ifc.IParameter;
import com.giri.target.dsl.ifc.IStatement;
import com.giri.target.ifc.ICommand;

/**
 * @author U20463
 *
 */
public abstract class BaseActionKeyword implements IActionKeyword{


	
	
	/**
	 * @param cmd
	 * @param parameters
	 * @param stmt
	 * @return
	 */
	public static boolean setLocator(ICommand cmd, IParameter[] parameters, IStatement stmt) {
		boolean locatorFound = false;
		final String locString = Utils.findLocatorString(parameters, stmt, cmd.isFlex());
		if(locString != null){
			cmd.setLocator(locString);
			locatorFound = true;
		}
		return locatorFound;
	}
	
}

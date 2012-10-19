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
package com.giri.target.ifc;

import java.util.List;

public interface ICommand {

	public abstract String getArgs();

	public abstract String getCommand();

	public String getCommandID();

	public abstract String getFlexAppName();

	public abstract String getLabel();

	public abstract String getLocator();

	public abstract String getMessage();

	public abstract boolean isEnsureVisible();

	public abstract boolean isFlex();

	public abstract void setArgs(String args);

	public abstract void setCommand(String command);

	public abstract void setEnsureVisible(boolean ensureVisible);

	public abstract void setFlex(boolean isFlex);

	public abstract void setFlexAppName(String flexAppName);

	public abstract void setLabel(String label);

	public abstract void setLocator(String objectId);

	public abstract void setMessage(String message);

	/**
	 * @return the options
	 */
	public String getOptions();

	/**
	 * @param options the options to set
	 */
	public void setOptions(String options);

	public void setAttribute(String key, Object value);
	public Object getAttribute(String key);
	
	public List<ICommand> getPreProcessingCommands();
	
	public List<ICommand> getPostProcessingCommands();
	
	public void addPreCommand(ICommand cmd);
	
	public void addPostCommnad(ICommand cmd);
	
	public ICommand clone();
}
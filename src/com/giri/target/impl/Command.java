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
package com.giri.target.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.giri.target.ifc.ICommand;

/**
 * @author Gireesh Kumar G
 * @Created Aug 6, 2009
 */
/**
 * @author U20463
 */
public class Command implements ICommand, Cloneable {

	public final String commandid = UUID.randomUUID().toString();
	private String command;
	private String locator;
	private String args;
	private String message;
	private boolean ensureVisible = true;
	private boolean isFlex = false;
	private String flexAppName;
	private String label;
	public String options;
	public Object orginalData;
	private HashMap<String, Object> attributes;
	private List<ICommand> preCmds;
	private List<ICommand> postCmds;
	
	public Command(String command, String locator) {
		this(command, locator, null, true);
	}

	public Command(String command, String objectid, boolean ensureVisible) {
		this(command, objectid, null, ensureVisible);

	}

	public Command(String command, String locator, String args) {
		this(command, locator, args, true);
	}

	public Command(String command, String locator, String args,
			boolean ensureVisible) {
		this.command = command;
		this.locator = locator;
		this.args = args;
		this.ensureVisible = ensureVisible;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sourceforge.seleniumflexapi.cal.ICommand#getArgs()
	 */
	public String getArgs() {
		return args;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sourceforge.seleniumflexapi.cal.ICommand#getCommand()
	 */
	public String getCommand() {
		return command;
	}

	public String getCommandID() {
		return commandid;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sourceforge.seleniumflexapi.cal.ICommand#getFlexAppName()
	 */
	public String getFlexAppName() {
		return flexAppName;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sourceforge.seleniumflexapi.cal.ICommand#getLabel()
	 */
	public String getLabel() {
		return label;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sourceforge.seleniumflexapi.cal.ICommand#getLocator()
	 */
	public String getLocator() {
		return locator;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sourceforge.seleniumflexapi.cal.ICommand#getMessage()
	 */
	public String getMessage() {
		return message;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sourceforge.seleniumflexapi.cal.ICommand#isEnsureVisible()
	 */
	public boolean isEnsureVisible() {
		return ensureVisible;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sourceforge.seleniumflexapi.cal.ICommand#isFlex()
	 */
	public boolean isFlex() {
		return isFlex;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sourceforge.seleniumflexapi.cal.ICommand#setArgs(java.lang.String)
	 */
	public void setArgs(String args) {
		this.args = args;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sourceforge.seleniumflexapi.cal.ICommand#setCommand(java.lang.String)
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sourceforge.seleniumflexapi.cal.ICommand#setEnsureVisible(boolean)
	 */
	public void setEnsureVisible(boolean ensureVisible) {
		this.ensureVisible = ensureVisible;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sourceforge.seleniumflexapi.cal.ICommand#setFlex(boolean)
	 */
	public void setFlex(boolean isFlex) {
		this.isFlex = isFlex;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sourceforge.seleniumflexapi.cal.ICommand#setFlexAppName(java.lang
	 * .String)
	 */
	public void setFlexAppName(String flexAppName) {
		this.flexAppName = flexAppName;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sourceforge.seleniumflexapi.cal.ICommand#setLabel(java.lang.String)
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sourceforge.seleniumflexapi.cal.ICommand#setLocator(java.lang.String)
	 */
	public void setLocator(String objectId) {
		this.locator = objectId;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sourceforge.seleniumflexapi.cal.ICommand#setMessage(java.lang.String)
	 */
	public void setMessage(String message) {
		if (message != null) {
			if ((message = message.trim()).length() == 0) {
				return;
			}
		}
		this.message = message;
	}

	/**
	 * @return the options
	 */
	public String getOptions() {
		return options;
	}

	/**
	 * @param options the options to set
	 */
	public void setOptions(String options) {
		this.options = options;
	}

	/**
	 * @return the orginalData
	 */
	public Object getOrginalData() {
		return orginalData;
	}

	/**
	 * @param orginalData the orginalData to set
	 */
	public void setOrginalData(Object orginalData) {
		this.orginalData = orginalData;
	}

	@Override
	public Object getAttribute(String key) {
		return (attributes == null ? null : attributes.get(key));
	}

	@Override
	public void setAttribute(String key, Object value) {
		if(attributes == null){
			attributes = new HashMap<String, Object>();
		}
		attributes.put(key, value);
	}

	@Override
	public List<ICommand> getPreProcessingCommands() {
		return preCmds;
	}

	@Override
	public List<ICommand> getPostProcessingCommands() {
		return postCmds;
	}

	@Override
	public void addPreCommand(ICommand cmd) {
		if(cmd != null){
			if(preCmds == null){
				preCmds = new ArrayList<ICommand>();
			}
			preCmds.add(cmd);
		}
	}

	@Override
	public void addPostCommnad(ICommand cmd) {
		if(cmd != null){
			if(postCmds == null){
				postCmds = new ArrayList<ICommand>();
			}
			postCmds.add(cmd);
		}
	}

	public Command clone() {
		Command clCmd = new Command(this.command, this.locator);
		clCmd.args = this.args;
		clCmd.message = this.message;
		clCmd.ensureVisible = this.ensureVisible;
		clCmd.isFlex = this.isFlex;
		clCmd.flexAppName = this.flexAppName;
		clCmd.label = this.label;
		clCmd.options = this.options;
		clCmd.orginalData = this.orginalData;
		clCmd.attributes = this.attributes;
		clCmd.preCmds = this.preCmds;
		clCmd.postCmds = this.postCmds;
		
		return clCmd;
	}

	
}

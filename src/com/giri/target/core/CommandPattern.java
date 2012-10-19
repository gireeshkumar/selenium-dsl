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

import com.giri.target.impl.Command;

/**
 * @author Gireesh Kumar G
 * @Created Jul 6, 2009 For UI display
 */
public class CommandPattern extends Command {

	private String display;
	private Help help;
	private boolean isFlex = false;

	/**
	 * @param command
	 * @param objectid
	 * @param args
	 */
	public CommandPattern(String display, String command, String objectid,
			String args, Help help) {
		super(command, objectid, args);
		this.display = display;
		this.help = help;
	}

	public CommandPattern(String display, String command, String objectid,
			String args) {
		this(display, command, objectid, args, (Help) null);
	}

	public CommandPattern(String display, String command, String objectid) {
		this(display, command, objectid, (Help) null);
	}

	public CommandPattern(String display, String command, String objectid,
			Help help) {
		this(display, command, objectid, null, help);
	}

	public CommandPattern(String display, String command) {
		this(display, command, (String) null);
	}

	public CommandPattern(String display, String command, Help help) {
		this(display, command, null, help);
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	@Override
	public String toString() {
		return display;
	}

	public Help getHelp() {
		return help;
	}

	public void setHelp(Help help) {
		this.help = help;
	}

	@Override
	public boolean isFlex() {
		return isFlex;
	}

	@Override
	public void setFlex(boolean isFlex) {
		this.isFlex = isFlex;
	}

	/**
	 * Mark the command as flex command
	 * 
	 * @return
	 */
	public CommandPattern toFlex() {
		this.isFlex = true;
		return this;
	}

	public static class Help {
		private String helpString;

		public Help(String help) {
			this.helpString = help;
		}

		public static Help to(String helpStr) {
			return new Help(helpStr);
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return helpString;
		}
	}

}

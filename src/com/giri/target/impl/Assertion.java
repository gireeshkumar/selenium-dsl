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

import com.giri.target.ifc.IAssertion;

/**
 * @author Gireesh Kumar G
 * @Created Aug 6, 2009
 */
public class Assertion extends Command implements IAssertion {

	private String expectedValue;
	private String actualValue;
	private boolean abortOnError = true;

	/**
	 * @param command
	 * @param objectid
	 */
	public Assertion(String command, String objectid) {
		super(command, objectid);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param command
	 * @param objectid
	 * @param ensureVisible
	 */
	public Assertion(String command, String objectid, boolean ensureVisible) {
		super(command, objectid, ensureVisible);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param command
	 * @param objectid
	 * @param args
	 */
	public Assertion(String command, String objectid, String args) {
		super(command, objectid, args);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param command
	 * @param objectid
	 * @param args
	 * @param ensureVisible
	 */
	public Assertion(String command, String objectid, String args,
			boolean ensureVisible) {
		super(command, objectid, args, ensureVisible);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * @see com.giri.gwat.svr.IAssertion#getActualValue()
	 */
	public String getActualValue() {
		return actualValue;
	}

	/*
	 * (non-Javadoc)
	 * @see com.giri.gwat.svr.IAssertion#getExpectedValue()
	 */
	public String getExpectedValue() {
		return expectedValue;
	}

	/*
	 * (non-Javadoc)
	 * @see com.giri.gwat.svr.IAssertion#setActualValue(java.lang.String)
	 */
	public void setActualValue(String actualValue) {
		this.actualValue = actualValue;
	}

	/*
	 * (non-Javadoc)
	 * @see com.giri.gwat.svr.IAssertion#setExpectedValue(java.lang.String)
	 */
	public void setExpectedValue(String expectedValue) {
		this.expectedValue = expectedValue;
	}

	/* (non-Javadoc)
	 * @see com.giri.target.ifc.IAssertion#abortOnError()
	 */
	@Override
	public boolean abortOnError() {
		return this.abortOnError;
	}

	/**
	 * @param abortOnError the abortOnError to set
	 */
	public void setAbortOnError(boolean abortOnError) {
		this.abortOnError = abortOnError;
	}
	

}

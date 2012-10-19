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

import java.util.Properties;

import com.giri.target.ifc.IModel;
import com.giri.target.ifc.IServerInfo;

/**
 * @author Gireesh Kumar G
 * @Created Jul 2, 2009
 */
public class Model extends Properties implements IModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2959335200793349704L;
	private IServerInfo serverinfo = new ServerInfo();
	private String appURL;

	/*
	 * (non-Javadoc)
	 * @see com.giri.gwat.ifc.IModel#getApplicationURL()
	 */
	@Override
	public String getApplicationURL() {
		return this.appURL;
	}

	/*
	 * (non-Javadoc)
	 * @see com.giri.gwat.ifc.IModel#getServerInfo()
	 */
	@Override
	public IServerInfo getServerInfo() {
		return this.serverinfo;
	}

	/*
	 * (non-Javadoc)
	 * @see com.giri.gwat.ifc.IModel#setApplicationURL(java.lang.String)
	 */
	@Override
	public void setApplicationURL(String appurl) {
		this.appURL = appurl;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.giri.gwat.ifc.IModel#setServerInfo(com.giri.gwat.ifc.IServerInfo)
	 */
	@Override
	public void setServerInfo(IServerInfo sinfo) {
		this.serverinfo = sinfo;
	}

}

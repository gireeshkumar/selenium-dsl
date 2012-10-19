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

import com.giri.target.ifc.IServerInfo;

/**
 * @author Gireesh Kumar G
 * @Created Jul 2, 2009
 */
public class ServerInfo implements IServerInfo {

	private String host = "127.0.0.1";
	private int port = 55555;

	
	public ServerInfo(){
		String tserver = System.getProperty("target.server");
		String tport = System.getProperty("target.port");
		
		if(tserver != null && (tserver = tserver.trim()).length() > 0){
			host = tserver;
		}
		if(tport != null && (tport = tport.trim()).length() > 0){
			port = Integer.parseInt(tport);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.giri.gwat.ifc.IServerInfo#getServerHost()
	 */
	@Override
	public String getServerHost() {
		return this.host;
	}

	/*
	 * (non-Javadoc)
	 * @see com.giri.gwat.ifc.IServerInfo#getServerPort()
	 */
	@Override
	public int getServerPort() {
		return this.port;
	}

	/*
	 * (non-Javadoc)
	 * @see com.giri.gwat.ifc.IServerInfo#setServerHost(java.lang.String)
	 */
	@Override
	public void setServerHost(String host) {
		this.host = host;
	}

	/*
	 * (non-Javadoc)
	 * @see com.giri.gwat.ifc.IServerInfo#setServerPort(int)
	 */
	@Override
	public void setServerPort(int port) {
		this.port = port;
	}

}

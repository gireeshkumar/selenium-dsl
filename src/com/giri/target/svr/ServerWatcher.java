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
package com.giri.target.svr;

import java.io.InputStream;
import java.net.Socket;

import com.giri.target.core.Application;
import com.giri.target.ifc.IServerInfo;
import com.giri.target.ifc.IStatusMonitor.STATUS;

/**
 * @author Gireesh Kumar G
 * @Created Jul 6, 2009
 */
public class ServerWatcher implements Runnable {

	Thread thread;
	private boolean stopRunning = false;

	public ServerWatcher() {
		thread = new Thread(this);
	}

	public void run() {
		while (true) {
			try {
				if (stopRunning) {
					break;
				}
				final IServerInfo serverInfo = Application.getInstance()
						.getModel().getServerInfo();
				final Socket socket = new Socket(serverInfo.getServerHost(),
						serverInfo.getServerPort());
				Application.getInstance().getStatusMonitor()
						.setConnectionStatus(STATUS.SUCCESS);
				socket.isConnected();
				InputStream inStream = socket.getInputStream();
				inStream.read();
			} catch (Exception e) {
				Application.getInstance().getStatusMonitor()
						.setConnectionStatus(STATUS.FAILED);
				try {
					Thread.sleep(1000);
				} catch (Throwable e1) {
				}
			}
		}
	}

	public void start() {
		try {
			thread.start();
		} catch (Exception e) {
			Application.getInstance().getLogger().log(e);
		}
	}

	public void stop() {
		stopRunning = true;
	}
}

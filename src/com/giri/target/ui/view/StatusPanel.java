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
package com.giri.target.ui.view;

import java.lang.reflect.Method;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.giri.target.Utils;
import com.giri.target.core.Application;
import com.giri.target.core.EventsConstants;
import com.giri.target.ifc.IStatusMonitor;

public class StatusPanel extends JPanel implements IStatusMonitor {

	public static final ImageIcon GREEN_ICON = Utils.toImageIcon(
			(StatusPanel.class.getResource("/asset/statusGreen.png")), 16, 16);
	public static final ImageIcon RED_ICON = Utils.toImageIcon(
			(StatusPanel.class.getResource("/asset/statusRed.png")), 16, 16);
	public static final String ONLINE = "Connected to test server"; // @jve:decl-index=0:
	public static final String OFFLINE = "Test server not available";

	private static final long serialVersionUID = 1L;
	private JLabel jLabel = null;
	private STATUS currentStatus = STATUS.FAILED; // @jve:decl-index=0:

	/**
	 * This is the default constructor
	 */
	public StatusPanel() {
		super();
		Application.getInstance().setStatusMonitor(this);
		initialize();
//		try {
//			Method actionPerformed = this.getClass().getMethod(
//					"actionPerformed");
//			Application.getInstance().addEventListener(
//					EventsConstants.SERVER_STARTED,
//					new MethodActionListener(actionPerformed, this));
//		} catch (Exception e) {
//			Application.getInstance().getLogger().log(e);
//		}
	}

	public void actionPerformed() {

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel = new JLabel();
		serverNotAvailable();
		this.setSize(300, 200);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(jLabel, null);
	}

	public void serverNotAvailable() {
		setStatus(false);
	}

	public void serverAvailable() {
		setStatus(true);
	}

	public void setStatus(boolean status) {
		jLabel.setText((status ? ONLINE : OFFLINE));
		jLabel.setIcon((status ? GREEN_ICON : RED_ICON));
	}

	@Override
	public void setConnectionStatus(STATUS status) {
		currentStatus = status;
		setStatus((status == STATUS.SUCCESS));
	}

	/*
	 * (non-Javadoc)
	 * @see com.giri.gwat.ifc.IStatusMonitor#getConnectionStatus()
	 */
	@Override
	public STATUS getConnectionStatus() {
		return currentStatus;
	}

	/**
	 * Creates an ImageIcon if the path is valid.
	 * 
	 * @param String
	 *            - resource path
	 * @param String
	 *            - description of the file
	 */
	protected static ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = StatusPanel.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

}

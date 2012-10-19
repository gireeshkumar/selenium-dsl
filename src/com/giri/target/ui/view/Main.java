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

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.giri.target.core.Application;

/**
 * @author Gireesh Kumar G
 * @Created Jul 2, 2009
 */
public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTabbedPane jTabbedPane = null;
	private TestCommander commander = null;
	private LogPanel logPanel = null;
	private ServerPanel serverPanel = null;
	private TestRunnerViewPanel runPanel = null;
	private StatusPanel statusPanel = null;

	/**
	 * This is the default constructor
	 */
	public Main() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initialize();
		Application.getInstance().startServerWatcher();
	}

	/**
	 * This method initializes commander
	 * 
	 * @return javax.swing.JPanel
	 */
	private TestCommander getCommander() {
		if (commander == null) {
			commander = new TestCommander();
		}
		return commander;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
			jContentPane.add(getStatusPanel(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("Test Commander", null, getCommander(), null);
			jTabbedPane.addTab("Log", null, getLogPanel(), null);
			jTabbedPane.addTab("Server", null, getServerPanel(), null);
			jTabbedPane.addTab("Test Runner", null, getRunPanel(), null);

		}
		return jTabbedPane;
	}

	/**
	 * This method initializes logPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private LogPanel getLogPanel() {
		if (logPanel == null) {
			logPanel = new LogPanel();
		}
		return logPanel;
	}

	/**
	 * This method initializes runPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private TestRunnerViewPanel getRunPanel() {
		if (runPanel == null) {
			runPanel = new TestRunnerViewPanel();
			// runPanel.setLayout(new BorderLayout());
		}
		return runPanel;
	}

	/**
	 * This method initializes serverPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private ServerPanel getServerPanel() {
		if (serverPanel == null) {
			serverPanel = new ServerPanel();
		}
		return serverPanel;
	}

	/**
	 * This method initializes statusPanel
	 * 
	 * @return com.giri.gtest.ui.StatusPanel
	 */
	private StatusPanel getStatusPanel() {
		if (statusPanel == null) {
			statusPanel = new StatusPanel();
		}
		return statusPanel;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(664, 540);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
	}

	public static void main(String[] args) {
		Main jf = new Main();
		jf.setVisible(true);
	}

} // @jve:decl-index=0:visual-constraint="15,15"

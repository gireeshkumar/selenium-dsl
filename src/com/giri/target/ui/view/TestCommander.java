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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.giri.target.ui.view.printer.XMLOutputPanel;

/**
 * @author Gireesh Kumar G
 * @Created Jul 2, 2009
 */
public class TestCommander extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTabbedPane jTabbedPane = null;
	private CommandPanel commandPanel = null;
	private JPanel javaOutPanel = null;
	private XMLOutputPanel xmlOutPanel = null;

	/**
	 * This is the default constructor
	 */
	public TestCommander() {
		super();
		initialize();
	}

	/**
	 * This method initializes commandPanel
	 * 
	 * @return com.giri.gtest.ui.CommandPanel
	 */
	private CommandPanel getCommandPanel() {
		if (commandPanel == null) {
			commandPanel = new CommandPanel();
		}
		return commandPanel;
	}

	/**
	 * This method initializes javaOutPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJavaOutPanel() {
		if (javaOutPanel == null) {
			javaOutPanel = new JPanel();
			javaOutPanel.setLayout(new GridBagLayout());
		}
		return javaOutPanel;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("Java", null, getJavaOutPanel(), null);
			jTabbedPane.addTab("XML", null, getXmlOutPanel(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes xmlOutPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private XMLOutputPanel getXmlOutPanel() {
		if (xmlOutPanel == null) {
			xmlOutPanel = new XMLOutputPanel();
		}
		return xmlOutPanel;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = -1;
		gridBagConstraints.gridy = -1;
		final GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
		gridBagConstraints7.gridx = 0;
		gridBagConstraints7.gridwidth = 4;
		gridBagConstraints7.gridy = 1;
		gridBagConstraints7.insets = new Insets(5, 5, 5, 5);
		gridBagConstraints7.fill = GridBagConstraints.HORIZONTAL;
		final GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
		gridBagConstraints5.fill = GridBagConstraints.BOTH;
		gridBagConstraints5.gridy = 2;
		gridBagConstraints5.weightx = 1.0;
		gridBagConstraints5.weighty = 1.0;
		gridBagConstraints5.gridwidth = 4;
		gridBagConstraints5.insets = new Insets(5, 5, 5, 5);
		gridBagConstraints5.gridx = 0;
		this.setSize(300, 200);
		this.setLayout(new GridBagLayout());
		this.add(getJTabbedPane(), gridBagConstraints5);
		this.add(getCommandPanel(), gridBagConstraints7);
	}

}

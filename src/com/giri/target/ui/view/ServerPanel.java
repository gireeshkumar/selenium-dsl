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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;

import com.giri.target.core.Application;

/**
 * @author Gireesh Kumar G
 * @Created Jul 2, 2009
 */
public class ServerPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton startEmbdSrvBtt = null;
	private JRadioButton useEmdSrvBtt = null;
	private JRadioButton useStdSrvBtt = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JTextField srvHostTextField = null;
	private JLabel jLabel2 = null;
	private JTextField srvPortTextField = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private final ButtonGroup bttGroup = new ButtonGroup(); // @jve:decl-index=0:
	ChangeListener bttStateChange = new javax.swing.event.ChangeListener() {
		public void stateChanged(javax.swing.event.ChangeEvent e) {
			changeState();
		}
	}; // @jve:decl-index=0:
	private JPanel jPanel = null;
	private JLabel jLabel5 = null;
	private JTextField embededSrvPortIN = null;
	private JLabel jLabel6 = null;
	private JButton jButton = null;

	/**
	 * This is the default constructor
	 */
	public ServerPanel() {
		super();
		initialize();
	}

	private void changeState() {
		startEmbdSrvBtt.setEnabled(useEmdSrvBtt.isSelected());
		final boolean useStdSrv = useStdSrvBtt.isSelected();
		srvHostTextField.setEditable(useStdSrv);
		srvPortTextField.setEditable(useStdSrv);
	}

	/**
	 * This method initializes srvHostTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getSrvHostTextField() {
		if (srvHostTextField == null) {
			srvHostTextField = new JTextField();
			srvHostTextField.setColumns(50);
		}
		return srvHostTextField;
	}

	/**
	 * This method initializes srvPortTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getSrvPortTextField() {
		if (srvPortTextField == null) {
			srvPortTextField = new JTextField();
		}
		return srvPortTextField;
	}

	/**
	 * This method initializes startEmbdSrvBtt
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getStartEmbdSrvBtt() {
		if (startEmbdSrvBtt == null) {
			startEmbdSrvBtt = new JButton();
			startEmbdSrvBtt.setText("Start embeded server");
			startEmbdSrvBtt
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
//							try {
//								Application.getInstance()
//										.getAutomationTestServer().stop();
//								Application.getInstance()
//										.getAutomationTestServer().start();
//							} catch (Exception e1) {
//								Application.getInstance().getLogger().log(e1);
//							}
						}
					});
		}
		return startEmbdSrvBtt;
	}

	/**
	 * This method initializes useEmdSrvBtt
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getUseEmdSrvBtt() {
		if (useEmdSrvBtt == null) {
			useEmdSrvBtt = new JRadioButton();
			useEmdSrvBtt.setSelected(true);
			useEmdSrvBtt.setText("Use Embedded server");
			useEmdSrvBtt.addChangeListener(bttStateChange);
			bttGroup.add(useEmdSrvBtt);
		}
		return useEmdSrvBtt;
	}

	/**
	 * This method initializes useStdSrvBtt
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getUseStdSrvBtt() {
		if (useStdSrvBtt == null) {
			useStdSrvBtt = new JRadioButton();
			useStdSrvBtt.setText("Use Standalone server");
			useStdSrvBtt.addChangeListener(bttStateChange);
			bttGroup.add(useStdSrvBtt);
		}
		return useStdSrvBtt;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 5;
		gridBagConstraints2.anchor = GridBagConstraints.WEST;
		gridBagConstraints2.gridy = 6;
		final GridBagConstraints gridBagConstraints29 = new GridBagConstraints();
		gridBagConstraints29.gridx = 6;
		gridBagConstraints29.gridy = 2;
		jLabel4 = new JLabel();
		jLabel4.setText(" ");
		final GridBagConstraints gridBagConstraints28 = new GridBagConstraints();
		gridBagConstraints28.gridx = 4;
		gridBagConstraints28.gridy = 2;
		jLabel3 = new JLabel();
		jLabel3.setText(" : ");
		final GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
		gridBagConstraints27.fill = GridBagConstraints.BOTH;
		gridBagConstraints27.gridy = 2;
		gridBagConstraints27.weightx = 1.0;
		gridBagConstraints27.anchor = GridBagConstraints.WEST;
		gridBagConstraints27.insets = new Insets(0, 0, 0, 20);
		gridBagConstraints27.gridx = 5;
		final GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
		gridBagConstraints26.gridx = 1;
		gridBagConstraints26.gridy = 2;
		jLabel2 = new JLabel();
		jLabel2.setText("Server Port");
		final GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
		gridBagConstraints25.fill = GridBagConstraints.BOTH;
		gridBagConstraints25.gridy = 1;
		gridBagConstraints25.weightx = 1.0;
		gridBagConstraints25.anchor = GridBagConstraints.WEST;
		gridBagConstraints25.insets = new Insets(0, 0, 0, 20);
		gridBagConstraints25.gridx = 5;
		final GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
		gridBagConstraints24.gridx = 4;
		gridBagConstraints24.gridy = 1;
		jLabel1 = new JLabel();
		jLabel1.setText(" : ");
		final GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
		gridBagConstraints23.gridx = 1;
		gridBagConstraints23.gridy = 1;
		jLabel = new JLabel();
		jLabel.setText("Server Host");
		final GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
		gridBagConstraints21.gridx = 1;
		gridBagConstraints21.fill = GridBagConstraints.BOTH;
		gridBagConstraints21.gridwidth = 0;
		gridBagConstraints21.insets = new Insets(5, 5, 5, 5);
		gridBagConstraints21.gridy = 4;
		final GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
		gridBagConstraints19.gridx = 1;
		gridBagConstraints19.gridwidth = 5;
		gridBagConstraints19.anchor = GridBagConstraints.WEST;
		gridBagConstraints19.gridy = 0;
		final GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
		gridBagConstraints18.gridx = 1;
		gridBagConstraints18.gridwidth = 5;
		gridBagConstraints18.anchor = GridBagConstraints.WEST;
		gridBagConstraints18.gridy = 3;
		this.setSize(300, 200);
		this.setLayout(new GridBagLayout());
		this.add(getUseEmdSrvBtt(), gridBagConstraints18);
		this.add(getUseStdSrvBtt(), gridBagConstraints19);
		this.add(jLabel, gridBagConstraints23);
		this.add(jLabel1, gridBagConstraints24);
		this.add(getSrvHostTextField(), gridBagConstraints25);
		this.add(jLabel2, gridBagConstraints26);
		this.add(getSrvPortTextField(), gridBagConstraints27);
		this.add(jLabel3, gridBagConstraints28);
		this.add(jLabel4, gridBagConstraints29);
		this.add(getJPanel(), gridBagConstraints2);
		changeState();
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.gridx = 0;
			gridBagConstraints31.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints31.insets = new Insets(5, 0, 0, 0);
			gridBagConstraints31.gridwidth = 3;
			gridBagConstraints31.gridy = 4;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 1;
			gridBagConstraints4.gridy = 2;
			jLabel6 = new JLabel();
			jLabel6.setText(" : ");
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.NONE;
			gridBagConstraints3.gridy = 2;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.anchor = GridBagConstraints.WEST;
			gridBagConstraints3.gridx = 2;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 2;
			jLabel5 = new JLabel();
			jLabel5.setText("Port");
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridwidth = 3;
			gridBagConstraints.insets = new Insets(5, 0, 0, 0);
			gridBagConstraints.gridy = 3;
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.add(getStartEmbdSrvBtt(), gridBagConstraints);
			jPanel.add(jLabel5, gridBagConstraints1);
			jPanel.add(getJTextField(), gridBagConstraints3);
			jPanel.add(jLabel6, gridBagConstraints4);
			jPanel.add(getJButton(), gridBagConstraints31);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (embededSrvPortIN == null) {
			embededSrvPortIN = new JTextField();
			embededSrvPortIN.setColumns(6);
			embededSrvPortIN.setEditable(false);
			embededSrvPortIN.setText("4444");
		}
		return embededSrvPortIN;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("Stop embeded server");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
//						Application.getInstance().getAutomationTestServer()
//								.stop();
					} catch (Exception e1) {
						Application.getInstance().getLogger().log(e1);
					}
				}
			});
		}
		return jButton;
	}

}

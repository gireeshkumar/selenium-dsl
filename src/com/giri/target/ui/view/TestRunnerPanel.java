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
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;

import com.giri.target.svr.XMLTestCaseRunner;

/**
 * @author Gireesh Kumar G
 * @Created Jul 2, 2009
 */
public class TestRunnerPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final ButtonGroup bttGroup = new ButtonGroup();

	final ChangeListener stateChangeListener = (new javax.swing.event.ChangeListener() {
		public void stateChanged(javax.swing.event.ChangeEvent e) {
		}
	});

	private JButton jButton = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JTextField jTextField = null;
	private ReportPanel reportPanel = null;
	private JButton jButton1 = null;
	private JScrollPane jScrollPane = null;
	private JEditorPane jEditorPane = null;

	/**
	 * oi This is the default constructor
	 */
	public TestRunnerPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
		gridBagConstraints6.gridx = 0;
		gridBagConstraints6.gridy = 3;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.insets = new Insets(5, 5, 5, 5);
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.gridy = 5;
		GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
		gridBagConstraints5.fill = GridBagConstraints.BOTH;
		gridBagConstraints5.gridy = 2;
		gridBagConstraints5.weightx = 1.0;
		gridBagConstraints5.anchor = GridBagConstraints.WEST;
		gridBagConstraints5.insets = new Insets(5, 5, 0, 5);
		gridBagConstraints5.gridx = 0;
		GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
		gridBagConstraints4.gridx = 0;
		gridBagConstraints4.insets = new Insets(0, 5, 0, 0);
		gridBagConstraints4.anchor = GridBagConstraints.WEST;
		gridBagConstraints4.gridy = 1;
		jLabel1 = new JLabel();
		jLabel1
				.setText("(XML file ,  Folder contains xml test files, zip file with xml test cases)");
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.insets = new Insets(0, 5, 0, 0);
		gridBagConstraints3.gridy = 0;
		gridBagConstraints3.anchor = GridBagConstraints.WEST;
		gridBagConstraints3.gridx = 0;
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.insets = new Insets(5, 0, 0, 5);
		gridBagConstraints2.gridy = 2;
		gridBagConstraints2.gridx = 1;
		jLabel = new JLabel();
		jLabel.setText("Select test case xml files");

		this.setLayout(new GridBagLayout());
		this.add(getJButton(), gridBagConstraints2);
		this.add(jLabel, gridBagConstraints3);
		this.add(jLabel1, gridBagConstraints4);
		this.add(getJTextField(), gridBagConstraints5);
		this.add(getReportPanel(), gridBagConstraints);
		this.add(getJButton1(), gridBagConstraints6);
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("Locate");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					loadTestcase();
				}
			});
		}
		return jButton;
	}

	private void loadTestcase() {
		try {
			JFileChooser jfChooser = new JFileChooser();
			jfChooser.showOpenDialog(null);
			jTextField.setText(jfChooser.getSelectedFile().getAbsolutePath());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
		}
		return jTextField;
	}

	/**
	 * This method initializes reportPanel
	 * 
	 * @return com.giri.gwat.ui.view.ReportPanel
	 */
	private ReportPanel getReportPanel() {
		if (reportPanel == null) {
			reportPanel = new ReportPanel();
			reportPanel.setLayout(new BorderLayout());
			reportPanel.setBorder(BorderFactory.createTitledBorder(null,
					"Report", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
			reportPanel.add(getJScrollPane(), BorderLayout.SOUTH);
		}
		return reportPanel;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("Run Test");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						File curFile = new File(jTextField.getText());
						new XMLTestCaseRunner().runTest(curFile);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJEditorPane());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jEditorPane
	 * 
	 * @return javax.swing.JEditorPane
	 */
	private JEditorPane getJEditorPane() {
		if (jEditorPane == null) {
			jEditorPane = new JEditorPane();
		}
		return jEditorPane;
	}

} // @jve:decl-index=0:visual-constraint="10,10"

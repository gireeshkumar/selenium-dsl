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

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import com.giri.target.Utils;
import com.giri.target.core.Application;
import com.giri.target.core.AssertionPattern;
import com.giri.target.core.BrowserTypes;
import com.giri.target.core.CommandPattern;
import com.giri.target.core.CommandPatterns;
import com.giri.target.core.TestFailedException;
import com.giri.target.ifc.ICommand;
import com.giri.target.impl.Assertion;
import com.giri.target.impl.Command;

/**
 * @author Gireesh Kumar G
 * @Created Jul 2, 2009
 */
public class CommandPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel = null;
	private JComboBox jComboBox = null;
	private JTextField objectidInput = null;
	private JButton jButton = null;
	private JLabel jLabel1 = null;
	private JTextField appURLTextField = null;
	private JButton jButton1 = null;
	private JLabel jLabel2 = null;
	private static final BrowserTypes[] browserTypes = new BrowserTypes[] {
			BrowserTypes.IE, BrowserTypes.FIREFOX };
	private JLabel jLabel4 = null;
	private JTextField appNameTextIn = null;
	private JComboBox commandCB = null;
	private JTextArea helpArea = null;
	private JPanel jPanel = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel5 = null;
	private JLabel jLabel6 = null;
	private JTextField paramInput = null;
	private JComboBox browserListCB = null;
	private JComboBox assertsComboBox = null;
	private JTextField assertObjId = null;
	private JTextField assertParamIn = null;
	private JButton assertButton = null;
	private JLabel assertionsLbl = null;
	private JLabel jLabel7 = null;
	private JTextField messageTextField = null;
	private JLabel jLabel8 = null;
	private JLabel jLabel9 = null;
	private JTextField expectedTextField = null;

	/**
	 * This is the default constructor
	 */
	public CommandPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes appURLTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getAppURLTextField() {
		if (appURLTextField == null) {
			appURLTextField = new JTextField();
			appURLTextField.setText("http://google.com");
		}
		return appURLTextField;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("Run Command");
			jButton.setIcon(Utils.toImageIcon(getClass().getResource(
					"/asset/run-48x48.png"), 16, 16));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					runCommand();
				}
			});
		}
		return jButton;
	}

	private void runAssertion() {
		try {
			AssertionPattern aPattern = (AssertionPattern) assertsComboBox
					.getSelectedItem();
			Assertion assertion = new Assertion(aPattern.getCommand(),
					objectidInput.getText(), paramInput.getText());
			assertion.setMessage(messageTextField.getText());
			assertion.setFlex(aPattern.isFlex());
			assertion.setExpectedValue(expectedTextField.getText());
			Application.getInstance().getTestCommandRunner().execute(aPattern);
			Application.getInstance().printCommand(aPattern);
		} catch (TestFailedException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(),
					"Assertion Failed", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void runCommand() {
		try {
			CommandPattern cmdPattern = (CommandPattern) commandCB
					.getSelectedItem();
			ICommand cmd = new Command(cmdPattern.getCommand(), objectidInput
					.getText(), paramInput.getText());
			cmd.setFlexAppName(appNameTextIn.getText());
			cmd.setMessage(messageTextField.getText());
			cmd.setFlex(cmdPattern.isFlex());
			Application.getInstance().getTestCommandRunner().execute(cmd);
			Application.getInstance().printCommand(cmd);
		} catch (TestFailedException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(),
					"Command Failed", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setIcon(Utils.toImageIcon(getClass().getResource(
					"/asset/html-file-48x48.png"), 16, 16));
			jButton1.setText("Load");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						Application.getInstance().getModel().setApplicationURL(
								appURLTextField.getText());
						int indx = browserListCB.getSelectedIndex();
						BrowserTypes brtype = browserTypes[indx];
						Application.getInstance().start(brtype.command(),
								appURLTextField.getText(), appNameTextIn.getText());
						Application.getInstance().getModel().setProperty("browser",
								brtype.command());
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (objectidInput == null) {
			objectidInput = new JTextField();
			objectidInput.setText("Object id");
		}
		return objectidInput;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints111 = new GridBagConstraints();
		gridBagConstraints111.fill = GridBagConstraints.BOTH;
		gridBagConstraints111.gridy = 3;
		gridBagConstraints111.weightx = 1.0;
		gridBagConstraints111.insets = new Insets(0, 5, 0, 5);
		gridBagConstraints111.gridx = 4;
		GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
		gridBagConstraints10.gridx = 0;
		gridBagConstraints10.fill = GridBagConstraints.BOTH;
		gridBagConstraints10.gridwidth = 13;
		gridBagConstraints10.gridy = 8;
		GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
		gridBagConstraints5.fill = GridBagConstraints.BOTH;
		gridBagConstraints5.gridy = 3;
		gridBagConstraints5.weightx = 1.0;
		gridBagConstraints5.insets = new Insets(0, 5, 0, 0);
		gridBagConstraints5.gridwidth = 3;
		gridBagConstraints5.gridx = 1;
		GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
		gridBagConstraints41.gridx = 0;
		gridBagConstraints41.insets = new Insets(0, 5, 0, 0);
		gridBagConstraints41.gridy = 3;
		jLabel4 = new JLabel();
		jLabel4.setText("Application Name");
		final GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
		gridBagConstraints13.gridx = 9;
		gridBagConstraints13.gridy = 3;
		jLabel2 = new JLabel();
		jLabel2.setText("");
		final GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.gridx = 12;
		gridBagConstraints11.insets = new Insets(0, 5, 0, 5);
		gridBagConstraints11.gridy = 1;
		final GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
		gridBagConstraints9.fill = GridBagConstraints.BOTH;
		gridBagConstraints9.gridy = 1;
		gridBagConstraints9.weightx = 1.0;
		gridBagConstraints9.gridwidth = 8;
		gridBagConstraints9.insets = new Insets(0, 5, 0, 5);
		gridBagConstraints9.gridx = 1;
		final GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
		gridBagConstraints8.gridx = 0;
		gridBagConstraints8.anchor = GridBagConstraints.WEST;
		gridBagConstraints8.insets = new Insets(5, 5, 5, 0);
		gridBagConstraints8.gridy = 1;
		jLabel1 = new JLabel();
		jLabel1.setText("Application URL");
		final GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.fill = GridBagConstraints.BOTH;
		gridBagConstraints1.gridy = 5;
		gridBagConstraints1.weightx = 1.0;
		gridBagConstraints1.insets = new Insets(0, 5, 0, 5);
		gridBagConstraints1.gridx = 1;
		jLabel = new JLabel();
		jLabel.setText("Command ");
		this.setSize(781, 162);
		this.setLayout(new GridBagLayout());
		this.add(jLabel1, gridBagConstraints8);
		this.add(getAppURLTextField(), gridBagConstraints9);
		this.add(getJButton1(), gridBagConstraints11);
		this.add(jLabel2, gridBagConstraints13);
		this.add(jLabel4, gridBagConstraints41);
		this.add(getAppNameTextIn(), gridBagConstraints5);
		this.add(getJPanel(), gridBagConstraints10);
		this.add(getBrowserListCB(), gridBagConstraints111);
	}

	/**
	 * This method initializes appNameTextIn
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getAppNameTextIn() {
		if (appNameTextIn == null) {
			appNameTextIn = new JTextField();
		}
		return appNameTextIn;
	}

	private void commandSelected(CommandPattern pattern) {
		helpArea.setText(pattern.getHelp().toString());

		String args = pattern.getArgs();
		String objid = pattern.getLocator();
		paramInput.setEditable((args != null && args.length() > 0));
		objectidInput.setEditable((objid != null && objid.length() > 0));

		paramInput.setText(args);
		objectidInput.setText(objid);
	}

	/**
	 * This method initializes commandCB
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCommandCB() {
		if (commandCB == null) {
			commandCB = new JComboBox(CommandPatterns.AVAILABLE_COMMANDS);
			commandCB.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					commandSelected((CommandPattern) commandCB
							.getSelectedItem());
				}
			});
		}
		return commandCB;
	}

	/**
	 * This method initializes jTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextArea() {
		if (helpArea == null) {
			helpArea = new JTextArea();
			helpArea.setEditable(false);
			helpArea.setRows(2);
			helpArea.setOpaque(false);
		}
		return helpArea;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
			gridBagConstraints51.fill = GridBagConstraints.BOTH;
			gridBagConstraints51.gridy = 3;
			gridBagConstraints51.weightx = 1.0;
			gridBagConstraints51.insets = new Insets(5, 5, 0, 5);
			gridBagConstraints51.gridx = 2;
			GridBagConstraints gridBagConstraints42 = new GridBagConstraints();
			gridBagConstraints42.gridx = 1;
			gridBagConstraints42.anchor = GridBagConstraints.EAST;
			gridBagConstraints42.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints42.gridy = 3;
			jLabel9 = new JLabel();
			jLabel9.setText("Expected Result");
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.gridx = 1;
			gridBagConstraints31.gridwidth = 3;
			gridBagConstraints31.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints31.gridy = 4;
			jLabel8 = new JLabel();
			jLabel8
					.setText("Message to display in the report when 'Assertion' failed or when exeucting command");
			jLabel8.setIcon(Utils.toImageIcon(getClass().getResource(
					"/asset/48px-Information_icon.svg.png"), 16, 16));
			jLabel8.setForeground(new Color(102, 153, 255));
			jLabel8.setFont(new Font("Dialog", Font.ITALIC, 12));
			jLabel8.setHorizontalAlignment(SwingConstants.LEADING);
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			gridBagConstraints22.fill = GridBagConstraints.BOTH;
			gridBagConstraints22.gridy = 5;
			gridBagConstraints22.weightx = 1.0;
			gridBagConstraints22.gridwidth = 3;
			gridBagConstraints22.gridx = 1;
			GridBagConstraints gridBagConstraints110 = new GridBagConstraints();
			gridBagConstraints110.gridx = 0;
			gridBagConstraints110.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints110.gridy = 5;
			jLabel7 = new JLabel();
			jLabel7.setText("Message");
			jLabel7.setHorizontalAlignment(SwingConstants.LEFT);
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.gridx = 0;
			gridBagConstraints21.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints21.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints21.gridy = 2;
			assertionsLbl = new JLabel();
			assertionsLbl.setText("Assertions");
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 1;
			gridBagConstraints.insets = new Insets(5, 5, 5, 0);
			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.gridx = 10;
			gridBagConstraints20.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints20.insets = new Insets(5, 5, 0, 5);
			gridBagConstraints20.gridy = 2;
			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			gridBagConstraints19.fill = GridBagConstraints.BOTH;
			gridBagConstraints19.gridy = 2;
			gridBagConstraints19.weightx = 1.0;
			gridBagConstraints19.insets = new Insets(5, 5, 0, 0);
			gridBagConstraints19.gridx = 3;
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.fill = GridBagConstraints.BOTH;
			gridBagConstraints18.gridy = 2;
			gridBagConstraints18.weightx = 1.0;
			gridBagConstraints18.insets = new Insets(5, 5, 0, 0);
			gridBagConstraints18.gridx = 2;
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints17.gridy = 2;
			gridBagConstraints17.weightx = 1.0;
			gridBagConstraints17.insets = new Insets(5, 5, 0, 0);
			gridBagConstraints17.gridx = 1;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints4.gridy = 1;
			gridBagConstraints4.fill = GridBagConstraints.BOTH;
			gridBagConstraints4.gridx = 10;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.fill = GridBagConstraints.BOTH;
			gridBagConstraints6.gridx = -1;
			gridBagConstraints6.gridy = -1;
			gridBagConstraints6.weightx = 1.0;
			gridBagConstraints6.insets = new Insets(0, 5, 0, 5);
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.fill = GridBagConstraints.BOTH;
			gridBagConstraints16.gridwidth = 10;
			gridBagConstraints16.gridx = 1;
			gridBagConstraints16.gridy = 6;
			gridBagConstraints16.weightx = 1.0;
			gridBagConstraints16.weighty = 1.0;
			gridBagConstraints16.gridheight = 1;
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.fill = GridBagConstraints.BOTH;
			gridBagConstraints15.gridy = 1;
			gridBagConstraints15.weightx = 1.0;
			gridBagConstraints15.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints15.gridx = 3;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.BOTH;
			gridBagConstraints3.gridwidth = 1;
			gridBagConstraints3.gridx = 2;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.insets = new Insets(0, 5, 0, 0);
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.gridx = 3;
			gridBagConstraints14.gridy = 0;
			jLabel6 = new JLabel();
			jLabel6.setText("Parameters");
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 2;
			gridBagConstraints12.gridy = 0;
			jLabel5 = new JLabel();
			jLabel5.setText("Object ID");
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 1;
			gridBagConstraints7.gridy = 0;
			jLabel3 = new JLabel();
			jLabel3.setText("Command");
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.BOTH;
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.insets = new Insets(0, 5, 0, 0);
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.add(getCommandCB(), gridBagConstraints2);
			jPanel.add(jLabel3, gridBagConstraints7);
			jPanel.add(jLabel5, gridBagConstraints12);
			jPanel.add(jLabel6, gridBagConstraints14);
			jPanel.add(getJTextField1(), gridBagConstraints3);
			jPanel.add(getJTextField(), gridBagConstraints15);
			jPanel.add(getJTextArea(), gridBagConstraints16);
			jPanel.add(getJButton(), gridBagConstraints4);
			jPanel.add(getAssertsComboBox(), gridBagConstraints17);
			jPanel.add(getAssertObjId(), gridBagConstraints18);
			jPanel.add(getAssertParamIn(), gridBagConstraints19);
			jPanel.add(getAssertButton(), gridBagConstraints20);
			jPanel.add(jLabel, gridBagConstraints);
			jPanel.add(assertionsLbl, gridBagConstraints21);
			jPanel.add(jLabel7, gridBagConstraints110);
			jPanel.add(getJTextField2(), gridBagConstraints22);
			jPanel.add(jLabel8, gridBagConstraints31);
			jPanel.add(jLabel9, gridBagConstraints42);
			jPanel.add(getExpectedTextField(), gridBagConstraints51);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (paramInput == null) {
			paramInput = new JTextField();
		}
		return paramInput;
	}

	/**
	 * This method initializes browserListCB
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getBrowserListCB() {
		if (browserListCB == null) {
			browserListCB = new JComboBox(browserTypes);
			ComboBoxRenderer renderer = new ComboBoxRenderer();
			browserListCB.setRenderer(renderer);
		}
		return browserListCB;
	}

	/**
	 * This method initializes assertsComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getAssertsComboBox() {
		if (assertsComboBox == null) {
			assertsComboBox = new JComboBox(
					CommandPatterns.AVAILABLE_ASSERTIONS);
		}
		return assertsComboBox;
	}

	/**
	 * This method initializes assertObjId
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getAssertObjId() {
		if (assertObjId == null) {
			assertObjId = new JTextField();
		}
		return assertObjId;
	}

	/**
	 * This method initializes assertParamIn
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getAssertParamIn() {
		if (assertParamIn == null) {
			assertParamIn = new JTextField();
		}
		return assertParamIn;
	}

	/**
	 * This method initializes assertButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAssertButton() {
		if (assertButton == null) {
			assertButton = new JButton();
			assertButton.setIcon(Utils.toImageIcon(getClass().getResource(
					"/asset/tick-48x48.png"), 16, 16));
			assertButton.setText("Validate");
			assertButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					runAssertion();
				}
			});
		}
		return assertButton;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField2() {
		if (messageTextField == null) {
			messageTextField = new JTextField();
		}
		return messageTextField;
	}

	class ComboBoxRenderer extends JLabel implements ListCellRenderer {
		private Font uhOhFont;

		public ComboBoxRenderer() {
			setOpaque(true);
			setHorizontalAlignment(LEFT);
			setVerticalAlignment(CENTER);
		}

		/*
		 * This method finds the image and text corresponding to the selected
		 * value and returns the label, set up to display the text and image.
		 */
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			BrowserTypes type = (BrowserTypes) value;
			setText(type.name());
			if (type.image() != null) {
				setIcon(Utils.toImageIcon(getClass().getResource(
						"/asset/" + type.image()), 16, 16));
			}
			// Get the selected index. (The index param isn't
			// always valid, so just use the value.)
			// int selectedIndex = ((Integer)value).intValue();
			//
			// if (isSelected) {
			// setBackground(list.getSelectionBackground());
			// setForeground(list.getSelectionForeground());
			// } else {
			// setBackground(list.getBackground());
			// setForeground(list.getForeground());
			// }

			// Set the icon and text. If icon was null, say so.

			return this;
		}

		// Set the font and text when no image was found.
		protected void setUhOhText(String uhOhText, Font normalFont) {
			if (uhOhFont == null) { // lazily create this font
				uhOhFont = normalFont.deriveFont(Font.ITALIC);
			}
			setFont(uhOhFont);
			setText(uhOhText);
		}
	}

	/**
	 * This method initializes expectedTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getExpectedTextField() {
		if (expectedTextField == null) {
			expectedTextField = new JTextField();
		}
		return expectedTextField;
	}

} // @jve:decl-index=0:visual-constraint="0,0"

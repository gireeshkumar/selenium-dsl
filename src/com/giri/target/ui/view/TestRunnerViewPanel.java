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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.giri.target.core.Application;
import com.giri.target.ifc.ITestDataCollector;
import com.giri.target.ifc.ITestListener;
import com.giri.target.svr.BaseTestCaseRunner;
import com.giri.target.svr.XMLTestCaseRunner;
import com.giri.target.svr.XMLWriter;

/**
 * @author Gireesh Kumar G
 * @Created Aug 21, 2009
 */
public class TestRunnerViewPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JTextField jTextField = null;
	private JScrollPane jScrollPane = null;
	private JEditorPane testLog = null;
	private Thread testrunner;

	/**
	 * This is the default constructor
	 */
	public TestRunnerViewPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(388, 197);
		this.setLayout(new BorderLayout());
		this.add(getJPanel(), BorderLayout.NORTH);
		this.add(getJScrollPane(), BorderLayout.CENTER);
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.gridx = 1;
			gridBagConstraints21.gridy = 3;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.fill = GridBagConstraints.BOTH;
			gridBagConstraints4.gridx = 1;
			gridBagConstraints4.gridy = 2;
			gridBagConstraints4.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints4.weightx = 1.0;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.gridy = 1;
			jLabel1 = new JLabel();
			jLabel1
					.setText("XML file ,  Folder contains xml test files, zip file with xml test cases)");
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridy = 2;
			gridBagConstraints2.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints2.gridx = 2;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.anchor = GridBagConstraints.WEST;
			gridBagConstraints1.gridy = 0;
			jLabel = new JLabel();
			jLabel.setText("Select test case xml files");
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.add(getJButton(), gridBagConstraints2);
			jPanel.add(jLabel, gridBagConstraints1);
			jPanel.add(jLabel1, gridBagConstraints3);
			jPanel.add(getJTextField(), gridBagConstraints4);
			jPanel.add(getJPanel1(), gridBagConstraints21);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("Browse");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						File rootdir = null;
						try {
							String existing = jTextField.getText();
							rootdir = new File(existing);
							if (rootdir.isFile()) {
								rootdir = rootdir.getParentFile();
							}
						} catch (Throwable t) {

						}

						JFileChooser jfChooser = new JFileChooser(rootdir);
						jfChooser
								.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
						jfChooser.showOpenDialog(null);
						jTextField.setText(jfChooser.getSelectedFile()
								.getAbsolutePath());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return jButton;
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
						testLog.setText("");
						if (testrunner == null || !testrunner.isAlive()) {
							jButton1.setEnabled(false);
							testrunner = new Thread(new Runnable() {
								@Override
								public void run() {
									final BaseTestCaseRunner runner = new XMLTestCaseRunner();
									File curFile = null;
									try {
										curFile = new File(jTextField.getText());
										runner.setListener(testListner);
										runner.runTest(curFile);
										testListner
												.log("Test case execution completed successfully");
									} catch (Exception e) {
										e.printStackTrace();
									} finally {
										jButton1.setEnabled(true);
									}
									try {
										// Test completed, generate reports
										XMLTestListener xlistener = runner
												.getXmlTestReporter();
										org.jdom.Document doc = xlistener
												.getDocument();
										XMLOutputter outputter = new org.jdom.output.XMLOutputter(
												Format.getPrettyFormat());
										outputter.output(doc, System.out);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									try {
										ITestDataCollector dataCollector = runner
												.getDataCollector();
										File reportfile = new File(curFile
												.getParentFile(), curFile
												.getName()
												+ "report.xml");
										System.out
												.println("Report generated @ "
														+ reportfile
																.getAbsolutePath());
										FileWriter fw = new FileWriter(
												reportfile);
										XMLWriter.write(dataCollector, reportfile.getParentFile(), fw);
										fw.flush();
										fw.close();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							});
							testrunner.start();
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField
					.setText("E:\\Gireesh\\workspace\\TestUI\\testcases\\newevent.tc.xml");
		}
		return jTextField;
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
		if (testLog == null) {
			testLog = new JEditorPane();
		}
		return testLog;
	}

	// / ITest Listener
	private ITestListener testListner = new ITestListener() {

		@Override
		public void asserting(String message, TEST_STATUS status) {
			setText("\nAsserting :" + message + "[" + status + "]"); // @jve:decl-index=0:
		}

		@Override
		public void endTest(String message) {
			setText("\nTest Completed :" + message);
		}

		@Override
		public void logError(String message) {
			setText("\nERROR :" + message);
		}

		@Override
		public void logError(Throwable ex) {
			// TODO Auto-generated method stub

		}

		@Override
		public void startTest(String message) {
			setText("\nTest started :" + message);
		}

		@Override
		public void log(String message) {
			setText("\nTest started :" + message);
		}

		@Override
		public void testing(String message, TEST_STATUS status) {
			setText("\nTesting :" + message + "[" + status + "]");
		}

		private void setText(String text) {
			lwriter.text = text;
			SwingUtilities.invokeLater(lwriter);
		}

	};
	private final LogWriter lwriter = new LogWriter();
	private JPanel jPanel1 = null;
	private JButton jButton2 = null;
	private JButton jButton3 = null;
	private JButton jButton4 = null;

	class LogWriter implements Runnable {
		public String text;

		public void run() {
			testLog.setText(testLog.getText() + text);
		}
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.gridx = 3;
			gridBagConstraints31.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints31.gridy = 0;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 2;
			gridBagConstraints6.gridy = 0;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 1;
			gridBagConstraints5.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints5.gridy = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints.gridy = -1;
			gridBagConstraints.gridx = -1;
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.add(getJButton1(), gridBagConstraints);
			jPanel1.add(getJButton2(), gridBagConstraints5);
			jPanel1.add(getJButton3(), gridBagConstraints6);
			jPanel1.add(getJButton4(), gridBagConstraints31);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("Pause");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						testrunner.wait();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						Application.getInstance().getLogger().log(e1);
					}
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("Stop");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					testrunner.stop();
				}
			});
		}
		return jButton3;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("Clear");
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					testLog.setText("");
				}
			});
		}
		return jButton4;
	}
} // @jve:decl-index=0:visual-constraint="0,0"

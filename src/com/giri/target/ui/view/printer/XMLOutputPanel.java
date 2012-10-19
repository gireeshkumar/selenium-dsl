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
package com.giri.target.ui.view.printer;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import jsyntaxpane.DefaultSyntaxKit;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.giri.target.core.Application;
import com.giri.target.ifc.IAssertion;
import com.giri.target.ifc.ICommand;
import com.giri.target.ifc.ICommandPrinter;
import com.giri.target.impl.Assertion;
import com.giri.target.impl.Command;
import com.giri.target.svr.BaseTestCaseRunner;
import com.giri.target.svr.XMLTestCaseRunner;

/**
 * @author Gireesh Kumar G
 * @Created Jul 28, 2009
 */
public class XMLOutputPanel extends JPanel implements ICommandPrinter {

	private static final long serialVersionUID = 1L;
	private JButton jButton = null;
	private JScrollPane jScrollPane = null;
	private Document xmldocument; // @jve:decl-index=0:
	private Element rootEle; // @jve:decl-index=0:
	private XMLOutputter xout = new XMLOutputter();
	private StringWriter sw;
	private JEditorPane xmlEditor;
	private JButton runtestBtt = null;
	private JPanel jPanel = null;
	private JEditorPane cmdXMLInputField = null;
	private JButton jButton1 = null;
	private SAXBuilder SAX_BUILDER = new org.jdom.input.SAXBuilder(); // @jve:decl-index=0:
	private BaseTestCaseRunner currentXMLTestRunner; // @jve:decl-index=0:
	private JScrollPane xmlInEditorSP;

	/**
	 * This is the default constructor
	 */
	public XMLOutputPanel() {
		super();
		initialize();
		Application.getInstance().addPrinters(this);
		xmlEditor.setContentType("text/xml");
		cmdXMLInputField.setContentType("text/xml");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
		gridBagConstraints12.gridx = 0;
		gridBagConstraints12.fill = GridBagConstraints.BOTH;
		gridBagConstraints12.gridwidth = 3;
		gridBagConstraints12.insets = new Insets(0, 5, 5, 5);
		gridBagConstraints12.gridy = 1;
		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.gridx = 0;
		gridBagConstraints11.gridy = 0;
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.fill = GridBagConstraints.BOTH;
		gridBagConstraints1.gridy = 2;
		gridBagConstraints1.weightx = 1.0;
		gridBagConstraints1.weighty = 1.0;
		gridBagConstraints1.gridwidth = 2;
		gridBagConstraints1.insets = new Insets(0, 5, 5, 5);
		gridBagConstraints1.gridx = 0;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.insets = new Insets(5, 0, 5, 0);
		gridBagConstraints.gridy = 0;
		this.setSize(300, 200);
		this.setLayout(new GridBagLayout());
		this.add(getJButton(), gridBagConstraints);
		this.add(getJScrollPane(), gridBagConstraints1);
		this.add(getRuntestBtt(), gridBagConstraints11);
		this.add(getJPanel(), gridBagConstraints12);
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("Save as XML Test case file");
		}
		return jButton;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTextPane());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTextPane
	 * 
	 * @return javax.swing.JTextPane
	 */
	private JEditorPane getJTextPane() {
		if (xmlEditor == null) {
			DefaultSyntaxKit.initKit();
			xmlEditor = new JEditorPane();
			// xmlEditor.setContentType("text/xml");
		}
		return xmlEditor;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.giri.gwat.ifc.ICommandPrinter#print(net.sourceforge.seleniumflexapi
	 * .cal.Command)
	 */
	@Override
	public void print(ICommand command) {
		addElement(toXMLElement(command));
	}

	private void addElement(Element element) {
		try {
			if (xmldocument == null) {
				xmlEditor.setContentType("text/xml");
				xmldocument = new Document();
				rootEle = new Element("testcase");
				rootEle.setAttribute("url", Application.getInstance()
						.getModel().getApplicationURL());
				rootEle.setAttribute("browser", Application.getInstance()
						.getModel().getProperty("browser"));
				xmldocument.setRootElement(rootEle);
			}
			rootEle.addContent(element);
			if (sw == null) {
				sw = new StringWriter();
			}
			sw.getBuffer().delete(0, sw.getBuffer().length());
			Format newFormat = Format.getPrettyFormat();
			xout.setFormat(newFormat);
			xout.output(xmldocument, sw);
			sw.flush();
			xmlEditor.setText(sw.getBuffer().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.giri.gwat.ifc.ICommandPrinter#print(net.sourceforge.seleniumflexapi
	 * .cal.Assertion)
	 */
	@Override
	public void print(IAssertion assertion) {
		addElement(toXMLElement(assertion));
	}

	/**
	 * @param command
	 * @return
	 */
	private Element toXMLElement(ICommand command) {
		final Element ele;
		if (command instanceof Assertion) {
			ele = new Element("assertion");
		} else {
			ele = new Element("command");
		}
		ele.setAttribute("command", command.getCommand());
		if (command.getLocator() != null) {
			ele.setAttribute("locator", command.getLocator());
		}
		if (command.getArgs() != null) {
			ele.setAttribute("args", command.getArgs());
		}

		if (command.getMessage() != null) {
			ele.setAttribute("message", command.getMessage());
		}

		if (command.getFlexAppName() != null) {
			ele.setAttribute("flexAppName", command.getFlexAppName());
		}

		ele.setAttribute("isFlex", command.isFlex() + "");
		ele.setAttribute("isEnsureVisible", command.isEnsureVisible() + "");

		return ele;
	}

	private ICommand toCommand(Element element) {
		final ICommand cmd;

		if (element.getName().equalsIgnoreCase("Assertion")) {
			cmd = new Assertion(element.getAttributeValue("command"), element
					.getAttributeValue("locator"));
		} else {
			cmd = new Command(element.getAttributeValue("command"), element
					.getAttributeValue("locator"));
		}

		if (element.getAttribute("args") != null) {
			cmd.setArgs((element.getAttributeValue("args")));
		}
		if (element.getAttribute("isFlex") != null) {
			cmd.setFlex(Boolean.parseBoolean((element
					.getAttributeValue("isFlex"))));
		}

		if (element.getAttribute("isEnsureVisible") != null) {
			cmd.setEnsureVisible(Boolean.parseBoolean((element
					.getAttributeValue("isEnsureVisible"))));
		}

		if (element.getAttribute("message") != null) {
			cmd.setMessage((element.getAttributeValue("message")));
		}

		if (element.getAttribute("flexAppName") != null) {
			cmd.setFlexAppName((element.getAttributeValue("flexAppName")));
		}

		return cmd;
	}

	private Element toXMLElement(IAssertion assertion) {
		Element ele = toXMLElement((ICommand) assertion);
		if (assertion.getExpectedValue() != null) {
			ele.setAttribute("expected", assertion.getExpectedValue());
		}
		return ele;
	}

	/**
	 * This method initializes runtestBtt
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getRuntestBtt() {
		if (runtestBtt == null) {
			runtestBtt = new JButton();
			runtestBtt.setText("Run Test");
			runtestBtt.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					runXMLTest();
				}
			});
		}
		return runtestBtt;
	}

	private void runXMLTest() {
		try {
			final String xmlText = xmlEditor.getText();
			try {
				xmlEditor.setContentType("text/xml");
				final StringReader xstrReader = new StringReader(xmlText);
				xmldocument = SAX_BUILDER.build(xstrReader);
				rootEle = xmldocument.getRootElement();
			} catch (IOException e) {
				e.printStackTrace();
			}

			final StringReader sr = new StringReader(xmlText);
			currentXMLTestRunner = new XMLTestCaseRunner();
			currentXMLTestRunner.runTest(sr, "xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJTextField(), BorderLayout.CENTER);
			jPanel.add(getJButton1(), BorderLayout.EAST);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JScrollPane getJTextField() {
		xmlInEditorSP = new JScrollPane();

		if (cmdXMLInputField == null) {
			DefaultSyntaxKit.initKit();
			cmdXMLInputField = new JEditorPane();
		}
		xmlInEditorSP.setViewportView(cmdXMLInputField);
		return xmlInEditorSP;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("Add Command");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						Document doc = SAX_BUILDER.build(new StringReader(
								cmdXMLInputField.getText()));
						ICommand cmd = toCommand(doc.getRootElement());
						Application.getInstance().getTestCommandRunner()
								.execute(cmd);
						Application.getInstance().printCommand(cmd);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return jButton1;
	}

}

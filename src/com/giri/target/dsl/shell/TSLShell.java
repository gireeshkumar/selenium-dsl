package com.giri.target.dsl.shell;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import jsyntaxpane.DefaultSyntaxKit;
import jxl.read.biff.File;

import com.giri.target.TARGet;
import com.giri.target.core.Application;
import com.giri.target.dsl.DSLTestCaseRunner;

public class TSLShell {

	static{
		DefaultSyntaxKit.initKit();
	}
	
	private JFrame jFrame = null;  //  @jve:decl-index=0:visual-constraint="8,-33"
	private JPanel jContentPane = null;
	private DSLTestCaseRunner runner;
	private JTabbedPane jTabbedPane = null;
	private ProjectDictionaryView projectDictionaryView = null;
	private TSLView TSLView = null;
	private TestDataView testDataView = null;
	private Boolean reset = true;  //  @jve:decl-index=0:
	private InputStream _projectDictStream;  //  @jve:decl-index=0:
	private InputStream _testData;  //  @jve:decl-index=0:
	
	private TSLShell(){
		
		try {
//			Application.getInstance().getAutomationTestServer().stop();
//			Application.getInstance().getAutomationTestServer().start();
		} catch (Exception e1) {
			Application.getInstance().getLogger().log(e1);
		}
		
		runner = new DSLTestCaseRunner();
	}
	
	
	private static TSLShell SELF;//
	private LogPanel logPanel = null;
	private JPanel jPanel = null;
	private JButton jButton = null;
	public static final TSLShell getInstance(){
		if(SELF == null){
			SELF = new TSLShell();
		}
		return SELF;
	}
	
	public void projectDictionary(InputStream inputStream){
		reset = true;
		_projectDictStream = inputStream;
	}
	public void testData(InputStream data){
		reset = true;
		_testData = data;
	}
	
	public void executeStatement(String statement) throws Exception{
		if(reset){
			runner.reset(_projectDictStream, _testData);
			reset = false;
		}
		runner.process(statement);
	}
	
	/**
	 * This method initializes jFrame	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setSize(new Dimension(653, 406));
			jFrame.setTitle("TSL Shell :: "+TARGet.BUILD);
			jFrame.setContentPane(getJContentPane());
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		return jFrame;
	}

	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setVgap(8);
			borderLayout.setHgap(5);
			jContentPane = new JPanel();
			jContentPane.setLayout(borderLayout);
			jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
//			jContentPane.add(getJPanel(), BorderLayout.NORTH);
			
			final String str = "Working Directory   :     "+System.getProperty("user.dir");
			
			jContentPane.add(new JLabel(str), BorderLayout.NORTH);
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
			jTabbedPane.addTab("TSL", getTSLView());
			jTabbedPane.addTab("Project Dictionary", getProjectDictionaryView());
			jTabbedPane.addTab("Test Data", getTestDataView());
			jTabbedPane.addTab("Logs", getLogPanel());
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes projectDictionaryView	
	 * 	
	 * @return com.giri.target.dsl.shell.ProjectDictionaryView	
	 */
	private ProjectDictionaryView getProjectDictionaryView() {
		if (projectDictionaryView == null) {
			projectDictionaryView = new ProjectDictionaryView();
		}
		return projectDictionaryView;
	}

	/**
	 * This method initializes TSLView	
	 * 	
	 * @return com.giri.target.dsl.shell.TSLView	
	 */
	private TSLView getTSLView() {
		if (TSLView == null) {
			TSLView = new TSLView();
		}
		return TSLView;
	}

	/**
	 * This method initializes testDataView	
	 * 	
	 * @return com.giri.target.dsl.shell.TestDataView	
	 */
	private TestDataView getTestDataView() {
		if (testDataView == null) {
			testDataView = new TestDataView();
		}
		return testDataView;
	}

	/**
	 * This method initializes logPanel	
	 * 	
	 * @return com.giri.target.dsl.shell.LogPanel	
	 */
	private LogPanel getLogPanel() {
		if (logPanel == null) {
			logPanel = new LogPanel();
		}
		return logPanel;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new ImagePanel();
//			jPanel.setSize(640,230);
			jPanel.setLayout(new GridBagLayout());
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TSLShell.show();
	}

	private static void show() {
		getInstance().getJFrame().setVisible(true);
	}

}

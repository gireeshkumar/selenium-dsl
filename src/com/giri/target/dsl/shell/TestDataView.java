package com.giri.target.dsl.shell;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;

public class TestDataView extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;
	private JButton jButton = null;
	private JButton saveButton = null;
	private JScrollPane jScrollPane = null;
	private JEditorPane editorInput = null;

	private File selectedFile;

	/**
	 * This is the default constructor
	 */
	public TestDataView() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
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
			jPanel = new JPanel();
			jPanel.setLayout(new BoxLayout(getJPanel(), BoxLayout.X_AXIS));
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton2(), null);
			jPanel.add(getSaveButton(), null);
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
			jButton.setText("Load Existing");
			jButton.addActionListener(loadActListener);
		}
		return jButton;
	}

	ActionListener loadActListener = new ActionListener() {

		public void actionPerformed(java.awt.event.ActionEvent e) {
			JFileChooser jf = new JFileChooser((selectedFile == null ? new File(".") : selectedFile.getParentFile())); // @jve:decl-index=0:
			jf.setFileFilter(new FileFilter() {
				@Override
				public String getDescription() {
					return "Project Data file (*.properties)";
				}

				@Override
				public boolean accept(File arg0) {
					return arg0.isDirectory() || arg0.getName().endsWith(".properties");
				}
			});
			jf.showOpenDialog(getThisContainer());
			selectedFile = jf.getSelectedFile();
			loadFileContent();
		}
	};
	private JButton jButton2 = null;

	private TestDataView getThisContainer() {
		return this;
	}

	private void loadFileContent() {
		if (selectedFile != null) {
			try {
				// jEditorPane.setPage(selectedFile.toURI().toURL());
				// jEditorPane.setContentType("text/xml");
				BufferedReader bReader = new BufferedReader(new FileReader(
						selectedFile));
				String line;
				StringBuffer sb = new StringBuffer();
				while ((line = bReader.readLine()) != null) {
					sb.append(line).append("\r");
				}
				editorInput.setText(sb.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method initializes saveButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = new JButton();
			saveButton.setEnabled(false);
			saveButton.setText("Save Changes");
		}
		return saveButton;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getEditorInput());
			editorInput.setContentType("text/properties");
			
			final StringBuilder sb = new StringBuilder();
			sb.append("#---- Testcase data ----\n");
			sb.append("# This data will be used inside the \n");
			sb.append("# project dictionary using '${<variable>}\n");
			sb.append("#\n");
			
			editorInput.setText(sb.toString());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes editorInput
	 * 
	 * @return javax.swing.JEditorPane
	 */
	private JEditorPane getEditorInput() {
		if (editorInput == null) {
			editorInput = new JEditorPane();
		}
		return editorInput;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("Apply");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String data = editorInput.getText();
					if(data != null && (data = data.trim()).length() > 0){
						ByteArrayInputStream bin = new ByteArrayInputStream(data.getBytes());
						TSLShell.getInstance().testData(bin);
					}
				}
			});
		}
		return jButton2;
	}

}

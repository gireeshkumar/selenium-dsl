package com.giri.target.dsl.shell;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.EditorKit;

import jsyntaxpane.DefaultSyntaxKit;
import jsyntaxpane.syntaxkits.GroovySyntaxKit;

/*
 * @author 
 * @date
 */
public class TSLView extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;
	private JPanel jPanel2 = null;
	private JButton executeButton = null;
	private JEditorPane commandInput = null;
	private JScrollPane jScrollPane = null;
	private JEditorPane tslEditor = null;
	private JPanel jPanel1 = null;
	private JButton saveButton = null;
	private MyStringBuilder stringBuilder;  //  @jve:decl-index=0:
	private JScrollPane jScrollPane1 = null;
	private JTextPane jTextPane = null;
	/**
	 * This is the default constructor
	 */
	public TSLView() {
		super();
		
		jsyntaxpane.DefaultSyntaxKit.initKit();
		
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
		this.add(getJPanel(), BorderLayout.SOUTH);
		this.add(getJScrollPane(), BorderLayout.CENTER);
		this.add(getJPanel1(), BorderLayout.NORTH);
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setVgap(2);
			borderLayout.setHgap(2);
			jPanel = new JPanel();
			jPanel.setLayout(borderLayout);
			jPanel.add(getJPanel2(), BorderLayout.SOUTH);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = GridBagConstraints.BOTH;
			gridBagConstraints11.gridy = 0;
			gridBagConstraints11.weightx = 1.0;
			gridBagConstraints11.weighty = 1.0;
			gridBagConstraints11.gridx = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 2;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.insets = new Insets(1, 1, 1, 1);
			gridBagConstraints.gridx = 0;
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GridBagLayout());
			jPanel2.add(getExecuteButton(), gridBagConstraints1);
			jPanel2.add(getJScrollPane1(), gridBagConstraints11);
		}
		return jPanel2;
	}
	private void initStringBuilderIfNull() {
		if(stringBuilder == null){
			stringBuilder = new MyStringBuilder();
			stringBuilder.addChangeListener(new ITextChangeListener() {
				@Override
				public void textChanged(MyStringBuilder myStringBuilder) {
					tslEditor.setText(stringBuilder.toString());
					saveButton.setEnabled((tslEditor.getText().trim().length() > 0));
				}
			});
		}
	}
	/**
	 * This method initializes executeButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getExecuteButton() {
		if (executeButton == null) {
			executeButton = new JButton();
			executeButton.setText("Execute");
			executeButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					executeStatement();
				}
			});
		}
		return executeButton;
	}
	private void executeStatement(){
		String statement = commandInput.getText();
		if(statement == null || (statement = statement.trim()).length() > 0){
			try{
				TSLShell.getInstance().executeStatement(statement);
				initStringBuilderIfNull();
				
				final String exStr = tslEditor.getText() + "\n" + statement;
				tslEditor.setText("");
				
				stringBuilder.delete(0,  stringBuilder.length());
				stringBuilder.append(exStr.trim());
				
				commandInput.setText("");
			}catch(Exception ex){
				JOptionPane.showMessageDialog(null, "Error in executing command\n"+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		}
	}

	/**
	 * This method initializes commandInput	
	 * 	
	 * @return javax.swing.JEditorPane	
	 */
	private JEditorPane getCommandInput() {
		if (commandInput == null) {
//			Sty
			commandInput = new JEditorPane();
			
			commandInput.addKeyListener(new java.awt.event.KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					String txt = commandInput.getText();
					executeButton.setEnabled((txt != null && (txt = txt.trim()).length() > 0));
					if(e.getKeyCode() == 10){
						commandInput.setText(txt);
						executeStatement();
					}
				}
			});
		}
		return commandInput;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTslEditor());
			tslEditor.setContentType("text/groovy");
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tslEditor	
	 * 	
	 * @return javax.swing.JEditorPane	
	 */
	private JEditorPane getTslEditor() {
		if (tslEditor == null) {
			tslEditor = new JEditorPane();
			tslEditor.addKeyListener(new java.awt.event.KeyAdapter() {   
				@Override
				public void keyReleased(java.awt.event.KeyEvent e) {
					saveButton.setEnabled((tslEditor.getText().trim().length() > 0));
					String text = tslEditor.getText();
					stringBuilder = null;
					initStringBuilderIfNull();
					stringBuilder.append(text);
				}
			});
		}
		return tslEditor;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BoxLayout(getJPanel1(), BoxLayout.X_AXIS));
			jPanel1.add(getSaveButton(), null);
			jPanel1.add(getLoadButton(), null);
			jPanel1.add(getRunButton(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes saveButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = new JButton();
			saveButton.setText("Save");
			saveButton.setEnabled(false);
			saveButton.addActionListener(fileSaveListener);
		}
		return saveButton;
	}
	private File selectedFile;
	ActionListener fileSaveListener = new ActionListener() {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			final JFileChooser jf = new JFileChooser((selectedFile == null ? new File(".") : selectedFile.getParentFile()));  //  @jve:decl-index=0:
			jf.setFileFilter(new FileFilter() {
				@Override
				public String getDescription() {
					return "TARGET DSL (*.tsl)";
				}
				@Override
				public boolean accept(File arg0) {
					return arg0.isDirectory() || arg0.getName().endsWith(".tsl");
				}
			});
			jf.showSaveDialog(getThisContainer());
			selectedFile = jf.getSelectedFile();
			saveFileContent();
		}
	};
	private JButton runButton = null;
	private JButton loadButton = null;
	private Component getThisContainer() {
		return this;
	}
	
	private void saveFileContent() {
		try {
			FileWriter fw = new FileWriter(selectedFile);
			fw.write("\n// TARGET - TSL (Automation test using TARGET Specific Language)-");
			fw.write("\n// Generated using TSL Shell ");
			fw.write("\n// @author\t"+systemUser());
			fw.write("\n// @date\t"+(new Date())+"\n\n");
			fw.write(tslEditor.getText());
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private String systemUser() {
		return System.getProperty("user.name");
	}

	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getCommandInput());
			commandInput.setContentType("text/groovy");
			
			EditorKit kit = commandInput.getEditorKit();
			GroovySyntaxKit gskit = (GroovySyntaxKit) kit;
			gskit.initKit();
			
			gskit.addAbbreviation("op", "open");
			
			commandInput.setText("open \"http://google.co.in\" in IE");
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes runButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getRunButton() {
		if (runButton == null) {
			runButton = new JButton();
			runButton.setText("Run");
			runButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try{
						String statement = tslEditor.getText();
						TSLShell.getInstance().executeStatement(statement);
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
			});
		}
		return runButton;
	}

	/**
	 * This method initializes loadButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getLoadButton() {
		if (loadButton == null) {
			loadButton = new JButton();
			loadButton.setText("Load");
			loadButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser jf = new JFileChooser((selectedFile == null ? new File(".") : selectedFile.getParentFile()));  //  @jve:decl-index=0:
					jf.setFileFilter(new FileFilter() {
						@Override
						public String getDescription() {
							return "TSL testcase (*.tsl)";
						}
						@Override
						public boolean accept(File arg0) {
							return arg0.isDirectory() || arg0.getName().endsWith(".tsl");
						}
					});
					jf.showOpenDialog(getThisContainer());
					selectedFile = jf.getSelectedFile();
					loadFileContent();
				}
			});
		}
		return loadButton;
	}
	private void loadFileContent() {
		try {
			loadFileContent(selectedFile == null ? null : new FileInputStream(selectedFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void loadFileContent(InputStream inputStream){
		if(inputStream != null){
			try {
				BufferedReader  bReader = new BufferedReader(new InputStreamReader(inputStream));
				String line;
				StringBuffer sb = new StringBuffer();
				while((line = bReader.readLine()) != null){
					sb.append(line).append("\r");
				}
				tslEditor.setText(sb.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

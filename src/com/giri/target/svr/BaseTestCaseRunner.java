/**
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
**/
package com.giri.target.svr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Properties;

import org.jdom.Document;

import com.giri.target.ifc.ICommand;
import com.giri.target.ifc.ICommandExeStatus;
import com.giri.target.ifc.IMultiFileTestDataCollector;
import com.giri.target.ifc.ITestDataCollector;
import com.giri.target.ifc.ITestListener;
import com.giri.target.svr.XMLTestCaseRunner.TCXMLFileFilter;
import com.giri.target.ui.view.XMLTestListener;

/**
 * @author U20463
 *
 */
public abstract class BaseTestCaseRunner {

	/**
	 * @param xmlTestListener 
	 * @param listener2 
	 * @param parent2
	 * @param data2
	 */
	public BaseTestCaseRunner(BaseTestCaseRunner l_parent, Properties l_data, ITestListener l_listener, XMLTestListener xmlTestListener) {
		parent = l_parent;
		data = l_data;
		listener = l_listener;
		dataCollector = new TestDataCollector();
		xmlTestReporter = xmlTestListener == null ? new XMLTestListener() : xmlTestListener;
		if(parent != null){
			parent.xmlTestReporter.add(this.xmlTestReporter);
			this.dataCollector = parent.dataCollector.newNestedCollector();
		}
	}
	protected static class TestDataCollectorDIR implements ITestDataCollector,
				IMultiFileTestDataCollector {
	
			private ArrayList<ITestDataCollector> nestedCollectors = null;
			private TestDataCollector currentDataCollector;
	
			public void endTest() {
				currentDataCollector.endTest();
			}
	
			public void executingCommand(ICommand cmd) {
				currentDataCollector.executingCommand(cmd);
			}
	
			/* (non-Javadoc)
			 * @see com.giri.target.ifc.ITestDataCollector#executingCommandFaild(com.giri.target.ifc.ICommand, java.lang.Throwable, boolean)
			 */
			@Override
			public void executingCommandFaild(ICommand cmd, Throwable error,
					boolean abort) {
				currentDataCollector.executingCommandFaild(cmd, error, abort);
			}
			
			public void executingCommandFaild(ICommand cmd) {
				currentDataCollector.executingCommandFaild(cmd);
			}
	
			public void executingCommandSuccess(ICommand cmd) {
				currentDataCollector.executingCommandSuccess(cmd);
			}
	
			public void executingCustomScript(String type, String flexAppName,
					String script, String result, Exception exception) {
				currentDataCollector.executingCustomScript(type, flexAppName,
						script, result, exception);
			}
	
			@Override
			public ArrayList<ITestDataCollector> getAllDataCollectors() {
				return nestedCollectors;
			}
	
			public Collection<? extends ICommandExeStatus> getCommandExeStatus() {
				return currentDataCollector.getCommandExeStatus();
			}
	
			public ITestDataCollector getParent() {
				return currentDataCollector.getParent();
			}
	
			public String getTestCaseSourceFile() {
				return currentDataCollector.getTestCaseSourceFile();
			}
	
			public Document getTestDocument() {
				return currentDataCollector.getTestDocument();
			}
	
			public long getTestEndTime() {
				return currentDataCollector.getTestEndTime();
			}
	
			public Throwable getTestFailedException() {
				return currentDataCollector.getTestFailedException();
			}
	
			public long getTestStartTime() {
				return currentDataCollector.getTestStartTime();
			}
	
			public String getTestURL() {
				return currentDataCollector.getTestURL();
			}
	
			@Override
			public int hashCode() {
				return currentDataCollector.hashCode();
			}
	
			@Override
			public ITestDataCollector newNestedCollector() {
				if (nestedCollectors == null) {
					nestedCollectors = new ArrayList<ITestDataCollector>();
				}
				final TestDataCollector dataCollector = new TestDataCollector();
				nestedCollectors.add(dataCollector);
				currentDataCollector = dataCollector;
				return dataCollector;
			}
	
			public void setTestCaseSourceFile(String filepath) {
				currentDataCollector.setTestCaseSourceFile(filepath);
			}
	
			public void setTestURL(String url) {
				currentDataCollector.setTestURL(url);
			}
	
			public void startTest(Document document) {
				currentDataCollector.startTest(document);
			}
	
			public void testFailed(Throwable th) {
				currentDataCollector.testFailed(th);
			}
	
			/* (non-Javadoc)
			 * @see com.giri.target.ifc.ITestDataCollector#setScreenshot(java.lang.String)
			 */
			public void setScreenshot(String path) {
				currentDataCollector.setScreenshot(path);
			}
	
			/* (non-Javadoc)
			 * @see com.giri.target.ifc.ITestDataCollector#getScreenshot()
			 */
			public String getScreenshot() {
				return currentDataCollector.getScreenshot();
			}
	
		}

	protected static class IndexFileListFilter implements FilenameFilter {
	
			private final ArrayList<String> nameInIndexFile;
	
			IndexFileListFilter(ArrayList<String> names) {
				this.nameInIndexFile = names;
			}
	
			@Override
			public boolean accept(File dir, String name) {
				return (nameInIndexFile.contains(name));
			}
	
		}

	protected final BaseTestCaseRunner parent;
	protected final Properties data;
	protected ITestDataCollector dataCollector;
	protected boolean isFlex = false;
	protected boolean isEnsureVisible = true;
	protected String flexAppName = null;
	protected ITestListener listener;
	protected final XMLTestListener xmlTestReporter;
	private File _currentDir;
	protected File currentDataFile;
	/**
	 * @param scriptToExecute
	 * @return
	 */
	protected String trimLines(String scriptToExecute) throws Exception {
		StringReader reader = new StringReader(scriptToExecute);
		BufferedReader  bReader = new BufferedReader(reader);
		StringBuffer sb = new StringBuffer();
		String line;
		while((line = bReader.readLine()) != null){
			line = line.trim();
			sb.append(line).append('\n');
		}
		return sb.toString();
	}
	public ITestDataCollector getDataCollector() {
		return dataCollector;
	}
	public XMLTestListener getXmlTestReporter() {
		return xmlTestReporter;
	}
	/**
	 * @return
	 */
	protected File getCurrentDir() {
		if (_currentDir == null) {
			_currentDir = new File(".");
		}
		return _currentDir;
	}
	protected ArrayList<String> getFileNamesFromIndexFile(File indexFile) throws Exception {
		BufferedReader breader = new BufferedReader(new FileReader(indexFile));
		ArrayList<String> names = new ArrayList<String>();
		String line;
		while ((line = breader.readLine()) != null) {
			if ((line = line.trim()).length() > 0 && !line.startsWith("#")) {
				names.add(line);
			}
		}
		return names;
	}
	/**
	 * 
	 */
	private void loadGlobalProperties() throws Exception {
		if (_currentDir != null) {
			File gprop = new File(_currentDir, "global.properties");
			if (gprop.exists()) {
				Properties props = new Properties();
				props.load(new FileInputStream(gprop));
				data.putAll(props);
			}
		}
	}
	protected void mergeProperties(Properties loadedProps) throws Exception {
		int len = loadedProps.size();
		if (len == 0) {
			return;
		}
		System.out.println(loadedProps);
	
		final Properties tempPropList = new Properties();
		tempPropList.putAll(data);
		tempPropList.putAll(loadedProps);
	
		final Enumeration<Object> keys = loadedProps.keys();
		String key;
		String value;
		String newValue;
		while (keys.hasMoreElements()) {
			key = keys.nextElement().toString();
			value = (String) loadedProps.get(key);
			newValue = processValue(value, tempPropList);
			data.put(key, newValue);
			tempPropList.put(key, newValue);
		}
		System.out.println(data);
		TestDataStore.getInstance().setCurrentTestInputData(data);
	}
	
	protected ArrayList<File> orderFiles(final File[] filesToExecute, final ArrayList<String> fileOrder) {
		final ArrayList<File> fileList = new ArrayList<File>(
				filesToExecute.length);
		int len = (fileOrder == null ? 0 : fileOrder.size());
	
		File file;
		String fileName;
		final int filelen = filesToExecute.length;
	
		for (int i = 0; i < len; i++) {
			fileName = fileOrder.get(i);
	
			for (int j = 0; j < filelen; j++) {
				file = filesToExecute[j];
				if (file != null && file.getName().endsWith(fileName)) {
					fileList.add(file);
					filesToExecute[j] = null;
					break;
				}
			}
		}
	
		for (File tfile : filesToExecute) {
			if (tfile != null) {
				fileList.add(tfile);
			}
		}
		return fileList;
	}
	protected void setCurrentDir(File dir) throws Exception {
		this._currentDir = dir;
		loadGlobalProperties();
	}
	/**
	 * @param testListner
	 */
	public void setListener(ITestListener testListner) {
		this.listener = testListner;
	}
	protected void executeTestCases(TestDataCollectorDIR dataCol, File[] filesToExecute, ArrayList<String> fileOrder)
			throws Exception {
				// TODO Auto-generated method stub
				final ArrayList<File> fileList = orderFiles(filesToExecute, fileOrder);
				final int len = (fileList == null ? 0 : fileList.size());
			
				for (int i = 0; i < len; i++) {
					this.dataCollector = dataCol.newNestedCollector();
					runTest(fileList.get(i));
				}
			}
	/**
	 * @param curFile
	 */
	public void runTest(File curFile) throws Exception {
		this.currentDataFile = curFile;
		this.dataCollector.setTestCaseSourceFile(null);
		if (curFile.isDirectory()) {
			final TestDataCollectorDIR dataCol = new TestDataCollectorDIR();
			this.dataCollector = dataCol;
			try {
				// TODO loading ALL XML test cases from this directory
				// Looking for "index.list" file
				final ArrayList<String> fileOrder;
				final File indexFile = new File(curFile, "index.list");
				final FilenameFilter filter;
				if (indexFile.exists()) {
					fileOrder = getFileNamesFromIndexFile(indexFile);
					filter = new IndexFileListFilter(fileOrder);
				} else {
					// load all "tc.xml" files
					fileOrder = null;
					filter = new TCXMLFileFilter();
				}
				File[] filesToExecute = curFile.listFiles(filter);
				executeTestCases(dataCol, filesToExecute, fileOrder);
			} catch (Exception e) {
				throw e;
			} finally {
				this.dataCollector = dataCol;
			}
		} else {
			final String filename = curFile.getName();
			if (filename.endsWith(".zip") || filename.endsWith(".jar")) {
				// TODO - load xml test cases from compressed files
			} else{
				final File curdir = curFile.getParentFile();
				setCurrentDir(curdir);
				this.dataCollector.setTestCaseSourceFile(curFile.getAbsolutePath());
				final String ext = filename.substring(filename.lastIndexOf(".")+1, filename.length());
				
				/*
				 * final String projDictFileAddr = System.getProperty("project.dictionary");
					final String testDataFileAddr = System.getProperty("test.data");
				 * curFile
				 */
				final String namepart = filename.substring(0,filename.lastIndexOf("."));
				
				/*
				 * Try to load Project XML and test case properties file from same location
				 */
				final String xfileStr = namepart+".xml";
				final String prpfileStr = namepart+".properties";
				
				final String projDictFileAddr = System.getProperty("project.dictionary");
				final String testDataFileAddr = System.getProperty("test.data");
				
				final File xfile = new File(curdir, xfileStr);
				if(xfile.exists() && (projDictFileAddr == null || projDictFileAddr.length() == 0)){
					System.setProperty("project.dictionary", xfile.getAbsolutePath());
				}
				
				final File prpfile = new File(curdir, prpfileStr);
				if(prpfile.exists() && (testDataFileAddr == null || testDataFileAddr.length() == 0)){
					System.setProperty("test.data", prpfile.getAbsolutePath());
				}
				System.out.println(curFile.getAbsolutePath());
				System.out.println(curFile.exists());
				runTest(new FileInputStream(curFile), ext);
			}
		}
	}
	
	
	
	public abstract void runTest(Reader reader,  String ext) throws Exception;
	public abstract void runTest(InputStream stream, String ext) throws Exception;
	/**
	 * @param attributeValue
	 * @return
	 * @throws Exception
	 */
	protected String processValue(String attributeValue, Object context) throws Exception {
		if (attributeValue == null || attributeValue.length() == 0) {
			return attributeValue;
		}
		return Utils.processValue(attributeValue, data, context);
	}
}

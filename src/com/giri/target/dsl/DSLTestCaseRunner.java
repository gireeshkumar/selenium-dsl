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
package com.giri.target.dsl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import jxl.Workbook;
import jxl.write.WritableWorkbook;

import org.jdom.Element;

import com.giri.target.common.Utils;
import com.giri.target.core.Application;
import com.giri.target.dsl.dict.DictionaryItem;
import com.giri.target.dsl.dict.ProjectDictionary;
import com.giri.target.dsl.ifc.IExecutableStatement;
import com.giri.target.dsl.ifc.IKeyword;
import com.giri.target.dsl.ifc.INonExecutableStatement;
import com.giri.target.dsl.ifc.IParameter;
import com.giri.target.dsl.ifc.IStatement;
import com.giri.target.dsl.ifc.IStatementExecutor;
import com.giri.target.dsl.ifc.ITestDataHolder;
import com.giri.target.dsl.keywords.IBrowserTypes;
import com.giri.target.dsl.keywords.KeywordClose;
import com.giri.target.dsl.keywords.KeywordInclude;
import com.giri.target.dsl.keywords.KeywordOpen;
import com.giri.target.ifc.ICommand;
import com.giri.target.ifc.IServerInfo;
import com.giri.target.impl.Command;
import com.giri.target.svr.BaseTestCaseRunner;
import com.giri.target.svr.SeleniumTestRunner;
import com.giri.target.svr.StmtWrapCommand;
import com.giri.target.svr.ThreadContext;
import com.giri.target.svr.XMLTestCaseRunner;

import de.susebox.jtopas.Token;

/**
 * @author U20463
 *
 */
public class DSLTestCaseRunner extends XMLTestCaseRunner implements IStatementExecutor, ITestDataHolder {

	private TARGetDSL dsl;
	private SeleniumTestRunner testRunner;
	private IServerInfo sinfo;
	private ProjectDictionary projectDictionary;
	/**
	 * @param lParent
	 * @param lData
	 * @param lListener
	 * @param xmlTestListener
	 */
	public DSLTestCaseRunner(BaseTestCaseRunner lParent) {
		super(lParent);
	}
	public DSLTestCaseRunner() {
		super();
	}

	/* (non-Javadoc)
	 * @see com.giri.target.svr.BaseTestCaseRunner#runTest(java.io.Reader)
	 */
	@Override
	public void runTest(Reader reader, String extension) throws Exception {
		runTest(new BufferedReader(reader), extension, true);
	}
	/* (non-Javadoc)
	 * @see com.giri.target.svr.BaseTestCaseRunner#runTest(java.io.InputStream)
	 */
	@Override
	public void runTest(InputStream stream, String extension) throws Exception {
		runTest(stream, extension, true);
	}
	
	public void runTest(InputStream stream, String extension, boolean reset) throws Exception {
		if(extension.equalsIgnoreCase("xls")){
			final Workbook wbin = Workbook.getWorkbook(stream);
			final WritableWorkbook writableWB = Workbook.createWorkbook(this.currentDataFile, wbin);
			
			
			runExcelTest(wbin, writableWB, reset);
			
//			final File outputfile = new File("./report.xls");			
//			writableWB.setOutputFile(outputfile);
//			writableWB.write();
//			writableWB.close();
//			
//			
//			
//			System.out.println("Report excel generated @ "+outputfile.getAbsolutePath());
		}else{
			runTest(new BufferedReader(new InputStreamReader(stream)), extension, reset);
		}
	}
	
	/**
	 * @param workbook
	 * @param writableWorkbook 
	 */
	private void runExcelTest(Workbook workbook, WritableWorkbook writableWorkbook, boolean reset) throws Exception {
		if(reset){
			init();
		}
		
		dsl.process(workbook, writableWorkbook, this);
	}
	public void runTest(BufferedReader reader, String extension, boolean reset) throws Exception {
		if(reset){
			init();
		}
		dsl.process(reader, this);
	}
	public void process(String text) throws Exception{
		BufferedReader breader = new BufferedReader(new StringReader(text));
		String line;
		while((line = breader.readLine()) != null){
			dsl.process(line, this);
		}
		
	}
	public void merge(Properties prop) throws Exception{
		super.mergeProperties(prop);
	}
	public Object get(String name){
		return super.data.get(name);
	}
	
	/**
	 * 
	 */
	private void init()  throws Exception {
		final String projDictFileAddr = System.getProperty("project.dictionary");
		final String testDataFileAddr = System.getProperty("test.data");
		
		reset(projDictFileAddr, testDataFileAddr);
	}
	public void reset(final String projDictFileAddr, final String testDataFileAddr)  throws Exception {
		

		final File projDictFile = (projDictFileAddr != null && (projDictFileAddr.trim()).length() > 0) ? new File(projDictFileAddr) : null;
		final File testDataFile = (testDataFileAddr != null && testDataFileAddr.trim().length() > 0 && testDataFileAddr.lastIndexOf(".properties") != -1) ? new File(testDataFileAddr) : null;
		
		boolean invalidinput_exit = false;
		if(projDictFile != null){
			System.err.println(projDictFile.getAbsolutePath());
			if(!projDictFile.exists()){
				invalidinput_exit = true;
				System.err.println("File '"+projDictFile.getAbsolutePath()+"' does not exist");
			}
		}
		
		if(testDataFile != null){
			System.err.println(testDataFile.getAbsolutePath());
			if(!testDataFile.exists()){
				invalidinput_exit = true;
				System.err.println("File '"+testDataFile.getAbsolutePath()+"' does not exist");
			}
		}
		
		if(invalidinput_exit){
			System.exit(0);
		}
		
		
		
		
		
		final InputStream dictionaryStream;
		if(projDictFile != null){
			dictionaryStream = new FileInputStream(projDictFile);
		}else{
			System.err.println("No project dictionary definied");
			dictionaryStream = null;
		}
		
		FileInputStream testDataStream;
		// will handle only '.properties' files
		if(testDataFile != null){
			testDataStream = new FileInputStream(new File(testDataFileAddr));
		}else{
			testDataStream = null;
			if(testDataFileAddr != null && testDataFileAddr.lastIndexOf(".properties") != -1){
				System.err.println("Data is not in a standard input type '.properties' (Excel files will be processed later)");
			}
			else{
				System.err.println("No test data provided");
			}
			
		}
		
		reset (dictionaryStream, testDataStream);
	}
	public void reset(InputStream projectDictStream, InputStream testData)  throws Exception {
		if(testData != null){
			loadData(testData, null);
		}
		
		testRunner = SeleniumTestRunner.getInstance();
		sinfo = Application.getInstance().getModel().getServerInfo();
		projectDictionary = (projectDictStream == null ? null : new ProjectDictionary(projectDictStream));
		
		dsl = new TARGetDSL(this, projectDictionary);
	}
	private static InputStream newStream(String string) {
		return DSLTestCaseRunner.class.getClassLoader().getResourceAsStream(string);
	}

	public void execute(IStatement stmt) throws Exception {
		ThreadContext.set("stmt", stmt);
		if(stmt instanceof IExecutableStatement){
			IExecutableStatement ieStmt = (IExecutableStatement) stmt;
			execute(ieStmt);
		} else {
			INonExecutableStatement nstmt = (INonExecutableStatement) stmt;
			if (nstmt.isComment()) {
				// use for printing only or ignore
			} else {

				ICommand cmd = nstmt.toICommand();
				
				final String key = dsl.generateDictionaryItemName(nstmt);
				DictionaryItem dItem = dsl.getDictionaryItem(key);
				
				if (cmd.isFlex() && dItem == null) {
					System.out.println("Try invoking flex function directly");
					testRunner.execute(new StmtWrapCommand(cmd, nstmt));
					
				} else {

					// check if this can be executed. Does this has a dictionary
					// definition ?
					// final String line = nstmt.getOrginalStatement();
					// build data set
					IParameter[] params = nstmt.getParameters();
					if (params != null) {
						for (IParameter param : params) {
							data.put("param" + param.index(), param.value());
						}
					}

					final List<Token> tkns = nstmt.getTokens();
					if (tkns != null) {
						int idx = 0;
						for (Token token : tkns) {
							data.put("token" + idx, token.getImage());
							idx++;
						}
					}

					
					if (dItem == null) {
						// does it has exact name Dict item ?
						if (tkns != null && tkns.size() > 0) {
							String name = tkns.get(0).getImage();
							dItem = dsl.getDictionaryItem(name);
						}
					}
					if (dItem != null) {
						final Element root = dItem.element;
						if ("tsl".equalsIgnoreCase(root
								.getAttributeValue("type"))) {
							String contentString = root.getValue();
							if (contentString != null
									&& (contentString = contentString.trim())
											.length() > 0) {
								final BufferedReader bread = new BufferedReader(
										new StringReader(contentString));
								String line = null;
								while ((line = bread.readLine()) != null) {
									final String scriptToExecute = processValue(
											line, null);
									process(scriptToExecute);
								}
								bread.close();
							}
							contentString = null;
						} else {
							final List<Element> nodes = root.getChildren();
							if (nodes != null) {
								for (Element ele : nodes) {
									Element elec = (Element) ele.clone();
									if(cmd != null && cmd.isFlex()){
										elec.setAttribute("flexAppName", cmd.getFlexAppName());
									}
									super.execute(testRunner, elec );
								}
							}
						}
					} else {
						// No dictionary item
						System.out
								.println("Let me fallback to selenium method call");
						// get the first word and match against the selenium
						// methods
						if (tkns != null && tkns.size() > 0) {
							final Token token = tkns.get(0);
							if (testRunner.hasMatchingMethod(token.getImage())) {
								// call matching selenium method
								cmd = new Command("SELENIUM", token.getImage());
								cmd.setAttribute(IStatement.NAME, stmt);

								final IParameter[] prms = stmt.getParameters();
								final String locator = Utils.findLocatorString(
										prms, stmt, false);
								final ArrayList<String> args = new ArrayList<String>();
								if (locator != null) {
									args.add(locator);
								}

								if (prms != null) {
									boolean skip = true;
									for (IParameter param : prms) {
										if (skip
												&& param.value()
														.equals(locator)) {
											// skip, only once
											skip = false;
											continue;
										}
										args.add(param.value());
									}
								}
								cmd.setAttribute(
										"parameters",
										(args.size() == 0 ? null
												: args.toArray(new String[args
														.size()])));
								testRunner.execute(cmd);
							}
						}
					}
				}

			}
		}
	}
	
	public void execute(IExecutableStatement ieStmt) throws Exception{
		try{
			super.executing = true;
			if(ieStmt.getMasterKeyword().name() == KeywordInclude.NAME){
				IParameter[] params = ieStmt.getParameters();
				if(params == null || params.length == 0){
					throw new Exception("Includet command require file location and type to be passed as parameter");
				}
				
				final String fileloc = (params.length >= 2 ? params[1].value() : params[0].value());
				
				if(params.length == 1 || "tsl".equals(params[0].value())){
					includeTSL(new File(fileloc));
				}
				else if(params.length == 1 || "data".equals(params[0].value())){
					includeData(new File(fileloc));
				}
				else if(params.length == 1 || "dict".equals(params[0].value()) || "dictionary".equals(params[0].value())){
					includeDictionary(new File(fileloc));
				}
				
				
				
			}
			else if(ieStmt.getMasterKeyword().name() == KeywordOpen.NAME){
				String url = getUrl(ieStmt);
				if(testRunner.isStarted()){
					testRunner.open(url);
				}
				else{
					String btype = getBrowserType(ieStmt);
					testRunner.start(sinfo.getServerHost(), ""+ sinfo.getServerPort(), btype, url, null);
				}
			}else if(ieStmt.getMasterKeyword().name() == KeywordClose.NAME){
				testRunner.stop();
			}else{
				ICommand cmd = ieStmt.toICommand();
				testRunner.execute(cmd);
			}
		}finally{
			super.executing = false;
		}
	}
	private void includeDictionary(File file) throws Exception {
		if(!file.exists()){
			final String msg = "Included dictionary file '"+file.getAbsolutePath()+"' doesn't exist";
			System.err.println(msg);
			throw new Exception(msg);
		}else{
			projectDictionary.includeXML(file);
		}
	}
	private void includeData(File file) throws Exception {
		if(!file.exists()){
			final String msg = "Included test data file '"+file.getAbsolutePath()+"' doesn't exist";
			System.err.println(msg);
			throw new Exception(msg);
		}else{
			loadData(new FileInputStream(file), null);
		}
		
		
	}
	private void includeTSL(File file) throws Exception {
		if(!file.exists()){
			final String msg = "Included testcase file '"+file.getAbsolutePath()+"' doesn't exist";
			System.err.println(msg);
			throw new Exception(msg);
		}else{
			final String filename = file.getName();
			final String ext = filename.substring(filename.lastIndexOf(".")+1, filename.length());
			runTest(new FileInputStream(file), ext, false);
		}
	}
	/**
	 * @param ieStmt
	 * @return
	 */
	private String getBrowserType(IExecutableStatement ieStmt) {
		ArrayList<IKeyword> others = ieStmt.getOtherKeywords();
		String type = IBrowserTypes.TYPE.FIREFOX.value();
		IBrowserTypes btype = null;
		if(others != null){
			for(IKeyword key : others){
				if(key instanceof IBrowserTypes){
					btype = (IBrowserTypes) key;
					break;
				}
			}
		}
		
		if(btype != null){
			type = btype.getType().value();
		}else{
			// find from parameters
			final IParameter[] params = ieStmt.getParameters();
			final IParameter[] otherParams = ieStmt.getOtherParameters();
			IParameter browseParam = findBrowserTypeParam(params);
			if(browseParam == null){
				browseParam = findBrowserTypeParam(otherParams);
			}
			
			if(browseParam != null){
				type = browseParam.value();
			}
		}
		return type;
	}
	
	private IParameter findBrowserTypeParam(IParameter[] params) {
		IParameter browseParam = null;
		if(params != null){
			for (IParameter iParameter : params) {
				if(iParameter.value().startsWith("*")){ // detect browser type
					browseParam = iParameter;
					break;
				}
			}
		}
		return browseParam;
	}
	/**
	 * @param ieStmt
	 * @return
	 * @throws Exception 
	 */
	private String getUrl(IExecutableStatement ieStmt) throws Exception {
		IParameter[] params = ieStmt.getParameters();
		if(params == null || params.length == 0){
			throw new Exception("Test case URL not provided");
		}
		String url = null;
		if(params.length == 1){
			url = params[0].value();
		}else{
			for(IParameter param : params){
				String val = param.value();
				if((val.indexOf("http://") != -1) || (val.indexOf("https://") != -1)){
					url = param.value();
				}
			}
		}
		if(url == null){
			throw new Exception("Test case URL not provided/not found.");
		}
		return url;
	}

}

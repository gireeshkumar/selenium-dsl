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
package com.giri.target.svr;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jdom.CDATA;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.giri.target.ifc.IAssertion;
import com.giri.target.ifc.ICommand;
import com.giri.target.ifc.ICommandExeStatus;
import com.giri.target.ifc.ICommandExeStatus.ExeStatus;
import com.giri.target.ifc.IMultiFileTestDataCollector;
import com.giri.target.ifc.ITestDataCollector;

public class XMLWriter {

	public static Document toXML(File parentdir, ITestDataCollector dataCollector) {
		XMLWriter xw = new XMLWriter(parentdir, dataCollector);
		return xw.toDocument();
	}

	public static void write(ITestDataCollector dataCollector, File parentdir, Writer writer)
			throws IOException {
		Document document = toXML(parentdir, dataCollector);
		XMLOutputter xout = new XMLOutputter(Format.getPrettyFormat());
		xout.output(document, writer);
		writer.flush();
	}

	private ITestDataCollector rootDataCollector;
	private Document doc;
	private File parentdir;

	public XMLWriter(File parentdir, ITestDataCollector dataCollector) {
		this.rootDataCollector = dataCollector;
		this.parentdir = parentdir;
		final Element root;
		root = new Element("TestReports");
		if (rootDataCollector instanceof IMultiFileTestDataCollector) {
			ArrayList<ITestDataCollector> dataCols = ((IMultiFileTestDataCollector) rootDataCollector)
					.getAllDataCollectors();
			int len = (dataCols == null ? 0 : dataCols.size());
			for (int i = 0; i < len; i++) {
				root.addContent(toXMLImpl(dataCols.get(i)));
			}
		} else {
			root.addContent(toXMLImpl(dataCollector));
		}
		doc = new Document(root);
	}

	private Element addElement(Element root, String name, String text) {
		return addElement(root, name, text, false);
	}

	private Element addElement(Element root, String name, String text,	boolean useCdata) {
		Element element = new Element(name);
		root.addContent(element);
		if(text != null){
			if (useCdata) {
				element.addContent(new CDATA(text));
			} else {
				element.setText(text);
			}
		}
		return element;
	}

	private Document toDocument() {
		return doc;
	}

	private Element toXMLImpl(ITestDataCollector dataCollector) {
		Element root = new Element("TestReport");
		if (dataCollector.getTestCaseSourceFile() != null) {
			root.setAttribute("source", dataCollector.getTestCaseSourceFile());
		}

		// has any screenshots , move to this location
		final String ssFilePath = dataCollector.getScreenshot();
		if(ssFilePath != null){
			final File testsource = new File(dataCollector.getTestCaseSourceFile());
			String sourcename = testsource.getName();
			sourcename = sourcename.substring(0, sourcename.indexOf(".xml"));
			
			final File file = new File(ssFilePath);
			final File destFile = new File(parentdir, sourcename+".screenshot.png");
			file.renameTo(destFile);
			System.out.println(destFile.getAbsolutePath());
			addElement(root, "screenshot", destFile.getAbsolutePath());
		}
		
		final Document testcaseDoc = dataCollector.getTestDocument();
		final String name = testcaseDoc == null ? null : testcaseDoc.getRootElement().getAttributeValue("name");
		final String desc = testcaseDoc == null ? null : testcaseDoc.getRootElement().getAttributeValue("desc");

		root.setAttribute("testCaseName", (name == null ? "not available": name));
		root.setAttribute("testCaseDesc", (desc == null ? "not available": desc));

		Date date = Calendar.getInstance().getTime();
		Element datele = addElement(root, "date", date.toLocaleString());
		datele.setAttribute("value", date.getTime() + "");

		final long starTime = dataCollector.getTestStartTime();
		final long endTime = dataCollector.getTestEndTime();

		// Get elapsed time in milliseconds
		final long elapsedTimeMillis = endTime - starTime;
		// Get elapsed time in seconds
		final float elapsedTimeSec = elapsedTimeMillis / 1000F;
		// Get elapsed time in minutes
		final float elapsedTimeMin = elapsedTimeMillis / (60 * 1000F);
		// Get elapsed time in hours
		final float elapsedTimeHour = elapsedTimeMillis / (60 * 60 * 1000F);

		addElement(root, "elapsedTime", elapsedTimeHour + ":" + elapsedTimeMin
				+ ":" + elapsedTimeSec);

		final List<ICommandExeStatus> statusList = (dataCollector == null || dataCollector.getCommandExeStatus() == null) ? null : new ArrayList<ICommandExeStatus>(dataCollector.getCommandExeStatus());
		final int len = (statusList == null ? 0 : statusList.size());
		if(statusList != null){
			Collections.sort(statusList);
		}
		
		ICommandExeStatus exeSts;
		ICommand cmd;
		IAssertion atn;
		int astnCount = 0;
		int cmdCount = 0;
		if (len > 0) {
			final Iterator<? extends ICommandExeStatus> its = statusList.iterator();
			while (its.hasNext()) {
				exeSts = its.next();
				cmd = exeSts.getCommand();
				
				Element statusEle = null;
				if (cmd instanceof IAssertion) {
					atn = (IAssertion) cmd;
					astnCount++;
					statusEle = addElement(root, "assertion", null);
				} else {
					cmdCount++;
					statusEle = addElement(root, "command", null);
				}
				
				if(cmd.getLabel() != null){
					statusEle.setAttribute("label", cmd.getLabel());
				}
				if(exeSts.getStatus() != null){
					statusEle.setAttribute("status", exeSts.getStatus().toString());
					
					if(exeSts.getStatus() == ExeStatus.FAILED){
						final Throwable th = exeSts.getError();
						if(th != null){
							Element thiselement = null;
							if((cmd instanceof IAssertion)){
								thiselement = addElement(statusEle, "assertFailedException", null);
							}else{
								thiselement = addElement(statusEle, "assertFailedException", null);
							}
							thiselement.setAttribute("message", th.getLocalizedMessage());
							addError(thiselement, th, "exception");
							writeTo(thiselement, cmd);
						}
					}
				}
				
			}
		}
		addElement(root, "numberOfCommands", cmdCount + "");
		addElement(root, "numberOfAssertions", astnCount + "");

		final Throwable failedExpection = dataCollector	.getTestFailedException();
		if (failedExpection != null) {
			
			addError(root, failedExpection, "testFailedException");
			
			root.setAttribute("status", "FAILED");
		} else {
			root.setAttribute("status", "SUCCESS");
		}
		return root;
	}
	
	/**
	 * @param ele
	 * @param cmd
	 */
	private void writeTo(Element ele, ICommand cmd) {
		ele.addContent(toElement(cmd));
	}

	/**
	 * @param cmd
	 * @return
	 */
	private Element toElement(ICommand cmd) {
		// use reflection TODO - fix this
		Element ele = new Element("content");
		try {
			Object orgValue = cmd.getClass().getMethod("getOrginalData", null).invoke(cmd,null);
			ele = (Element) orgValue;
			
			ele = (Element) ele.clone();
			ele.detach();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ele;
	}

	/**
	 * @param ele
	 * @param cmd
	 */
	private void writeTo(Element ele, IAssertion cmd) {
		ele.addContent(toElement(cmd));
	}

	private Element addError(Element root, Throwable error, String element){
		final StringWriter sw = new StringWriter();
		error.printStackTrace(new PrintWriter(sw));
		return addElement(root, element , sw.getBuffer().toString(), true);
	}
}

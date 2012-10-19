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

import java.util.ArrayList;

import org.jdom.Document;
import org.jdom.Element;

import com.giri.target.ifc.ITestListener;

/**
 * @author Gireesh Kumar G
 * @Created Aug 28, 2009
 */
public class XMLTestListener implements ITestListener {

	private Document document;
	private Element rootElement;
	private ArrayList<XMLTestListener> childListeners;

	public XMLTestListener() {
		rootElement = new Element("TestReport");
		document = new Document(rootElement);
	}

	/*
	 * (non-Javadoc)
	 * @see com.giri.gwat.ifc.ITestListener#asserting(java.lang.String,
	 * com.giri.gwat.ifc.ITestListener.TEST_STATUS)
	 */
	@Override
	public void asserting(String message, TEST_STATUS status) {
		Element element = new Element("asserting");
		rootElement.addContent(element);
		element.setAttribute("status", status.name());
	}

	/*
	 * (non-Javadoc)
	 * @see com.giri.gwat.ifc.ITestListener#endTest(java.lang.String)
	 */
	@Override
	public void endTest(String message) {
		Element element = new Element("endtest");
		rootElement.addContent(element);
		element.setAttribute("message", message);
	}

	/*
	 * (non-Javadoc)
	 * @see com.giri.gwat.ifc.ITestListener#log(java.lang.String)
	 */
	@Override
	public void log(String message) {
		Element element = new Element("log");
		rootElement.addContent(element);
		element.setAttribute("message", message);
	}

	/*
	 * (non-Javadoc)
	 * @see com.giri.gwat.ifc.ITestListener#logError(java.lang.String)
	 */
	@Override
	public void logError(String message) {
		Element element = new Element("error");
		rootElement.addContent(element);
		element.setAttribute("message", message);
	}

	/*
	 * (non-Javadoc)
	 * @see com.giri.gwat.ifc.ITestListener#logError(java.lang.Throwable)
	 */
	@Override
	public void logError(Throwable ex) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see com.giri.gwat.ifc.ITestListener#startTest(java.lang.String)
	 */
	@Override
	public void startTest(String message) {
		Element element = new Element("starttest");
		rootElement.addContent(element);
		element.setAttribute("message", message);
	}

	/*
	 * (non-Javadoc)
	 * @see com.giri.gwat.ifc.ITestListener#testing(java.lang.String,
	 * com.giri.gwat.ifc.ITestListener.TEST_STATUS)
	 */
	@Override
	public void testing(String message, TEST_STATUS status) {
		Element element = new Element("testing");
		rootElement.addContent(element);
		element.setAttribute("status", status.name());
	}

	public void add(XMLTestListener xmlTestReporter) {
		if (childListeners == null) {
			childListeners = new ArrayList<XMLTestListener>();
		}
	}

	public ArrayList<XMLTestListener> getChildListeners() {
		return childListeners;
	}

	public Document getDocument() {
		return document;
	}

	public Element getRootElement() {
		return rootElement;
	}

}

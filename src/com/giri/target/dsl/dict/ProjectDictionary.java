package com.giri.target.dsl.dict;

import java.io.InputStream;

import org.jdom.Element;
import org.jdom.xpath.XPath;


public class ProjectDictionary extends Metadata{

	public ProjectDictionary(final InputStream dictionaryStream)
			throws Exception {
		super();
		load(dictionaryStream);
	}

	// 	<flexapp name="_flexcalc" locator="FlexCalculator"/>
	
	protected XPath getFlexAppXPath() throws Exception{
		return XPath.newInstance("/dictionary/flexapp");
	}
	
	protected XPath getKeywordXPath() throws Exception{
		return XPath.newInstance("/dictionary/keywords/keyword");
	}

	protected XPath getItemsXPath() throws Exception{
		return XPath.newInstance("/dictionary/items/item");
	}
	
	protected void loadKeyword(Element node) throws Exception{
		Metadata.getInstance().loadKeyword(node);
	}

}

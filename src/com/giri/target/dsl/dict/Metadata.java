package com.giri.target.dsl.dict;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

import com.giri.target.dsl.ifc.IKeyword;
import com.giri.target.dsl.keywords.DataKeyword;
import com.giri.target.dsl.keywords.KeywordFlex;
import com.giri.target.dsl.keywords.LocatorKeyword;
import com.giri.target.dsl.keywords.ScriptBasedKeyword;

public class Metadata {

	private static final Metadata _METADATA = new Metadata();

	public static final Metadata getInstance() {
		return _METADATA;
	}

	public static Metadata load() {
		final InputStream stream = Metadata.class.getClassLoader()
				.getResourceAsStream("metadata.xml");
		System.out.println(Metadata.class.getClassLoader().getResource("metadata.xml"));
		_METADATA.load(stream);
		return _METADATA;
	}

	private HashMap<String, IKeyword> _keywordmap;
	private HashMap<String, DictionaryItem> dictItems = new HashMap<String, DictionaryItem>();
	private ArrayList<String> ignoredWords;
	protected Metadata() {
	}
	public DictionaryItem getDictionaryItem(final String key) {
		return dictItems.get(key.toLowerCase().trim());
	}

	public IKeyword findKeywordByName(final String name) {
		return _keywordmap == null ? null : _keywordmap.get(name.toLowerCase());
	}

	/**
	 * @return the ignoredWords
	 */
	public ArrayList<String> getIgnoredWords() {
		return ignoredWords;
	}

	public List<IKeyword> getKeyWords() {
		return (_keywordmap == null ? null : new ArrayList<IKeyword>(
				_keywordmap.values()));
	}

	protected void load(final InputStream stream) {
		try {
//			registerKeyword(new com.giri.target.dsl.keywords.html.KeywordIE());
//			registerKeyword(new com.giri.target.dsl.keywords.html.KeywordFirefox());
//			registerKeyword(new com.giri.target.dsl.keywords.html.KeywordSafari());
//
//			registerKeyword(new com.giri.target.dsl.keywords.html.KeywordButton());
//			registerKeyword(new com.giri.target.dsl.keywords.html.KeywordLink());
//
//
//			registerKeyword(new com.giri.target.dsl.keywords.KeywordClose());
//			registerKeyword(new com.giri.target.dsl.keywords.KeywordOpen());
//			registerKeyword(new com.giri.target.dsl.keywords.KeywordClick());
//			registerKeyword(new com.giri.target.dsl.keywords.KeywordType());
//			registerKeyword(new com.giri.target.dsl.keywords.KeywordWait());
//			registerKeyword(new com.giri.target.dsl.keywords.SelectRowKeyword());
			
			
			final Document document = new SAXBuilder().build(stream);
			// process includes
			XPath xpath = getIncliudeXPath();
			List<Element> nodes = xpath.selectNodes(document);
			if(nodes != null){
				for (Element node : nodes) {
					includeXML(node);
				}
			}
			
			xpath = getFlexAppXPath();
			nodes = xpath.selectNodes(document);
			if(nodes != null){
				for (Element node : nodes) {
					registerFlexApp(node);
				}
			}
			
			
			xpath = getKeywordXPath();
			nodes = xpath.selectNodes(document);
			if(nodes != null){
				for (Element node : nodes) {
					loadKeyword(node);
				}
			}
			xpath = getItemsXPath();
			nodes = xpath.selectNodes(document);
			if(nodes != null){
				for (Element node : nodes) {
					System.out.println(node);
					DictionaryItem di = new DictionaryItem();
					di.name = node.getAttributeValue("name");
					di.name = di.name.toLowerCase().trim();
					di.element = node;
					dictItems.put(di.name, di);
				}
			}
			
			Element ignored = document.getRootElement().getChild("ignored");
			if(ignored != null){
				final String txt = ignored.getText();
				final String[] wrds = txt.split(",");
				ignoredWords = new ArrayList<String>();
				for(String wrd:wrds){
					wrd = wrd.trim();
					ignoredWords.add(wrd);
				}
			}
			
		} catch (final Throwable e) {
			e.printStackTrace();
			System.out.println(" Exception during navigation " + e);
		}
	}
	
	protected XPath getIncliudeXPath() throws Exception{
		return XPath.newInstance("/metadata/include");
	}
	protected XPath getKeywordXPath() throws Exception{
		return XPath.newInstance("/metadata/keywords/keyword");
	}
	protected XPath getFlexAppXPath() throws Exception{
		return XPath.newInstance("/metadata/flexapp");
	}

	protected XPath getItemsXPath() throws Exception{
		return XPath.newInstance("/metadata/items/item");
	}
	
	private void includeXML(Element node) {
		try {
			// relative url
			String sourcexmlfile = node.getAttributeValue("source");
			final File file = new File(sourcexmlfile);
			includeXML(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void includeXML(File file) throws Exception {
		if(!file.exists()){
			throw new Exception("Included dictionary file '"+file.getAbsolutePath()+"' doesn't exist");
		}else{
			try {
				System.out.println("Loading included xml file : "+file.getAbsolutePath());
				final FileInputStream inputstream = new FileInputStream(file);
				load(inputstream);
			} catch (FileNotFoundException e) {
				System.err.println("Included dictionary file '"+file.getAbsolutePath()+"' doesn't exist");
				throw e;
			}
		}
	}
	
	private void registerFlexApp(Element node) {
		// TODO Auto-generated method stub
		KeywordFlex kflex = new KeywordFlex(node.getAttributeValue("name"), node.getAttributeValue("locator"));
		registerKeyword(kflex);
	}

	protected void loadKeyword(Element node) throws Exception {
		if(node.getAttributeValue("implementation") != null){
			loadKeyword(node.getAttributeValue("name"), 
					node.getAttributeValue("implementation"));
		}else{
			final List nodes = node.getChildren();
			if(nodes != null && nodes.size() > 0){
				final Element childnode = (Element) nodes.get(0);
				if(childnode != null){
					if(childnode.getName().equals("locator")){ // locator type keyword
						String locatorStr = childnode.getText();
						if(locatorStr != null && (locatorStr = locatorStr.trim()).length() > 0){
							registerKeyword(new LocatorKeyword(node.getAttributeValue("name"), locatorStr));
						}
					}else if(childnode.getName().equals("locators")){ // locator type keyword
						String locatorsStr = childnode.getText();
						if(locatorsStr != null && (locatorsStr = locatorsStr.trim()).length() > 0){
							StringReader stringReader = new StringReader(locatorsStr);
							BufferedReader bReader = new BufferedReader(stringReader);
							String line;
							while((line = bReader.readLine()) != null){
								registerKeywordByString(line);
							}
							bReader.close();
							bReader = null;
							
							stringReader.close();
							stringReader = null;
							
							line = null;
						}
					}
					else if(childnode.getName().equals("data")){ //data node
						registerKeyword(new DataKeyword(node.getAttributeValue("name"), childnode.getText()));
					}else if(childnode.getName().equals("script")){ //script node
						registerKeyword(newScriptBasedKeyword(node, childnode));
					}
				}
			}
		}
	}
	/**
	 * Parse and register locator keyword from passed String
	 * Expecting a string with the below format
	 * <locator name> = <locator expression>
	 * e.g
	 *  next_button = jq=#nxtButton   // using JQuery locator
	 *  
	 * 
	 * @param locator - locator string 
	 */
	private void registerKeywordByString(String locator) {
		int idx = (locator == null ? -1 : locator.indexOf("="));
		if(idx != -1){
			final String name = locator.substring(0, idx).trim();
			final String loc = locator.substring((idx+1), locator.length()).trim();
			
			if(name.length() > 0 && loc.length() > 0){
				registerKeyword(new LocatorKeyword(name, loc));
			}
		}
	}

	private IKeyword newScriptBasedKeyword(Element parent, Element child) {
		final ScriptBasedKeyword sbk = new ScriptBasedKeyword(parent.getAttributeValue("name"));
		sbk.setType(child.getAttributeValue("type"));
		sbk.setTarget(child.getAttributeValue("target"));
		sbk.setScript(child.getText());
		return sbk;
	}

	private void loadKeyword(final String name, final String klassFQN)
			throws Exception {
		final Class<? extends IKeyword> klass = (Class<? extends IKeyword>) Class.forName(klassFQN);
		// does this class has a "getInstance" method , if yes use that, else create using "new" instance
		
		try {
			Method getInstanceMethod = klass.getMethod("getInstance", null);
			if(getInstanceMethod != null){
				registerKeyword((IKeyword) getInstanceMethod.invoke(null, null));
			}
			return;
		} 
		catch (NoSuchMethodException ne) {
			//skip
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		registerKeyword(klass.newInstance());
	}

	public void registerKeyword(final IKeyword keyword) {
		if (_keywordmap == null) {
			_keywordmap = new HashMap<String, IKeyword>();
		}
		_keywordmap.put(keyword.name().toLowerCase(), keyword);
	}

	public void registerKeyword(final String name, final String klassFQN)
			throws Exception {
		loadKeyword(name, klassFQN);
	}

	public boolean isIgnored(String img) {
		return (ignoredWords == null ? false : ignoredWords.contains(img));
	}

}

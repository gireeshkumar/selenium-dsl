package net.sourceforge.seleniumflexapi.cal;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import net.sourceforge.seleniumflexapi.FlexSelenium;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.giri.target.svr.GWebDriverBackedSelenium;
import com.giri.target.svr.SeleniumTestRunner;

public class TARGetPageDriver extends DefaultPageDriver {

	public static final String TABS_ID = "tabs";
	private static final String LOC_CSS = "css=";
	private static final String LOC_LINK = "link=";
	private static final String LOC_NAME = "name=";
	private static final String LOC_JQ = "jq=";
	private static final String LOC_JS = "js=";
	
	private WebDriver webdriver;
	private GWebDriverBackedSelenium webDriverBackedSelenium;
	
	public TARGetPageDriver(GWebDriverBackedSelenium wdSelenium, FlexSelenium flexSelenium, WebDriver webdriver) {
		super(flexSelenium);
		this.webdriver = webdriver;
		this.webDriverBackedSelenium = wdSelenium;
	}
	
	public void navigateToPage() {
		final long timeoutPoint = System.currentTimeMillis() + APP_LOAD_TIMEOUT_MS;
		while (timeoutPoint > System.currentTimeMillis()) {
			try {
				if (getFlexSelenium().isVisible(TABS_ID)) {
					return;
				}
				Thread.sleep(250);
			} catch (Exception e) {
				// Ignore this, we are just waiting for the app to load
			}
		}
		Assert.fail("Application did not load");
	}
	
	public void selectTab(final String tabTitle) {
		selectTab(TABS_ID, tabTitle);
	}
	
	public String executeAS3Script(final String script) {
		return flexSelenium.call("executeAS3Script", script);
	}

	
	
	public GWebDriverBackedSelenium getWebDriverBackedSelenium() {
		return webDriverBackedSelenium;
	}


	public WebDriver getWebdriver() {
		return webdriver;
	}
	
	public static WebElement findElement(TARGetPageDriver targetDriver, String locator) throws Exception {
			final String orgLocator = locator;
			if(!(locator != null && (locator = locator.trim()).length() > 0)){
				throw new RuntimeException("Invalid locator pattern: '"+orgLocator+"'");
			}
			final WebElement element;
			if(targetDriver != null){
				final By finder;
				if(locator.startsWith("#")){
					finder = By.id(toL(locator));
				}
				else if(locator.startsWith(".")){
					finder = By.className(toL(locator));
				}else{
					final String[] split = locator.split("=");
					if(split.length > 1){
						final String key = split[0].trim().toLowerCase();
						if("css".equals(key)){
							finder = By.cssSelector(toL(locator));
						}
						else if("link".equals(key)){
							finder = By.linkText(toL(locator));
						}
						else if("name=".equals(key)){
							finder = By.name(toL(locator));
						}else{
							finder = null;
						}
					}else{
						finder = null; 
					}
				}
				
				if(finder == null){
					if(locator.startsWith("jq=")){
						final String js = "return TSLFINDERS.toDOMElement($(\""+toL(locator)+"\"))"; 
						element = executeScript(targetDriver, js);//"$(\""+toL(locator)+"\")");
					}
					else if(locator.startsWith("js=")){
						final String js = "return "+toL(locator);
						element = executeScript(targetDriver, js);
					}else{
						throw new RuntimeException("Unknown locator pattern: '"+orgLocator+"'");
					}
				}else{
					element = targetDriver.getWebdriver().findElement(finder);
				}
			}else{
				element = null;
			}
			return element;
		}
	
	private static WebElement executeScript(TARGetPageDriver webdriver, String js) throws Exception {
		final Object returnObj = webdriver.executeScript(js);
		final WebElement element;
		if(returnObj instanceof WebElement){
			element = (WebElement) returnObj;
		}
		else if(returnObj instanceof List){
			final List<WebElement> list = ((List<WebElement>) returnObj);
			element = ((list == null || list.size() == 0) ? null : list.get(0));
		}else{
			element = null;
		}
		return element;
	}

	public static List<WebElement> findElements(WebDriver webdriver, String locator) throws Exception {
		List<WebElement> elements = (List<WebElement>) findElementImpl(webdriver, locator, true);
		return elements;
	}
	
	public static Object findElementImpl(WebDriver webdriver, String locator, boolean multi) throws Exception{
		
		final String orgLocator = locator;
		if(!(locator != null && (locator = locator.trim()).length() > 0)){
			throw new RuntimeException("Invalid locator pattern: '"+orgLocator+"'");
		}
		final Object elements;
		if(webdriver != null){
			final By finder;
			if(locator.startsWith("#")){
				finder = By.id(toL(locator));
			}
			else if(locator.startsWith(".")){
				finder = By.className(toL(locator));
			}else{
				final String[] split = locator.split("=");
				if(split.length > 1){
					final String key = split[0].trim().toLowerCase();
					if("css".equals(key)){
						finder = By.cssSelector(toL(locator));
					}
					else if("link".equals(key)){
						finder = By.linkText(toL(locator));
					}
					else if("name=".equals(key)){
						finder = By.name(toL(locator));
					}else{
						finder = null;
					}
				}else{
					finder = null; 
				}
			}
			
			if(finder == null){
				if(locator.startsWith("jq=")){
					final String js = "return TSLFINDERS.toDOMElement($(\""+toL(locator)+"\"))";
					elements = ((JavascriptExecutor)webdriver).executeScript(js);
				}
				else if(locator.startsWith("js=")){
					String js = "return "+toL(locator);
					elements = ((JavascriptExecutor)webdriver).executeScript(js);
				}else{
					throw new RuntimeException("Unknown locator pattern: '"+orgLocator+"'");
				}
			}else{
				elements = multi ? webdriver.findElements(finder) : webdriver.findElement(finder);
			}
		}else{
			elements = null;
		}
		return elements;
	}

	private static String toL(String locator) {
		String locstr = locator;
		int sidx = 0;
		
		if(locator.startsWith(LOC_CSS)){
			sidx = LOC_CSS.length();
		}
		else if(locator.startsWith(LOC_LINK)){
			sidx = LOC_LINK.length();
		}
		else if(locator.startsWith(LOC_NAME)){
			sidx = LOC_NAME.length();
		}
		else if(locator.startsWith(LOC_JQ)){
			sidx = LOC_JQ.length();
		}
		else if(locator.startsWith(LOC_JS)){
			sidx = LOC_JS.length();
		}
		
		if(sidx > 0){
			locstr = locator.substring(sidx, locator.length());
		}
		
		return locstr;
	}

	public Object executeScript(String script) throws Exception {
		loadScripts();
		return ((JavascriptExecutor)webdriver).executeScript(script);
	}
	
	/////////////////////////////////////////////////////////////////////
	private void loadScripts() throws Exception {
		JavascriptExecutor jsExec = (JavascriptExecutor) webdriver;
		if(jsExec == null){
			return;
		}
		
		String jqcheck = "return (function(){ return(typeof jQuery == 'undefined') ? false : true;  })()";
		
		Object rslt = jsExec.executeScript(jqcheck).toString();
		
		if(!"true".equals(rslt)){
			loadScript("js/jquery-1.7.min.js", jsExec);
		}
		
		rslt = jsExec.executeScript("return (function(){ return(typeof TSLFINDERS == 'undefined') ? false : true;  })()").toString();
		if(!"true".equals(rslt)){
			loadScript("js/tsl_extenstion.js", jsExec);
		}
		
	}

	private static void loadScript(String scriptpath, JavascriptExecutor jsExec) throws Exception{
		// should we load this as different modules ?
		
		final InputStream stream = SeleniumTestRunner.class.getClassLoader().getResourceAsStream(scriptpath);
		final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		
		String line;
		StringBuffer sb = new StringBuffer();
		while((line = reader.readLine()) != null){
			sb.append(line);
		}
		
		reader.close();
		
		String js = sb.toString();
		jsExec.executeScript(js);
	}
}

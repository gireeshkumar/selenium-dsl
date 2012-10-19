package com.giri.target.svr.cmd;

import java.lang.reflect.Method;
import java.util.Hashtable;

import net.sourceforge.seleniumflexapi.cal.TARGetPageDriver;

import org.openqa.selenium.WebDriver;

import com.giri.target.ifc.ICommand;
import com.giri.target.ifc.IHTMLCommandProcessor;
import com.giri.target.ifc.ISeleniumTestRunner;
import com.giri.target.svr.CommandRegistry;
import com.giri.target.svr.GWebDriverBackedSelenium;

public class SeleniumMethodInvocation implements IHTMLCommandProcessor {
	
	public static final String CMD = "SELENIUM";
	
	private static Hashtable<String, Method> methodMap = null;
	
	static{
		new SeleniumMethodInvocation();
	}
	
	private SeleniumMethodInvocation(){
		CommandRegistry.register(this);
	}

	/* (non-Javadoc)
	 * @see com.giri.target.ifc.ICommandProcessor#supportedCommands()
	 */
	@Override
	public String[] supportedCommands() {
		return new String[]{CMD};
	}

	@Override
	public String process(ISeleniumTestRunner testRunner, WebDriver webdriver,
			TARGetPageDriver driver, ICommand command) throws Exception {
		// Invoke selenium methods via reflection
		
		GWebDriverBackedSelenium selenium = driver.getWebDriverBackedSelenium();
		
		String locs = command.getLocator();
		locs = (locs == null ? null : locs.toLowerCase().trim());
		
		if(methodMap == null){
			methodMap = new Hashtable<String, Method>();
			Method[] mthds = selenium.getClass().getMethods();
			for (Method method : mthds) {
				String name = method.getName();
				name = name.toLowerCase();
				
				methodMap.put(name, method);
			}
		}
		
		final Method method = (locs == null ? null : methodMap.get(locs));
		if(method == null){
			throw new Exception("No matching selenium method found for - "+command.getLocator());
		}
		
		
		final Class<?>[] prs = method.getParameterTypes();
		final String[] parameters;
		if(prs != null && prs.length > 0){
			final String[] pars = (String[]) command.getAttribute("parameters");
			final String[] params = new String[prs.length];
			int cnt = 0;
			for (String string : params) {
				params[cnt] = string;
				cnt++;
				if(cnt == params.length){
					break;
				}
			}
			parameters = params;
		}else{
			parameters = null;
		}
		
		
		final Object rlst = method.invoke(selenium, parameters);
		
		return (method.getReturnType().equals(Void.TYPE)) ? null : (rlst == null ? null : rlst.toString());
	}

}

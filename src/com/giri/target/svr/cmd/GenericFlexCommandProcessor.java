package com.giri.target.svr.cmd;

import net.sourceforge.seleniumflexapi.cal.TARGetPageDriver;

import org.openqa.selenium.WebDriver;

import com.giri.target.dsl.ifc.IParameter;
import com.giri.target.dsl.ifc.IStatement;
import com.giri.target.ifc.ICommand;
import com.giri.target.ifc.IFlexCommandProcessor;
import com.giri.target.ifc.ISeleniumTestRunner;
import com.giri.target.svr.CommandRegistry;
import com.giri.target.svr.StmtWrapCommand;

/*
 * 
 * Try to invoke Flex exposed functions 
 * 
 * format
 * <funciton name> "<parma1>" "<param2>" .... "<param-n>"
 * 
 */


public class GenericFlexCommandProcessor implements IFlexCommandProcessor {

	
	static{
		new GenericFlexCommandProcessor();
	}

	private GenericFlexCommandProcessor() {
		CommandRegistry.register(this);
	}
	
	@Override
	public String[] supportedCommands() {
		return new String[]{"genericflexcommands"};
	}

	@Override
	public String process(ISeleniumTestRunner testRunner, WebDriver webdriver, TARGetPageDriver driver, ICommand command) throws Exception {
		
		final StmtWrapCommand scmd = (StmtWrapCommand) command;
		final IStatement orgStatement = scmd.getOrginalStatement();
		
		driver.getFlexSelenium().setFlashObjectID(command.getFlexAppName());
		
		IParameter[] params = orgStatement.getParameters();
		String[] args = new String[params.length];
		int i = 0;
		for (IParameter param : params) {
			args[i++] = param.value();
		}
		
		IParameter[] oParam = orgStatement.getOtherParameters();
		IParameter p1 = oParam[0];
		
//		if (command.isEnsureVisible()) {
//			driver.ensureWidgetVisibility(command.getLocator(), true);
//			driver.ensureWidgetEnabled(command.getLocator(), true);
//		}
		
		String rslt = driver.getFlexSelenium().call(p1.value(), args);
		
		return rslt;
	}
	
	/*
	 * 
	 * private String getEvalString(ICommand cmd) {
		// window.document['IGECalendar'].doFlexClick('2052009','')
		StringBuffer sbr = new StringBuffer();
		sbr.append("window.document");
		sbr.append("['").append(cmd.getFlexAppName()).append("']");
		sbr.append(".");
		sbr.append(cmd.getCommand()).append("(");
		sbr.append("'").append(cmd.getLocator()).append("'");
		sbr.append(",");
		sbr.append("'").append((cmd.getArgs() == null ? "" : cmd.getArgs()))
				.append("'");
		sbr.append(")");
		return sbr.toString();
	}
	 * *
	 */

}

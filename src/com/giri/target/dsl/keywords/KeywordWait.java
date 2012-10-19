package com.giri.target.dsl.keywords;

import com.giri.target.dsl.ifc.IActionKeyword;
import com.giri.target.dsl.ifc.IExecutableStatement;
import com.giri.target.dsl.ifc.IParameter;
import com.giri.target.ifc.ICommand;

public class KeywordWait implements IActionKeyword {
	
	public static final String NAME = "WAIT";

	@Override
	public String name() {
		return NAME;
	}

	@Override
	public void modifyCommand(IExecutableStatement stmt,ICommand cmd) {
		cmd.setCommand("sleep");
		IParameter[] params = stmt.getParameters();
		String seconds = params[0].value();
		int intsec = Integer.parseInt(seconds);
		int msec = 1000 * intsec;
		cmd.setArgs(msec+"");
	}
}

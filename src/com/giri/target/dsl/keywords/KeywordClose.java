package com.giri.target.dsl.keywords;

import com.giri.target.dsl.ifc.IActionKeyword;
import com.giri.target.dsl.ifc.IExecutableStatement;
import com.giri.target.ifc.ICommand;

public class KeywordClose implements IActionKeyword {
	
	public static final String NAME = "CLOSE";

	@Override
	public String name() {
		return NAME;
	}

	@Override
	public void modifyCommand(IExecutableStatement stmt,ICommand cmd) {
		cmd.setCommand("close");
	}
}

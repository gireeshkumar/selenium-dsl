package com.giri.target.dsl.keywords;

import com.giri.target.dsl.ifc.IActionKeyword;
import com.giri.target.dsl.ifc.IExecutableStatement;
import com.giri.target.ifc.ICommand;

public class KeywordOpen implements IActionKeyword {
	
	public static final String NAME = "OPEN";

	@Override
	public String name() {
		return NAME;
	}

	@Override
	public void modifyCommand(IExecutableStatement stmt,ICommand cmd) {
		cmd.setCommand("open");
	}
}

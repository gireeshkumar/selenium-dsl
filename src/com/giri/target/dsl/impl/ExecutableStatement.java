package com.giri.target.dsl.impl;

import com.giri.target.dsl.ifc.IActionKeyword;
import com.giri.target.dsl.ifc.IExecutableStatement;
import com.giri.target.ifc.ICommand;

public class ExecutableStatement extends StatementBaseClass implements IExecutableStatement {

	@Override
	public ICommand toICommand() throws Exception {
		ICommand cmd = super.toICommand();
		
		IActionKeyword keyword = (IActionKeyword) getMasterKeyword();
		keyword.modifyCommand(this, cmd);
		
		
		return cmd;
	}

	

}

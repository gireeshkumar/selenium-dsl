package com.giri.target.dsl.ifc;

import com.giri.target.ifc.ICommand;

public interface IActionKeyword extends IKeyword {

	public void modifyCommand(IExecutableStatement executableStatement, ICommand cmd) throws Exception;

}

package com.giri.target.dsl.keywords;

import com.giri.target.dsl.ifc.IExecutableStatement;
import com.giri.target.dsl.ifc.IParameter;
import com.giri.target.ifc.ICommand;

public class KeywordHas  extends BaseActionKeyword  {
	
	public static final String NAME = "HAS";

	@Override
	public String name() {
		return NAME;
	}

	@Override
	public void modifyCommand(IExecutableStatement stmt,ICommand cmd) {
		cmd.setCommand("has");
		final IParameter[] params = stmt.getParameters();
		// need only one parameter
		if(params != null && params.length > 0){
			cmd.setArgs(params[0].value());
		}
		IParameter[] aclone = null;
			int len = params.length;
			aclone = new IParameter[len];
			System.arraycopy(params, 0, aclone, 0, len);
		setLocator(cmd, aclone, stmt);
	}
}

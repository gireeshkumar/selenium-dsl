package com.giri.target.dsl.keywords;

import com.giri.target.dsl.ifc.IExecutableStatement;
import com.giri.target.dsl.ifc.IParameter;
import com.giri.target.ifc.ICommand;

/**
 * @author U20463
 *
 */
public class KeywordRun extends BaseActionKeyword {

	public static final String NAME = "RUN";
	
	@Override
	public String name() {
		return NAME;
	}

	@Override
	public void modifyCommand(IExecutableStatement stmt,ICommand cmd) throws Exception {
		cmd.setCommand("run");
		
		IParameter[] params = stmt.getParameters();
		if(params != null && params.length > 0){
			cmd.setArgs(params[0].value());
		}else{
			throw new Exception("run command require at least one parameter to be passed");
		}
//		setLocator(cmd, stmt.getParameters(), stmt);
	}

}

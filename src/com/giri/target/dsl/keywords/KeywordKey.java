package com.giri.target.dsl.keywords;

import com.giri.target.dsl.ifc.IExecutableStatement;
import com.giri.target.dsl.ifc.IParameter;
import com.giri.target.ifc.ICommand;

/**
 * @author U20463
 *
 * send key events to browser
 * eg.  - key "esc" or key enter , key F1 , 
 *
 */
public class KeywordKey  extends BaseActionKeyword  {
	
	public static final String NAME = "KEY";

	@Override
	public String name() {
		return NAME;
	}

	@Override
	public void modifyCommand(IExecutableStatement stmt,ICommand cmd) throws Exception {
		cmd.setCommand("key");
		IParameter[] params = stmt.getParameters();
		if(params != null && params.length > 0){
			cmd.setArgs(params[0].value());
			
			IParameter[] aclone = null;
			if(params.length > 1){
				int len = params.length - 1;
				aclone = new IParameter[len];
				System.arraycopy(params, 1, aclone, 0, len);
				setLocator(cmd, aclone, stmt);
			}
		}else{
			throw new Exception("Key command require at least one parameter to be passed ");
		}
	}


	
}

package com.giri.target.dsl.keywords;

import com.giri.target.common.FinderString;
import com.giri.target.dsl.ifc.IExecutableStatement;
import com.giri.target.dsl.ifc.IParameter;
import com.giri.target.ifc.ICommand;

public class KeywordType extends BaseActionKeyword {

	public static final String NAME = "TYPE";

	@Override
	public String name() {
		return NAME;
	}

	@Override
	public void modifyCommand(IExecutableStatement stmt,ICommand cmd) {
		cmd.setCommand("type");
		
		final IParameter[] params = stmt.getParameters();
		// need only one parameter
		if(params != null && params.length > 0){
			cmd.setArgs(params[0].value());
		}
		IParameter[] aclone = null;
		if(params.length > 1){
			int len = params.length - 1;
			aclone = new IParameter[len];
			System.arraycopy(params, 1, aclone, 0, len);
		}
		
		if(!setLocator(cmd, aclone, stmt)){
			System.out.println("Locator not found for type, using first visible textbox");
			cmd.setLocator(FinderString.FIRST_VISIBLE_TEXTBOX);
		}
		
		ICommand clone = cmd.clone();
		clone.setCommand("click");
		
		//cmd.addPreCommand(clone);
	}

}

package com.giri.target.dsl.keywords;

import com.giri.target.dsl.ifc.IExecutableStatement;
import com.giri.target.ifc.ICommand;

/**
 * @author U20463
 *
 */
public class KeywordIS extends BaseActionKeyword {

	public static final String NAME = "IS";
	
	@Override
	public String name() {
		return NAME;
	}

	@Override
	public void modifyCommand(IExecutableStatement stmt,ICommand cmd) {
		cmd.setCommand("is");
		cmd.setArgs(stmt.getOrginalStatement());
		setLocator(cmd, stmt.getParameters(), stmt);
		
	}

}

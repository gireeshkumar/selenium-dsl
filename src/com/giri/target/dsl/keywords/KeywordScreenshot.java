package com.giri.target.dsl.keywords;

import com.giri.target.dsl.ifc.IExecutableStatement;
import com.giri.target.ifc.ICommand;

/**
 * @author U20463
 *
 *
 */
public class KeywordScreenshot  extends BaseActionKeyword  {
	
	public static final String NAME = "SCREENSHOT";

	@Override
	public String name() {
		return NAME;
	}

	@Override
	public void modifyCommand(IExecutableStatement stmt,ICommand cmd) throws Exception {
		cmd.setCommand("screenshot");
	}
	
}

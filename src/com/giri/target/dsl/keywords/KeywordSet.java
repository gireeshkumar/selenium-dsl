package com.giri.target.dsl.keywords;

import com.giri.target.common.FinderString;
import com.giri.target.dsl.ifc.IExecutableStatement;
import com.giri.target.dsl.ifc.IParameter;
import com.giri.target.ifc.ICommand;





///// TODO not used


public class KeywordSet extends BaseActionKeyword {

	@Override
	public void modifyCommand(IExecutableStatement stmt,
			ICommand cmd) throws Exception {
		cmd.setCommand("set");
		
		if(setLocator(cmd, stmt.getParameters(), stmt)){
			// if its any tag click
			//
			if(cmd.getLocator().contains("anyTagContains")){
				// give first preference to link, button, any tag
				IParameter prs = stmt.getParameters()[0];
				cmd.setLocator(FinderString.findByLinkButtonAny(prs.value()));
			}
		}
	}

	public static final String NAME = "SET";
	
	@Override
	public String name() {
		return NAME;
	}
}

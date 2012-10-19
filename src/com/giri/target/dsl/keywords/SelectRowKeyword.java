package com.giri.target.dsl.keywords;

import java.util.ArrayList;

import com.giri.target.dsl.ifc.IExecutableStatement;
import com.giri.target.dsl.ifc.IKeyword;
import com.giri.target.dsl.ifc.ILocatorKeyword;
import com.giri.target.dsl.ifc.IParameter;
import com.giri.target.ifc.ICommand;

public class SelectRowKeyword extends BaseActionKeyword {

	public static final String NAME = "SELECTROW";
	
	@Override
	public String name() {
		return NAME;
	}

	@Override
	public void modifyCommand(IExecutableStatement stmt,ICommand cmd) {
		cmd.setCommand("script");
		
		// need at least one keyword
		ArrayList<IKeyword> oKeywords = stmt.getOtherKeywords();
		String locator = null;
		if(oKeywords != null){
			for(IKeyword kword:oKeywords){
				if(kword instanceof ILocatorKeyword){
					ILocatorKeyword iloc = (ILocatorKeyword) kword;
					locator = (iloc.locator());
				}
			}
		}
		
		StringBuffer sb = new StringBuffer("$(${doc}).find('"+locator.split("=")[1]+"')");
		IParameter[] params = stmt.getParameters();
		if(params != null){
			for(IParameter param : params){
				//jQuery("#user_list").find("td:contains('Gireesh')").parent()
				sb.append(".find(\"td:contains('"+param.value()+"')\").parent()");
			}
		}
		sb.append(".trigger('click')");
		cmd.setArgs(sb.toString());
	}

}

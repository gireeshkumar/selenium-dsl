package com.giri.target.dsl.keywords;

import java.util.ArrayList;

import com.giri.target.common.FinderString;
import com.giri.target.dsl.ifc.IExecutableStatement;
import com.giri.target.dsl.ifc.IKeyword;
import com.giri.target.dsl.ifc.IParameter;
import com.giri.target.dsl.keywords.html.IHTMLTags;
import com.giri.target.ifc.ICommand;

/**
 * @author U20463
 *
 */
public class KeywordClick extends BaseActionKeyword {

	public static final String NAME = "CLICK";
	
	@Override
	public String name() {
		return NAME;
	}

	@Override
	public void modifyCommand(IExecutableStatement stmt,ICommand cmd) {
		cmd.setCommand("click");
		
		if(!setLocator(cmd, stmt.getParameters(), stmt)){
			System.out.println("Locator not found for type, using first visible button");
			
			ArrayList<IKeyword> oKeywords = stmt.getOtherKeywords();
			// click command needs one location, no parameters needed
			// How to find locator -> keyword, parameters, tokens
			IHTMLTags htmlTag = null;
			if(oKeywords != null){
				for(IKeyword kword:oKeywords){
					if(htmlTag == null && (kword instanceof IHTMLTags)){
						// check what HTML tag it could be
						htmlTag = (IHTMLTags) kword;
						break;
					}
				}
			}
			
			if(htmlTag != null){
				if(htmlTag.getTag() == IHTMLTags.TAG.BUTTON){
					cmd.setLocator(FinderString.FIRST_VISIBLE_BUTTON);
				}else if(htmlTag.getTag() == IHTMLTags.TAG.TEXTBOX){
					cmd.setLocator(FinderString.FIRST_VISIBLE_BUTTON);
				}
			}
		}else{
			// if its any tag click
			//
			if(cmd.getLocator().contains("anyTagContains")){
				// give first preference to link, button, any tag
				IParameter prs = stmt.getParameters()[0];
				cmd.setLocator(FinderString.findByLinkButtonAny(prs.value()));
			}
		}
	}

}

/**
*	License
*	
*	This file is part of The TARGet framework
* 
*   	/__  ___/ // | |     //   ) )  //   ) )
*   	  / /    //__| |    //___/ /  //         ___    __  ___
*   	 / /    / ___  |   / ___ (   //  ____  //___) )  / /
*   	/ /    //    | |  //   | |  //    / / //        / /
*      / /    //     | | //    | | ((____/ / ((____    / /
*   	 
*	    ______     __,             _ ___              ,____                                                   
*      (  /       /  |            ( /   )              /   )                                            
*	     /       /-.-|             /-.-<              /  __                                  
*  Web _/est   _/    |_utomation f/     \_amework by (___/iri
*  -.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.
*	

*  TARGet is free software: you can redistribute it and/or
*  modify it  under  the  terms  of  the  GNU  General Public License as 
*  published  by  the  Free  Software Foundation,  either  version  3 of 
*  the License, or any later version.
*
*  TARGet is distributed in the hope that it will be useful,
*  but  WITHOUT  ANY  WARRANTY;  without  even the  implied  warranty  of
*  MERCHANTABILITY   or   FITNESS   FOR  A  PARTICULAR  PURPOSE.  See the
*  GNU General Public License for more details.
*
*  You should have received a copy of the GNU General Public License
*  along with The SeleniumFlex-API.
*  If not, see http://www.gnu.org/licenses/
*  
* 
*  @Author	Gireesh Kumar G - gireeshkumar.g@gmail.com
*  @Date 	July 2010
*
**/
package com.giri.target.svr;

import java.util.HashMap;

import com.giri.target.ifc.ICommandProcessor;
import com.giri.target.ifc.IFlexCommandProcessor;
import com.giri.target.ifc.IHTMLCommandProcessor;

/**
 * @author U20463
 *
 */
public final class CommandRegistry {

	private static final CommandRegistry _INSTANCE = new CommandRegistry();
	private static final HashMap<String, IFlexCommandProcessor> _flex_cmd_registry = new HashMap<String, IFlexCommandProcessor>();
	private static final HashMap<String, IHTMLCommandProcessor> _html_cmd_registry = new HashMap<String, IHTMLCommandProcessor>();

	private CommandRegistry(){}
	
	public static final CommandRegistry getInstance(){
		return _INSTANCE;
	}
	public final static void register(ICommandProcessor processor){
		CommandRegistry.getInstance().registerProcessor(processor);
	}
	public static IHTMLCommandProcessor getGenericHTMLCommandProcessor(){
		return (IHTMLCommandProcessor)CommandRegistry.getInstance().getProcessor(IHTMLCommandProcessor.TYPE, "genericHTMLCommand");
	}
	public static IFlexCommandProcessor getGenericFlexCommandProcessor(){
		return (IFlexCommandProcessor)CommandRegistry.getInstance().getProcessor(IFlexCommandProcessor.TYPE, "genericflexcommands");
	}
	public final void registerProcessor(ICommandProcessor processor){

		final String[] cmds = processor.supportedCommands();
		final boolean isflex = processor instanceof IFlexCommandProcessor;
		final boolean ishtml = processor instanceof IHTMLCommandProcessor;
		
		if(cmds == null || cmds.length == 0){
			throw new RuntimeException("CommandProcessor should have at least one supporting command");
		}
		for (String cmd : cmds) {
			if(isflex){
				_flex_cmd_registry.put(cmd, (IFlexCommandProcessor) processor);
			}
			
			if(ishtml){
				_html_cmd_registry.put(cmd, (IHTMLCommandProcessor) processor);
			}
		}
	}
	
	public final ICommandProcessor getProcessor(Class<? extends ICommandProcessor> type, String command){
		ICommandProcessor processor = (type == IFlexCommandProcessor.TYPE) ? _flex_cmd_registry.get(command) : _html_cmd_registry.get(command);
		if(processor == null && type == IHTMLCommandProcessor.TYPE){
			processor = _html_cmd_registry.get("genericHTMLCommand");
		}
		return processor;
	}
}

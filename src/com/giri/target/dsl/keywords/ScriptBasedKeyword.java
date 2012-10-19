package com.giri.target.dsl.keywords;

import com.giri.target.dsl.ifc.IExecutableStatement;
import com.giri.target.ifc.ICommand;

/**
 * @author U20463
 *
 */
public class ScriptBasedKeyword extends BaseActionKeyword {

	private String name;
	private String type;
	private String target;
	private String script;

	public ScriptBasedKeyword(String name){
		this.name = name;
	}
	
	@Override
	public String name() {
		return name;
	}


	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * @return the script
	 */
	public String getScript() {
		return script;
	}

	/**
	 * @param script the script to set
	 */
	public void setScript(String script) {
		this.script = script;
	}

	@Override
	public void modifyCommand(IExecutableStatement stmt,ICommand cmd) {
		cmd.setCommand("script");
		cmd.setAttribute("type", getType());
		cmd.setAttribute("target", getTarget());
		cmd.setArgs(getScript());
	}
}

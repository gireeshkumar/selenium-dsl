package com.giri.target.common;

public class ScriptCommandTag implements ICommandTag{

	private ScriptTypes scriptType;
	private String scriptContent;
	
	/**
	 * @return the scriptType
	 */
	public ScriptTypes getScriptType() {
		return scriptType;
	}
	/**
	 * @param scriptType the scriptType to set
	 */
	public void setScriptType(ScriptTypes scriptType) {
		this.scriptType = scriptType;
	}
	/**
	 * @return the scriptContent
	 */
	public String getScriptContent() {
		return scriptContent;
	}
	/**
	 * @param scriptContent the scriptContent to set
	 */
	public void setScriptContent(String scriptContent) {
		this.scriptContent = scriptContent;
	}
	
	
}

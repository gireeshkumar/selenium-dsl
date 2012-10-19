package com.giri.target.svr;

import java.util.List;

import com.giri.target.dsl.ifc.IStatement;
import com.giri.target.ifc.ICommand;

public class StmtWrapCommand implements ICommand {

	private final ICommand orginalCommand;
	private final IStatement orginalStatement;

	public StmtWrapCommand(ICommand orgcmd, IStatement stmt){
		this.orginalCommand = orgcmd;
		this.orginalStatement = stmt;
	}

	public String getArgs() {
		return orginalCommand.getArgs();
	}

	public String getCommand() {
		return orginalCommand.getCommand();
	}

	public String getCommandID() {
		return orginalCommand.getCommandID();
	}

	public String getFlexAppName() {
		return orginalCommand.getFlexAppName();
	}

	public String getLabel() {
		return orginalCommand.getLabel();
	}

	public String getLocator() {
		return orginalCommand.getLocator();
	}

	public String getMessage() {
		return orginalCommand.getMessage();
	}

	public boolean isEnsureVisible() {
		return orginalCommand.isEnsureVisible();
	}

	public boolean isFlex() {
		return orginalCommand.isFlex();
	}

	public void setArgs(String args) {
		orginalCommand.setArgs(args);
	}

	public void setCommand(String command) {
		orginalCommand.setCommand(command);
	}

	public void setEnsureVisible(boolean ensureVisible) {
		orginalCommand.setEnsureVisible(ensureVisible);
	}

	public void setFlex(boolean isFlex) {
		orginalCommand.setFlex(isFlex);
	}

	public void setFlexAppName(String flexAppName) {
		orginalCommand.setFlexAppName(flexAppName);
	}

	public void setLabel(String label) {
		orginalCommand.setLabel(label);
	}

	public void setLocator(String objectId) {
		orginalCommand.setLocator(objectId);
	}

	public void setMessage(String message) {
		orginalCommand.setMessage(message);
	}

	public String getOptions() {
		return orginalCommand.getOptions();
	}

	public void setOptions(String options) {
		orginalCommand.setOptions(options);
	}

	public void setAttribute(String key, Object value) {
		orginalCommand.setAttribute(key, value);
	}

	public Object getAttribute(String key) {
		return orginalCommand.getAttribute(key);
	}

	public List<ICommand> getPreProcessingCommands() {
		return orginalCommand.getPreProcessingCommands();
	}

	public List<ICommand> getPostProcessingCommands() {
		return orginalCommand.getPostProcessingCommands();
	}

	public void addPreCommand(ICommand cmd) {
		orginalCommand.addPreCommand(cmd);
	}

	public void addPostCommnad(ICommand cmd) {
		orginalCommand.addPostCommnad(cmd);
	}

	public ICommand clone() {
		return orginalCommand.clone();
	}

	public ICommand getOrginalCommand() {
		return orginalCommand;
	}

	public IStatement getOrginalStatement() {
		return orginalStatement;
	}
}

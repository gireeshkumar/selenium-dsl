package com.giri.target.dsl.impl;

import java.util.ArrayList;

import com.giri.target.dsl.ifc.IActionKeyword;
import com.giri.target.dsl.ifc.IKeyword;
import com.giri.target.dsl.ifc.IParameter;
import com.giri.target.dsl.ifc.IStatement;
import com.giri.target.dsl.keywords.KeywordFlex;
import com.giri.target.ifc.ICommand;
import com.giri.target.impl.Command;

import de.susebox.jtopas.Token;

public abstract class StatementBaseClass implements IStatement {

	private IKeyword masterKeyword;
	private String orginalStatement;
	private IParameter[] parameters;
	private IParameter[] otherParameters;
	private ArrayList<Token> tokens;
	public ArrayList<IKeyword> otherKeywords;
	private ArrayList<Token> patterns;
	
	@Override
	public IKeyword getMasterKeyword() {
		return masterKeyword;
	}

	@Override
	public String getOrginalStatement() {
		return orginalStatement;
	}

	@Override
	public IParameter[] getParameters() {
		return parameters;
	}

	/**
	 * @param masterKeyword the masterKeyword to set
	 */
	public void setMasterKeyword(IKeyword masterKeyword) {
		this.masterKeyword = masterKeyword;
	}

	/**
	 * @param orginalStatement the orginalStatement to set
	 */
	public void setOrginalStatement(String orginalStatement) {
		this.orginalStatement = orginalStatement;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(IParameter[] parameters) {
		this.parameters = parameters;
	}

	/**
	 * @return the tokens
	 */
	@Override
	public ArrayList<Token> getTokens() {
		return tokens;
	}

	/**
	 * @param tokens the tokens to set
	 */
	public void setTokens(ArrayList<Token> tokens) {
		this.tokens = tokens;
	}

	/**
	 * @return the others
	 */
	public ArrayList<IKeyword> getOtherKeywords() {
		return otherKeywords;
	}

	/**
	 * @param others the others to set
	 */
	public void setOtherKeywords(ArrayList<IKeyword> others) {
		this.otherKeywords = others;
	}

	/**
	 * @return the patterns
	 */
	public ArrayList<Token> getPatterns() {
		return patterns;
	}

	/**
	 * @param patterns the patterns to set
	 */
	public void setPatterns(ArrayList<Token> patterns) {
		this.patterns = patterns;
	}

	/**
	 * @return the otherParameters
	 */
	public IParameter[] getOtherParameters() {
		return otherParameters;
	}

	/**
	 * @param otherParameters the otherParameters to set
	 */
	public void setOtherParameters(IParameter[] otherParameters) {
		this.otherParameters = otherParameters;
	}

	@Override
	public String toString() {
		return "["+getClass().getSimpleName()+"] {"+toStatement()+"}";
	}

	private StringBuffer toStatement() {
		StringBuffer sb = new StringBuffer();
		for (Token token : tokens) {
			sb.append(token.getImage()).append(' ');
		}
		return sb;
	}
	
	
	public ICommand toICommand() throws Exception {
		String cmdStr = "";
		String locator = "";
		Command cmd = new Command(cmdStr, locator);

		ArrayList<IKeyword> oCmds = getOtherKeywords();
		if(oCmds != null && oCmds.size() > 0){
			for (IKeyword iKeyword : oCmds) {
				if(iKeyword instanceof KeywordFlex){
					cmd.setFlex(true);
					cmd.setFlexAppName( ((KeywordFlex)iKeyword).value() );
					break;
				}
			}
		}
		
		cmd.setAttribute(IStatement.NAME, this);
		
		return cmd;
	}

}

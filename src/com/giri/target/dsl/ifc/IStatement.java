package com.giri.target.dsl.ifc;

import java.util.ArrayList;
import java.util.List;

import com.giri.target.ifc.ICommand;

import de.susebox.jtopas.Token;

public interface IStatement {
	
	public static final String NAME = "statement";
	
	public IKeyword getMasterKeyword();// the root keyword which would drive this statement
	public IParameter[] getParameters();
	
	/**
	 * Extra values found could be used, which is not in double qotes
	 * @return
	 */
	public IParameter[] getOtherParameters();
	public String getOrginalStatement();
	/**
	 * @return the others
	 */
	public ArrayList<IKeyword> getOtherKeywords();
	public abstract List<Token> getTokens();
	
	public ICommand toICommand() throws Exception;
	
}

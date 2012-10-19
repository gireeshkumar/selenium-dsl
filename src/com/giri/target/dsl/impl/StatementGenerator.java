package com.giri.target.dsl.impl;

import java.util.ArrayList;

import com.giri.target.dsl.TARGetDSL;
import com.giri.target.dsl.dict.Metadata;
import com.giri.target.dsl.ifc.IActionKeyword;
import com.giri.target.dsl.ifc.IKeyword;
import com.giri.target.dsl.ifc.IParameter;
import com.giri.target.dsl.ifc.IStatement;

import de.susebox.jtopas.Token;

public final class StatementGenerator {

	/**
	 * @param tarGetDSL 
	 * @param line  Original statement string
	 * @param keyword	Auto identified master keyword
	 * @param parameters	identified parameter tokens
	 * @param otherKeywords	identified other keywords
	 * @param tokens	list of all tokens
	 * @return
	 */
	public static  IStatement newStatement(TARGetDSL tarGetDSL, String line,
										   IKeyword keyword,
										   ArrayList<Token> paramTokens,
										   ArrayList<Token> otherwords,
										   ArrayList<Token> otherKeywords,
										   ArrayList<Token> tokens
										   ) {
		StatementBaseClass stmt = null;
		if(keyword instanceof IActionKeyword){
			stmt = new ExecutableStatement();
		}else{
			stmt = new NonExecutableStatement();
		}
		stmt.setMasterKeyword(keyword);
		stmt.setTokens(tokens);
		stmt.setOrginalStatement(line);
		stmt.setOtherParameters(toParameters(otherwords));
		stmt.setParameters(toParameters(paramTokens));
		stmt.setOtherKeywords(toKeywords(tarGetDSL, otherKeywords));
		return stmt;
	}

	private static ArrayList<IKeyword> toKeywords(TARGetDSL tarGetDSL, ArrayList<Token> otherKeywords) {
		ArrayList<IKeyword> keywords  = null;
		if(otherKeywords != null && otherKeywords.size() > 0){
			IKeyword keyword;
			for(Token token:otherKeywords){
				keyword = tarGetDSL.findKeywordByName(token.getImage());
				
				if(keyword != null){
					if(keywords == null){
						keywords = new ArrayList<IKeyword>();
					}
					keywords.add(keyword);
				}else{
					System.err.println("No such keyword mapping - "+token.getImage());
				}
			}
		}
		return keywords;
	}

	private static IParameter[] toParameters(ArrayList<Token> paramTokens) {
		IParameter[] params = null;
		if(paramTokens != null && paramTokens.size() > 0){
			params = new IParameter[paramTokens.size()];
			int index = 0;
			for(Token token:paramTokens){
				params[index] = new Parameter(index, token.getImage());
				index++;
			}
		}
		return params;
	}

	/**
	 * Create new non-executable statement
	 * @param line
	 * @param iscomment 
	 * @param tokens 
	 * @param tokens2 
	 * @return
	 */
	public static IStatement newStatement(TARGetDSL tarGetDSL, String line, boolean iscomment, ArrayList<Token> strings, ArrayList<Token> otherwords, ArrayList<Token> otherKeywords, ArrayList<Token> tokens) {
		NonExecutableStatement stmt = new NonExecutableStatement();
		stmt.setTokens(tokens);
		stmt.setOrginalStatement(line);
		stmt.setIsComment(iscomment);
		stmt.setParameters(toParameters(strings));
		stmt.setOtherParameters(toParameters(otherwords));
		stmt.setOtherKeywords(toKeywords(tarGetDSL, otherKeywords));
		return stmt;
	}
}

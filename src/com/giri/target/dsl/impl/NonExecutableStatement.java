package com.giri.target.dsl.impl;

import com.giri.target.dsl.ifc.INonExecutableStatement;

public class NonExecutableStatement extends StatementBaseClass implements INonExecutableStatement {

	private boolean isComment;

	/**
	 * @return the isComment
	 */
	public boolean isComment() {
		return isComment;
	}

	/**
	 * @param isComment the isComment to set
	 */
	public void setIsComment(boolean isComment) {
		this.isComment = isComment;
	}
	
}

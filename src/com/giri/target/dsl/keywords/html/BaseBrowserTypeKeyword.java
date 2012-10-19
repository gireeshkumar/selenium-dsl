package com.giri.target.dsl.keywords.html;

import com.giri.target.dsl.keywords.DataKeyword;
import com.giri.target.dsl.keywords.IBrowserTypes;

public abstract class BaseBrowserTypeKeyword extends DataKeyword implements IBrowserTypes {

	private TYPE browserType;

	public BaseBrowserTypeKeyword(String name, TYPE brType) {
		super(name, brType.value());
		this.browserType = brType;
	}

	@Override
	public TYPE getType() {
		return browserType;
	}

}

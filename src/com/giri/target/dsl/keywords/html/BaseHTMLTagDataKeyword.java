package com.giri.target.dsl.keywords.html;

import com.giri.target.dsl.keywords.DataKeyword;

public abstract class BaseHTMLTagDataKeyword extends DataKeyword implements IHTMLTags {

	private TAG htmlTag;

	public BaseHTMLTagDataKeyword(String name, TAG htmltag) {
		super(name, htmltag.name());
		this.htmlTag = htmltag;
	}

	@Override
	public TAG getTag() {
		return htmlTag;
	}

}

package com.giri.target.common;

public class FinderString {

	public static final String FIRST_VISIBLE_BUTTON = "js=TSLFINDERS.firstVisibleElement('button,input[type=submit],input[type=button],input[type=rest]')";
	public static final String FIRST_VISIBLE_TEXTBOX = "js=TSLFINDERS.firstVisibleElement('input[type=text]')";
	
	public static String anyTagContains(String search){
		return anyTagContains(search, null);
	}
	public static String anyTagContains(String search, String tag){
		return "jq="+((tag == null || (tag = tag.trim()).length() == 0) ? "" : tag)+":anyTagContains('"+search+"')";
	}
	public static String findLink(String search){
		return "js=TSLFINDERS.findLink('"+search+"')";
	}
	public static String anyButtonContains(String search){
		return "js=TSLFINDERS.buttonContains('"+search+"')";
	}
	public static String findByLinkButtonAny(String search){
		return "js=TSLFINDERS.findByLinkButtonAny('"+search+"')";
	}
	
}

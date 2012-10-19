package com.giri.target.dsl.keywords;

import com.giri.target.dsl.ifc.IDataKeyword;

public class DataKeyword implements IDataKeyword {

	protected String value;
	protected String name;
	
	public DataKeyword(){
		
	}
	/**
	 * @param value
	 * @param name
	 */
	public DataKeyword(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	/**
	 * @return the value
	 */
	public String value() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the name
	 */
	public String name() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	

}

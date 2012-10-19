package com.giri.target.dsl.impl;

import com.giri.target.dsl.ifc.IParameter;

public class Parameter implements IParameter {

	private int index;
	private String value;
	public Parameter(int l_index, String l_value) {
		this.index = l_index;
		setValue(l_value);
	}
	/**
	 * @return the index
	 */
	public int index() {
		return index;
	}
	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	/**
	 * @return the value
	 */
	@Override
	public String value() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		if(value.startsWith("\"") && value.length() > 1){
			value = value.substring(1, value.length());
		}
		if(value.endsWith("\"") && value.length() > 1){
			value = value.substring(0, value.length()-1);
		}
		this.value = value;
	}
	

}

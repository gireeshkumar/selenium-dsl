package com.giri.target.dsl.ifc;

import java.util.Properties;

public interface ITestDataHolder {

	public void merge(Properties prop) throws Exception;
	public Object get(String name);
	
}

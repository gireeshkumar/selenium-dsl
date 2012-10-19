package com.giri.target.ifc;

public interface ITestDataStore {
	public void put(String chain, Object value);
	public Object get(String chain);
}

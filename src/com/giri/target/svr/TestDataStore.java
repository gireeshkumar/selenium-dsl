/**
*	License
*	
*	This file is part of The TARGet framework
* 
*   	/__  ___/ // | |     //   ) )  //   ) )
*   	  / /    //__| |    //___/ /  //         ___    __  ___
*   	 / /    / ___  |   / ___ (   //  ____  //___) )  / /
*   	/ /    //    | |  //   | |  //    / / //        / /
*      / /    //     | | //    | | ((____/ / ((____    / /
*   	 
*	    ______     __,             _ ___              ,____                                                   
*      (  /       /  |            ( /   )              /   )                                            
*	     /       /-.-|             /-.-<              /  __                                  
*  Web _/est   _/    |_utomation f/     \_amework by (___/iri
*  -.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.
*	

*  TARGet is free software: you can redistribute it and/or
*  modify it  under  the  terms  of  the  GNU  General Public License as 
*  published  by  the  Free  Software Foundation,  either  version  3 of 
*  the License, or any later version.
*
*  TARGet is distributed in the hope that it will be useful,
*  but  WITHOUT  ANY  WARRANTY;  without  even the  implied  warranty  of
*  MERCHANTABILITY   or   FITNESS   FOR  A  PARTICULAR  PURPOSE.  See the
*  GNU General Public License for more details.
*
*  You should have received a copy of the GNU General Public License
*  along with The SeleniumFlex-API.
*  If not, see http://www.gnu.org/licenses/
*  
* 
*  @Author	Gireesh Kumar G - gireeshkumar.g@gmail.com
*  @Date 	July 2010
*
**/
package com.giri.target.svr;

import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import com.giri.target.ifc.ITestDataStore;

/**
 * @author U20463
 *
 */
public class TestDataStore implements ITestDataStore {

	private static final TestDataStore _INSTANCE = new TestDataStore();
	private HashMap<String, Object> mainHolder = new HashMap<String, Object>();
	private Properties currentTestInputData;
	private TestDataStore(){}
	public static TestDataStore getInstance(){
		return _INSTANCE;
	}
	
	public void put(Properties props) {
		if(props != null){
			Set<Object> keys = props.keySet();
			for (Object object : keys) {
				put(object.toString(), props.get(object));
			}
		}
	}
	
	public void put(String chain, Object value){

		final int idx = chain.lastIndexOf('.');
		if(idx != -1){
			final String subchain = chain.substring(0, idx);
			final String key = chain.substring((idx+1), chain.length());
			getHolder(subchain).put(key, value);
		}else{
			mainHolder.put(chain, value);
		}

		
	}
	
	public Object get(String chain){
		Object value = null;
		
		final int idx = chain.lastIndexOf('.');
		if(idx != -1){
			final String subchain = chain.substring(0, idx);
			final String key = chain.substring((idx+1), chain.length());
			value =  getHolder(subchain).get(key);
			if(value instanceof java.util.HashMap){
				value = null;
			}
		}else{
			value =  mainHolder.put(chain, value);
		}
		return value;
	}
	
	private HashMap<String, Object> getHolder(String chain){
		String[] keys = chain.split("\\.");
		HashMap<String, Object> currentHolder = mainHolder;
		for(String key : keys){
			if(currentHolder.containsKey(key)){
				currentHolder = (HashMap<String, Object>) currentHolder.get(key);
			}else{
				HashMap<String, Object> temp = new HashMap<String, Object>();
				currentHolder.put(key, temp);
				currentHolder = temp;
			}
		}
		return currentHolder;
	}
	
	/**
	 * @param data
	 */
	public void setCurrentTestInputData(Properties data) {
		this.currentTestInputData = data;
	}
	
	public Properties getCurrentTestInputData(){
		return this.currentTestInputData;
	}
	
	public static void main(String[] args) {
		TestDataStore store = TestDataStore.getInstance();
		store.put("com.ge.test.name", "testing 01");
		store.put("author", "gireesh");
		store.put("version", "1.1");
		store.put("ge.vendor","ust");
		store.put("com.ge.test.location", "trivandrum");
		
		System.out.println("com.ge.test.name :"+(store.get("com.ge.test.name")));
		System.out.println("author :"+(store.get("author")));
		System.out.println("version :"+(store.get("version")));
		System.out.println("ge.vendor :"+(store.get("ge.vendor")));
		System.out.println("com.ge.test.location :"+(store.get("com.ge.test.location")));
	}
	
	
}

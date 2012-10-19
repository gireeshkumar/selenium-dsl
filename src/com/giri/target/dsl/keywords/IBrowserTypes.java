package com.giri.target.dsl.keywords;

import com.giri.target.dsl.ifc.IDataKeyword;

public interface IBrowserTypes extends IDataKeyword{
	
	enum TYPE{
		IE("ie"), 
		FIREFOX("firefox"), 
		OPERA("opera"), 
		SAFARI("safari"), 
		CHROME("chrome");
		
		private String _value;
		TYPE(String val){
			_value = val;
		}
		
		public String value(){
			return _value;
		}
		
	}
	
	public TYPE getType();
	
}

package com.giri.target.dsl.shell;

public class MyStringBuilder {

	private  StringBuilder _builder;
	private ITextChangeListener _listener;
	
	public MyStringBuilder(){
		_builder = new StringBuilder();
	}
	
	public StringBuilder delete(int arg0, int arg1) {
		return _builder.delete(arg0, arg1);
	}

	public int length(){
		return _builder.length();
	}
	
	public void addChangeListener(ITextChangeListener listener){
		this._listener = listener;
	}

	public MyStringBuilder append(CharSequence arg0) {
		_builder.append(arg0);
		_listener.textChanged(this);
		return this;
	}

	public MyStringBuilder append(String arg0) {
		_builder.append(arg0);
		_listener.textChanged(this);
		return this;
	}

	public String toString() {
		return _builder.toString();
	}
	
}

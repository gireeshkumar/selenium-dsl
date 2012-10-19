package com.giri.target.svr;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class ExternalScriptEngine {

	public static Object execute(String type, Reader reader, HashMap<String, Object> context, Reader preprocess) throws Exception{
		final ScriptEngineManager factory = new ScriptEngineManager();
		final ScriptEngine engine = factory.getEngineByName(type);
		
		if(engine == null){
			throw new Exception("No such script engine configured - "+type);
		}
		
		push(engine, context);
		push(engine, ThreadContext.getAll());
		
		/*
		 * Is there a better way to merge two scripts ?
		 */
		StringBuilder sb = new StringBuilder();
		
		if(preprocess != null){
			int c;
			while((c = preprocess.read()) != -1){
				sb.append((char)c);
			}
		}
		
		if(reader != null){
			int c;
			while((c = reader.read()) != -1){
				sb.append((char)c);
			}
		}
		
		System.out.println("==============Executing script ["+type+"]");
        Object result = engine.eval(sb.toString());
        
        System.out.println("Result :"+result);
        System.out.println("===================*=*=*===================");
        return result;
	}
	private static void push(ScriptEngine engine,
						Map<String, Object> context) {
		if(context != null){
			final Set<String> keys = context.keySet();
			for (String key : keys) {
				engine.put(key, context.get(key));
			}
		}
	}
	public static Object execute(String type, Reader reader, HashMap<String, Object> context) throws Exception{
		return execute(type, reader, context, null);
	}
	public static Object execute(String type, String scriptToExecute, HashMap<String, Object> context) throws Exception{
		System.out.println("==============Executing script ["+type+"]=========\n"+scriptToExecute);
		return execute(type, new StringReader(scriptToExecute), context, null);		
	}

}

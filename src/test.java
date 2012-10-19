import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import bsh.engine.BshScriptEngineFactory;


public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		final ScriptEngineManager factory = new ScriptEngineManager();
		final ScriptEngine engine = factory.getEngineByName("groovy");

		System.out.println(engine);
		
		engine.put("vrslt", "true");
		
		System.out.println("Result:"+engine.eval("vrslt == 'true'"));
	}

}

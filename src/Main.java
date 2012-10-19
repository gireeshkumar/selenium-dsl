import java.io.InputStream;

import com.giri.target.dsl.TARGetDSL;
import com.giri.target.dsl.dict.ProjectDictionary;

public class Main {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		final InputStream stream = newStream("testcase1.tdsl");
		final InputStream dictionaryStream = newStream("google.dictionary.xml");
		
		//new TARGetDSL(new ProjectDictionary(dictionaryStream)).process(stream);
	}

	private static InputStream newStream(String string) {
		return Main.class.getClassLoader().getResourceAsStream(string);
	}

}

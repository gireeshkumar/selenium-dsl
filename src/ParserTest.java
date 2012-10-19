import java.util.ArrayList;

import com.giri.target.dsl.ifc.IKeyword;
import com.giri.target.dsl.ifc.IStatement;

import de.susebox.jtopas.Flags;
import de.susebox.jtopas.StandardTokenizer;
import de.susebox.jtopas.StandardTokenizerProperties;
import de.susebox.jtopas.StringSource;
import de.susebox.jtopas.Token;
import de.susebox.jtopas.Tokenizer;
import de.susebox.jtopas.TokenizerProperties;


public class ParserTest {

	private static final TokenizerProperties PROPERTIES = new StandardTokenizerProperties();
	private static final Tokenizer TOKENIZER = new StandardTokenizer(PROPERTIES);

	static {
		PROPERTIES.setParseFlags(Flags.F_NO_CASE);

		PROPERTIES.addString("\"", "\"", "\\");
		PROPERTIES.addLineComment("//");
		PROPERTIES.addBlockComment("/*", "*/");
		PROPERTIES.addPattern("\\$\\{.*?\\}");

		
		PROPERTIES.addKeyword("and");
		
	}
	
	public static void main(String[] args) throws Exception{
		String line = args[0];
		

		TOKENIZER.setSource(new StringSource(line));
		System.out.println(line);
		while (TOKENIZER.hasMoreToken()) {
			Token token = TOKENIZER.nextToken();
			System.out.println(token);
		}
	}
	
}

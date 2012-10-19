package com.giri.target.dsl;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.biff.EmptyCell;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.giri.target.dsl.dict.DictionaryItem;
import com.giri.target.dsl.dict.Metadata;
import com.giri.target.dsl.dict.ProjectDictionary;
import com.giri.target.dsl.ifc.IActionKeyword;
import com.giri.target.dsl.ifc.IKeyword;
import com.giri.target.dsl.ifc.INonExecutableStatement;
import com.giri.target.dsl.ifc.IStatement;
import com.giri.target.dsl.ifc.IStatementExecutor;
import com.giri.target.dsl.ifc.ITestDataHolder;
import com.giri.target.dsl.impl.NonExecutableStatement;
import com.giri.target.dsl.impl.StatementGenerator;
import com.giri.target.svr.TestDataStore;

import de.susebox.jtopas.Flags;
import de.susebox.jtopas.StandardTokenizer;
import de.susebox.jtopas.StandardTokenizerProperties;
import de.susebox.jtopas.StringSource;
import de.susebox.jtopas.Token;
import de.susebox.jtopas.Tokenizer;
import de.susebox.jtopas.TokenizerProperties;

public class TARGetDSL {

	private static final Metadata METADATA = Metadata.load();
	private static final TokenizerProperties PROPERTIES = new StandardTokenizerProperties();
	private static final Tokenizer TOKENIZER = new StandardTokenizer(PROPERTIES);

	static {
		PROPERTIES.setParseFlags(Flags.F_NO_CASE);

		PROPERTIES.addString("\"", "\"", "\\");
		PROPERTIES.addLineComment("//");
		PROPERTIES.addBlockComment("/*", "*/");
		PROPERTIES.addPattern("\\$\\{.*?\\}");
		
		PROPERTIES.addKeyword("and");  // statement split keyworkd
	}

	private final ProjectDictionary projectDictionary;
	private ITestDataHolder dataholder;

	public TARGetDSL(final ITestDataHolder dataholder, final ProjectDictionary l_projectDictionary) {
		projectDictionary = l_projectDictionary;
		this.dataholder = dataholder;

		final List<IKeyword> keywords = METADATA.getKeyWords();
		if (keywords != null) {
			for (final IKeyword key : keywords) {
				PROPERTIES.addKeyword(key.name());
			}
		}
		
		if(projectDictionary != null){
			final List<IKeyword> keywords1 = projectDictionary.getKeyWords();
			if (keywords1 != null) {
				for (final IKeyword key : keywords1) {
					PROPERTIES.addKeyword(key.name());
				}
			}
		}
		
		
		
//		PROPERTIES.addKeyword("_flex");
	}

	public String generateDictionaryItemName(final INonExecutableStatement nstmt) {
		final NonExecutableStatement nexe = (NonExecutableStatement) nstmt;
		final ArrayList<Token> tokens = nexe.getTokens();
		final StringBuffer strb = new StringBuffer();
		if (tokens != null) {
			for (final Token tk : tokens) {
				if (tk.getType() == Token.NORMAL) {
					strb.append(tk.getImage());
				}
			}
		}
		return (strb.length() > 0 ? strb.toString().toLowerCase() : null);
	}

	public DictionaryItem getDictionaryItem(final String key) {
		DictionaryItem item = projectDictionary == null ? null : projectDictionary.getDictionaryItem(key);
		if (item == null) {
			item = METADATA.getDictionaryItem(key);
		}
		return item;
	}

	public static void main(String[] args) throws Exception{
		final TARGetDSL dsl = new TARGetDSL(null, null);
		
		mainHelper(dsl, "click next and verifyalert \"Should have 5\"");
		mainHelper(dsl, "click next and verifyalert \"Should have 5 and 6\"");
		mainHelper(dsl, "open ${application.url} in IE");
		mainHelper(dsl, "click on \"AcceptButton\" and go next and open page \"x and y\"");
		
	}
	private static void mainHelper(final TARGetDSL dsl, final String line) throws Exception{
		final ArrayList<IStatement> stmts = dsl.parseLine(line);
		System.out.println("\n-----------------------------------------------");
		System.out.println("Orginal Statement:"+line);
		int count = 1;
		for (IStatement iStatement : stmts) {
			System.out.println("Statement["+count+"] : "+iStatement.toString());
		}
		System.out.println("\n");
	}
	
	private ArrayList<IStatement> parseLine(String line) throws Exception {
		if ((line == null) || ((line = line.trim()).length() == 0)) {
			return null;
		}
		
		final ArrayList<IStatement> stmts = new ArrayList<IStatement>();
		
		IStatement stmt = null;
		IKeyword keyword = null;

		TOKENIZER.setSource(new StringSource(line));
		Token token;
		
		ArrayList<Token> tokens = new ArrayList<Token>();
		ArrayList<Token> strings = new ArrayList<Token>();
		ArrayList<Token> otherKeywords = new ArrayList<Token>();
		ArrayList<Token> otherwords = new ArrayList<Token>();
		
		boolean iscomment = true;
		int tokencount = 0;
		System.out.println("TARGetDSL#parseLine :-> "+line);
		while (TOKENIZER.hasMoreToken()) {
			token = TOKENIZER.nextToken();
			
			if (token.getType() == Token.KEYWORD) {
				if("and".equalsIgnoreCase(token.getImage())){
					// Split statement 
					
					if (tokencount > 0) {
						iscomment = false;
					}
					
					if (keyword != null) {
						stmt = StatementGenerator.newStatement(this, line, keyword, strings, otherwords, otherKeywords, tokens);
					} else {
						stmt = StatementGenerator.newStatement(this, line, iscomment, strings, otherwords, otherKeywords, tokens);
						System.err.println("Dont know what to do with this statement[IsComment:" + iscomment + "] '" + line + "'");
					}
					
					stmts.add(stmt);
					
					stmt = null;
					keyword = null;
					iscomment = true;
					tokens = new ArrayList<Token>();
					strings = new ArrayList<Token>();
					otherKeywords = new ArrayList<Token>();
					otherwords = new ArrayList<Token>();
					continue;
				}
				if (keyword == null) {
					keyword = findKeywordByName(token.getImage());
					if(!(keyword instanceof IActionKeyword)){
						otherKeywords.add(token);
						keyword = null;
					}
				} else {
					otherKeywords.add(token);
				}
			} else if (token.getType() == Token.STRING) {
				strings.add(token);
			}else if (token.getType() == Token.PATTERN){
				final String img = token.getImage();
				final String value;
				if(img.startsWith("${") && img.endsWith("}") && dataholder != null){
					final String key = img.substring(2, img.length()-1);
					final String prpvalue = (String) dataholder.get(key);
					if(prpvalue == null){
						Object obj = TestDataStore.getInstance().get(key);
						value = obj == null ? null : obj.toString();
					}else{
						value = prpvalue;
					}
				}else{
					value = img;
				}
				strings.add(new Token(Token.STRING, value));
			}else if (token.getType() == Token.NORMAL) {
				final String img = token.getImage();
				if(!METADATA.isIgnored(img)){
					otherwords.add(token);
				}
			}
			if (token.getType() != Token.EOF) {
				tokencount++;
			}
			
			tokens.add(token);
		}
		if (tokencount > 0) {
			iscomment = false;
		}
		if (keyword != null) {
			stmt = StatementGenerator.newStatement(this, line, keyword, strings, otherwords, otherKeywords, tokens);
		} else {
			stmt = StatementGenerator.newStatement(this, line, iscomment, strings, otherwords, otherKeywords, tokens);
			System.err.println("Dont know what to do with this statement[IsComment:" + iscomment + "] '" + line + "'");
		}
		stmts.add(stmt);
		return stmts;
	}

	public IKeyword findKeywordByName(final String name) {
		IKeyword keyword = Metadata.getInstance().findKeywordByName(name);
		if(keyword == null){
			keyword = projectDictionary.findKeywordByName(name);
		}
		return keyword;
	}
	
	public void process(final BufferedReader reader,
			final IStatementExecutor stmtExe) throws Exception {
		String line;
		while ((line = reader.readLine()) != null) {
			process(line, stmtExe);
		}
	}
	
	public void process(final String line, final IStatementExecutor stmtExe)  throws Exception{
		final ArrayList<IStatement> stmts = parseLine(line);
		if(stmts == null || stmts.size() == 0){
			return ;
		}
		for (IStatement iStatement : stmts) {
			if (stmts == null) {
				return;
			}
			processStatement(stmtExe, iStatement);
		}
	}

	public void process(Workbook workbook, WritableWorkbook writableWorkbook, final IStatementExecutor stmtExe)  throws Exception{
		Sheet[] sheets = workbook.getSheets();
		if(sheets != null){
			int idx = 0;
			for(Sheet sheet:sheets){
				if(!sheet.getName().startsWith("_")){
					
					process(workbook, sheet, writableWorkbook.getSheet(idx), stmtExe);
				}
				idx++;
			}
		}
	}
	
	//TODO fixed to read from Row 11,1
	private void process(Workbook workbook, Sheet sheet,
			WritableSheet writableSheet, IStatementExecutor stmtExe)  throws Exception{
		System.out.println("Processing sheet - "+sheet.getName());
		
		//int cols = sheet.getColumns();

		Hashtable<String, Properties> dataset = new Hashtable<String, Properties>();
		// load data from 'Data-Tab'
		//'Data-Tab' can be comma separated, same test case should run for multiple data set
		// TODO data can also be supplied as separate Excel sheet
		for(int row = 0; row < 11; row++){
			Cell[] rowdata = sheet.getRow(row);
			if(rowdata != null && rowdata.length > 0){
				if(rowdata[0].getContents().equalsIgnoreCase("Data-Tab")){
					String dataSheetNames = rowdata[1].getContents();
					if(dataSheetNames != null && (dataSheetNames = dataSheetNames.trim()).length() > 0){
						final String[] sheetArray = dataSheetNames.split(",");
						loaddata(sheetArray, workbook, dataset);
						loadDataFromExternalExcelFile(dataset, sheetArray);
						break;
					}
				}
			}
		}
		
		if(dataset.size() > 0){
			Collection<Properties> list = dataset.values();
			for(Properties prop:list){
				runTest(sheet,writableSheet, stmtExe, prop);
			}
			
		}else{
			// will run once anyway
			runTest(sheet,writableSheet, stmtExe, null);
		}
		
		
	}

	private void loadDataFromExternalExcelFile(Hashtable<String, Properties> dataset,
			String[] sheetArray) throws Exception{
		final String testDataFileAddr = System.getProperty("test.data");
		if(testDataFileAddr != null && testDataFileAddr.indexOf(".xls") != -1){
			Workbook book = Workbook.getWorkbook(new File(testDataFileAddr));
			loaddata(sheetArray, book, dataset);
		}
	}

	private void loaddata(String[] sheetArray, Workbook book, Hashtable<String, Properties> dataset) throws Exception{
		if(sheetArray != null){
			for(String sheetname:sheetArray){
				Properties data = loadData(book.getSheet(sheetname.trim()));
				if(data != null){
					dataset.put(sheetname, data);
				}
			}
		}
	}

	private void runTest(Sheet sheet,WritableSheet writableSheet, IStatementExecutor stmtExe, Properties data) throws Exception{
		if(data != null){
			if(data.size() > 0){
				dataholder.merge(data);
			}
			System.out.println("Running test using data from sheet -> "+data.getProperty("data._sheet._name"));
		}else{
			System.out.println("Running test without data");
		}
		int rows = sheet.getRows();
		for(int row = 10; row < rows; row++){
			Cell[] rowdata = sheet.getRow(row);
			if(rowdata != null && rowdata.length > 0){
//				WritableCell cell = writableSheet.getWritableCell(row, 3);
				try{
					process(rowdata.length >= 2 ? rowdata[1] : null, stmtExe);
					process(rowdata.length >= 3 ? rowdata[2] : null, stmtExe);
//					
//					((EmptyCell)cell)
//					if (cell.getType() == CellType.LABEL)
//					{
//					  Label l = (Label) cell;
//					  l.setString("SUCCESS");
//					} 
				}catch(Exception e){
					System.err.println("Error:"+e.getMessage());
//					if (cell.getType() == CellType.LABEL)
//					{
//					  Label l = (Label) cell;
//					  l.setString("FAILED");
//					} 
//					WritableCell ecell = writableSheet.getWritableCell(row, 4);
//					((Label)ecell).setString(e.getMessage());
				}
			}
		}
		process("close testing", stmtExe);
	}
	
	private void process(Cell cell, IStatementExecutor stmtExe) throws Exception {
		if(cell == null){
			return;
		}
		String line = cell.getContents();
		if(line != null && (line = line.trim()).length() > 0){
			process(line, stmtExe);
		}
	}

	private Properties loadData(Sheet sheet) throws Exception {
		if(sheet == null){
			return null;
		}
		System.out.println("Loading data from sheet ->"+sheet.getName());
		int rows = sheet.getRows();
		Properties props = new Properties();
		props.put("data._sheet._name", sheet.getName());
		for(int i = 0; i < rows; i++){
			Cell[] row = sheet.getRow(i);
			if(row != null && row.length >= 2){
				props.put(row[0].getContents(), row[1].getContents());
			}
		}
		return props;
	}

	public void process(final InputStream stream,
			final IStatementExecutor stmtExe) throws Exception {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(
				stream));
		process(reader, stmtExe);
	}

	private void processStatement(final IStatementExecutor stmtExe, final IStatement stmt) throws Exception {
		System.out.println("Process Statement - "	+ stmt.toString());
		stmtExe.execute(stmt);
	}
}
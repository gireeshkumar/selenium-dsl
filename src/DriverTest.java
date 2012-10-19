import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class DriverTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\U20463\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe");
		// TODO Auto-generated method stub
		open("firefox", "http://google.com");
//		open("ie", "http://google.com");
//		open("chrome", "http://google.com");
//		open("opera", "http://google.com");
//		open("httpunit", "http://google.com");
	}

	private static void open(String type, String url) throws Exception{
		newWebDriver(type).get(url);
	}
	private static WebDriver newWebDriver(String browser) throws Exception {
		String driverclassStr = null;
		if(driverClasses != null){
			driverclassStr = (String) driverClasses.get(browser);
		}
		final Class<? extends WebDriver> driverClass;
		if(driverclassStr == null){
			// Default is Firefox, possibility of available in ALL OS
			driverClass = FirefoxDriver.class;
		}else{
			driverClass = (Class<? extends WebDriver>) Class.forName(driverclassStr);
		}
		System.out.println(driverclassStr);
		return driverClass.newInstance();
	}
	
	private static Properties loadDriverClasses() {
		try {
			final InputStream stream = DriverTest.class.getClassLoader().getResourceAsStream("browsertypes.properties");
			Properties props = new Properties();
			props.load(stream);
			return props;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static final Properties driverClasses = loadDriverClasses();

}

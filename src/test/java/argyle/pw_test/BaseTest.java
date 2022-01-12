package argyle.pw_test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import utils.EnvData;

import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BaseTest {

	public static final Logger log = LoggerFactory.getLogger(BaseTest.class);

	protected Playwright playwright;
	protected Browser browser;
	protected Page page;

	protected EnvData data = new EnvData();

	@BeforeClass(alwaysRun = true)
	protected void init(){

		playwright = Playwright.create();

		browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
		page = browser.newPage();

	}

	@AfterClass(alwaysRun = true)
	public void cleanup(){
		log.info("Closing page");
		page.close();
		browser.close();
		playwright.close();
	}

	@BeforeMethod(alwaysRun = true)
	protected void beforeMethod(Method method) throws Exception {
		MDC.put("logFileName", method.getDeclaringClass().getSimpleName());

		log.debug("Running test {}", method.getName());

		log.debug("navigating to homepage {}", data.getURL());
		page.navigate(data.getURL());

	}

	public void screenshot(){
		String testName = Thread.currentThread().getStackTrace()[2].getMethodName();
		String timestamp = new SimpleDateFormat("HH:mm:ss").format( Calendar.getInstance().getTime());
		String filename = String.format("%s-%s.png", testName, timestamp);

		log.info("Saving screenshot to file {}", filename);
		page.screenshot(new Page.ScreenshotOptions()
				.setPath(Paths.get(filename))
				.setFullPage(true));
	}

}

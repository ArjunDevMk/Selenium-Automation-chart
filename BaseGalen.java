package galen;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.galenframework.api.Galen;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;

public class BaseGalen {
	// TODO: will be moved to jenkins config in future
	String input = "http://charts3.equitystory/chart/kpn";
	String username = "adev";
	String country = "UK";

	// String input = JOptionPane.showInputDialog(this, "ENTER THE CHART URL");
	// String username = JOptionPane.showInputDialog(this, "Enter Your Windows
	// Username");
	// String[] countries = new String[] { "Germany", "UK", "HongKong",
	// "Singapore", "France", "Russia", "Switzerland","Dubai", "US" };
	// String country = (String) JOptionPane.showInputDialog(null, "Choose the
	// country of the chart", "Country Selection",JOptionPane.QUESTION_MESSAGE,
	// null, countries, countries[0]);

	public static List<String> languages = null;
	public static List<String> urlArray = null;
	public static LayoutReport layoutReport = null;
	public static String browser = null;
	public static String lang = null;
	WebDriver driver;

	public void warmUp() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public File getLatestFilefromDir(String downloadPath) {
		File dir = new File(downloadPath);
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			return null;
		}

		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				lastModifiedFile = files[i];
			}
		}
		return lastModifiedFile;
	}

	@DataProvider(name = "irLanguage")
	public Object[][] lang() {
		
		Scanner s = new Scanner(
	            "Germany         /German/\n" +
	            "Germany         /English/\n" +
	            "UK         /English/\n" +
	            "US         /English/\n" +
	            "Russia         /English/\n" +
	            "Russia         /Russian/\n" +
	            "Singapore         /English/\n" +
	            "Singapore         /ChineseSimple/\n" +
	            "Switzerland         /German/\n" +
	            "Switzerland         /English/\n" +
	            "HongKong         /ChineseTraditional/\n" +
	            "HongKong         /English/\n" +
	            "HongKong         /ChineseSimple/\n" +
	            "France         /English/\n" +
	            "France         /French/\n" +
				"Dubai         /English/\n" +
				"Dubai         /Arabic/\n");
		Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();
		while (s.hasNext()) {

			String key = s.next();
			if (!map.containsKey(key))
				map.put(key, new LinkedList<String>());

			map.get(key).add(s.next());

		}
		urlArray = map.get(country);
		String[][] shades = new String[urlArray.size()][2];
		for (int i = 0; i < urlArray.size(); i++) {
		    shades[i][0] = urlArray.get(i).toString();
			shades[i][1] = input + urlArray.get(i).toString();
			lang = shades[i][0];
			//System.out.println(shades[i][0]);
		}

		return shades;
	}

	@BeforeMethod
	@Parameters("browser")

	public void beforeTest(String browser) {
		try {

			if (browser.equalsIgnoreCase("ie")) {
				System.setProperty("webdriver.ie.driver", "C:\\IEDriverServer.exe");
				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
				driver = new InternetExplorerDriver(capabilities);
			} else if (browser.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				// options.addArguments("headless");
				driver = new ChromeDriver(options);
				Map<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("profile.default_content_settings.popups", 0);
				chromePrefs.put("download.default_directory", "C:\\Users\\" + username + "\\Downloads");
				chromePrefs.put("profile.content_settings.exceptions.automatic_downloads.*.setting", 1);
				chromePrefs.put("download.prompt_for_download", false);
			}
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		}

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

//	@AfterTest(alwaysRun = true)
//	public void tearDown() {
//		driver.quit();
//		driver = null;
//	}
	
	@AfterMethod
	public void tearDown1() {
		driver.quit();
	}
}

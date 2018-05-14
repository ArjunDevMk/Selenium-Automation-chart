package galen;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;

import com.galenframework.api.Galen;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;

import galen.GalenEclipse;
import galen.GalenEclipse;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.internal.selenesedriver.IsElementDisplayed;
import org.openqa.selenium.internal.seleniumemulation.IsElementPresent;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

public class GalenEclipse extends BaseGalen {
	@SuppressWarnings("deprecation")

	public static void main(String[] args) {
		TestListenerAdapter tla = new TestListenerAdapter();
		TestNG testng = new TestNG();
		testng.setTestClasses(new Class[] { GalenEclipse.class });
		testng.addListener(tla);
		testng.run();
	}

	@Test(dataProvider = "irLanguage")

	public void Check_whether_the_Average_is_getting_off_when_switching_to_intraday(String lang, String c_url) {
		driver.get(c_url);
		driver.manage().window().maximize();
		warmUp();

		WebElement daterange3month = driver
				.findElement(By.cssSelector("#range-selector > ul:nth-child(1) > li:nth-child(3)"));
		WebElement daterangeintraday = driver
				.findElement(By.cssSelector("#range-selector > ul:nth-child(1) > li:nth-child(1)"));

		try {
			Select select = new Select(driver.findElement(By.className("smaverage-select")));
			WebElement option = select.getFirstSelectedOption();
			String Value = option.getText();
			select.selectByVisibleText("40");
			daterangeintraday.click();
			Value = Value.split(" ")[0];
			Assert.assertEquals(Value, "Off");
		} catch (NoSuchElementException en) {
			Select select = new Select(driver.findElement(By.className("selectric-wrapper selectric-smaverage-select selectric-responsive")));
			WebElement option = select.getFirstSelectedOption();
			String Value = option.getText();
			select.selectByVisibleText("40");
			daterangeintraday.click();
			Value = Value.split(" ")[0];
			Assert.assertEquals(Value, "Off");
		}

		driver.switchTo().activeElement();
		daterange3month.click();

		warmUp();

	}

	@Test(dataProvider = "irLanguage")
	public void check_average_is_disabled_in_intraday(String lang, String c_url) {
		driver.get(c_url);
		driver.manage().window().maximize();
		warmUp();
		WebElement daterangeintraday = driver
				.findElement(By.cssSelector("#range-selector > ul:nth-child(1) > li:nth-child(1)"));
		daterangeintraday.click();
		try {
			WebElement average = driver.findElement(By.className("smaverage-select"));
			Assert.assertEquals(false, average.isEnabled());
		} catch (NoSuchElementException e) {
			WebElement average1 = driver
					.findElement(By.className("selectric-wrapper selectric-smaverage-select selectric-responsive"));
			Assert.assertEquals(false, average1.isEnabled());
		}

	}

	@Test(dataProvider = "irLanguage")

	public void check_benchark_is_disabled_in_intraday(String lang, String c_url) {
		driver.get(c_url);
		driver.manage().window().maximize();
		warmUp();
		WebElement daterangeintraday = driver
				.findElement(By.cssSelector("#range-selector > ul:nth-child(1) > li:nth-child(1)"));
		daterangeintraday.click();
		WebElement indeces = driver.findElement(By.className("benchmark-select"));
		Assert.assertEquals(false, indeces.isEnabled());
	}

	@Test(dataProvider = "irLanguage")

	public void check_peers_is_disabled_in_intraday(String lang, String c_url) {
		driver.get(c_url);
		driver.manage().window().maximize();
		warmUp();
		WebElement daterangeintraday = driver
				.findElement(By.cssSelector("#range-selector > ul:nth-child(1) > li:nth-child(1)"));
		daterangeintraday.click();
		WebElement peers = driver.findElement(By.className("benchmark-select"));
		Assert.assertEquals(false, peers.isEnabled());

	}

	@Test(dataProvider = "irLanguage")

	public void Check_the_download_pdf_file_name(String lang, String c_url) {
		// System.setProperty("webdriver.chrome.driver",
		// "C:\\chromedriver.exe");
		// ChromeOptions options = new ChromeOptions();
		// options.addArguments("headless");
		// driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get(c_url);
		warmUp();

		String company_name = driver.getTitle();
		String downloadPath = "C:\\Users\\" + username + "\\Downloads";
		WebElement Download = driver.findElement(By.cssSelector("#chartexport > a.chart-pdf"));
		Download.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		File getLatestFile = getLatestFilefromDir(downloadPath);
		String fileName = getLatestFile.getName();
		company_name = company_name.split(" ")[0];
		fileName = fileName.split("-")[0];
		fileName = fileName.split(" ")[0];
		System.out.println(company_name);
		System.out.println(fileName);
		Assert.assertTrue(company_name.contains(fileName));

	}

	@Test(dataProvider = "irLanguage")

	public void Check_the_download_png_file_name(String lang, String c_url) {
		// System.setProperty("webdriver.chrome.driver",
		// "C:\\chromedriver.exe");
		// ChromeOptions options = new ChromeOptions();
		// // options.addArguments("headless");
		// driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get(c_url);

		warmUp();

		String company_name = driver.getTitle();
		String downloadPath = "C:\\Users\\" + username + "\\Downloads";
		WebElement Download = driver.findElement(By.cssSelector("#chartexport > a.chart-png"));
		Download.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		File getLatestFile = getLatestFilefromDir(downloadPath);
		String fileName = getLatestFile.getName();
		company_name = company_name.split(" ")[0];
		fileName = fileName.split("-")[0];
		fileName = fileName.split(" ")[0];
		System.out.println(company_name);
		System.out.println(fileName);
		Assert.assertTrue(company_name.contains(fileName));

	}

	@Test(dataProvider = "irLanguage")

	public void Check_the_download_Excel_file_name(String lang, String c_url) {
		// System.setProperty("webdriver.chrome.driver",
		// "C:\\chromedriver.exe");
		// ChromeOptions options = new ChromeOptions();
		// // options.addArguments("headless");
		// driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get(c_url);

		warmUp();

		String company_name = driver.getTitle();
		String downloadPath = "C:\\Users\\" + username + "\\Downloads";
		try {
			WebElement Download = driver.findElement(By.cssSelector("#chart-footer > div.history-download > a"));
			Download.click();
		} catch (NoSuchElementException e) {
			WebElement Download1 = driver.findElement(By.cssSelector("#chart-footer > div.history-download > div > a"));
			Download1.click();
		}

		warmUp();
		File getLatestFile = getLatestFilefromDir(downloadPath);
		String fileName = getLatestFile.getName();
		company_name = company_name.split(" ")[0];
		fileName = fileName.split("-")[0];
		fileName = fileName.split(" ")[0];
		System.out.println(company_name);
		System.out.println(fileName);

		Assert.assertTrue(company_name.contains(fileName));
	}

	@Test(dataProvider = "irLanguage")
	public void easy_xdm_check(String lang, String c_url) {
		driver.get(c_url);
		driver.manage().window().maximize();
		warmUp();
		Assert.assertTrue(driver.getPageSource().contains("easyXDM"));
	}

	@Test(dataProvider = "irLanguage")
	public void Currency_Checker(String lang, String c_url) {
		driver.get(c_url);
		driver.manage().window().maximize();
		warmUp();

		if (country == "Germany") {
			Assert.assertTrue(driver.getPageSource().contains("EUR"));
		} else if (country == "UK") {
			Assert.assertTrue(driver.getPageSource().contains("GBp"));
		} else if (country == "HongKong") {
			Assert.assertTrue(driver.getPageSource().contains("EUR"));
		} else if (country == "Singapore") {
			Assert.assertTrue(driver.getPageSource().contains("EUR"));
		} else if (country == "France") {
			Assert.assertTrue(driver.getPageSource().contains("EUR"));
		} else if (country == "Dubai") {
			Assert.assertTrue(driver.getPageSource().contains("EUR"));
		} else if (country == "Russia") {
			Assert.assertTrue(driver.getPageSource().contains("EUR"));
		} else if (country == "Switzerland") {
			Assert.assertTrue(driver.getPageSource().contains("CHF"));
		} else if (country == "US") {
			Assert.assertTrue(driver.getPageSource().contains("EUR"));
		}
	}

	@Test(dataProvider = "irLanguage")
	public void disclaimer_check(String lang, String c_url) {
		driver.get(c_url);
		driver.manage().window().maximize();
		warmUp();
		if (country == "Germany") {
			Assert.assertTrue(driver.getPageSource().contains("Data provided by vwd group / EQS Group AG."));
		} else if (country == "UK") {
			Assert.assertTrue(driver.getPageSource().contains(
					"This is a solution provided by <a href='http://www.eqs.co.uk/' target='_blank'>EQS Group</a>, incorporating their financial market data and investor tools on this site. The data is provided by vwd group. Prices at least 15 minutes delayed. <a href='http://www.eqs.co.uk/websites/eqs_uk/English/8/legal.html' target='_blank'>Terms & Conditions</a>"));
		} else if (country == "HongKong") {
			Assert.assertTrue(driver.getPageSource().contains(
					"HKEX INFORMATION SERVICES LIMITED, ITS HOLDING COMPANIES AND/OR ANY SUBSIDIARIES OF SUCH HOLDING COMPANIES ENDEAVOUR TO ENSURE THE ACCURACY AND RELIABILITY OF THE INFORMATION PROVIDED BUT DO NOT GUARANTEE ITS ACCURACY OR RELIABILITY AND ACCEPT NO LIABILITY (WHETHER IN TORT OR CONTRACT OR OTHERWISE) FOR ANY LOSS OR DAMAGE ARISING FROM ANY INACCURACIES OR OMISSIONS"));
		} else if (country == "Singapore") {
			Assert.assertTrue(driver.getPageSource().contains(
					"Disclaimer: EQS Group endeavours to ensure the accuracy and reliability of the information provided. However, the Group does not guarantee the accuracy and reliability of the information, and accepts no liability (whether in tort or contract or otherwise) for any loss or damage arising from any inaccuracies or omissions."));
		} else if (country == "France") {
			Assert.assertTrue(driver.getPageSource().contains("EUR"));
		} else if (country == "Dubai") {
			Assert.assertTrue(driver.getPageSource().contains("Data provided by vwd group / EQS Group AG"));
		} else if (country == "Russia") {
			Assert.assertTrue(driver.getPageSource().contains("EUR"));
		} else if (country == "Switzerland") {
			Assert.assertTrue(driver.getPageSource().contains("EUR"));
		} else if (country == "US") {
			Assert.assertTrue(driver.getPageSource().contains("EUR"));
		}

	}

	@Test(dataProvider = "irLanguage")
	public void TimeZone_Check(String lang, String c_url) {
		driver.get(c_url);
		driver.manage().window().maximize();
		warmUp();
		if (country == "Germany") {
			Assert.assertTrue(driver.getPageSource().contains("CET"));
		} else if (country == "UK") {
			Assert.assertTrue(driver.getPageSource().contains("GMT"));
		} else if (country == "HongKong") {
			Assert.assertTrue(driver.getPageSource().contains("HKT"));
		} else if (country == "Singapore") {
			Assert.assertTrue(driver.getPageSource().contains("SGT"));
		} else if (country == "France") {
			Assert.assertTrue(driver.getPageSource().contains("CEST"));
		} else if (country == "Dubai") {
			Assert.assertTrue(driver.getPageSource().contains("GMT"));
		} else if (country == "Russia") {
			Assert.assertTrue(driver.getPageSource().contains("MSK"));
		} else if (country == "Switzerland") {
			Assert.assertTrue(driver.getPageSource().contains("CET"));
		} else if (country == "US") {
			Assert.assertTrue(driver.getPageSource().contains("EST"));
		}
	}

	@Test(dataProvider = "irLanguage")
	public void Negative_or_zero_value_check_yaxis(String lang, String c_url) {
		driver.get(c_url);
		driver.manage().window().maximize();
		warmUp();
		try {
			WebElement yaxis = driver.findElement(By.cssSelector("g.highcharts-axis-labels:nth-child(24)"));
			String yaxis1 = yaxis.getText();
			Assert.assertFalse(yaxis1.contains("-"));
		} catch (NoSuchElementException e) {
			WebElement yaxis = driver.findElement(By.cssSelector("g.highcharts-axis:nth-child(10)"));
			String yaxis1 = yaxis.getText();
			Assert.assertFalse(yaxis1.contains("-"));
		}

	}

	@Test(dataProvider = "irLanguage")
	public void Check_VolumeTable_intraday_in_UK(String lang, String c_url) {
		driver.get(c_url);
		driver.manage().window().maximize();
		warmUp();
		if (country == "UK") {
			WebElement daterangeintraday = driver
					.findElement(By.cssSelector("#range-selector > ul:nth-child(1) > li:nth-child(1)"));
			daterangeintraday.click();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String volume = driver.findElement(By.className("highcharts-container")).getText();
			Assert.assertFalse(volume.contains("Volume"));

		}
	}

	@Test(dataProvider = "irLanguage")
	public void Date_Format_Check(String lang, String c_url) {
		driver.get(c_url);
		driver.manage().window().maximize();
		warmUp();
		if (country == "Germany") {
			Assert.assertTrue(driver.getPageSource().contains("mm/dd/yyyy"));
		} else if (country == "UK") {
			Assert.assertTrue(driver.getPageSource().contains("dd/mm/yyyy"));
		} else if (country == "HongKong") {
			Assert.assertTrue(driver.getPageSource().contains("dd/mm/yyyy"));
		} else if (country == "Singapore") {
			Assert.assertTrue(driver.getPageSource().contains("dd/mm/yyyy"));
		} else if (country == "France") {
			Assert.assertTrue(driver.getPageSource().contains("dd/mm/yyyy"));
		} else if (country == "Dubai") {
			Assert.assertTrue(driver.getPageSource().contains("dd/mm/yyyy"));
		} else if (country == "Russia") {
			Assert.assertTrue(driver.getPageSource().contains("dd/mm/yyyy"));
		} else if (country == "Switzerland") {
			Assert.assertTrue(driver.getPageSource().contains("dd/mm/yyyy"));
		} else if (country == "US") {
			Assert.assertTrue(driver.getPageSource().contains("mm/dd/yyyy"));
		}
	}

	@Test(dataProvider = "irLanguage")
	public void Check_new_printer_icon_is_implemented(String lang, String c_url) {
		driver.get(c_url);
		driver.manage().window().maximize();
		warmUp();
		Assert.assertTrue(driver.getPageSource().contains("icon-icon-printer"));
	}

	@Test(dataProvider = "irLanguage")
	public void Check_new_download_pdf_icon_is_implemented(String lang, String c_url) {
		driver.get(c_url);
		driver.manage().window().maximize();
		;
		warmUp();
		Assert.assertTrue(driver.getPageSource().contains("icon-icon-pdf"));
	}

	@Test(dataProvider = "irLanguage")
	public void Check_new_download_png_icon_is_implemented(String lang, String c_url) {
		driver.get(c_url);
		driver.manage().window().maximize();
		warmUp();
		Assert.assertTrue(driver.getPageSource().contains("icon-icon-png"));
	}

	@Test(dataProvider = "irLanguage")
	public void Check_the_volume_table_for_pieces(String lang, String c_url) {
		driver.get(c_url);
		driver.manage().window().maximize();
		warmUp();
		Assert.assertFalse(driver.getPageSource().contains("pieces"));
	}
}
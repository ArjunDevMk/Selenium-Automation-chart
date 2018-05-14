package galen;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.Test;

import com.galenframework.api.Galen;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;

public class UITestGalen extends BaseGalen {
	@SuppressWarnings("deprecation")

	public static void main(String[] args) {
		TestListenerAdapter tla = new TestListenerAdapter();
		TestNG testng = new TestNG();
		testng.setTestClasses(new Class[] { GalenEclipse.class });
		testng.addListener(tla);
		testng.run();
	}

	@Test(dataProvider = "irLanguage")
	public void ChartPageLayout_mobile(String lang, String c_url) throws IOException {
		driver.get(c_url);
		driver.manage().window().setSize(new Dimension(375, 667));
		warmUp();

//		if (lang == "/English/") {
			layoutReport = Galen.checkLayout(driver,
					"C://Users//adev//Desktop//Selenium essentials//galen-bin-2.2.5//Basic_Chart_Specification_eng.spec",
					Arrays.asList("mobile"));
//		} else if (lang == "/German/") {
//			layoutReport = Galen.checkLayout(driver,
//					"C://Users//adev//Desktop//Selenium essentials//galen-bin-2.2.5//Basic_Chart_Specification_ger.spec",
//					Arrays.asList("mobile"));
//		}

		List<GalenTestInfo> tests = new LinkedList<GalenTestInfo>();

		GalenTestInfo test = GalenTestInfo.fromString("Chart layout2");

		test.getReport().layout(layoutReport, "iphone6s");

		tests.add(test);

		HtmlReportBuilder htmlReportBuilder = new HtmlReportBuilder();

		htmlReportBuilder.build(tests, "target");

		if (layoutReport.errors() > 0) {
			AssertJUnit.fail("Layout test failed");
		}
	}

	@Test(dataProvider = "irLanguage")
	public void ChartPageLayout_ipadMini(String lang, String c_url) throws IOException {
		driver.get(c_url);
		driver.manage().window().setSize(new Dimension(768, 1024));
		warmUp();

//		if (lang == "/English/") {
//			System.out.println(lang);
			layoutReport = Galen.checkLayout(driver,
					"C://Users//adev//Desktop//Selenium essentials//galen-bin-2.2.5//Basic_Chart_Specification_eng.spec",
					Arrays.asList("tab"));
//		} else if (lang == "/German/") {
//			layoutReport = Galen.checkLayout(driver,
//					"C://Users//adev//Desktop//Selenium essentials//galen-bin-2.2.5//Basic_Chart_Specification_ger.spec",
//					Arrays.asList("tab"));
//		}
		List<GalenTestInfo> tests = new LinkedList<GalenTestInfo>();

		GalenTestInfo test = GalenTestInfo.fromString("Chart layout4");

		test.getReport().layout(layoutReport, "ipadMini");

		tests.add(test);

		HtmlReportBuilder htmlReportBuilder = new HtmlReportBuilder();

		htmlReportBuilder.build(tests, "target");

		if (layoutReport.errors() > 0) {
			AssertJUnit.fail("Layout test failed");
		}
	}

	@Test(dataProvider = "irLanguage")
	public void ChartPageLayout_desktop(String lang, String c_url) throws IOException {
		driver.get(c_url);
		driver.manage().window().setSize(new Dimension(1920, 1080));
		warmUp();

//		if (lang == "/English/") {
			layoutReport = Galen.checkLayout(driver,
					"C://Users//adev//Desktop//Selenium essentials//galen-bin-2.2.5//Basic_Chart_Specification_eng.spec",
					Arrays.asList("desktop"));
//		} else if (lang == "/German/") {
//			layoutReport = Galen.checkLayout(driver,
//					"C://Users//adev//Desktop//Selenium essentials//galen-bin-2.2.5//Basic_Chart_Specification_ger.spec",
//					Arrays.asList("desktop"));
//		}

		List<GalenTestInfo> tests = new LinkedList<GalenTestInfo>();

		GalenTestInfo test = GalenTestInfo.fromString("Chart layout1");

		test.getReport().layout(layoutReport, "desktop");

		tests.add(test);

		HtmlReportBuilder htmlReportBuilder = new HtmlReportBuilder();

		htmlReportBuilder.build(tests, "target");

		if (layoutReport.errors() > 0) {
			AssertJUnit.fail("Layout test failed");
		}
	}

}

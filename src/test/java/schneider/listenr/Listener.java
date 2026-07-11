package schneider.listenr;

import java.lang.reflect.Method;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import schneider.annotations.TestInfo;
import schneider.reports.DashboardBuilder;
import schneider.reports.ExtentReportNG;
import schneider.reports.ExtentTestManager;
import schneider.reports.ModuleDashboard;
import schneider.testcomponents.BaseTest;
import schneider.utils.DriverUtils;
import schneider.utils.ScreenshotUtils;
import schneider.utils.WebDriverFactory;

public class Listener implements ITestListener {
	private long start;
	private static ExtentReports extent = ExtentReportNG.getInstance();
	//private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	@Override
	public void onTestStart(ITestResult result) {
		WebDriver driver = WebDriverFactory.getDriver();
		start = System.currentTimeMillis();
		Method method = result.getMethod().getConstructorOrMethod().getMethod();
		TestInfo info = method.getAnnotation(TestInfo.class);
		String testName;
		if (info != null && !info.description().isEmpty()) {
			testName = info.description();

		} else {
			testName = result.getMethod().getMethodName();
		}
		ExtentTest extentTest = extent.createTest(testName);
		ExtentTestManager.setTest(extentTest);
		String[] groups = result.getMethod().getGroups();

	    for (String group : groups) {
	        ExtentTestManager.getTest().assignCategory(group);
	    }
	    if (info != null) {
			
			ExtentTestManager.getTest().assignAuthor(info.author());
			ExtentTestManager.getTest().info(info.description());
			ExtentTestManager.getTest().info("Module : " + info.module());
			ExtentTestManager.getTest().info("Priority : " + info.priority());			
		}
	    Capabilities cap = DriverUtils.getCapabilities(driver);
	    if (cap != null) {
			ExtentTestManager.getTest().info("Browser: " + cap.getBrowserName());
			ExtentTestManager.getTest().info("Version: " + cap.getBrowserVersion());
		}
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		WebDriver driver = WebDriverFactory.getDriver();
		long end = System.currentTimeMillis();
		ExtentTestManager.getTest().info("Execution Time : " + (end - start) / 1000.0 + " sec");
		ExtentTestManager.getTest().pass("Test Passed");
		Method method = result.getMethod().getConstructorOrMethod().getMethod();
		TestInfo info = method.getAnnotation(TestInfo.class);

		if (info != null) {
			ModuleDashboard.markPassed(info.module());
		}
		String testName = result.getMethod().getMethodName();

		String path = ScreenshotUtils.getScreenshot(driver, testName);

		ExtentTestManager.getTest().addScreenCaptureFromPath(path);
		String[] groups = getSuite(result);

		DashboardBuilder.markPassed(groups);

		DashboardBuilder.addTags(groups);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		WebDriver driver = WebDriverFactory.getDriver();
		ExtentTestManager.getTest().fail(result.getThrowable());
		if (driver == null) {
			ExtentTestManager.getTest().warning("Driver is null. Screenshot not captured.");
			return;
		}
		String testName = result.getMethod().getMethodName();
		String path = ScreenshotUtils.getScreenshot(driver,testName);
		ExtentTestManager.getTest().addScreenCaptureFromPath(path);
		LogEntries logs = driver.manage().logs().get(LogType.BROWSER);
		ExtentTestManager.getTest().info("Current URL : " + driver.getCurrentUrl());
		Capabilities cap = DriverUtils.getCapabilities(driver);
		ExtentTestManager.getTest().info("Browser : " + cap.getBrowserName());
		ExtentTestManager.getTest().info("Version : " + cap.getBrowserVersion());
		String[] groups = getSuite(result);

		DashboardBuilder.markFailed(groups);

		DashboardBuilder.addTags(groups);

		Method method = result.getMethod().getConstructorOrMethod().getMethod();
	    TestInfo info = method.getAnnotation(TestInfo.class);

	    if (info != null) {
	        ModuleDashboard.markFailed(info.module());
	    }

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ExtentTestManager.getTest().skip("Test Skipped");
	}

	@Override
	public void onFinish(org.testng.ITestContext context) {
		DashboardBuilder.generateDashboard();
		ExtentReportNG.getInstance().createTest("Execution Dashboard").info(DashboardBuilder.generateDashboard());
		ModuleDashboard.generateDashboard();
		ExtentReportNG.getInstance().createTest("Execution Module Dashboard").info(ModuleDashboard.generateDashboard());
		extent.flush();
	}
	
	public void log(String message) {

		ExtentTest test = ExtentTestManager.getTest();

		if (test != null) {
			test.pass(message);
		} else {
			System.out.println("ExtentTest NULL → " + message);
		}
	}
	
	private String[] getSuite(ITestResult result) {

		TestInfo info = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(TestInfo.class);

		//return info != null ? info.category() : "Unknown";
		return result.getMethod().getGroups();
	}
	
	public static Capabilities getCapabilities(WebDriver driver) {
		try {
			if (driver instanceof RemoteWebDriver) {
				return ((RemoteWebDriver) driver).getCapabilities();
			}

			// fallback for EventFiringDecorator
			if (driver instanceof HasCapabilities) {
				return ((HasCapabilities) driver).getCapabilities();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

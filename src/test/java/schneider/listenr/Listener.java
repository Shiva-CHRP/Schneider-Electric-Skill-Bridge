package schneider.listenr;

import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import schneider.testcomponents.BaseTest;
import schneider.utils.ExtentReportNG;
import schneider.utils.ScreenshotUtils;

public class Listener extends BaseTest implements ITestListener {
	private static ExtentReports extent = ExtentReportNG.getInstance();
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	@Override
	public void onTestStart(ITestResult result) {
		ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
		test.set(extentTest);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test.get().pass("Test Passed");
		//String testName = result.getMethod().getMethodName();

		//String path = ScreenshotUtils.getScreenshot(driver,testName);

		//test.get().addScreenCaptureFromPath(path);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		test.get().fail(result.getThrowable());
		String testName = result.getMethod().getMethodName();

		String path = ScreenshotUtils.getScreenshot(driver,testName);

		test.get().addScreenCaptureFromPath(path);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		test.get().skip("Test Skipped");
	}

	@Override
	public void onFinish(org.testng.ITestContext context) {
		extent.flush();
	}
}

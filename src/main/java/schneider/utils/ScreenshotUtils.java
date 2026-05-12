package schneider.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class ScreenshotUtils {

	public static String getScreenshot(String testName) {

		String path = System.getProperty("user.dir") + "/reports/screenshots/" + testName + ".png";

		try {

			File src = ((TakesScreenshot) WebDriverFactory.getDriver()).getScreenshotAs(OutputType.FILE);

			File dest = new File(path);

			FileUtils.copyFile(src, dest);

		} catch (IOException e) {

			e.printStackTrace();
		}

		return path;
	}
}

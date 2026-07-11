package schneider.utils;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverUtils {
	public static Capabilities getCapabilities(WebDriver driver) {

        try {

            if (driver instanceof RemoteWebDriver) {
                return ((RemoteWebDriver) driver).getCapabilities();
            }

            if (driver instanceof HasCapabilities) {
                return ((HasCapabilities) driver).getCapabilities();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}

package schneider.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverFactory {

	private static ThreadLocal<WebDriver> driver =
            new ThreadLocal<>();

    public WebDriver initializeDriver() throws IOException {

        Properties prop = new Properties();

        String path = System.getProperty("user.dir")
                + "/src/main/resources/GlobalData.properties";

        FileInputStream fis = new FileInputStream(path);

        prop.load(fis);

        //String browserName = prop.getProperty("browser");
        String browserName = ConfigReader.getBrowser();
        if (browserName.equalsIgnoreCase("chrome")) {

            WebDriverManager.chromedriver().setup();

            driver.set(new ChromeDriver());
        }

        getDriver().manage().window().maximize();

        getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(2));

        return getDriver();
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {

        if (driver.get() != null) {

            driver.get().quit();

            driver.remove();
        }
    }
}

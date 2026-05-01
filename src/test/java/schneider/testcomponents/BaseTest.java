package schneider.testcomponents;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;

import schneider.pageobjects.LoginPage;
import schneider.property.ExtentReportNG;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	protected static ExtentReports extent = ExtentReportNG.getInstance();

	public WebDriver driver;
	public WebDriver initializeDriver() throws IOException {
		
		Properties prop = new Properties();
		String path = System.getProperty("user.dir") + "/src/main/resources/GlobalData.properties";
		System.out.println("PATH: " + path);

		FileInputStream fis = new FileInputStream(path);
		prop.load(fis);
		
		String browserName =prop.getProperty("browser");
		
		if (browserName.equalsIgnoreCase("chrome")) {
		    WebDriverManager.chromedriver().setup();
		    driver = new ChromeDriver();
		}
		
		 driver.manage().window().maximize();
	        driver.manage().timeouts()
	                .implicitlyWait(Duration.ofSeconds(5));
		return driver;		
	}
	
	@BeforeTest(alwaysRun=true)
	public LoginPage launchApplication() throws IOException {
		
		driver=initializeDriver();
		LoginPage loginPage=new LoginPage(driver);
		loginPage.goTo();
		return loginPage;
	}
	
    @AfterSuite
    public void tearDown() {
    	if (driver != null) {
            driver.quit();
        }
        extent.flush(); // Important to write data to report
    }
}

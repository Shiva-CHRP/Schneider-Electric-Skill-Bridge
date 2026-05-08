package schneider.testcomponents;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;

import schneider.pageobjects.Category;
import schneider.pageobjects.Cluster;
import schneider.pageobjects.Country;
import schneider.pageobjects.Department;
import schneider.pageobjects.GroupMaster;
import schneider.pageobjects.LoginPage;
import schneider.pageobjects.OfferType;
import schneider.pageobjects.Partner;
import schneider.pageobjects.SubCategory;
import schneider.pageobjects.Zone;
import schneider.property.ExtentReportNG;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	protected static ExtentReports extent = ExtentReportNG.getInstance();

	public WebDriver driver;
	public LoginPage loginPage;
	public OfferType offerType;
	public Category category;
	public SubCategory subCategory;
	public Partner partner;
	public Department department;
	public Zone zone;
	public Cluster cluster;
	public Country country;
	public GroupMaster groupMaster;

	public WebDriver initializeDriver() throws IOException {

		Properties prop = new Properties();
		String path = System.getProperty("user.dir") + "/src/main/resources/GlobalData.properties";
		System.out.println("PATH: " + path);

		FileInputStream fis = new FileInputStream(path);
		prop.load(fis);

		String browserName = prop.getProperty("browser");

		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		return driver;
	}

	@BeforeMethod
	public void setup() throws IOException {

		driver = initializeDriver();
		loginPage = new LoginPage(driver);
		offerType = new OfferType(driver);
		category = new Category(driver);
		subCategory = new SubCategory(driver);
		partner = new Partner(driver);
		department = new Department(driver);
		zone = new Zone(driver);
		cluster = new Cluster(driver);
		country = new Country(driver);
		groupMaster = new GroupMaster(driver);
		loginPage.goTo();
	}

	/*
	 * @BeforeTest(alwaysRun=true) public LoginPage launchApplication() throws
	 * IOException { LoginPage loginPage=new LoginPage(driver); loginPage.goTo();
	 * return loginPage; }
	 */

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
		extent.flush(); // Important to write data to report
	}
}

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
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

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
import schneider.utils.ConfigReader;
import schneider.utils.ExtentReportNG;
import schneider.utils.WebDriverFactory;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	protected static ExtentReports extent = ExtentReportNG.getInstance();
	protected SoftAssert softAssert;
	public WebDriver driver;

	WebDriverFactory factory;

	// Page Objects
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

	@BeforeSuite
	public void setup() throws IOException {

		extent = ExtentReportNG.getInstance();
	}

	public void initializePageObjects() {

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
	}
	
	@BeforeMethod(alwaysRun = true)
	public void setUup() throws IOException {
		factory = new WebDriverFactory();
		driver = factory.initializeDriver();
		initializePageObjects();
		driver.get(ConfigReader.getUrl());
		softAssert = new SoftAssert();
	}
	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		WebDriverFactory.quitDriver();
	}
	
	@AfterSuite
	public void tearDownSuite() {

		//softAssert = null;
		extent.flush();
	}


}

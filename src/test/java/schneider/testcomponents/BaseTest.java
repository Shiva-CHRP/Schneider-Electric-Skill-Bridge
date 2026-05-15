package schneider.testcomponents;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import schneider.pageobjects.Dashboard;
import schneider.pageobjects.Assessments;
import schneider.pageobjects.AssignedUsers;
import schneider.pageobjects.BulkUpload;
import schneider.pageobjects.Category;
import schneider.pageobjects.CertificateTemplates;
import schneider.pageobjects.Certificates;
import schneider.pageobjects.Cluster;
import schneider.pageobjects.ContentHub;
import schneider.pageobjects.Country;
import schneider.pageobjects.Department;
import schneider.pageobjects.Events;
import schneider.pageobjects.External;
import schneider.pageobjects.Feedback;
import schneider.pageobjects.GroupMaster;
import schneider.pageobjects.LoginPage;
import schneider.pageobjects.OfferType;
import schneider.pageobjects.Partner;
import schneider.pageobjects.QuestionBank;
import schneider.pageobjects.QuestionStrategies;
import schneider.pageobjects.Reports;
import schneider.pageobjects.Settings;
import schneider.pageobjects.SubCategory;
import schneider.pageobjects.Users;
import schneider.pageobjects.Zone;
import schneider.pageobjects.trainer.AssessmentReports;
import schneider.pageobjects.trainer.EventsCalendar;
import schneider.pageobjects.trainer.MyGroup;
import schneider.pageobjects.trainer.TrainerAssignedUsers;
import schneider.pageobjects.trainer.TrainerCertificates;
import schneider.pageobjects.trainer.TrainerContentHub;
import schneider.pageobjects.trainer.TrainerCourseLibrary;
import schneider.pageobjects.trainer.TrainerDashboard;
import schneider.pageobjects.trainer.TrainerFeedback;
import schneider.pageobjects.trainer.TrainerSettings;
import schneider.pageobjects.user.UserAssessments;
import schneider.pageobjects.user.UserCertificates;
import schneider.pageobjects.user.UserCourseLibrary;
import schneider.pageobjects.user.UserDashboard;
import schneider.pageobjects.user.UserEvents;
import schneider.pageobjects.user.UserFeedback;
import schneider.utils.ConfigReader;
import schneider.utils.ExtentReportNG;
import schneider.utils.ExtentTestManager;
import schneider.utils.ToastResponse;
import schneider.utils.ToastUtils;
import schneider.utils.WebDriverFactory;

public class BaseTest {

	protected static ExtentReports extent = ExtentReportNG.getInstance();
	protected SoftAssert softAssert;
	public WebDriver driver;
	public ToastUtils toastUtils;
	protected ExtentTest test;
	WebDriverFactory factory;

	// Page Objects
	public LoginPage loginPage;
	public Dashboard dashboard;
	public OfferType offerType;
	public Category category;
	public SubCategory subCategory;
	public Partner partner;
	public Department department;
	public Zone zone;
	public Cluster cluster;
	public Country country;
	public GroupMaster groupMaster;
	public Users users;
	public AssignedUsers assignedUsers;
	public ContentHub contentHub;
	public Assessments assessments;
	public QuestionBank questionBank;
	public BulkUpload bulkUpload;
	public QuestionStrategies questionStrategies;
	public Reports reports;
	public External external;
	public Events events;
	public Certificates certificates;
	public CertificateTemplates certificateTemplates;
	public Settings settings;
	public Feedback feedback;
	public TrainerDashboard trainerDashboard;
	public MyGroup myGroup;
	public TrainerAssignedUsers trainerAssignedUsers;
	public TrainerCourseLibrary trainerCourseLibrary;
	public EventsCalendar eventsCalendar;
	public AssessmentReports assessmentReports;
	public TrainerContentHub trainerContentHub;
	public TrainerFeedback trainerFeedback;
	public TrainerCertificates trainerCertificates;
	public TrainerSettings trainerSettings;
	public UserDashboard userDashboard;
	public UserCourseLibrary userCourseLibrary;
	public UserEvents userEvents;
	public UserAssessments userAssessments;
	public UserCertificates userCertificates;
	public UserFeedback userFeedback;

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
		users = new Users(driver);
		assignedUsers = new AssignedUsers(driver);
		contentHub = new ContentHub(driver);
		assessments = new Assessments(driver);
		questionBank = new QuestionBank(driver);
		bulkUpload = new BulkUpload(driver);
		questionStrategies = new QuestionStrategies(driver);
		reports = new Reports(driver);
		external = new External(driver);
		events = new Events(driver);
		certificates = new Certificates(driver);
		certificateTemplates = new CertificateTemplates(driver);
		settings = new Settings(driver);
		feedback = new Feedback(driver);
		dashboard = new Dashboard(driver);
	}

	public void initializeTrainerPageObjects() {
		trainerDashboard = new TrainerDashboard(driver);
		myGroup = new MyGroup(driver);
		trainerAssignedUsers = new TrainerAssignedUsers(driver);
		trainerCourseLibrary = new TrainerCourseLibrary(driver);
		eventsCalendar = new EventsCalendar(driver);
		assessmentReports = new AssessmentReports(driver);
		trainerContentHub = new TrainerContentHub(driver);
		trainerFeedback = new TrainerFeedback(driver);
		trainerCertificates = new TrainerCertificates(driver);
		trainerSettings = new TrainerSettings(driver);
	}

	public void initializeUserPageObjects() {
		userDashboard = new UserDashboard(driver);
		userCourseLibrary = new UserCourseLibrary(driver);
		userEvents = new UserEvents(driver);
		userAssessments = new UserAssessments(driver);
		userCertificates = new UserCertificates(driver);
		userFeedback = new UserFeedback(driver);
	}

	public void assertToast(ToastResponse toast, String expectedMessage, String expectedType) {

		Assert.assertEquals(toast.getMessage(), expectedMessage);
		Assert.assertEquals(toast.getType(), expectedType);
	}

	@BeforeMethod(alwaysRun = true)
	public void setUup() throws IOException {
		factory = new WebDriverFactory();
		driver = factory.initializeDriver();
		extent = ExtentReportNG.getInstance();
		test = extent.createTest("Test Execution");
		ExtentTestManager.setTest(test);
		initializePageObjects();
		initializeTrainerPageObjects();
		initializeUserPageObjects();
		toastUtils = new ToastUtils(driver);
		driver.get(ConfigReader.getUrl());
		softAssert = new SoftAssert();
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		WebDriverFactory.quitDriver();
		ExtentTestManager.unload();
	}

	@AfterSuite
	public void tearDownSuite() {

		// softAssert = null;
		extent.flush();
	}

}

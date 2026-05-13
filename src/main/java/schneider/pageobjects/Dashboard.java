package schneider.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;
import schneider.utils.ScreenshotUtils;
import schneider.utils.StepName;

public class Dashboard extends AbstractComponent{

	public Dashboard(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		// TODO Auto-generated constructor stub
	}
	@FindBy(xpath = "//a[@href='/admin']")
	WebElement aDashboardNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Dashboard']")
	WebElement aDashboardScreenName;

	@FindBy(xpath = "//button[.//p[normalize-space()='Admin']]")
	WebElement adminProfileDropdown;

	@FindBy(xpath = "//button[.//p[normalize-space()='Trainer']]")
	WebElement trainerProfileDropdown;

	@FindBy(xpath = "//button[.//p[normalize-space()='User']]")
	WebElement userProfileDropdown;

	@FindBy(xpath = "//button[.//span[normalize-space()='Switch Account']]")
	WebElement switchAccountBtn;

	@FindBy(xpath = "//button[.//span[normalize-space()='Admin']]")
	WebElement adminRole;

	@FindBy(xpath = "//button[.//span[normalize-space()='Trainer']]")
	WebElement trainerRole;

	@FindBy(xpath = "//h2[normalize-space()='Trainer Dashboard']")
	WebElement trainerDashboard;

	@FindBy(xpath = "//button[.//span[normalize-space()='User']]")
	WebElement userRole;

	@FindBy(xpath = "//h2[normalize-space()='Welcome back!']")
	WebElement userDashboard;
	
	@FindBy(xpath = "//button[.//span[normalize-space()='Logout']]")
	WebElement logout;
	
	@StepName("Navigated to the Admin Dashboard")
	public void goToDashboard() {
		aDashboardNavigation.click();
		waitElementToAppear(aDashboardScreenName);
	}
	
	public void clickProfile() {
		adminProfileDropdown.click();
		waitElementToAppear(switchAccountBtn);
	}

	public void clickSwitchAccount() {
		switchAccountBtn.click();
		waitElementToAppear(adminRole);
	}
	@StepName("Switch Account to Admin Role")
	public void clickAdminRole() {
		adminRole.click();
		waitElementToAppear(aDashboardScreenName);
	}
	@StepName("Switch Account to Trainer Role")
	public void clickTrainerRole() {
		trainerRole.click();
		waitElementToAppear(trainerDashboard);
	}

	public void clickTrainerProfile() {
		trainerProfileDropdown.click();
		waitElementToAppear(switchAccountBtn);
	}

	public void clickUserProfile() {
		userProfileDropdown.click();
		waitElementToAppear(switchAccountBtn);
	}
	@StepName("Switch Account to User Role")
	public void clickUserRole() {
		userRole.click();
		waitElementToAppear(userDashboard);
	}
	
	public void clickLogout() {
		logout.click();
	}
}

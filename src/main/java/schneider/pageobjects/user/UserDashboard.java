package schneider.pageobjects.user;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class UserDashboard extends AbstractComponent{

	public UserDashboard(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//a[@href='/user']")
	WebElement dashboardNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Welcome back!']")
	WebElement dashboardScreenName;

	public void goToUserDashboard() {
		dashboardNavigation.click();
		waitElementToAppear(dashboardScreenName);
	}

}

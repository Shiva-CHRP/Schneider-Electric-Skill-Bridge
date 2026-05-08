package schneider.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class AdminDashboard extends AbstractComponent {

	public AdminDashboard(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@href='/admin']")
	WebElement aDashboardNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Dashboard']")
	WebElement aDashboardScreenName;

	public void goToZone() {
		aDashboardNavigation.click();
		waitElementToAppear(aDashboardScreenName);
	}

}

package schneider.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class Reports extends AbstractComponent{
	
	public Reports(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
	}

	@FindBy(xpath = "//a[@href=/admin/assessment-hub/reports']")
	WebElement reportsNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Assessment Reports']")
	WebElement reportScreenName;

	public void goToCategory() {
		reportsNavigation.click();
		waitElementToAppear(reportScreenName);
	}
	
}

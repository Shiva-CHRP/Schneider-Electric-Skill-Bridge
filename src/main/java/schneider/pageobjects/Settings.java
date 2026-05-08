package schneider.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class Settings extends AbstractComponent{
	
	public Settings(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
	}

	@FindBy(xpath = "//a[@href=/admin/settings']")
	WebElement settingNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Settings']")
	WebElement settingScreenName;
	
	public void goToCategory() {
		settingNavigation.click();
		waitElementToAppear(settingScreenName);
	}
	
}

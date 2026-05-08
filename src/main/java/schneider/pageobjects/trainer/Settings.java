package schneider.pageobjects.trainer;

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
	
	@FindBy(xpath = "//a[@href=/trainer/settings']")
	WebElement settingsNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Settings']")
	WebElement settingsScreenName;

	public void goToCategory() {
		settingsNavigation.click();
		waitElementToAppear(settingsScreenName);
	}

	
}

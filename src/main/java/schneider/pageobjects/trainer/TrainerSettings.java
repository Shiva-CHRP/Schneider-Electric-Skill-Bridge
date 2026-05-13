package schneider.pageobjects.trainer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class TrainerSettings extends AbstractComponent{

	public TrainerSettings(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
	}
	
	@FindBy(xpath = "//a[@href='/trainer/settings']")
	WebElement settingsNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Settings']")
	WebElement settingsScreenName;

	public void goToTrainerSettings() {
		settingsNavigation.click();
		waitElementToAppear(settingsScreenName);
	}

	
}

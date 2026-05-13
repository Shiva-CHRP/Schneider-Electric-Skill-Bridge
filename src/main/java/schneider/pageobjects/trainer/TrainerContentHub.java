package schneider.pageobjects.trainer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class TrainerContentHub extends AbstractComponent{

	public TrainerContentHub(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
	}
	
	@FindBy(xpath = "//a[@href='/trainer/content-hub']")
	WebElement contentHubNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Content Hub']")
	WebElement contentHubScreenName;

	public void goToTrainerContentHub() {
		contentHubNavigation.click();
		waitElementToAppear(contentHubScreenName);
	}

	
}

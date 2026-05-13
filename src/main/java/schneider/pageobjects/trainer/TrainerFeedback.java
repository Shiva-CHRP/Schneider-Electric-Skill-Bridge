package schneider.pageobjects.trainer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class TrainerFeedback extends AbstractComponent{

	public TrainerFeedback(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
	}
	
	@FindBy(xpath = "//a[@href='/trainer/feedback']")
	WebElement feedbackNavigation;

	@FindBy(xpath = "//h2[normalize-space()='General Feedback']")
	WebElement feedbackScreenName;

	public void goToTrainerFeedback() {
		feedbackNavigation.click();
		waitElementToAppear(feedbackScreenName);
	}

	
}

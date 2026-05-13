package schneider.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class Feedback extends AbstractComponent{
	
	public Feedback(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
	}

	@FindBy(xpath = "//a[@href='/admin/feedback-management/feedback']")
	WebElement feedbackNavigation;
	
	@FindBy(xpath = "//h2[normalize-space()='General Feedback']")
	WebElement feedbackScreenName;

	public void goToFeedback() {
		feedbackNavigation.click();
		waitElementToAppear(feedbackScreenName);
	}
	
}

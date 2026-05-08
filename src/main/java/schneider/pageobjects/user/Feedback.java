package schneider.pageobjects.user;

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
	
	@FindBy(xpath = "//a[@href=/user/feedback']")
	WebElement feedbackNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Feedback']")
	WebElement feedbackScreenName;

	public void goToCategory() {
		feedbackNavigation.click();
		waitElementToAppear(feedbackScreenName);
	}

}

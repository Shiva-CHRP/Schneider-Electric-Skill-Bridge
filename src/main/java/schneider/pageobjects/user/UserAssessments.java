package schneider.pageobjects.user;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class UserAssessments extends AbstractComponent{

	public UserAssessments(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//a[@href='/user/assessments']")
	WebElement assessmentsNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Assessments']")
	WebElement assessmentsScreenName;

	public void goToUserAssessments() {
		assessmentsNavigation.click();
		waitElementToAppear(assessmentsScreenName);
	}

}

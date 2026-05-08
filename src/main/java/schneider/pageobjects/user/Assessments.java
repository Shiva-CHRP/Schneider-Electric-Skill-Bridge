package schneider.pageobjects.user;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class Assessments extends AbstractComponent{

	public Assessments(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//a[@href=/user/assessments']")
	WebElement assessmentsNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Assessments']")
	WebElement assessmentsScreenName;

	public void goToCategory() {
		assessmentsNavigation.click();
		waitElementToAppear(assessmentsScreenName);
	}

}

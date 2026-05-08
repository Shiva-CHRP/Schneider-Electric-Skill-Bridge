package schneider.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class External extends AbstractComponent{
	
	public External(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
	}

	@FindBy(xpath = "//a[@href=/admin/assessment-hub/external-assessments']")
	WebElement externalAssessmentNavigation;

	@FindBy(xpath = "//h2[normalize-space()='External Assessments']")
	WebElement externalAssessmentScreenName;
	

	public void goToCategory() {
		externalAssessmentNavigation.click();
		waitElementToAppear(externalAssessmentScreenName);
	}
	
}

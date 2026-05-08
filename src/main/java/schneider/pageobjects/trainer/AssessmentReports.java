package schneider.pageobjects.trainer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class AssessmentReports extends AbstractComponent{

	public AssessmentReports(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
	}
	
	@FindBy(xpath = "//a[@href=/trainer/assessment-reports']")
	WebElement assessmentReportNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Assessment Reports']")
	WebElement assessmentReportScreenName;

	public void goToCategory() {
		assessmentReportNavigation.click();
		waitElementToAppear(assessmentReportScreenName);
	}
	

}

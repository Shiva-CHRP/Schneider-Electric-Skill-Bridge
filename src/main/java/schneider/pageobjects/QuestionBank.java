package schneider.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class QuestionBank extends AbstractComponent{
	
	public QuestionBank(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
	}

	@FindBy(xpath = "//a[@href='/admin/assessment-hub/question-bank']")
	WebElement questionBankNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Questions']")
	WebElement questionBankScreenName;

	
	public void goToQuestionBank() {
		questionBankNavigation.click();
		waitElementToAppear(questionBankScreenName);
	}
	
}

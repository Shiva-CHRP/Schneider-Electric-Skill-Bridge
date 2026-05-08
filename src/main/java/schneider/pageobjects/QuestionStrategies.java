package schneider.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class QuestionStrategies extends AbstractComponent{
	
	public QuestionStrategies(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
	}

	@FindBy(xpath = "//a[@href=/admin/question-strategies']")
	WebElement questionStrategiesNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Question Strategies']")
	WebElement questionStrategiesScreenName;

	public void goToCategory() {
		questionStrategiesNavigation.click();
		waitElementToAppear(questionStrategiesScreenName);
	}
	
	
}

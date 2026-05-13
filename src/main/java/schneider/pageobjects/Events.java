package schneider.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class Events extends AbstractComponent{
	
	public Events(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
	}

	@FindBy(xpath = "//a[@href='/admin/events']")
	WebElement eventsNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Events']")
	WebElement eventScreenName;

	public void goToEvents() {
		eventsNavigation.click();
		waitElementToAppear(eventScreenName);
	}
	
}

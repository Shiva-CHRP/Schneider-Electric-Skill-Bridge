package schneider.pageobjects.user;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class UserEvents extends AbstractComponent{

	public UserEvents(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//a[@href='/user/events']")
	WebElement eventsNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Events & Calendar']")
	WebElement eventsScreenName;

	public void goToUserEvents() {
		eventsNavigation.click();
		waitElementToAppear(eventsScreenName);
	}

}

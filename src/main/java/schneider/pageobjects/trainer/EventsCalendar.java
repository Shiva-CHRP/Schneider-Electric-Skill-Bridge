package schneider.pageobjects.trainer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class EventsCalendar extends AbstractComponent{

	public EventsCalendar(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
	}
	
	@FindBy(xpath = "//a[@href=/trainer/events-calendar']")
	WebElement eventsCalendarNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Events']")
	WebElement eventsCalendarScreenName;

	public void goToCategory() {
		eventsCalendarNavigation.click();
		waitElementToAppear(eventsCalendarScreenName);
	}

	
}

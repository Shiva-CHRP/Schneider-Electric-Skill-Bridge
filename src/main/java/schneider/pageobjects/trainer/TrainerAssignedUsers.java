package schneider.pageobjects.trainer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class TrainerAssignedUsers extends AbstractComponent{

	public TrainerAssignedUsers(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
	}
	
	@FindBy(xpath = "//a[@href='/trainer/assigned-users']")
	WebElement assignedUserNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Assigned Users']")
	WebElement assignedUserScreenName;

	public void goToTrainerAssignedUsers() {
		assignedUserNavigation.click();
		waitElementToAppear(assignedUserScreenName);
	}
	

}

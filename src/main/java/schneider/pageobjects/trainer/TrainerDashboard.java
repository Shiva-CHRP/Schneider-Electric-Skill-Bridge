package schneider.pageobjects.trainer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class TrainerDashboard extends AbstractComponent{

	public TrainerDashboard(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
	}
	
	@FindBy(xpath = "//a[@href='/trainer']")
	WebElement tDashboardNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Trainer Dashboard']")
	WebElement tDashboardScreenName;

	public void goToTrainerDashboard() {
		tDashboardNavigation.click();
		waitElementToAppear(tDashboardScreenName);
	}

}

package schneider.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class Users extends AbstractComponent{
	
	public Users(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
	}

	@FindBy(xpath = "//a[@href='/admin/users']")
	WebElement userNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Users Management']")
	WebElement userScreenName;

	public void goToCategory() {
		userNavigation.click();
		waitElementToAppear(userScreenName);
	}
	
}

package schneider.pageobjects.trainer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class ContentHub extends AbstractComponent{

	public ContentHub(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
	}
	
	@FindBy(xpath = "//a[@href=/trainer/content-hub']")
	WebElement contentHubNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Content Hub']")
	WebElement contentHubScreenName;

	public void goToCategory() {
		contentHubNavigation.click();
		waitElementToAppear(contentHubScreenName);
	}

	
}

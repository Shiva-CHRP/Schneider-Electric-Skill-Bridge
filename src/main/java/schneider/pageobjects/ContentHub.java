package schneider.pageobjects;

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

	@FindBy(xpath = "//a[@href='/admin/content-hub']")
	WebElement contentHubNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Content Hub Manager']")
	WebElement contentHubScreenName;
	

	public void goToCategory() {
		contentHubNavigation.click();
		waitElementToAppear(contentHubScreenName);
	}
	
}

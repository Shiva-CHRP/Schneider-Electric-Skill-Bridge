package schneider.pageobjects.user;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class UserCertificates extends AbstractComponent{

	public UserCertificates(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//a[@href='/user/certificates']")
	WebElement certificatesNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Certificates']")
	WebElement certificatesScreenName;

	public void goToUserCertificates() {
		certificatesNavigation.click();
		waitElementToAppear(certificatesScreenName);
	}

}

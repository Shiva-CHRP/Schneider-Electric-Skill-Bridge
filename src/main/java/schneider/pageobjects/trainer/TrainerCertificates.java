package schneider.pageobjects.trainer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class TrainerCertificates extends AbstractComponent{

	public TrainerCertificates(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
	}
	
	@FindBy(xpath = "//a[@href='/trainer/certificates']")
	WebElement certificatesNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Certificates']")
	WebElement certificatesScreenName;

	public void goToTrainerCertificates() {
		certificatesNavigation.click();
		waitElementToAppear(certificatesScreenName);
	}

	
}

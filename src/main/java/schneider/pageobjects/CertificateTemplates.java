package schneider.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class CertificateTemplates extends AbstractComponent{
	
	public CertificateTemplates(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
	}

	@FindBy(xpath = "//a[@href=/admin/certificate-templates']")
	WebElement templateNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Certificate Templates']")
	WebElement templateScreenName;
	
	public void goToCategory() {
		templateNavigation.click();
		waitElementToAppear(templateScreenName);
	}
	
}

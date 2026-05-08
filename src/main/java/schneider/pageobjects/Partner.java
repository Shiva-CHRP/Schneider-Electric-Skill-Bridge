package schneider.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class Partner extends AbstractComponent {

	public Partner(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@href='/admin/partner']")
	WebElement partnerNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Partner Master']")
	WebElement partnerScreenName;

	public void goToPartner() {
		partnerNavigation.click();
		waitElementToAppear(partnerScreenName);
	}

}

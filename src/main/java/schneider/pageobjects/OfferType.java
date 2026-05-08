package schneider.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class OfferType extends AbstractComponent {

	public OfferType(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@href='/admin/offer-type']")
	WebElement offerNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Offer Type Master']")
	WebElement offerScreenName;

	@FindBy(xpath = "//input[@name='offerTypeName']")
	WebElement offerName;

	By statusToggle = By.xpath("//label[contains(@class,'cursor-pointer')]");	
	
	
	
	public void goToOfferTypePage() {
		offerNavigation.click();
		waitElementToAppear(offerScreenName);
	}

	public void createOfferType(String Name) {
		clickAddButton();
		waitElementToAppear(offerName);
		offerName.sendKeys(Name);
		
	}
	
	

}

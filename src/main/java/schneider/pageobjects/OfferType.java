package schneider.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class OfferType extends AbstractComponent{

	public OfferType(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);	
	}
	
	public void createOfferType() {
		clickAddButton();
	}
	

}

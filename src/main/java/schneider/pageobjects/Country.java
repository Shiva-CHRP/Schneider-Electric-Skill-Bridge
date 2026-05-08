package schneider.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class Country extends AbstractComponent {

	public Country(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@href='/admin/country']")
	WebElement countryNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Country Master']")
	WebElement countryScreenName;

	public void goToCountryPage() {
		countryNavigation.click();
		waitElementToAppear(countryScreenName);
	}

}

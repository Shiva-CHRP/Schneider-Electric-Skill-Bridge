package schneider.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class Zone extends AbstractComponent {

	public Zone(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@href='/admin/zone']")
	WebElement zoneNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Zone Master']")
	WebElement zoneScreenName;

	public void goToZone() {
		zoneNavigation.click();
		waitElementToAppear(zoneScreenName);
	}

}

package schneider.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class Category extends AbstractComponent {

	public Category(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@href='/admin/category']")
	WebElement categoryNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Category Master']")
	WebElement categoryScreenName;

	public void goToCategory() {
		categoryNavigation.click();
		waitElementToAppear(categoryScreenName);
	}

}

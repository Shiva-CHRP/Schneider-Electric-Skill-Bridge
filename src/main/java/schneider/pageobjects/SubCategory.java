package schneider.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class SubCategory extends AbstractComponent {

	public SubCategory(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@href='/admin/subcategory']")
	WebElement subCategoryNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Sub Category Master']")
	WebElement subCategoryScreenName;

	public void goToSubCategory() {
		subCategoryNavigation.click();
		waitElementToAppear(subCategoryScreenName);
	}

}

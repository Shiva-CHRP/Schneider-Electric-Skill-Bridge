package schneider.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;
import schneider.utils.ToastResponse;
import schneider.utils.WaitUtils;

public class Category extends AbstractComponent {

	public Category(WebDriver driver) {
		super(driver);
		this.driver = driver;
		this.waitUtils = new WaitUtils(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@href='/admin/category']")
	WebElement categoryNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Category Master']")
	WebElement categoryScreenName;

	@FindBy(xpath = "//input[@name='categoryName']")
	WebElement categoryName;

	@FindBy(xpath = "//label[contains(normalize-space(),'Offer Type')]/following::button[@role='combobox'][1]")
	WebElement offerTypeDropdown;

	@FindBy(xpath = "//button[normalize-space()='Save']")
	WebElement saveCategory;

	public void goToCategory() {
		categoryNavigation.click();
		waitElementToAppear(categoryScreenName);
	}

	public void createCategory(String Name) {
		clickAddButton();
		waitElementToAppear(categoryName);
		categoryName.sendKeys(Name);

	}

	public void selectOfferType(String offerName) {
		// waitElementToBeClickable(offerTypeDropdown);
		offerTypeDropdown.click();

		waitUtils.waitForVisibility(
	            By.cssSelector("button[aria-expanded='true']")
	    );
		
		By options = By.cssSelector("[cmdk-item]");
	    waitUtils.waitForVisibility(options);
	    //System.out.println(driver.findElements(By.cssSelector("[cmdk-item]")).size());
	    By target = By.xpath(
	            "//div[@cmdk-item and @data-value='" + offerName + "']"
	    );
	    WebElement element = null;
	    for (int i = 0; i < 10; i++) {
	        try {
	            element = driver.findElement(target);

	            if (element.isDisplayed() && element.isEnabled()) {
	                break;
	            }
	        } catch (Exception ignored) {}

	        try {
	            Thread.sleep(300);
	        } catch (InterruptedException e) {}
	    }
	    if (element == null) {
	        throw new RuntimeException("Offer Type not found: " + offerName);
	    }
	    
	    ((JavascriptExecutor) driver)
        .executeScript("arguments[0].scrollIntoView(true);", element);
	    try { Thread.sleep(300); } catch (Exception ignored) {}
	    ((JavascriptExecutor) driver)
        .executeScript("arguments[0].click();", element);
	}

	public void saveCategory() {
		saveCategory.click();
	}

	public int getColumnIndex(String columnName) {

		List<WebElement> headers = driver.findElements(By.xpath("//table//thead//th"));

		for (int i = 0; i < headers.size(); i++) {

			String headerText = headers.get(i).getText().trim();

			if (headerText.equalsIgnoreCase(columnName)) {
				return i + 1;
			}
		}

		return -1;
	}

	public boolean isCategoryPresentInList(String columnName, String expectedValue) {

		int columnIndex = getColumnIndex("Category");

		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));

		return rows.stream().map(WebElement::getText).map(String::trim)
				.anyMatch(value -> value.equalsIgnoreCase(expectedValue));
	}

	public void clickEditCategory(String category) {

		By editButton = By.xpath("//tr[td[normalize-space()='" + category + "']]//button[@title='Edit']");

		driver.findElement(editButton).click();
	}

	public void deleteCategory(String category) {

		By rowDeleteButton = By.xpath("//tr[td[normalize-space()='" + category + "']]//button[@title='Delete']");

		By confirmDeleteButton = By.xpath("//div[contains(@class,'justify-end')]//button[normalize-space()='Delete']");

		driver.findElement(rowDeleteButton).click();

		waitUtils.waitForVisibility(confirmDeleteButton);

		driver.findElement(confirmDeleteButton).click();
	}

	public void waitForCategoryToDisappear(String category) {

		By offerRow = By.xpath("//tr[td[normalize-space()='" + category + "']]");

		waitUtils.waitForInvisibility(offerRow);
	}
	
	public ToastResponse captureToast() {
	    return toastUtils.captureToast();
	}

	public void waitForToastToDisappear() {
	    toastUtils.waitForToastToDisappear();
	}

}

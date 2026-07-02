package schneider.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;
import schneider.utils.ToastResponse;

public class SubCategory extends AbstractComponent {

	public SubCategory(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@href='/admin/subcategory']")
	WebElement subCategoryNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Sub Category Master']")
	WebElement subCategoryScreenName;

	@FindBy(xpath = "//input[@name='subcategoryName']")
	WebElement subCategoryName;

	@FindBy(xpath = "//label[contains(normalize-space(),'Category')]/following::button[@role='combobox'][1]")
	WebElement categoryDropdown;

	@FindBy(xpath = "//button[normalize-space()='Save']")
	WebElement saveSubCategory;

	public void goToSubCategory() {
		subCategoryNavigation.click();
		waitElementToAppear(subCategoryScreenName);
	}

	public void createSubCategory(String Name) {
		clickAddButton();
		waitElementToAppear(subCategoryName);
		subCategoryName.sendKeys(Name);
	}

	public void selectCategory(String categoryName) {

		categoryDropdown.click();

		waitUtils.waitForVisibility(By.cssSelector("button[aria-expanded='true']"));

		By options = By.cssSelector("[cmdk-item]");
		waitUtils.waitForVisibility(options);
		By target = By.xpath("//div[@cmdk-item and @data-value='" + categoryName + "']");
		WebElement element = null;
		for (int i = 0; i < 10; i++) {
			try {
				element = driver.findElement(target);

				if (element.isDisplayed() && element.isEnabled()) {
					break;
				}
			} catch (Exception ignored) {
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		if (element == null) {
			throw new RuntimeException("Category not found: " + categoryName);
		}

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		try {
			Thread.sleep(1000);
		} catch (Exception ignored) {
		}
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	public void saveSubCategory() {
		saveSubCategory.click();
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

	public boolean isSubCategoryPresentInList(String columnName, String expectedValue) {

		int columnIndex = getColumnIndex("Subcategory");

		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));

		return rows.stream().map(WebElement::getText).map(String::trim)
				.anyMatch(value -> value.equalsIgnoreCase(expectedValue));
	}

	public void clickEditSubCategory(String subCategory) {

		By editButton = By.xpath("//tr[td[normalize-space()='" + subCategory + "']]//button[@title='Edit']");

		driver.findElement(editButton).click();
	}

	public void deleteSubCategory(String subCategory) {

		By rowDeleteButton = By.xpath("//tr[td[normalize-space()='" + subCategory + "']]//button[@title='Delete']");

		By confirmDeleteButton = By.xpath("//div[contains(@class,'justify-end')]//button[normalize-space()='Delete']");

		driver.findElement(rowDeleteButton).click();

		waitUtils.waitForVisibility(confirmDeleteButton);

		driver.findElement(confirmDeleteButton).click();
	}

	public void waitForSubCategoryToDisappear(String subCategory) {

		By offerRow = By.xpath("//tr[td[normalize-space()='" + subCategory + "']]");

		waitUtils.waitForInvisibility(offerRow);
	}

	public ToastResponse captureToast() {
		return toastUtils.captureToast();
	}

	public void waitForToastToDisappear() {
		toastUtils.waitForToastToDisappear();
	}
}

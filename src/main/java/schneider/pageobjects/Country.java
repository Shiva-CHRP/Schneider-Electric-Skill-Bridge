package schneider.pageobjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import schneider.abstractcomponent.AbstractComponent;
import schneider.annotations.StepName;
import schneider.utils.ToastResponse;

public class Country extends AbstractComponent {

	public Country(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@href='/admin/country']")
	WebElement countryNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Country Master']")
	WebElement countryScreenName;

	@FindBy(xpath = "//input[@name='countryName']")
	WebElement countryName;

	@FindBy(xpath = "//label[contains(normalize-space(),'Zone')]/following::button[@role='combobox'][1]")
	WebElement zoneDropdown;

	@FindBy(xpath = "//label[contains(normalize-space(),'Cluster')]/following::button[@role='combobox'][1]")
	WebElement clusterDropdown;

	@FindBy(xpath = "//button[normalize-space()='Save']")
	WebElement saveCountry;

//	@FindBy(xpath = "//button[.//*[contains(@class,'lucide-chevron-right')]]")
//	private List<WebElement> nextButton;
	private By nextButton = By.xpath("//button[.//*[contains(@class,'lucide-chevron-right')]]");

	@StepName("Navigated to the Country screen")
	public void goToCountryPage() {
		countryNavigation.click();
		waitElementToAppear(countryScreenName);
	}

	@StepName("Click on the Add Button for Creating a new Country")
	public void createCountry(String Name) {
		clickAddButton();
		waitElementToAppear(countryName);
		countryName.sendKeys(Name);
	}

	@StepName("Select the Zone from the Zone ID Dropdown")
	public void selectZone(String zoneName) {

		zoneDropdown.click();

		waitUtils.waitForVisibility(By.cssSelector("button[aria-expanded='true']"));

		By options = By.cssSelector("[cmdk-item]");
		waitUtils.waitForVisibility(options);
		By target = By.xpath("//div[@cmdk-item and @data-value='" + zoneName + "']");
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
			throw new RuntimeException("Zone not found: " + zoneName);
		}

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		try {
			Thread.sleep(1000);
		} catch (Exception ignored) {
		}
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	@StepName("Select the Cluster from the Cluster Dropdown")
	public void selectCluster(String clusterName) {

		clusterDropdown.click();

		waitUtils.waitForVisibility(By.cssSelector("button[aria-expanded='true']"));

		By options = By.cssSelector("[cmdk-item]");
		waitUtils.waitForVisibility(options);
		By target = By.xpath("//div[@cmdk-item and @data-value='" + clusterName + "']");
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
			throw new RuntimeException("Cluster not found: " + clusterName);
		}

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		try {
			Thread.sleep(1000);
		} catch (Exception ignored) {
		}
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	@StepName("Created a new Country")
	public void saveCountry() {
		saveCountry.click();
	}

	public int getColumnIndex(String columnName) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		By headersLocator = By.xpath("//table//thead//th");

		wait.until(ExpectedConditions.visibilityOfElementLocated(headersLocator));

		List<WebElement> headers = driver.findElements(headersLocator);

		for (int i = 0; i < headers.size(); i++) {

			if (headers.get(i).getText().trim().equalsIgnoreCase(columnName)) {
				return i + 1;
			}
		}

		throw new RuntimeException("Column not found: " + columnName);
	}

	@StepName("Verified the Country is present in the list")
	public boolean isCountryPresentInList(String columnName, String expectedValue) {
		search(expectedValue);

		if (!driver.findElements(By.xpath("//*[contains(text(),'No data found')]")).isEmpty()) {
			return false;
		}

		int columnIndex = getColumnIndex(columnName);

		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));

		return rows.stream().map(WebElement::getText).map(String::trim)
				.anyMatch(text -> text.equalsIgnoreCase(expectedValue));
	}

	@StepName("Retrieved the Cluster from the Offer list")
	public String getClusterFromList(String columnName, String expectedValue) {
		search(expectedValue);

		if (!driver.findElements(By.xpath("//table//tbody//td[contains(text(),'No data found')]")).isEmpty()) {
			return null;
		}

		int columnIndex = getColumnIndex(columnName);

		List<WebElement> categories = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));

		for (WebElement category : categories) {

			String actualCategory = category.getText().trim();

			if (actualCategory.equalsIgnoreCase(expectedValue)) {
				return actualCategory.replaceAll("\\s+", " ");
			}
		}

		return null;
	}

	@StepName("Navigated to the first page of the Country list")
	public void goToFirstPage() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		while (true) {

			List<WebElement> previousButtons = driver.findElements(
					By.xpath("//button//*[name()='svg' and contains(@class,'chevron-left')]/parent::button"));

			if (previousButtons.isEmpty()) {
				break;
			}

			WebElement previous = previousButtons.get(0);

			if (previous.isEnabled()) {
				previous.click();
				wait.until(ExpectedConditions.stalenessOf(previous));
			} else {
				break;
			}
		}
	}

	@StepName("Opened the Country for editing")
	public void clickEditCountry(String country) {

		By editButton = By.xpath("//tr[td[normalize-space()='" + country + "']]//button[@title='Edit']");

		driver.findElement(editButton).click();
	}

	@StepName("Deleted the Country")
	public void deleteCountry(String country) {

		By rowDeleteButton = By.xpath("//tr[td[normalize-space()='" + country + "']]//button[@title='Delete']");

		By confirmDeleteButton = By.xpath("//div[contains(@class,'justify-end')]//button[normalize-space()='Delete']");

		driver.findElement(rowDeleteButton).click();

		waitUtils.waitForVisibility(confirmDeleteButton);

		driver.findElement(confirmDeleteButton).click();
	}

	public void waitForCountryToDisappear(String country) {

		By offerRow = By.xpath("//tr[td[normalize-space()='" + country + "']]");

		waitUtils.waitForInvisibility(offerRow);
	}

	public ToastResponse captureToast() {
		return toastUtils.captureToast();
	}

	public void waitForToastToDisappear() {
		toastUtils.waitForToastToDisappear();
	}
}

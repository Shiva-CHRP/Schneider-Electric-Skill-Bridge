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
	
	public void goToCountryPage() {
		countryNavigation.click();
		waitElementToAppear(countryScreenName);
	}
	
	public void createSountry(String Name) {
		clickAddButton();
		waitElementToAppear(countryName);
		countryName.sendKeys(Name);
	}

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
	
	public void saveCountry() {
		saveCountry.click();
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
	
	public boolean isCountryPresentInList(String columnName, String expectedValue) {

		int columnIndex = getColumnIndex("Country");

		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));

		return rows.stream().map(WebElement::getText).map(String::trim)
				.anyMatch(value -> value.equalsIgnoreCase(expectedValue));
	}
	
	public void clickEditCountry(String country) {

		By editButton = By.xpath("//tr[td[normalize-space()='" + country + "']]//button[@title='Edit']");

		driver.findElement(editButton).click();
	}

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

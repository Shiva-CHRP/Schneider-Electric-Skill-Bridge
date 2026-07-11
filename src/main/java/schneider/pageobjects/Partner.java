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

public class Partner extends AbstractComponent {

	public Partner(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@href='/admin/partner']")
	WebElement partnerNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Partner Master']")
	WebElement partnerScreenName;

	@FindBy(xpath = "//input[@name='partnerName']")
	WebElement partnerName;

	@FindBy(xpath = "//input[@name='bFo_id']")
	WebElement bFOId;

	@FindBy(xpath = "//label[contains(normalize-space(),'Zone')]/following::button[@role='combobox'][1]")
	WebElement zoneDropdown;

	@FindBy(xpath = "//label[contains(normalize-space(),'Cluster')]/following::button[@role='combobox'][1]")
	WebElement clusterDropdown;

	@FindBy(xpath = "//label[contains(normalize-space(),'Country')]/following::button[@role='combobox'][1]")
	WebElement countryDropdown;

	@FindBy(xpath = "//button[normalize-space()='Save']")
	WebElement savePartner;

	@StepName("Navigated to the Department screen")
	public void goToPartner() {
		partnerNavigation.click();
		waitElementToAppear(partnerScreenName);
	}

	@StepName("Click on the Add Button for Creating a new Partner")
	public void createPartner(String Name) {
		clickAddButton();
		waitElementToAppear(partnerName);
		partnerName.sendKeys(Name);

	}

	@StepName("Input the bFOId in the bFOID field")
	public void inputbFOId(String Name) {
		bFOId.sendKeys(Name);
	}

	@StepName("Select the Zone from the Zone Dropdown")
	public void selectZone(String zoneName) {

		zoneDropdown.click();

		waitUtils.waitForVisibility(By.cssSelector("button[aria-expanded='true']"));

		By options = By.cssSelector("[cmdk-item]");
		waitUtils.waitForVisibility(options);
		// System.out.println(driver.findElements(By.cssSelector("[cmdk-item]")).size());
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
		// System.out.println(driver.findElements(By.cssSelector("[cmdk-item]")).size());
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
			throw new RuntimeException("Zone not found: " + clusterName);
		}

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		try {
			Thread.sleep(1000);
		} catch (Exception ignored) {
		}
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	@StepName("Select the Country from the Country Dropdown")
	public void selectCountry(String countryName) {

		countryDropdown.click();

		waitUtils.waitForVisibility(By.cssSelector("button[aria-expanded='true']"));

		By options = By.cssSelector("[cmdk-item]");
		waitUtils.waitForVisibility(options);
		// System.out.println(driver.findElements(By.cssSelector("[cmdk-item]")).size());
		By target = By.xpath("//div[@cmdk-item and @data-value='" + countryName + "']");
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
			throw new RuntimeException("Zone not found: " + countryName);
		}

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		try {
			Thread.sleep(1000);
		} catch (Exception ignored) {
		}
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}
	
	@StepName("Created a new Partner")
	public void savePartner() {
		savePartner.click();
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

	@StepName("Verified the Partner is present in the list")
	public boolean isPartnerPresentInList(String columnName, String expectedValue) {
		search(expectedValue);

		if (!driver.findElements(By.xpath("//*[contains(text(),'No data found')]")).isEmpty()) {
			return false;
		}

		int columnIndex = getColumnIndex(columnName);

		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));

		return rows.stream().map(WebElement::getText).map(String::trim)
				.anyMatch(text -> text.equalsIgnoreCase(expectedValue));
	}

	@StepName("Retrieved the Partner from the list")
	public String getPartnerFromList(String columnName, String expectedValue) {
		search(expectedValue);

		if (!driver.findElements(By.xpath("//table//tbody//td[contains(text(),'No data found')]")).isEmpty()) {
			return null;
		}

		int columnIndex = getColumnIndex(columnName);

		List<WebElement> partners = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));

		for (WebElement partner : partners) {

			String actualPartner = partner.getText().trim();

			if (actualPartner.equalsIgnoreCase(expectedValue)) {
				return actualPartner.replaceAll("\\s+", " ");
			}
		}

		return null;
	}

	@StepName("Navigated to the first page of the Category list")
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
	
	@StepName("Opened the Partner for editing")
	public void clickEditPartner(String partner) {

		By editButton = By.xpath("//tr[td[normalize-space()='" + partner + "']]//button[@title='Edit']");

		driver.findElement(editButton).click();
	}

	@StepName("Deleted the Category")
	public void deletePartner(String partner) {

		By rowDeleteButton = By.xpath("//tr[td[normalize-space()='" + partner + "']]//button[@title='Delete']");

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

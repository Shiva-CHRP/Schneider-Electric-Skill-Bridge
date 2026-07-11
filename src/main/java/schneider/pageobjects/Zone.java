package schneider.pageobjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import schneider.abstractcomponent.AbstractComponent;
import schneider.annotations.StepName;
import schneider.utils.ToastResponse;

public class Zone extends AbstractComponent {

	public Zone(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@href='/admin/zone']")
	WebElement zoneNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Zone Master']")
	WebElement zoneScreenName;

	@FindBy(xpath = "//input[@name='zoneName']")
	WebElement zoneName;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement zoneSave;

//	@FindBy(xpath = "//button[.//*[contains(@class,'lucide-chevron-right')]]")
//	private List<WebElement> nextButton;
	private By nextButton = By.xpath("//button[.//*[contains(@class,'lucide-chevron-right')]]");

	@StepName("Navigated to the Zone screen")
	public void goToZone() {
		zoneNavigation.click();
		waitElementToAppear(zoneScreenName);
	}

	@StepName("Click on the Add Button for Creating a new Zone")
	public void createZone(String Name) {
		clickAddButton();
		waitElementToAppear(zoneName);
		zoneName.sendKeys(Name);
	}

	@StepName("Created a new Zone")
	public void createZone() {
		zoneName.click();
	}

	@StepName("Retrieved the zones details by name")
	public String getZoneByName(String zoneName) {
		return driver.findElement(By.xpath("//td[text()='" + zoneName + "']")).getText();
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

	@StepName("Verified the Zone is present in the list")
	public boolean isZonePresentInList(String columnName, String expectedValue) {
		search(expectedValue);

		if (!driver.findElements(By.xpath("//*[contains(text(),'No data found')]")).isEmpty()) {
			return false;
		}

		int columnIndex = getColumnIndex(columnName);

		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));

		return rows.stream().map(WebElement::getText).map(String::trim)
				.anyMatch(text -> text.equalsIgnoreCase(expectedValue));
	}

	@StepName("Retrieved the Zone from the Offer list")
	public String getZoneFromList(String columnName, String expectedValue) {
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

	@StepName("Navigated to the first page of the Zone list")
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

	@StepName("Opened the Zone for editing")
	public void clickEditZone(String zoneName) {

		By editButton = By.xpath("//tr[td[normalize-space()='" + zoneName + "']]//button[@title='Edit']");

		driver.findElement(editButton).click();
	}

	@StepName("Deleted the Zone")
	public void deleteZone(String zoneName) {

		By rowDeleteButton = By.xpath("//tr[td[normalize-space()='" + zoneName + "']]//button[@title='Delete']");

		By confirmDeleteButton = By.xpath("//div[contains(@class,'justify-end')]//button[normalize-space()='Delete']");

		driver.findElement(rowDeleteButton).click();

		waitUtils.waitForVisibility(confirmDeleteButton);

		driver.findElement(confirmDeleteButton).click();
	}

	public void waitForZoneToDisappear(String zoneName) {

		By offerRow = By.xpath("//tr[td[normalize-space()='" + zoneName + "']]");

		waitUtils.waitForInvisibility(offerRow);
	}

	public ToastResponse captureToast() {
		return toastUtils.captureToast();
	}

	public void waitForToastToDisappear() {
		toastUtils.waitForToastToDisappear();
	}
}

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
import schneider.utils.ToastUtils;
import schneider.utils.WaitUtils;

public class OfferType extends AbstractComponent {
	WebDriver driver;
	WaitUtils waitUtils;
	ToastUtils toastUtils;

	public OfferType(WebDriver driver) {
		super(driver);
		this.driver = driver;
		this.waitUtils = new WaitUtils(driver);
		toastUtils = new ToastUtils(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@href='/admin/offer-type']")
	WebElement offerNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Offer Type Master']")
	WebElement offerScreenName;

	@FindBy(xpath = "//input[@name='offerTypeName']")
	WebElement offerName;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement offerSave;

//	@FindBy(xpath = "//button[.//*[contains(@class,'lucide-chevron-right')]]")
//	private List<WebElement> nextButton;

	private By nextButton = By.xpath("//button[.//*[contains(@class,'lucide-chevron-right')]]");

	@FindBy(xpath = "//input[@placeholder='Search offer types...']")
	WebElement txtSearchFilter;

	@StepName("Navigated to the Offer Type Screen")
	public void goToOfferTypePage() {
		offerNavigation.click();
		waitElementToAppear(offerScreenName);
	}

	@StepName("Click on the Add Button for Creating a new Offer Type")
	public void createOfferType(String Name) {
		clickAddButton();
		waitElementToAppear(offerName);
		offerName.sendKeys(Name);
	}

	@StepName("Created a new Offer Type")
	public void createOffer() {
		offerSave.click();
	}

	@StepName("Retrieved the offer type details by name")
	public String getOfferByName(String offerName) {
		return driver.findElement(By.xpath("//td[text()='" + offerName + "']")).getText();
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

	@StepName("Verified the offer type is present in the Offer Type list")
	public boolean isOfferPresentInList(String columnName, String expectedValue) {
		search(expectedValue);

		if (!driver.findElements(By.xpath("//*[contains(text(),'No data found')]")).isEmpty()) {
			return false;
		}

		int columnIndex = getColumnIndex(columnName);

		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));

		return rows.stream().map(WebElement::getText).map(String::trim)
				.anyMatch(text -> text.equalsIgnoreCase(expectedValue));

	}

	@StepName("Retrieved the Offer Type from the Offer list")
	public String getOfferTypeFromList(String columnName, String expectedValue) {
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

	@StepName("Navigated to the first page of the Offer Type list")
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

	@StepName("Opened the Offer Type for editing")
	public void clickEdit(String offerTypeName) {

		By editButton = By.xpath("//tr[td[normalize-space()='" + offerTypeName + "']]//button[@title='Edit']");

		driver.findElement(editButton).click();
	}

	@StepName("Deleted the Offer")
	public void deleteOffer(String offerTypeName) {

		By rowDeleteButton = By.xpath("//tr[td[normalize-space()='" + offerTypeName + "']]//button[@title='Delete']");

		By confirmDeleteButton = By.xpath("//div[contains(@class,'justify-end')]//button[normalize-space()='Delete']");

		driver.findElement(rowDeleteButton).click();

		waitUtils.waitForVisibility(confirmDeleteButton);

		driver.findElement(confirmDeleteButton).click();
	}

	public void waitForOfferToDisappear(String offerTypeName) {

		By offerRow = By.xpath("//tr[td[normalize-space()='" + offerTypeName + "']]");

		waitUtils.waitForInvisibility(offerRow);
	}

	public ToastResponse captureToast() {
		return toastUtils.captureToast();
	}

	public void waitForToastToDisappear() {
		toastUtils.waitForToastToDisappear();
	}
	/*
	 * public WebElement waitForToast() {
	 * 
	 * WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	 * 
	 * return
	 * wait.until(ExpectedConditions.visibilityOfElementLocated(toastLocator)); }
	 * 
	 * public String getToastType() {
	 * 
	 * WebElement toast = waitUtils.waitForVisibility(toastLocator);
	 * 
	 * String type = toast.getAttribute("data-type");
	 * 
	 * return type != null ? type.trim() : ""; }
	 * 
	 * public String getToastMessage() {
	 * 
	 * WebElement toast = waitUtils.waitForVisibility(toastLocator);
	 * 
	 * return toast.getText().trim(); }
	 */

}

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
import schneider.utils.WaitUtils;

public class OfferType extends AbstractComponent {
	WebDriver driver;
	WaitUtils waitUtils;

	public OfferType(WebDriver driver) {
		super(driver);
		this.driver = driver;
		this.waitUtils = new WaitUtils(driver);
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

	public void goToOfferTypePage() {
		offerNavigation.click();
		waitElementToAppear(offerScreenName);
	}

	public void createOfferType(String Name) {
		clickAddButton();
		waitElementToAppear(offerName);
		offerName.sendKeys(Name);
	}

	public void createOffer() {
		offerSave.click();
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

	public boolean isOfferPresentInList(String columnName, String expectedValue) {

		int columnIndex = getColumnIndex("Offer Type");

		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));

		return rows.stream().map(WebElement::getText).map(String::trim)
				.anyMatch(value -> value.equalsIgnoreCase(expectedValue));
	}

	public void clickEdit(String offerTypeName) {

		By editButton = By.xpath("//tr[td[normalize-space()='" + offerTypeName + "']]//button[@title='Edit']");

		driver.findElement(editButton).click();
	}

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

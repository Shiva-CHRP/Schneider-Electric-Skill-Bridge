package schneider.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;
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
	
	public void goToZone() {
		zoneNavigation.click();
		waitElementToAppear(zoneScreenName);
	}
	
	public void createZone(String Name) {
		clickAddButton();
		waitElementToAppear(zoneName);
		zoneName.sendKeys(Name);
	}
	
	public void createZone() {
		zoneName.click();
	}
	
	public String getZoneByName(String zoneName) {
	    return driver.findElement(By.xpath("//td[text()='" + zoneName + "']")).getText();
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
	
	public boolean isZonePresentInList(String columnName, String expectedValue) {

		int columnIndex = getColumnIndex("Zone");

		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));

		return rows.stream().map(WebElement::getText).map(String::trim)
				.anyMatch(value -> value.equalsIgnoreCase(expectedValue));
	}
	
	public void clickEditZone(String zoneName) {

		By editButton = By.xpath("//tr[td[normalize-space()='" + zoneName + "']]//button[@title='Edit']");

		driver.findElement(editButton).click();
	}
	
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

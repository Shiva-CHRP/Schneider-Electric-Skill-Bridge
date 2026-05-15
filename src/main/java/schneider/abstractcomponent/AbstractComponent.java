package schneider.abstractcomponent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import schneider.utils.ToastUtils;
import schneider.utils.WaitUtils;

public class AbstractComponent {

	protected WebDriver driver;
	protected WaitUtils waitUtils;
	public ToastUtils toastUtils;
	public AbstractComponent(WebDriver driver) {

		this.driver = driver;

		this.waitUtils = new WaitUtils(driver);
		toastUtils = new ToastUtils(driver);
		PageFactory.initElements(driver, this);
	}

	// WAIT WRAPPERS
	public void waitElementToAppear(WebElement element) {

		waitUtils.waitForVisibility(element);
	}

	public void waitElementToBeClickable(WebElement element) {

		waitUtils.waitForClickable(element);
	}
	public void waitElementToBeClickable(By locator) {

		waitUtils.waitForClickable(locator);
	}
	public void waitForElementToDisappear(WebElement element) {

		waitUtils.waitForInvisibility(element);
	}
	
	public WebElement waitForVisibility(By locator) {

		return waitUtils.waitForVisibility(locator);
	}
	public void waitForVisibility(WebElement element) {

		waitUtils.waitForVisibility(element);
	}
	
	// Drop Downs
	public void selectByVisibleText(WebElement element, String text) {

		new Select(element).selectByVisibleText(text);
	}

	public void selectByValue(WebElement element, String value) {

		new Select(element).selectByValue(value);
	}

	public void selectByIndex(WebElement element, int index) {

		new Select(element).selectByIndex(index);
	}

	// Alert
	public void acceptAlert() {

		waitUtils.waitForAlert();

		driver.switchTo().alert().accept();
	}

	public void dismissAlert() {

		waitUtils.waitForAlert();

		driver.switchTo().alert().dismiss();
	}

	public String getAlertText() {

		waitUtils.waitForAlert();

		return driver.switchTo().alert().getText();
	}

	// Scroll
	public void scrollToElement(WebElement element) {

		waitUtils.scrollIntoView(element);
	}

	// Window Handlers
	public void switchToWindow(String title) {

		for (String handle : driver.getWindowHandles()) {

			driver.switchTo().window(handle);

			if (driver.getTitle().contains(title)) {
				break;
			}
		}
	}

	protected By addBtn = By.xpath("//div[@title='Add']");

	public void clickAddButton() {

		driver.findElement(addBtn).click();
	}
	
	
	

}

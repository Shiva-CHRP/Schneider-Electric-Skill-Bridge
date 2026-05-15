package schneider.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtils {

	private WebDriver driver;
	private WebDriverWait wait;

	public WaitUtils(WebDriver driver) {

		this.driver = driver;

		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// VISIBILITY
	public void waitForVisibility(WebElement element) {

		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public WebElement waitForVisibility(By locator) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	// CLICKABLE
	public void waitForClickable(WebElement element) {

		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public void waitForClickable(By locator) {

		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	// INVISIBILITY
	public void waitForInvisibility(WebElement element) {

		wait.until(ExpectedConditions.invisibilityOf(element));
	}

	public boolean waitForInvisibility(By locator) {

		return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	// ALERT
	public void waitForAlert() {

		wait.until(ExpectedConditions.alertIsPresent());
	}

	// TITLE
	public void waitForTitleContains(String title) {

		wait.until(ExpectedConditions.titleContains(title));
	}

	// URL
	public void waitForUrlContains(String url) {

		wait.until(ExpectedConditions.urlContains(url));
	}

	// SCROLL
	public void scrollIntoView(WebElement element) {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	// JS CLICK
	public void clickUsingJS(WebElement element) {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("arguments[0].click();", element);
	}

	public static void waitForPageLoad(WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	    wait.until(webDriver ->
	        ((JavascriptExecutor) webDriver)
	            .executeScript("return document.readyState")
	            .equals("complete")
	    );
	}

	
}

package schneider.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ToastUtils {
	WebDriver driver;
	WebDriverWait wait;
	private By toastLocator = By.xpath("//*[contains(@data-type,'success') or contains(@data-type,'error')]");

	public ToastUtils(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	}

	private WebElement getToast() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(toastLocator));
	}

	public String getToastType() {
		String type = getToast().getAttribute("data-type");
		return type != null ? type.trim() : "";
	}

	public String getToastMessage() {
		return getToast().getText().trim();
	}
}

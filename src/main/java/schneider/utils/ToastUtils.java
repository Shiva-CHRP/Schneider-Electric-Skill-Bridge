package schneider.utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ToastUtils {
	WebDriver driver;
	WebDriverWait wait;
	private WebElement toastElement;
	private By toastLocator = By.xpath("//li[@data-sonner-toast]");

	public ToastUtils(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	/*public String getToastMessage() {
		if (toastElement == null) {
			return "NO_TOAST";
		}

		return toastElement.getText().trim();
	}

	public String getToastType() {
		if (toastElement == null) {
			return "unknown";
		}

		return toastElement.getAttribute("data-type");
	}*/

	
	
	public ToastResponse captureToast() {

	    By toast = By.xpath("//li[@data-sonner-toast]");
	    WebElement el = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(toast)
	    );
	   
	    return new ToastResponse(
                el.getAttribute("data-type"),
                el.getText().trim()
        );
	    
	}
	public void waitForToastToDisappear() {

		By toast = By.xpath("//li[@data-sonner-toast]");

        wait.until(ExpectedConditions.invisibilityOfElementLocated(toast));
	}
}

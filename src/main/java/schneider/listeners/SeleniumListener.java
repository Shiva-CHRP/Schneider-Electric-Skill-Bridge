package schneider.listeners;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.*;
import org.openqa.selenium.support.events.WebDriverListener;
import com.aventstack.extentreports.MediaEntityBuilder;

import schneider.utils.ExtentTestManager;
import schneider.utils.ScreenshotUtils;
import schneider.utils.StepNameUtil;
import schneider.utils.WaitUtils;

public class SeleniumListener implements WebDriverListener  {

	WebDriver driver;

	public SeleniumListener() {
		this.driver = driver;
	}

	 private void log(String stepName) {
		 try {
			WaitUtils.waitForPageLoad(driver);
			 //String stepName = StepNameUtil.getStepName();
			    ExtentTestManager.getTest().pass(stepName,
			            MediaEntityBuilder.createScreenCaptureFromPath(ScreenshotUtils.getScreenshot(driver,stepName))
			            .build());
		 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
	    }

	

    @Override
    public void afterClick(WebElement element) {
        log(StepNameUtil.getStepName());
    }

   

    @Override
    public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
        log(StepNameUtil.getStepName());
    }

    

    @Override
    public void afterGet(WebDriver driver,String url) {
        log(StepNameUtil.getStepName());
    }
}

package schneider.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class BulkUpload extends AbstractComponent{
	
	public BulkUpload(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
	}

	@FindBy(xpath = "//a[@href='/admin/assessment-hub/bulk-upload']")
	WebElement bulkUploadNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Bulk Upload Questions']")
	WebElement bulkUploadScreenName;

	
	public void goToBulkUpload() {
		bulkUploadNavigation.click();
		waitElementToAppear(bulkUploadScreenName);
	}
	
}

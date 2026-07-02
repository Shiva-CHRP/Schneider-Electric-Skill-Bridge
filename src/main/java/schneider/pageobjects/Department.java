package schneider.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;
import schneider.utils.ToastResponse;

public class Department extends AbstractComponent {

	public Department(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@href='/admin/departments']")
	WebElement departmentNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Department Master']")
	WebElement departmentScreenName;
	
	@FindBy(xpath = "//input[@name='deptName']")
	WebElement departmentName;
	
	@FindBy(xpath = "//button[@type='submit']")
	WebElement departmentSave;
	public void goToDepartment() {
		departmentNavigation.click();
		waitElementToAppear(departmentScreenName);
	}
	
	public void createDepartment(String Name) {
		clickAddButton();
		waitElementToAppear(departmentName);
		departmentName.sendKeys(Name);
	}

	public void createDepartment() {
		departmentSave.click();
	}
	
	public String getDepartmentByName(String departmentName) {
	    return driver.findElement(By.xpath("//td[text()='" + departmentName + "']")).getText();
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
	
	public boolean isDepartmentPresentInList(String columnName, String expectedValue) {

		int columnIndex = getColumnIndex("Offer Type");

		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));

		return rows.stream().map(WebElement::getText).map(String::trim)
				.anyMatch(value -> value.equalsIgnoreCase(expectedValue));
	}
	
	public void clickEditDepartment(String departmentName) {

		By editButton = By.xpath("//tr[td[normalize-space()='" + departmentName + "']]//button[@title='Edit']");

		driver.findElement(editButton).click();
	}
	
	public void deleteDepartment(String departmentName) {

		By rowDeleteButton = By.xpath("//tr[td[normalize-space()='" + departmentName + "']]//button[@title='Delete']");

		By confirmDeleteButton = By.xpath("//div[contains(@class,'justify-end')]//button[normalize-space()='Delete']");

		driver.findElement(rowDeleteButton).click();

		waitUtils.waitForVisibility(confirmDeleteButton);

		driver.findElement(confirmDeleteButton).click();
	}
	
	public void waitForDepartmentToDisappear(String departmentName) {

		By offerRow = By.xpath("//tr[td[normalize-space()='" + departmentName + "']]");

		waitUtils.waitForInvisibility(offerRow);
	}
	
	public ToastResponse captureToast() {
	    return toastUtils.captureToast();
	}

	public void waitForToastToDisappear() {
	    toastUtils.waitForToastToDisappear();
	}
}

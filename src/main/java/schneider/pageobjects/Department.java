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

//	@FindBy(xpath = "//button[.//*[contains(@class,'lucide-chevron-right')]]")
//	private List<WebElement> nextButton;
	private By nextButton = By.xpath("//button[.//*[contains(@class,'lucide-chevron-right')]]");

	@StepName("Navigated to the Department screen")
	public void goToDepartment() {
		departmentNavigation.click();
		waitElementToAppear(departmentScreenName);
	}

	@StepName("Click on the Add Button for Creating a new Department")
	public void createDepartment(String Name) {
		clickAddButton();
		waitElementToAppear(departmentName);
		departmentName.sendKeys(Name);
	}

	@StepName("Created a new Department")
	public void saveDepartment() {
		departmentSave.click();
	}

	@StepName("Retrieved the departments details by name")
	public String getDepartmentByName(String departmentName) {
		return driver.findElement(By.xpath("//td[text()='" + departmentName + "']")).getText();
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

	@StepName("Verified the Department is present in the list")
	public boolean isDepartmentPresentInList(String columnName, String expectedValue) {
		search(expectedValue);

		if (!driver.findElements(By.xpath("//*[contains(text(),'No data found')]")).isEmpty()) {
			return false;
		}

		int columnIndex = getColumnIndex(columnName);

		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));

		return rows.stream().map(WebElement::getText).map(String::trim)
				.anyMatch(text -> text.equalsIgnoreCase(expectedValue));
	}

	@StepName("Retrieved the Department from the Offer list")
	public String getDepartmentFromList(String columnName, String expectedValue) {
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

	@StepName("Navigated to the first page of the Department list")
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

	@StepName("Opened the Department for editing")
	public void clickEditDepartment(String departmentName) {

		By editButton = By.xpath("//tr[td[normalize-space()='" + departmentName + "']]//button[@title='Edit']");

		driver.findElement(editButton).click();
	}

	@StepName("Deleted the Department")
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

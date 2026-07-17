package schneider.pageobjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import schneider.abstractcomponent.AbstractComponent;
import schneider.annotations.StepName;
import schneider.utils.StepNameUtil;
import schneider.utils.ToastResponse;

public class Users extends AbstractComponent {

	public Users(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//a[@href='/admin/users']")
	WebElement userNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Users Management']")
	WebElement userScreenName;

	@FindBy(xpath = "//div[@title='Add']")
	WebElement addBtn;

	@FindBy(xpath = "//label[contains(normalize-space(),'User Type')]/following-sibling::button[@role='combobox']")
	WebElement userTypeDropdown;

	@FindBy(xpath = "//label[contains(normalize-space(),'SESA ID')]/following::input[1]")
	WebElement sesaID;

	@FindBy(xpath = "//label[contains(normalize-space(),'First Name')]/following::input[1]")
	WebElement firstName;

	@FindBy(xpath = "//label[contains(normalize-space(),'Last Name')]/following::input[1]")
	WebElement lastName;

	@FindBy(xpath = "//label[contains(normalize-space(),'Email')]/following::input[1]")
	WebElement email;

	@FindBy(xpath = "//label[contains(normalize-space(),'Employee Code')]/following::input[1]")
	WebElement otherEmployeeCode;

	@FindBy(xpath = "//label[contains(text(),'User Role')]/following::button[contains(.,'Select role')]")
	WebElement userRolesSE;

	@FindBy(xpath = "//label[contains(.,'User Role')]/parent::div//input")
	WebElement userRolesPartner;

	@FindBy(xpath = "//button[@role='combobox']//span[normalize-space()='Select role']/parent::button")
	WebElement userRolesOther;

	@FindBy(xpath = "//label[contains(.,'Partner')]/parent::div//button")
	WebElement partnerDropDown;

	@FindBy(xpath = "//label[contains(.,'BFO ID')]/parent::div//input")
	WebElement bFOId;

	@FindBy(xpath = "//label[contains(.,'Department')]/following::button[1]")
	WebElement departmentSE;

	@FindBy(xpath = "//label[contains(.,'Offer Type')]/following-sibling::div//button")
	WebElement offerType;

	@FindBy(xpath = "//label[contains(.,'Category')]/following-sibling::div//button")
	WebElement category;

	@FindBy(xpath = "//label[contains(.,'Sub Category')]/following-sibling::div//button")
	WebElement subCategory;

	@FindBy(xpath = "//label[contains(.,'Country')]/following::button[1]")
	WebElement country;

	@FindBy(xpath = "//label[contains(.,'Cluster')]/following::button[1]")
	WebElement cluster;

	@FindBy(xpath = "//label[contains(.,'Zone')]/following::button[1]")
	WebElement zone;

	@FindBy(xpath = "//label[contains(.,'Country')]/parent::div//button[@role='combobox']")
	WebElement countryPartner;

	@FindBy(xpath = "//label[contains(.,'Cluster')]/parent::div//button[@role='combobox']")
	WebElement clusterPartner;

	@FindBy(xpath = "//label[contains(.,'Zone')]/parent::div//button[@role='combobox']")
	WebElement zonePartner;

	@FindBy(xpath = "//label[contains(.,'Department')]/parent::div//button[@role='combobox']")
	WebElement departmentOther;

	@FindBy(xpath = "//button[@type='submit' and normalize-space()='Create User']")
	WebElement createUser;

	@FindBy(xpath = "//button[@type='button' and normalize-space()='Back to List']")
	WebElement backToList;

	private By duplicateEmailToast = By.xpath("//div[contains(@class,'toast')]");

	@FindBy(xpath = "//div[b[normalize-space()='ID:']]")
	private WebElement createdUserId;

	@FindBy(xpath = "//div[b[normalize-space()='Email:']]")
	private WebElement createdUserEmail;

	@FindBy(xpath = "//div[normalize-space()='Password']/following-sibling::code")
	private WebElement createdUserPassword;

	@FindBy(xpath = "//button[normalize-space()='Done']")
	private WebElement doneButtonAfterUserCreation;

	@StepName("Navigated to the User Screen")
	public void goToUsers() {
		userNavigation.click();
		waitElementToAppear(userScreenName);
	}

	public boolean usersAddButtonEnable() {
		boolean isEnabled = addBtn.isEnabled();
		return isEnabled;
	}

	@StepName("Click on the Add Button for Creating a new User")
	public void clickOnAddUser() {
		clickAddButton();
		waitElementToAppear(userTypeDropdown);
	}

	@StepName("Created a new User")
	public void createUser() throws InterruptedException {
		createUser.click();
//		Thread.sleep(1000);
//		System.out.println(driver.getPageSource());
		// waitElementToAppear(createdUserId);
	}
	
	@StepName("Click on the 'Back To List' Button for Exsiting from User Creation screen")
	public void clickOnBackToList() {
		waitElementToAppear(backToList);
		backToList.click();
		waitElementToAppear(userScreenName);
	}

	@StepName("Validating Email Error while Creating the User")
	public boolean isEmailAlreadyRegisteredErrorDisplayed() {
		try {
			WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));

			return shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@data-sonner-toast]")))
					.isDisplayed();

		} catch (TimeoutException e) {
			return false;
		}
	}

	@StepName("Validating Email Error while Creating the User")
	public String getEmailAlreadyRegisteredError() {
		return waitUtils.waitForVisibility(duplicateEmailToast).getText();
	}

	@StepName("Get Created User ID")
	public String getCreatedUserId() {
		waitUtils.waitForVisibility(createdUserId);
		return createdUserId.getText().replace("ID:", "").trim();
	}

	@StepName("Get Created User Email")
	public String getCreatedUserEmail() {
		waitUtils.waitForVisibility(createdUserEmail);
		return createdUserEmail.getText().replace("Email:", "").trim();
	}

	@StepName("Get Created User Password")
	public String getCreatedUserPassword() {
		waitUtils.waitForVisibility(createdUserPassword);
		return createdUserPassword.getText().trim();
	}

	@StepName("Click on Done after User Creation")
	public void clickDoneAfterUserCreation() {

		waitUtils.waitForVisibility(doneButtonAfterUserCreation);
		doneButtonAfterUserCreation.click();
		waitElementToAppear(userScreenName);

	}

	@StepName("Select the User Type from the User Type Dropdown")
	public void selectUserType(String userType) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		wait.until(ExpectedConditions.elementToBeClickable(userTypeDropdown)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[role='listbox']")));

		By option = By
				.xpath("//div[@role='listbox']//div[@role='option'][.//span[normalize-space()='" + userType + "']]");

		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(option));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

		element.click();

	}

	@StepName("Input the Valid SESA Id")
	public void inputSESAId(String sesaId) {
		sesaID.sendKeys(sesaId);
	}
	
	@StepName("Input the First Name")
	public void inputFirstName(String name) {
		firstName.sendKeys(name);
	}
	
	@StepName("Input the Last Name")
	public void inputLastName(String name) {
		lastName.sendKeys(name);
	}
	
	@StepName("Input the Email")
	public void inputEmail(String name) {
		email.sendKeys(name);
	}

	@StepName("Input thr Other Employee Code")
	public void inputOtherEmployeeCode(String name) {
		otherEmployeeCode.sendKeys(name);
	}

	@StepName("Select User Role(s)")
	public void selectUserRole(String userType, String roles) throws InterruptedException {

		StepNameUtil.setStepName("Selected " + roles + " Role(s) for " + userType + " User Type");

		try {
			waitUtils.waitForVisibility(userRolesSE);
			userRolesSE.click();

			String[] roleList = roles.split("\\+");

			for (String role : roleList) {

				role = role.trim();

				By roleOption = By.xpath(
						"//div[contains(@class,'overflow-auto')]//button[.//span[normalize-space()='" + role + "']]");

				waitUtils.waitForVisibility(roleOption);

				driver.findElement(roleOption).click();

				// System.out.println("Selected Role: " + role);
			}

			By doneButton = By.xpath("//button[normalize-space()='Done']");

			waitUtils.waitForVisibility(doneButton);

			driver.findElement(doneButton).click();

		} finally {

			StepNameUtil.clearStepName();

		}

	}
	
	@StepName("Select the User Role's for the Other User Type")
	public void selectUserRoleOther(String userRole) {

		waitUtils.waitForVisibility(userRolesOther);
		userRolesOther.click();
		By roleOption = By.xpath("//select[@aria-hidden='true']//option[normalize-space()='" + userRole + "']");

		WebElement option = null;

		for (int i = 0; i < 10; i++) {
			try {
				WebElement temp = driver.findElement(roleOption);

				if (temp.isDisplayed()) {
					option = temp;
					break;
				}

			} catch (Exception ignored) {
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}

		if (option == null) {
			throw new RuntimeException("User Role not found: " + userRole);
		}

		// Click dropdown option from visible list
		By visibleRole = By.xpath("//div[@role='option' and normalize-space()='" + userRole + "']");

		waitUtils.waitForVisibility(visibleRole);
		driver.findElement(visibleRole).click();
	}

	@StepName("Select the Department for Other User Type")
	public void selectDepartmentOther(String departmentName) {

		departmentOther.click();

		By departmentOption = By.xpath("//div[@role='option']//span[normalize-space(text())='" + departmentName + "']");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		wait.until(ExpectedConditions.elementToBeClickable(departmentOption)).click();
	}

	@StepName("Select the Partner from the Partner dropdown for Partner User Type")
	public void selectPartner(String partnerName) {

		partnerDropDown.click();
		// System.out.println(driver.getPageSource());
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		By partnerOption = By.xpath("//div[@role='option'][.//span[normalize-space()='" + partnerName + "']]");

		wait.until(ExpectedConditions.elementToBeClickable(partnerOption)).click();
	}

	@StepName("Validate the Partner bFO ID")
	public void verifyPartenerbFOId(String expectedbFOId) {

		waitUtils.waitForVisibility(bFOId);

		String actualPartnerbFOId = bFOId.getAttribute("value");

		if (!actualPartnerbFOId.equals(expectedbFOId)) {
			throw new RuntimeException(
					"Partner bFOId mismatch. Expected: " + expectedbFOId + " but found: " + actualPartnerbFOId);
		}
	}

	@StepName("Validate the Partner User Role")
	public void verifyPartenerUserRole(String expectedUserRole) {
		waitUtils.waitForVisibility(userRolesPartner);

		String actualPartnerUserRole = userRolesPartner.getAttribute("value").trim();

		if (!actualPartnerUserRole.equals(expectedUserRole)) {
			throw new RuntimeException(
					"Partner User mismatch. Expected: " + expectedUserRole + " but found: " + actualPartnerUserRole);
		}
	}

	@StepName("Validate the Department for SE Employee User Type")
	public void verifyDepartmentForSEUserType(List<String> expectedDepartment) {

		waitUtils.waitForVisibility(departmentSE);

		String actualDepartment = departmentSE.getText().trim();

		for (String expected : expectedDepartment) {
			if (!actualDepartment.contains(expected)) {
				throw new RuntimeException(
						"Department mismatch. Expected: " + expectedDepartment + " but found: " + actualDepartment);
			}
		}

	}

	@StepName("Select the Department for SE Employee User Type")
	public void selectDepartmentsSE(String... departments) {
		waitUtils.waitForVisibility(departmentSE);
		departmentSE.click();

		for (String department : departments) {

			By option;

			if (department.equalsIgnoreCase("Select All")) {
				option = By.xpath(
						"//div[contains(@class,'border-b')]//button[.//input[@type='checkbox'] and contains(.,'Select All')]");
			} else {
				option = By.xpath("//div[contains(@class,'overflow-auto')]//button[.//span[normalize-space()='"
						+ department + "']]");
			}

			waitUtils.waitForVisibility(option);
			driver.findElement(option).click();
		}

		driver.findElement(By.xpath("//button[normalize-space()='Done']")).click();
	}

	@StepName("Select the Offer Types from Offer Type dropdown")
	public void selectOfferTypes(String... offerTypes) {
		waitUtils.waitForVisibility(offerType);
		offerType.click();

		for (String offerType : offerTypes) {

			By option;

			if (offerType.equalsIgnoreCase("Select All")) {
				option = By.xpath(
						"//div[contains(@class,'border-b')]//button[.//input[@type='checkbox'] and contains(.,'Select All')]");
			} else {
				option = By.xpath("//div[contains(@class,'overflow-auto')]//button[.//span[normalize-space()='"
						+ offerType + "']]");
			}

			waitUtils.waitForVisibility(option);
			driver.findElement(option).click();
		}

		driver.findElement(By.xpath("//button[normalize-space()='Done']")).click();
	}

	@StepName("Select the Categories from Category dropdown")
	public void selectCategories(String... categories) {
		waitUtils.waitForVisibility(category);
		category.click();
		for (String category : categories) {

			By option;

			if (category.equalsIgnoreCase("Select All")) {

				option = By.xpath(
						"//div[contains(@class,'border-b')]//button[.//input[@type='checkbox'] and contains(.,'Select All')]");

			} else {

				option = By.xpath("//div[contains(@class,'overflow-auto')]//button[.//span[normalize-space()='"
						+ category + "']]");
			}

			waitUtils.waitForVisibility(option);
			driver.findElement(option).click();
		}

		driver.findElement(By.xpath("//button[normalize-space()='Done']")).click();
	}

	@StepName("Select the Sub Categories from Sub Category dropdown")
	public void selectSubCategories(String... subCategories) {
		waitUtils.waitForVisibility(subCategory);
		subCategory.click();
		for (String subCategory : subCategories) {

			By option;

			if (subCategory.equalsIgnoreCase("Select All")) {

				option = By.xpath(
						"//div[contains(@class,'border-b')]//button[.//input[@type='checkbox'] and contains(.,'Select All')]");

			} else {

				option = By.xpath("//div[contains(@class,'overflow-auto')]//button[.//span[normalize-space()='"
						+ subCategory + "']]");
			}

			waitUtils.waitForVisibility(option);
			driver.findElement(option).click();
		}

		driver.findElement(By.xpath("//button[normalize-space()='Done']")).click();
	}

	@StepName("Select the Zones from Zone dropdown")
	public void selectZones(String... zones) {
		waitUtils.waitForVisibility(zone);
		zone.click();
		for (String zone : zones) {

			By option;

			if (zone.equalsIgnoreCase("Select All")) {

				option = By.xpath(
						"//div[contains(@class,'border-b')]//button[.//input[@type='checkbox'] and contains(.,'Select All')]");

			} else {

				option = By.xpath(
						"//div[contains(@class,'overflow-auto')]//button[.//span[normalize-space()='" + zone + "']]");
			}

			waitUtils.waitForVisibility(option);
			driver.findElement(option).click();
		}

		driver.findElement(By.xpath("//button[normalize-space()='Done']")).click();
	}

	@StepName("Select the Zones from Zone dropdown for Partner User Type")
	public void selectZonePartner(String zoneName) {

		waitUtils.waitForVisibility(zonePartner);
		zonePartner.click();

		By option = By.xpath("//select//option[normalize-space()='" + zoneName + "']");

		waitUtils.waitForVisibility(option);
		driver.findElement(option).click();
	}

	@StepName("Select the Clusters from Cluster dropdown")
	public void selectClusters(String... clusters) {
		waitUtils.waitForVisibility(cluster);
		cluster.click();
		for (String cluster : clusters) {

			By option;

			if (cluster.equalsIgnoreCase("Select All")) {

				option = By.xpath(
						"//div[contains(@class,'border-b')]//button[.//input[@type='checkbox'] and contains(.,'Select All')]");

			} else {

				option = By.xpath("//div[contains(@class,'overflow-auto')]//button[.//span[normalize-space()='"
						+ cluster + "']]");
			}

			waitUtils.waitForVisibility(option);
			driver.findElement(option).click();
		}

		driver.findElement(By.xpath("//button[normalize-space()='Done']")).click();
	}

	@StepName("Select the Clusters from Cluster dropdown for Partner User Type")
	public void selectClusterPartner(String clusterName) {

		waitUtils.waitForVisibility(clusterPartner);
		clusterPartner.click();

		By option = By.xpath("//div[contains(@class,'overflow-auto')]//span[normalize-space()='" + clusterName + "']");

		waitUtils.waitForVisibility(option);
		driver.findElement(option).click();
	}

	@StepName("Select the Multi-Countries from Country dropdown")
	public void selectCountries(String... countries) {

		waitUtils.waitForVisibility(country);
		country.click();

		for (String countryName : countries) {

			By option;

			if (countryName.equalsIgnoreCase("Select All")) {

				option = By.xpath("//button[.//input[@type='checkbox'] and contains(.,'Select All')]");

			} else {

				option = By.xpath("//button[.//span[normalize-space()='" + countryName + "']]");
			}

			waitUtils.waitForVisibility(option);
			WebElement countryOption = driver.findElement(option);
			countryOption.click();
		}
		By doneButton = By.xpath("//button[normalize-space()='Done']");

		waitUtils.waitForVisibility(doneButton);
		driver.findElement(doneButton).click();
	}

	@StepName("Select the Country from Country dropdown")
	public void selectCountry(String countryName) {

		waitUtils.waitForVisibility(countryPartner);
		countryPartner.click();

		By option = By.xpath("//div[@role='option' and normalize-space()='" + countryName + "']");

		waitUtils.waitForClickable(option);
		driver.findElement(option).click();
	}

	public ToastResponse captureToast() {
		return toastUtils.captureToast();
	}

	public void waitForToastToDisappear() {
		toastUtils.waitForToastToDisappear();
	}
}

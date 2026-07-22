package schneider.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;
import schneider.annotations.StepName;

public class GroupMaster extends AbstractComponent {

	public GroupMaster(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@href='/admin/group-master']")
	WebElement groupMasterNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Group Master']")
	WebElement groupMasterScreenName;

	@FindBy(xpath = "//h1[normalize-space()='Create Group']")
	WebElement createGroup;

	@FindBy(xpath = "//label[normalize-space()='Group Name']/following-sibling::input")
	WebElement groupName;

	@FindBy(xpath = "//label[normalize-space()='Offer Type']/following-sibling::button")
	WebElement offerType;

	@FindBy(xpath = "//label[normalize-space()='Category']/following-sibling::div//button")
	WebElement category;

	@FindBy(xpath = "//label[normalize-space()='Sub Category']/following-sibling::div//button")
	WebElement subCategory;

	@FindBy(xpath = "//label[normalize-space()='Zone']/following-sibling::div//button")
	WebElement zone;

	@FindBy(xpath = "//label[normalize-space()='Cluster']/following-sibling::div//button")
	WebElement cluster;

	@FindBy(xpath = "//label[normalize-space()='Country']/following-sibling::div//button")
	WebElement country;

	@FindBy(xpath = "//label[normalize-space()='Assign Trainer']/following-sibling::button")
	WebElement assignTrainer;

	@FindBy(xpath = "//table/tbody/tr")
	private List<WebElement> tableRows;

	@FindBy(xpath = "//button[normalize-space()='Create Group']")
	private WebElement createGroupButton;

	@FindBy(xpath = "//button[normalize-space()='Cancel']")
	private WebElement cancelButton;
	
	@StepName("Navigate to the Group Master")
	public void goToGroupMaster() {
		groupMasterNavigation.click();
		waitElementToAppear(groupMasterScreenName);
	}

	@StepName("Click on the Add Group Button")
	public void clickOnAddGroup() {
		clickAddButton();
		waitElementToAppear(groupName);
	}
	
	@StepName("Input the Group Name")
	public void inputGroupName(String name) {

		groupName.sendKeys(name);
	}

	@StepName("Select the Offer Type from Offer Type dropdown")
	public void selectOfferType(String offerTypes) {

		waitUtils.waitForVisibility(offerType);
		offerType.click();

		By option = By.xpath("//div[@role='option' and normalize-space()='" + offerTypes + "']");

		waitUtils.waitForClickable(option);
		driver.findElement(option).click();
	}

	@StepName("Select the Categories from Category dropdown")
	public void selectCategories(String... categories) {

		waitUtils.waitForVisibility(category);
		category.click();

		for (String categoryName : categories) {

			By option;

			if (categoryName.equalsIgnoreCase("Select All")) {

				option = By.xpath("//button[.//input[@type='checkbox'] and contains(.,'Select All')]");

			} else {

				option = By.xpath("//button[.//span[normalize-space()='" + categoryName + "']]");
			}

			waitUtils.waitForVisibility(option);
			WebElement categoryOption = driver.findElement(option);
			categoryOption.click();
		}
		By doneButton = By.xpath("//button[normalize-space()='Done']");

		waitUtils.waitForVisibility(doneButton);
		driver.findElement(doneButton).click();

	}

	@StepName("Select the Sub Categories from Sub Category dropdown")
	public void selectSubCategories(String... subCategories) {

		waitUtils.waitForVisibility(subCategory);
		subCategory.click();

		for (String subCategoryName : subCategories) {

			By option;

			if (subCategoryName.equalsIgnoreCase("Select All")) {

				option = By.xpath("//button[.//input[@type='checkbox'] and contains(.,'Select All')]");

			} else {

				option = By.xpath("//button[.//span[normalize-space()='" + subCategoryName + "']]");
			}

			waitUtils.waitForVisibility(option);
			WebElement subCategoryOption = driver.findElement(option);
			subCategoryOption.click();
		}
		By doneButton = By.xpath("//button[normalize-space()='Done']");

		waitUtils.waitForVisibility(doneButton);
		driver.findElement(doneButton).click();

	}

	@StepName("Select the Zones from Zone dropdown")
	public void selectZone(String... zones) {

		waitUtils.waitForVisibility(zone);
		zone.click();

		for (String zoneName : zones) {

			By option;

			if (zoneName.equalsIgnoreCase("Select All")) {

				option = By.xpath("//button[.//input[@type='checkbox'] and contains(.,'Select All')]");

			} else {

				option = By.xpath("//button[.//span[normalize-space()='" + zoneName + "']]");
			}

			waitUtils.waitForVisibility(option);
			WebElement zoneOption = driver.findElement(option);
			zoneOption.click();
		}
		By doneButton = By.xpath("//button[normalize-space()='Done']");

		waitUtils.waitForVisibility(doneButton);
		driver.findElement(doneButton).click();

	}

	@StepName("Select the Clusters from Cluster dropdown")
	public void selectCluster(String... clusters) {

		waitUtils.waitForVisibility(cluster);
		cluster.click();

		for (String clusterName : clusters) {

			By option;

			if (clusterName.equalsIgnoreCase("Select All")) {

				option = By.xpath("//button[.//input[@type='checkbox'] and contains(.,'Select All')]");

			} else {

				option = By.xpath("//button[.//span[normalize-space()='" + clusterName + "']]");
			}

			waitUtils.waitForVisibility(option);
			WebElement clusterOption = driver.findElement(option);
			clusterOption.click();
		}
		By doneButton = By.xpath("//button[normalize-space()='Done']");

		waitUtils.waitForVisibility(doneButton);
		driver.findElement(doneButton).click();

	}

	@StepName("Select the Countries from Country dropdown")
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
	
	@StepName("Select the Offer Type from Offer Type dropdown")
	public void selectAssignTrainer(String assignTrainers) {

		waitUtils.waitForVisibility(assignTrainer);
		assignTrainer.click();

		By option = By.xpath("//div[@role='option' and normalize-space()='" + assignTrainers + "']");

		waitUtils.waitForClickable(option);
		driver.findElement(option).click();
	}

	@StepName("Select the Users from Employee Selection")
	public void selectFirstRow() {
		if (!tableRows.isEmpty()) {
			tableRows.get(0).findElement(By.xpath(".//button[@role='checkbox']")).click();
		}
	}

	@StepName("Click on the Create Group Button")
	public void clickCreateGroup() {
		createGroupButton.click();
	}

	@StepName("Click on the Cancel Group Button")
	public void clickCancel() {
		waitElementToAppear(cancelButton);
		cancelButton.click();
		waitElementToAppear(groupMasterScreenName);
	}

}

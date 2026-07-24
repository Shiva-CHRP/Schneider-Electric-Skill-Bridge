package schneider.pageobjects;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import schneider.abstractcomponent.AbstractComponent;
import schneider.annotations.StepName;
import schneider.utils.ToastResponse;

public class ContentHub extends AbstractComponent {

	public ContentHub(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//a[@href='/admin/content-hub']")
	WebElement contentHubNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Content Hub Manager']")
	WebElement contentHubScreenName;

	@FindBy(xpath = "//div[@role='button' and normalize-space()='Upload']")
	WebElement uploadButton;

	@FindBy(xpath = "//h2[normalize-space()='Content Upload']")
	WebElement contentUploadScreenName;

	@FindBy(xpath = "//div[@role='button' and normalize-space()='Curricula']")
	WebElement curriculaButton;

	@FindBy(xpath = "//h2[normalize-space()='Curriculum Management']")
	WebElement curriculumManagementScreenName;

	@FindBy(xpath = "//div[@role='button' and normalize-space()='Add']")
	WebElement addCurriculumButton;

	@FindBy(xpath = "//h3[normalize-space()='Curriculum Details']")
	WebElement curriculumDetails;

	@FindBy(xpath = "//input[@id='name']")
	WebElement curriculumName;

	@FindBy(xpath = "//textarea[@id='description']")
	WebElement curriculumDescription;

	@FindBy(xpath = "//label[contains(normalize-space(.),'Offer Type')]/following::button[@role='combobox'][1]")
	WebElement offerType;

	@FindBy(xpath = "//label[contains(normalize-space(.),'Category')]/following::button[@role='combobox'][1]")
	WebElement category;

	@FindBy(xpath = "//label[contains(normalize-space(.),'Sub Category')]/following::button[@role='combobox'][1]")
	WebElement subCategory;

	@FindBy(xpath = "//input[@type='file' and @accept='image/*']")
	private WebElement coverImageUpload;

	@FindBy(xpath = "//button[normalize-space()='Crop & Save']")
	private WebElement cropAndSaveBtn;

	@FindBy(xpath = "//button[normalize-space()='Create Curriculum']")
	private WebElement createCurriculumButton;

	@FindBy(xpath = "//button[normalize-space()='Cancel']")
	private WebElement cancelCurriculumButton;

	@FindBy(xpath = "//button[normalize-space()='Update Curriculum']")
	private WebElement updateCurriculumButton;

	@FindBy(xpath = "//div[@role='menu' and @data-state='open']//div[@role='menuitem' and normalize-space()='Edit']")
	private WebElement editOption;

	@FindBy(xpath = "//div[@role='menu' and @data-state='open']//div[@role='menuitem' and normalize-space()='Delete']")
	private WebElement deleteOption;

	@FindBy(xpath = "//button[.//*[local-name()='svg' and contains(@class,'lucide-arrow-left')]]")
	private WebElement backButton;

	@FindBy(xpath = "//label[contains(normalize-space(.),'Offer Type')]/following-sibling::button[@role='combobox']")
	private WebElement offerTypeDropdown;

	@FindBy(xpath = "//label[contains(normalize-space(.),'Category')]/following-sibling::button[@role='combobox']")
	private WebElement categoryDropdown;

	@FindBy(xpath = "//label[contains(normalize-space(.),'Sub Category')]/following-sibling::button[@role='combobox']")
	private WebElement subCategoryDropdown;

	@FindBy(xpath = "//label[contains(normalize-space(.),'Curriculum')]/following-sibling::button[@role='combobox']")
	private WebElement curriculumDropdown;

	@FindBy(xpath = "//input[@id='title']")
	WebElement lessonTitle;

	@FindBy(xpath = "//button[.//span[normalize-space()='Upload File']]")
	private WebElement uploadFileSourceBtn;

	@FindBy(xpath = "//button[.//span[normalize-space()='External Link']]")
	private WebElement externalLinkSourceBtn;

	@FindBy(xpath = "//input[@id='file-upload']")
	private WebElement uploadFileInput;

	@FindBy(xpath = "//input[@id='externalUrl']")
	private WebElement externalFileLink;

	@FindBy(xpath = "//label[contains(normalize-space(.),'Duration')]/following-sibling::div//input")
	private List<WebElement> durationInputs;

	@FindBy(xpath = "//button[normalize-space()='Create Content']")
	private WebElement createContentButton;

	@FindBy(xpath = "//button[normalize-space()='Reset']")
	private WebElement resetButton;

	@FindBy(xpath = "//button[@type='submit' and normalize-space()='Upload Content']")
	private WebElement uploadContentButton;

	@FindBy(xpath = "//button[normalize-space()='Open']")
	private WebElement openButton;

	@FindBy(xpath = "//p[contains(@class,'font-semibold') and contains(@class,'text-foreground')]")
	private WebElement uploadedFileName;

	@FindBy(xpath = "//button[normalize-space()='Delete permanently']")
	WebElement curriculumDeletePermanentlyButton;

	@FindBy(xpath = "//button[normalize-space()='Cancel']")
	WebElement cancelDeleteModel;

	@FindBy(xpath = "//button[normalize-space()='Delete']")
	WebElement contentDeletePermanentlyButton;

	@FindBy(xpath = "//div[@role='menu' and @data-state='open']//div[@role='menuitem' and normalize-space()='Manage Versions']")
	private WebElement contentManageVersions;

	@FindBy(xpath = "//p[normalize-space()='Version Control']")
	WebElement versionControlModel;

	@FindBy(xpath = "//button[contains(normalize-space(),'Add New Version')]")
	WebElement addNewVersionButton;

	@FindBy(xpath = "//label[normalize-space()='Version Note']/following-sibling::textarea")
	WebElement versionNoteTextArea;

	@FindBy(xpath = "//h3[normalize-space()='Upload New Version']/ancestor::div[contains(@class,'w-80')]//input[@type='file']")
	WebElement versionFileUpload;

	@FindBy(xpath = "//button[@id='confirm-replace' and @role='checkbox']")
	WebElement confirmReplaceCheckbox;

	@FindBy(xpath = "//button[contains(normalize-space(),'Upload') and contains(normalize-space(),'Activate')]")
	WebElement uploadActivateButton;

	@FindBy(xpath = "//h3[normalize-space()='Upload New Version']/ancestor::div[contains(@class,'w-80')]//button[normalize-space()='Cancel']")
	WebElement cancelUploadVersionButton;

	@FindBy(xpath = "//span[normalize-space()='v1.0']/ancestor::div[contains(@class,'rounded-xl')]")
	WebElement versionCard;

	@FindBy(xpath = "//span[normalize-space()='v1.0']/ancestor::div[contains(@class,'rounded-xl')]//button[normalize-space()='View']")
	WebElement viewVersionButton;

	@FindBy(xpath = "//span[normalize-space()='v1.0']/ancestor::div[contains(@class,'rounded-xl')]//button[normalize-space()='Restore']")
	WebElement restoreVersionButton;

	@FindBy(xpath = "//span[normalize-space()='v1.0']/ancestor::div[contains(@class,'rounded-xl')]//button[normalize-space()='Delete']")
	WebElement deleteVersionButton;

	@FindBy(xpath = "//button[contains(normalize-space(),'Uploading')]")
	WebElement uploadingButton;

	@FindBy(xpath = "//h3[normalize-space()='Version History']")
	WebElement versionHistoryTitle;

	@FindBy(xpath = "//h3[normalize-space()='Upload New Version']")
	private WebElement uploadNewVersionDialog;
	
	@FindBy(xpath="//button[normalize-space()='Update]")
	WebElement updateContentButton;
	
	@FindBy(xpath = "//div[contains(@class,'bg-red-50')]//button")
	private WebElement duplicateContentExpandButton;

	@FindBy(xpath = "//div[contains(@class,'bg-red-50')]//div[contains(@class,'text-destructive')]")
	private WebElement duplicateContentErrorMessage;

	public void goToContentHub() {
		contentHubNavigation.click();
		waitElementToAppear(contentHubScreenName);
	}

	public void clickOnCurriculaButton() {
		curriculaButton.click();
		waitElementToAppear(curriculumManagementScreenName);
	}

	public void clickOnAddCurriculumManagementButton() {
		addCurriculumButton.click();
		waitElementToAppear(curriculumDetails);
	}

	public void inputCurriculumName(String name) {
		curriculumName.sendKeys(name);
	}

	public void inputCurriculumDescription(String name) {
		waitElementToBeClickable(curriculumDescription);
		curriculumDescription.sendKeys(name);
	}

	public void selectOfferType(String offerTypes) {
		waitUtils.waitForVisibility(offerType);
		offerType.click();

		By option = By.xpath("//div[@role='option' and normalize-space()='" + offerTypes + "']");

		waitUtils.waitForClickable(option);
		driver.findElement(option).click();
	}

	public void selectCategory(String categories) {
		waitUtils.waitForVisibility(category);
		category.click();

		By option = By.xpath("//div[@role='option' and normalize-space()='" + categories + "']");

		waitUtils.waitForClickable(option);
		driver.findElement(option).click();
	}

	public void selectSubCategory(String subCategories) {
		waitUtils.waitForVisibility(subCategory);
		subCategory.click();

		By option = By.xpath("//div[@role='option' and normalize-space()='" + subCategories + "']");

		waitUtils.waitForClickable(option);
		driver.findElement(option).click();
	}

	public void uploadCoverImage(String filePath) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.display='block';", coverImageUpload);
		coverImageUpload.sendKeys(filePath);
	}

	public void clickCropAndSave() {
		waitElementToBeClickable(cropAndSaveBtn);
		cropAndSaveBtn.click();
	}

	public void clickonCreateCurriculumButton() {
		waitElementToBeClickable(createCurriculumButton);
		createCurriculumButton.click();
	}

	public void clickonCancelCurriculumButton() {
		waitElementToBeClickable(cancelCurriculumButton);
		cancelCurriculumButton.click();
	}

	public void clickonUpdateCurriculumButton() {
		waitElementToBeClickable(updateCurriculumButton);
		updateCurriculumButton.click();
	}

	public void clickActionMenu(String itemName) {
		driver.findElement(By.xpath("//tr[td[contains(.,'" + itemName + "')]]//button[@aria-haspopup='menu']")).click();
	}

	public void clickEdit() {
		waitElementToBeClickable(editOption);
		editOption.click();
	}

	public void clickDelete() {
		waitElementToBeClickable(deleteOption);
		deleteOption.click();
	}

	public void clickCurriculumDeletePermanently() {
		waitElementToBeClickable(curriculumDeletePermanentlyButton);
		curriculumDeletePermanentlyButton.click();
	}

	public void clickCancelDelete() {
		waitElementToBeClickable(cancelDeleteModel);
		cancelDeleteModel.click();
	}

	public void clickBackButton() {
		waitElementToBeClickable(backButton);
		backButton.click();
		waitElementToAppear(contentHubScreenName);
	}

	public void clickContentDeletePermanently() {
		waitElementToBeClickable(contentDeletePermanentlyButton);
		contentDeletePermanentlyButton.click();
	}

	public void clickOnUploadButton() {
		uploadButton.click();
		waitElementToAppear(contentUploadScreenName);
	}

	public void selectOfferTypeDropdown(String offerTypes) {

		waitUtils.waitForVisibility(offerTypeDropdown);
		offerTypeDropdown.click();

		By option = By.xpath("//div[@role='option' and normalize-space()='" + offerTypes + "']");

		waitUtils.waitForClickable(option);
		driver.findElement(option).click();

	}

	public void selectCategoryDropdown(String categories) {

		waitUtils.waitForVisibility(categoryDropdown);
		categoryDropdown.click();

		By option = By.xpath("//div[@role='option' and normalize-space()='" + categories + "']");

		waitUtils.waitForClickable(option);
		driver.findElement(option).click();

	}

	public void selectSubCategoryDropdown(String subCategories) {

		waitUtils.waitForVisibility(subCategoryDropdown);
		subCategoryDropdown.click();

		By option = By.xpath("//div[@role='option' and normalize-space()='" + subCategories + "']");

		waitUtils.waitForClickable(option);
		driver.findElement(option).click();
	}

	public void selectCurriculumDropdown(String curriculums) {

		waitUtils.waitForVisibility(curriculumDropdown);
		curriculumDropdown.click();

		By option = By.xpath("//div[@role='option' and normalize-space()='" + curriculums + "']");

		waitUtils.waitForClickable(option);
		driver.findElement(option).click();
		waitElementToAppear(lessonTitle);
	}

	public void inputLessonTitle(String name) {
		lessonTitle.sendKeys(name);
	}

	public void selectContentType(String contentType) {

		WebElement option = driver.findElement(By.xpath("//button[.//span[normalize-space()='" + contentType + "']]"));
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(option))
				.click();
	}

	public void selectUploadFileSource() {

		waitElementToBeClickable(uploadFileSourceBtn);
		uploadFileSourceBtn.click();
		waitElementToAppear(uploadFileInput);
	}

	public void uploadContentTypeFile(String filePath) {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("arguments[0].style.display='block';", uploadFileInput);

		uploadFileInput.sendKeys(filePath);
		waitForUploadComplete();
	}

	public void waitForUploadComplete() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(15));

		wait.until(ExpectedConditions.visibilityOf(openButton));

		/*
		 * wait.until(driver -> { try { return openButton.isDisplayed(); } catch
		 * (NoSuchElementException | StaleElementReferenceException e) { return false; }
		 * });
		 */
	}

	public void selectExternalLinkSource() {

		waitElementToBeClickable(externalLinkSourceBtn);
		externalLinkSourceBtn.click();
		waitElementToAppear(externalFileLink);
	}

	public void inputExternalFileLink(String link) {
		externalFileLink.sendKeys(link);
	}

	public void inputDuration(String hh, String mm, String ss) {
		durationInputs.get(0).clear();
		durationInputs.get(0).sendKeys(hh);

		durationInputs.get(1).clear();
		durationInputs.get(1).sendKeys(mm);

		durationInputs.get(2).clear();
		durationInputs.get(2).sendKeys(ss);
	}

	public void clickOnCreateContentButton() {
		waitElementToBeClickable(createContentButton);
		createContentButton.click();
		waitElementToAppear(uploadContentButton);
	}

	public void clickOnResetContentButton() {
		waitElementToBeClickable(resetButton);
		resetButton.click();
	}

	public void clickOnUploadContentButton() {
		uploadContentButton.click();
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

	@StepName("Verified the Curriculum is present in the Curriculum Management list")
	public boolean isCurriculumPresentInList(String columnName, String expectedValue) {
		search(expectedValue);

		if (!driver.findElements(By.xpath("//*[contains(text(),'No data found')]")).isEmpty()) {
			return false;
		}

		int columnIndex = getColumnIndex(columnName);

		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));

		return rows.stream().map(WebElement::getText).map(String::trim)
				.anyMatch(text -> text.equalsIgnoreCase(expectedValue));

	}

	@StepName("Retrieved the Curriculum from the Curriculum Management list")
	public String getCurriculumFromList(String columnName, String expectedValue) {
		search(expectedValue);

		if (!driver.findElements(By.xpath("//table//tbody//td[contains(text(),'No data found')]")).isEmpty()) {
			return null;
		}

		int columnIndex = getColumnIndex(columnName);

		List<WebElement> curriculums = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));

		for (WebElement curriculum : curriculums) {

			String actualCurriculums = curriculum.getText().trim();

			if (actualCurriculums.equalsIgnoreCase(expectedValue)) {
				return actualCurriculums.replaceAll("\\s+", " ");
			}
		}

		return null;
	}

	@StepName("Navigated to the first page of list")
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

	@StepName("Verified the Content is present in the Content Hub list")
	public boolean isContentPresentInList(String columnName, String expectedValue) {
		search(expectedValue);

		if (!driver.findElements(By.xpath("//*[contains(text(),'No content found')]")).isEmpty()) {
			return false;
		}

		int columnIndex = getColumnIndex(columnName);

		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));

		return rows.stream().map(WebElement::getText).map(String::trim)
				.anyMatch(text -> text.equalsIgnoreCase(expectedValue));

	}

	@StepName("Retrieved the Content from the Content Hub list")
	public String getContentFromList(String columnName, String expectedValue) {
		search(expectedValue);

		if (!driver.findElements(By.xpath("//table//tbody//td[contains(text(),'No content found')]")).isEmpty()) {
			return null;
		}

		int columnIndex = getColumnIndex(columnName);

		List<WebElement> curriculums = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));

		for (WebElement curriculum : curriculums) {

			String actualCurriculums = curriculum.getText().trim();

			if (actualCurriculums.equalsIgnoreCase(expectedValue)) {
				return actualCurriculums.replaceAll("\\s+", " ");
			}
		}

		return null;
	}

	public void clickManageVersions() {

		waitElementToBeClickable(contentManageVersions);

		contentManageVersions.click();
	}

	public void waitForVersionControl() {

		waitElementToAppear(versionControlModel);
	}

	public void clickAddNewVersion() {
		waitElementToBeClickable(addNewVersionButton);
		addNewVersionButton.click();
	}

	public void uploadVersionFile(String filePath) {

		waitUtils.waitForVisibility(versionFileUpload);

		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("arguments[0].classList.remove('hidden');" + "arguments[0].style.display='block';",
				versionFileUpload);

		versionFileUpload.sendKeys(filePath);
	}

	public void enterVersionNote(String note) {

		waitForFileUploadComplete();

		waitElementToBeClickable(versionNoteTextArea);

		versionNoteTextArea.clear();
		versionNoteTextArea.sendKeys(note);
	}

	public void selectReplaceActiveVersion() {

		String checked = confirmReplaceCheckbox.getAttribute("aria-checked");

		if (!"true".equalsIgnoreCase(checked)) {

			waitElementToBeClickable(confirmReplaceCheckbox);

			confirmReplaceCheckbox.click();
		}
	}

	public void clickUploadActivate() {

		waitElementToBeClickable(uploadActivateButton);

		uploadActivateButton.click();

		waitForFileUploadComplete();
	}

	public void cancelUploadVersion() {

		waitElementToBeClickable(cancelUploadVersionButton);

		cancelUploadVersionButton.click();
		
		waitElementToAppear(contentHubScreenName);
	}

	public WebElement getVersionActionButton(String version, String action) {

		return driver.findElement(By.xpath("//span[normalize-space()='" + version + "']"
				+ "/ancestor::div[contains(@class,'rounded-xl')]" + "//button[normalize-space()='" + action + "']"));
	}

	public String getVersionNote(String version) {

		return driver.findElement(By.xpath("//span[normalize-space()='" + version + "']"
				+ "/ancestor::div[contains(@class,'rounded-xl')]" + "//p[contains(@class,'text-muted-foreground')]"))
				.getText();
	}

	public WebElement getVersionCard(String version) {

		return driver.findElement(By.xpath(
				"//span[normalize-space()='" + version + "']" + "/ancestor::div[contains(@class,'rounded-xl')]"));
	}

	public void waitForVersionHistory() {

		waitElementToAppear(versionHistoryTitle);
	}

	public boolean isVersionPresent(String version) {

		return !driver.findElements(By.xpath("//h3[normalize-space()='Version History']" + "/ancestor::div"
				+ "//span[normalize-space()='" + version + "']")).isEmpty();
	}

	public boolean isVersionStatusDisplayed(String version, String status) {

		return !driver
				.findElements(By.xpath(
						"//span[normalize-space()='" + version + "']" + "/ancestor::div[contains(@class,'rounded-xl')]"
								+ "//div[contains(@class,'rounded-full') and normalize-space()='" + status + "']"))
				.isEmpty();
	}

	public void waitForVersionCreation(String version) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(15));

		wait.until(driver -> {

			List<WebElement> elements = driver.findElements(By.xpath("//span[normalize-space()='" + version + "']"));

			return !elements.isEmpty();

		});
	}

	public String getLatestVersion() {

		List<WebElement> versions = driver
				.findElements(By.xpath("//div[contains(@class,'rounded-xl')]//span[starts-with(text(),'v')]"));

		if (versions.isEmpty()) {
			return "v1.0";
		}

		return versions.get(versions.size()-1).getText().trim();
	}

	public String getNextVersion(String currentVersion) {

		if (currentVersion == null || currentVersion.isEmpty()) {
			return "v1.0";
		}

		int currentVersionNumber = Integer.parseInt(currentVersion.replace("v", "").split("\\.")[0]);

		return "v" + (currentVersionNumber + 1) + ".0";
	}

	public void waitForFileUploadComplete() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(15));

		wait.until(ExpectedConditions.invisibilityOf(uploadingButton));
	}

	public boolean isNoDataFoundDisplayed() {

		List<WebElement> noData = driver
				.findElements(By.xpath("//*[contains(text(),'No data found') or contains(text(),'No content found')]"));

		return !noData.isEmpty();
	}

	public boolean isVersionUploadDialogDisplayed() {

		try {

			waitElementToAppear(uploadNewVersionDialog);

			return uploadNewVersionDialog.isDisplayed();

		} catch (Exception e) {

			return false;
		}
	}
	
	public void clickOnUpdateContentButton(){
	    waitElementToBeClickable(updateContentButton);
	    updateContentButton.click();
	}
	
	public void expandContentDuplicateValidation() {

	    waitElementToBeClickable(duplicateContentExpandButton);

	    duplicateContentExpandButton.click();
	}
	
	public String getContentDuplicateValidationMessage() {

	    waitUtils.waitForVisibility(duplicateContentErrorMessage);

	    return duplicateContentErrorMessage.getText().trim();
	}
	
	public boolean isContentDuplicateValidationDisplayed() {

	    try {
	        waitUtils.waitForVisibility(duplicateContentErrorMessage);
	        return duplicateContentErrorMessage.isDisplayed();

	    } catch (Exception e) {
	        return false;
	    }
	}

	public ToastResponse captureToast() {
		return toastUtils.captureToast();
	}

	public void waitForToastToDisappear() {
		toastUtils.waitForToastToDisappear();
	}

}

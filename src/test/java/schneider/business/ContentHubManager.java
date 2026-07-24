package schneider.business;

import org.testng.Assert;

import schneider.pageobjects.ContentHub;
import schneider.pojo.ContentData;
import schneider.pojo.ContentHubScenario;
import schneider.pojo.CurriculumData;
import schneider.utils.ToastResponse;

public class ContentHubManager {

	private final ContentHub contentHub;

	public ContentHubManager(ContentHub contentHub) {
		this.contentHub = contentHub;
	}

	public void createContentHubData(ContentHubScenario scenario) {

		if (scenario == null) {
			throw new RuntimeException("Content Hub scenario is missing.");
		}
		if (scenario.getCurriculum() == null) {
			throw new RuntimeException("Curriculum scenario is missing.");
		}
		if (scenario.getContent() == null) {
			throw new RuntimeException("Content scenario is missing.");
		}

		createCurriculumIfNotExists(scenario);

		createContentIfNotExists(scenario);

	}

	public void createCurriculumIfNotExists(ContentHubScenario scenario) {

		CurriculumData curriculum = scenario.getCurriculum();

		contentHub.goToContentHub();
		contentHub.clickOnCurriculaButton();
		contentHub.goToFirstPage();

		if (contentHub.isCurriculumPresentInList("Name", curriculum.getName())) {
			System.out.println("Curriculum already exists : " + curriculum.getName());
			return;
		}

		contentHub.clickOnAddCurriculumManagementButton();

		contentHub.inputCurriculumName(curriculum.getName());
		contentHub.inputCurriculumDescription(curriculum.getDescription());

		contentHub.selectOfferType(curriculum.getOfferType());
		contentHub.selectCategory(curriculum.getCategory());
		contentHub.selectSubCategory(curriculum.getSubCategory());

		contentHub.uploadCoverImage(curriculum.getCoverImage());
		contentHub.clickCropAndSave();

		contentHub.clickonCreateCurriculumButton();

		ToastResponse toast = contentHub.captureToast();

		Assert.assertNotNull(toast, "Validation toast was not displayed");

		Assert.assertEquals(toast.getType().toString(), "success");

		contentHub.waitForToastToDisappear();

		Assert.assertTrue(contentHub.isCurriculumPresentInList("Name", curriculum.getName()),
				"Curriculum was not created.");
		contentHub.clickBackButton();
	}

	public void createContentIfNotExists(ContentHubScenario scenario) {

		ContentData content = scenario.getContent();

		contentHub.goToContentHub();
		contentHub.goToFirstPage();

		if (contentHub.isContentPresentInList("Title", content.getLessonTitle())) {
			System.out.println("Content already exists : " + content.getLessonTitle());
			return;
		}

		contentHub.clickOnUploadButton();

		contentHub.selectOfferTypeDropdown(content.getOfferType());
		contentHub.selectCategoryDropdown(content.getCategory());
		contentHub.selectSubCategoryDropdown(content.getSubCategory());
		contentHub.selectCurriculumDropdown(content.getCurriculum());

		contentHub.inputLessonTitle(content.getLessonTitle());

		contentHub.selectContentType(content.getContentType());

		if ("Upload File".equalsIgnoreCase(content.getSourceType())) {

			contentHub.selectUploadFileSource();
			contentHub.uploadContentTypeFile(content.getFilePath());

		} else {

			contentHub.selectExternalLinkSource();
			contentHub.inputExternalFileLink(content.getExternalLink());
		}

		contentHub.inputDuration(content.getDuration().getHours(), content.getDuration().getMinutes(),
				content.getDuration().getSeconds());

		contentHub.clickOnCreateContentButton();
		contentHub.clickOnUploadContentButton();

		ToastResponse toast = contentHub.captureToast();

		Assert.assertNotNull(toast, "Validation toast was not displayed");

		Assert.assertEquals(toast.getType().toString(), "success");

		contentHub.waitForToastToDisappear();

		Assert.assertTrue(contentHub.isContentPresentInList("Title", content.getLessonTitle()),
				"Content was not created.");
	}

	public void editCurriculum(ContentHubScenario scenario) {

		CurriculumData curriculum = scenario.getCurriculum();

		contentHub.goToContentHub();

		contentHub.clickOnCurriculaButton();

		if (!contentHub.isCurriculumPresentInList("Name", curriculum.getName())) {
			throw new RuntimeException("Curriculum not found for edit: " + curriculum.getName());
		}

		contentHub.clickActionMenu(curriculum.getName());

		contentHub.clickEdit();

		// Update fields
		contentHub.inputCurriculumDescription(curriculum.getDescription());

		contentHub.selectOfferType(curriculum.getOfferType());
		contentHub.selectCategory(curriculum.getCategory());
		contentHub.selectSubCategory(curriculum.getSubCategory());

		if (curriculum.getCoverImage() != null && !curriculum.getCoverImage().isEmpty()) {

			contentHub.uploadCoverImage(curriculum.getCoverImage());
			contentHub.clickCropAndSave();
		}

		contentHub.clickonUpdateCurriculumButton();

		ToastResponse toast = contentHub.captureToast();

		Assert.assertNotNull(toast, "Validation toast was not displayed");

		Assert.assertEquals(toast.getType().toString(), "success", "Curriculum update failed");

		contentHub.waitForToastToDisappear();

		contentHub.clickBackButton();
	}

	public void deleteCurriculum(ContentHubScenario scenario) {

		CurriculumData curriculum = scenario.getCurriculum();

		contentHub.goToContentHub();

		contentHub.clickOnCurriculaButton();

		if (!contentHub.isCurriculumPresentInList("Name", curriculum.getName())) {
			System.out.println("Curriculum not available for delete: " + curriculum.getName());
			return;
		}

		contentHub.clickActionMenu(curriculum.getName());

		contentHub.clickDelete();

		contentHub.clickCurriculumDeletePermanently();

		ToastResponse toast = contentHub.captureToast();

		if (hasCurriculumDependencies(toast)) {

			System.out.println("Curriculum deletion blocked due to dependencies: " + toast.getMessage());

			contentHub.waitForToastToDisappear();
			return;
		}

		Assert.assertNotNull(toast, "Validation toast was not displayed");

		Assert.assertEquals(toast.getType().toString(), "success", "Curriculum deletion failed: " + toast.getMessage());

		contentHub.waitForToastToDisappear();

		Assert.assertFalse(contentHub.isCurriculumPresentInList("Name", curriculum.getName()),
				"Curriculum still exists after deletion");
		contentHub.clickBackButton();
	}

	public void editContent(ContentHubScenario scenario) {

		ContentData content = scenario.getContent();

		contentHub.goToContentHub();

		if (!contentHub.isContentPresentInList("Title", content.getLessonTitle())) {

			throw new RuntimeException("Content not found for edit: " + content.getLessonTitle());
		}

		contentHub.clickActionMenu(content.getLessonTitle());

		contentHub.clickEdit();

		contentHub.inputLessonTitle(content.getLessonTitle());

		// contentHub.selectContentType(content.getContentType());

		contentHub.clickOnUpdateContentButton();

		ToastResponse toast = contentHub.captureToast();

		Assert.assertNotNull(toast, "Validation toast was not displayed");

		Assert.assertEquals(toast.getType().toString(), "success");

		contentHub.waitForToastToDisappear();
	}

	public void deleteContent(ContentHubScenario scenario) {

		ContentData content = scenario.getContent();

		contentHub.goToContentHub();

		if (!contentHub.isContentPresentInList("Title", content.getLessonTitle())) {

			System.out.println("Content not available for delete: " + content.getLessonTitle());
			return;
		}

		contentHub.clickActionMenu(content.getLessonTitle());

		contentHub.clickDelete();

		contentHub.clickContentDeletePermanently();

		ToastResponse toast = contentHub.captureToast();

		Assert.assertNotNull(toast, "Validation toast was not displayed");

		Assert.assertEquals(toast.getType().toString(), "success", "Content delete failed");

		contentHub.waitForToastToDisappear();

		Assert.assertFalse(contentHub.isContentPresentInList("Title", content.getLessonTitle()),
				"Content still exists after delete");
	}

	private boolean hasCurriculumDependencies(ToastResponse toast) {

		if (toast == null || toast.getMessage() == null) {
			return false;
		}

		String message = toast.getMessage().toLowerCase();

		return message.contains("active lesson") || message.contains("active certificate")
				|| message.contains("active assignment") || message.contains("associated with it")
				|| message.contains("remove or reassign");
	}

	public void uploadContentNewVersion(ContentHubScenario scenario, String expectedVersion) {

		if (scenario == null || scenario.getContent() == null) {
			throw new RuntimeException("Content scenario is missing.");
		}

		ContentData content = scenario.getContent();

		contentHub.goToContentHub();

		if (!contentHub.isContentPresentInList("Title", content.getLessonTitle())) {

			throw new RuntimeException("Content not found for version upload: " + content.getLessonTitle());
		}

		contentHub.clickActionMenu(content.getLessonTitle());

		contentHub.clickManageVersions();

		contentHub.waitForVersionControl();

		String currentVersion = contentHub.getLatestVersion();

		String expectedNewVersion = contentHub.getNextVersion(currentVersion);

		contentHub.clickAddNewVersion();

		contentHub.uploadVersionFile(content.getFilePath());

		contentHub.waitForFileUploadComplete();

		contentHub.enterVersionNote(content.getVersionNote());

		contentHub.selectReplaceActiveVersion();

		contentHub.clickUploadActivate();

		contentHub.waitForFileUploadComplete();

		contentHub.waitForVersionCreation(expectedNewVersion);

		Assert.assertTrue(contentHub.isVersionPresent(expectedNewVersion),
				"Version not created: " + expectedNewVersion);

		Assert.assertTrue(contentHub.isVersionStatusDisplayed(expectedNewVersion, "Active"),
				"New version is not Active: " + expectedNewVersion);
	}

	public void validateCancelCurriculumDelete(ContentHubScenario scenario) {

		CurriculumData curriculum = scenario.getCurriculum();

		contentHub.goToContentHub();

		contentHub.clickOnCurriculaButton();

		contentHub.clickActionMenu(curriculum.getName());

		contentHub.clickDelete();

		contentHub.clickCancelDelete();

		Assert.assertTrue(contentHub.isCurriculumPresentInList("Name", curriculum.getName()));

		contentHub.clickBackButton();

	}

	public void validateCancelContentDelete(ContentHubScenario scenario) {

		ContentData content = scenario.getContent();

		contentHub.goToContentHub();

		contentHub.clickActionMenu(content.getLessonTitle());

		contentHub.clickDelete();

		contentHub.clickCancelDelete();

		Assert.assertTrue(contentHub.isContentPresentInList("Title", content.getLessonTitle()));

	}

	public void cancelVersionUpload(ContentHubScenario scenario) {

		ContentData content = scenario.getContent();

		contentHub.goToContentHub();

		contentHub.clickActionMenu(content.getLessonTitle());

		contentHub.clickManageVersions();

		contentHub.waitForVersionControl();

		contentHub.clickAddNewVersion();

		contentHub.cancelUploadVersion();

		Assert.assertFalse(contentHub.isVersionUploadDialogDisplayed());
	}

	public void verifyInvalidCurriculumSearch() {

		contentHub.goToContentHub();

		contentHub.clickOnCurriculaButton();

		contentHub.search("Invalid123");

		Assert.assertTrue(contentHub.isNoDataFoundDisplayed());

	}

	public void verifyInvalidContentSearch() {

		contentHub.goToContentHub();

		contentHub.search("Invalid123");

		Assert.assertTrue(contentHub.isNoDataFoundDisplayed());
	}

	public void validateDuplicateCurriculum(ContentHubScenario scenario) {

		CurriculumData curriculum = scenario.getCurriculum();

		contentHub.goToContentHub();
		contentHub.clickOnCurriculaButton();

		contentHub.clickOnAddCurriculumManagementButton();

		contentHub.inputCurriculumName(curriculum.getName());
		contentHub.inputCurriculumDescription(curriculum.getDescription());

		contentHub.selectOfferType(curriculum.getOfferType());
		contentHub.selectCategory(curriculum.getCategory());
		contentHub.selectSubCategory(curriculum.getSubCategory());

		contentHub.uploadCoverImage(curriculum.getCoverImage());
		contentHub.clickCropAndSave();

		contentHub.clickonCreateCurriculumButton();

		ToastResponse toast = contentHub.captureToast();

		Assert.assertNotNull(toast, "Duplicate curriculum toast not displayed");

		String actualMessage = toast.getMessage();

		Assert.assertTrue(
				actualMessage.contains("A curriculum with name") && actualMessage.contains(curriculum.getName())
						&& actualMessage.contains("already exists for this subcategory"),
				"Unexpected duplicate curriculum validation message: " + actualMessage);

		contentHub.waitForToastToDisappear();

		contentHub.clickonCancelCurriculumButton();

		contentHub.clickBackButton();
	}

	public void validateDuplicateContent(ContentHubScenario scenario) {

		ContentData content = scenario.getContent();

		contentHub.goToContentHub();

		contentHub.clickOnUploadButton();

		contentHub.selectOfferTypeDropdown(content.getOfferType());
		contentHub.selectCategoryDropdown(content.getCategory());
		contentHub.selectSubCategoryDropdown(content.getSubCategory());
		contentHub.selectCurriculumDropdown(content.getCurriculum());

		contentHub.inputLessonTitle(content.getLessonTitle());

		contentHub.selectContentType(content.getContentType());

		if ("Upload File".equalsIgnoreCase(content.getSourceType())) {

			contentHub.selectUploadFileSource();
			contentHub.uploadContentTypeFile(content.getFilePath());

		} else {

			contentHub.selectExternalLinkSource();
			contentHub.inputExternalFileLink(content.getExternalLink());

		}
		contentHub.inputDuration(content.getDuration().getHours(), content.getDuration().getMinutes(),
				content.getDuration().getSeconds());

		// First create content
		contentHub.clickOnCreateContentButton();

		// Then upload content
		contentHub.clickOnUploadContentButton();

		contentHub.expandContentDuplicateValidation();

		Assert.assertTrue(contentHub.isContentDuplicateValidationDisplayed(),
				"Duplicate content validation is not displayed");

		String actualMessage = contentHub.getContentDuplicateValidationMessage();

		Assert.assertEquals(actualMessage,
				"A lesson with title '" + content.getLessonTitle() + "' already exists in this curriculum.",
				"Duplicate content validation message mismatch");
		contentHub.clickBackButton();
	}

	public void verifyCurriculumMandatoryValidation(ContentHubScenario scenario) {

		CurriculumData curriculum = scenario.getCurriculum();

		contentHub.goToContentHub();

		contentHub.clickOnCurriculaButton();

		/*
		 * Validation 1: Please select a sub category
		 */

		contentHub.clickOnAddCurriculumManagementButton();

		contentHub.inputCurriculumName(curriculum.getName());

		contentHub.clickonCreateCurriculumButton();

		ToastResponse toast = contentHub.captureToast();

		Assert.assertNotNull(toast, "Validation toast not displayed");

		Assert.assertEquals(toast.getMessage(), "Please select a sub category",
				"Sub Category mandatory validation failed");

		contentHub.waitForToastToDisappear();

		/*
		 * Validation 2: Category Click Create Curriculum Expected: Please upload an
		 * image before proceeding.
		 */

		contentHub.selectOfferType(curriculum.getOfferType());

		contentHub.selectCategory(curriculum.getCategory());

		contentHub.selectSubCategory(curriculum.getSubCategory());

		contentHub.clickonCreateCurriculumButton();

		toast = contentHub.captureToast();

		Assert.assertNotNull(toast, "Validation toast not displayed");

		Assert.assertEquals(toast.getMessage(), "Please upload an image before proceeding.",
				"Cover image mandatory validation failed");

		contentHub.waitForToastToDisappear();

		contentHub.clickonCancelCurriculumButton();

		contentHub.clickBackButton();

	}

	public void verifyContentMandatoryValidation(ContentHubScenario scenario) {

		ContentData content = scenario.getContent();

		contentHub.goToContentHub();

		/*
		 * Validation 1: Title is required
		 */

		contentHub.clickOnUploadButton();

		contentHub.selectOfferTypeDropdown(content.getOfferType());

		contentHub.selectCategoryDropdown(content.getCategory());

		contentHub.selectSubCategoryDropdown(content.getSubCategory());

		contentHub.selectCurriculumDropdown(content.getCurriculum());

		contentHub.clickOnCreateContentButton();

		ToastResponse toast = contentHub.captureToast();

		Assert.assertNotNull(toast, "Validation toast not displayed");

		Assert.assertEquals(toast.getMessage(), "Title is required", "Title mandatory validation failed");

		contentHub.waitForToastToDisappear();

		/*
		 * Validation 2: File is required
		 */

		contentHub.inputLessonTitle(content.getLessonTitle());

		contentHub.clickOnCreateContentButton();

		toast = contentHub.captureToast();

		Assert.assertNotNull(toast, "Validation toast not displayed");

		Assert.assertEquals(toast.getMessage(), "File is required", "File mandatory validation failed");

		contentHub.waitForToastToDisappear();

		/*
		 * Validation 3: Select Duration...
		 */

		if ("Upload File".equalsIgnoreCase(content.getSourceType())) {

			contentHub.selectUploadFileSource();
			contentHub.uploadContentTypeFile(content.getFilePath());

		} else {

			contentHub.selectExternalLinkSource();
			contentHub.inputExternalFileLink(content.getExternalLink());

		}

		contentHub.clickOnCreateContentButton();

		toast = contentHub.captureToast();

		Assert.assertNotNull(toast, "Validation toast not displayed");

		Assert.assertEquals(toast.getMessage(), "Select Duration...", "Duration mandatory validation failed");

		contentHub.waitForToastToDisappear();

		contentHub.clickBackButton();

	}

}

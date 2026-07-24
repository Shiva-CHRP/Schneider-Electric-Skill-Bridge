package schneider.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import schneider.pageobjects.Users;
import schneider.pojo.MasterData;
import schneider.pojo.UserScenario;
import schneider.utils.ToastResponse;

public class UsersManagement {

	private Users users;

	public UsersManagement(Users users) {
		this.users = users;
	}

	public void createUser(UserScenario scenario, MasterData masterData) throws InterruptedException {

		users.goToUsers();
		users.clickOnAddUser();
		String userType = scenario.getUserType();
		// System.out.println("User Type = " + userType);
		users.selectUserType(userType);
		if (userType == null || userType.isEmpty()) {
			throw new RuntimeException("User Type missing in JSON");
		}
		Map<String, String> personalInfo = scenario.getUserDetails().getPersonalInformation();
		Map<String, String> location = scenario.getUserDetails().getLocation();
		if (personalInfo == null || personalInfo.isEmpty()) {
			throw new RuntimeException("Personal Information data missing for User Type: " + userType);
		}
		if (("SE Employee".equalsIgnoreCase(userType) || "Other".equalsIgnoreCase(userType))
				&& (location == null || location.isEmpty())) {
			throw new RuntimeException("Location data missing for User Type: " + userType);
		}
		switch (userType) {

		case "SE Employee":

			createSEEmployee(personalInfo, location, masterData);

			break;

		case "Partner":

			createPartner(personalInfo, masterData);

			break;

		case "Other":

			createOther(personalInfo, location, masterData);

			break;

		default:

			throw new RuntimeException("Invalid User Type : " + userType);

		}
		users.createUser();
		ToastResponse toast = users.captureToast();
		Assert.assertNotNull(toast, "Toast was not displayed");
		if ("error".equalsIgnoreCase(String.valueOf(toast.getType())) && users.isUserCreationErrorDisplayed(toast)) {

			String message = toast.getMessage().toLowerCase();

			Assert.assertTrue(
					message.contains("already registered") || message.contains("employee code")
							|| message.contains("already exists") || message.contains("unique code"),
					"Unexpected error toast: " + toast.getMessage());

			users.waitForToastToDisappear();
			users.clickOnBackToList();
			return;
		}
		validateCreatedUser(userType, personalInfo);
		users.clickDoneAfterUserCreation();
	}

	private void createSEEmployee(Map<String, String> data, Map<String, String> location, MasterData masterData)
			throws InterruptedException {

		users.inputSESAId(data.get("SESAID"));

		users.inputFirstName(data.get("FirstName"));

		users.inputLastName(data.get("LastName"));

		users.inputEmail(data.get("Email"));

		String userRole = data.get("UserRole");

		users.selectUserRole("SE Employee", userRole);

		// Organization
		List<String> expectedDepartments = getDepartmentsByUserRole(userRole, masterData);

		users.verifyDepartmentForSEUserType(expectedDepartments);

		users.selectOfferTypes(masterData.getOfferType());

		users.selectCategories(masterData.getCategory());

		users.selectSubCategories(masterData.getSubCategory());

		// Location
		if (isMultiCountryRole(userRole)) {

			// users.selectCountries(masterData.getCountry());
			users.selectCountries(location.get("Country"));

		} else {

			// users.selectCountry(masterData.getCountry());
			users.selectCountry(location.get("Country"));

		}

	}

	private void createPartner(Map<String, String> data, MasterData masterData) {

		// Personal Information

		users.selectPartner(masterData.getPartner());

		// users.verifyPartenerbFOId(data.get("BFOID"));

		users.verifyPartenerUserRole(data.get("UserRole"));

		users.inputFirstName(data.get("FirstName"));

		users.inputLastName(data.get("LastName"));

		users.inputEmail(data.get("Email"));

		// Organization

		users.selectOfferTypes(masterData.getOfferType());

		users.selectCategories(masterData.getCategory());

		users.selectSubCategories(masterData.getSubCategory());

	}

	private void createOther(Map<String, String> data, Map<String, String> location, MasterData masterData) {

		users.inputOtherEmployeeCode(data.get("EmployeeCode"));

		users.inputFirstName(data.get("FirstName"));

		users.inputLastName(data.get("LastName"));

		users.inputEmail(data.get("Email"));

		String userRole = data.get("UserRole");

		if (userRole.contains("Trainer")) {

			users.selectDepartmentOther(masterData.getTrainerRoleDepartment());

		} else {

			users.selectDepartmentOther(masterData.getUserRoleDepartment());

		}
		users.selectUserRoleOther(userRole);

		users.selectOfferTypes(masterData.getOfferType());

		// users.selectCategories(masterData.getCategory());
		users.selectCategories("Select All");

		// users.selectSubCategories(masterData.getSubCategory());
		users.selectSubCategories("Select All");

		if (isMultiCountryRole(userRole)) {
			// users.selectCountries(masterData.getCountry());
			users.selectCountries(location.get("Country"));
		} else {
			// users.selectCountry(masterData.getCountry());
			users.selectCountry(location.get("Country"));
		}

	}

	private boolean isMultiCountryRole(String userRole) {

		if (userRole == null) {
			return false;
		}
		return !userRole.contains("User");

	}

	private List<String> getDepartmentsByUserRole(String userRole, MasterData masterData) {

		List<String> departments = new ArrayList<>();

		boolean hasAdmin = userRole.contains("Admin");
		boolean hasTrainer = userRole.contains("Trainer");
		boolean hasUser = userRole.contains("User");

		if (hasAdmin || hasTrainer) {
			departments.add(masterData.getAdminRoleDepartment());
		}

		if (hasUser) {
			departments.add(masterData.getUserRoleDepartment());
		}

		return departments;
	}

	private void validateCreatedUser(String userType, Map<String, String> data) {

		String actualId = users.getCreatedUserId();
		String actualEmail = users.getCreatedUserEmail();
		String actualPassword = users.getCreatedUserPassword();

		switch (userType) {

		case "SE Employee":
			String expectedSESId = "SESA" + data.get("SESAID");
			Assert.assertEquals(actualId, expectedSESId, "User ID mismatch");
			break;

		case "Partner":

			break;

		case "Other":

			break;

		default:
			throw new RuntimeException("Unknown User Type: " + userType);
		}

		Assert.assertEquals(actualEmail, data.get("Email"), "Email mismatch");
		Assert.assertFalse(actualPassword == null || actualPassword.isBlank(), "Password was not generated");

		System.out.println("Created User ID      : " + actualId);
		System.out.println("Created User Email   : " + actualEmail);
		System.out.println("Generated Password   : " + actualPassword);
	}

	public void editUser(UserScenario scenario, MasterData masterData) throws InterruptedException {

		String userType = scenario.getUserType();

		Map<String, String> personalInfo = scenario.getUserDetails().getPersonalInformation();
		Map<String, String> location = scenario.getUserDetails().getLocation();

		String email = personalInfo.get("Email");

		users.goToUsers();

		if (!users.isUserPresentInList("Email", email)) {
			throw new RuntimeException("User not found for edit: " + email);
		}

		users.clickActionMenu(email);

		users.clickEdit();

		switch (userType) {

		case "SE Employee":
			createSEEmployee(personalInfo, location, masterData);
			break;

		case "Partner":
			createPartner(personalInfo, masterData);
			break;

		case "Other":
			createOther(personalInfo, location, masterData);
			break;

		default:
			throw new RuntimeException("Invalid User Type : " + userType);
		}

		users.clickUpdateUser();

		ToastResponse toast = users.captureToast();
		
		Assert.assertNotNull(toast, "Toast was not displayed");

		Assert.assertEquals(toast.getType().toString(), "success", "User update failed");

		users.waitForToastToDisappear();

		Assert.assertTrue(users.isUserPresentInList("Email", email), "Updated user not found.");

		users.clickActionMenu(email);

		users.clickViewButton();

		verifyUserDetailsOnViewScreen(scenario);
		
		users.clickOnViewBackToListButton();
	}

	private void verifyUserDetailsOnViewScreen(UserScenario scenario) {

		String userType = scenario.getUserType();

		Map<String, String> data = scenario.getUserDetails().getPersonalInformation();

		switch (userType) {

		case "SE Employee":
			verifySEEmployeeDetails(data);
			break;

		case "Partner":
			verifyPartnerDetails(data);
			break;

		case "Other":
			verifyOtherDetails(data);
			break;

		}

	}

	public void deleteUser(UserScenario scenario) {

		String userType = scenario.getUserType();

		String email = scenario.getUserDetails().getPersonalInformation().get("Email");

		users.goToUsers();

		Assert.assertTrue(users.isUserPresentInList("Email", email), userType + " user is not available before delete");

		users.clickActionMenu(email);

		users.clickDelete();

		users.clickUserDeletePermanently();

		ToastResponse toast = users.captureToast();
		
		Assert.assertNotNull(toast, "Toast was not displayed");

		Assert.assertEquals(toast.getType().toString(), "success", "User deletion failed: " + toast.getMessage());

		users.waitForToastToDisappear();

		Assert.assertFalse(users.isUserPresentInList("Email", email), "User still exists after deletion");
	}

	public void validateCancelDelete(UserScenario scenario) {

		String email = scenario.getUserDetails().getPersonalInformation().get("Email");

		users.goToUsers();

		if (!users.isUserPresentInList("Email", email)) {

			throw new RuntimeException("User not found : " + email);
		}

		users.clickActionMenu(email);

		users.clickDelete();

		users.clickCancelDelete();

		Assert.assertTrue(users.isUserPresentInList("Email", email), "User removed after cancelling delete");

	}

	public void changePassword(UserScenario scenario) {

		String email = scenario.getUserDetails().getPersonalInformation().get("Email");

		String password = scenario.getUserDetails().getPersonalInformation().get("Password");

		users.goToUsers();

		if (!users.isUserPresentInList("Email", email)) {
			throw new RuntimeException("User not found for password change: " + email);
		}

		users.clickActionMenu(email);

		users.clickChangePassword();

		users.inputSetNewPassword(password);

		users.inputConfirmNewPassword(password);

		users.clickOnUpdatePasswordButton();

		ToastResponse toast = users.captureToast();
		
		Assert.assertNotNull(toast, "Toast was not displayed");

		Assert.assertEquals(toast.getType().toString(), "success", "Password update failed: " + toast.getMessage());

		Assert.assertFalse(users.isChangePasswordDialogDisplayed(),
				"Password dialog still open after successful update");

		users.waitForToastToDisappear();
	}

	public void verifyMandatoryValidation(UserScenario scenario) throws InterruptedException {

		users.goToUsers();
		users.clickOnAddUser();

		String userType = scenario.getUserType();

		users.selectUserType(userType);

		users.createUser();

		switch (userType) {

		case "SE Employee":
			verifySEEmployeeMandatoryValidation();
			break;

		case "Partner":
			verifyPartnerMandatoryValidation();
			break;

		case "Other":
			verifyOtherMandatoryValidation();
			break;

		default:
			throw new RuntimeException("Invalid User Type : " + userType);
		}
		users.clickOnBackToList();
	}

	private void verifySEEmployeeMandatoryValidation() {

		Assert.assertEquals(users.getMandatoryValidationMessage("SESA ID"), "SESA ID is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("First Name"), "First name is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("Last Name"), "Last name is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("Email"), "Business email is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("User Role"), "Role is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("Department"), "Department is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("Offer Type"), "Offer type is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("Category"), "Category is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("Sub Category"), "Subcategory is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("Country"), "Country is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("Cluster"), "Cluster is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("Zone"), "Zone is required");
	}

	private void verifyPartnerMandatoryValidation() {

		Assert.assertEquals(users.getMandatoryValidationMessage("Partner"), "Partner Name is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("First Name"), "First name is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("Last Name"), "Last name is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("Email"), "Business email is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("Offer Type"), "Offer type is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("Category"), "Category is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("Sub Category"), "Subcategory is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("Country"), "Country is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("Cluster"), "Cluster is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("Zone"), "Zone is required");
	}

	private void verifyOtherMandatoryValidation() {

		Assert.assertEquals(users.getMandatoryValidationMessage("Employee Code"), "Employee code is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("First Name"), "First name is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("Last Name"), "Last name is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("Email"), "Business email is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("User Role"), "Role is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("Department"), "Department is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("Offer Type"), "Offer type is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("Category"), "Category is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("Sub Category"), "Subcategory is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("Country"), "Country is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("Cluster"), "Cluster is required");
		Assert.assertEquals(users.getMandatoryValidationMessage("Zone"), "Zone is required");
	}

	public void verifyUserDetails(UserScenario scenario) {

		String userType = scenario.getUserType();

		Map<String, String> personalInfo = scenario.getUserDetails().getPersonalInformation();

		String email = personalInfo.get("Email");

		users.goToUsers();

		if (!users.isUserPresentInList("Email", email)) {
			throw new RuntimeException("User not found for view validation: " + email);
		}

		users.clickActionMenu(email);

		users.clickViewButton();

		switch (userType) {

		case "SE Employee":

			verifySEEmployeeDetails(personalInfo);
			break;

		case "Partner":

			verifyPartnerDetails(personalInfo);
			break;

		case "Other":

			verifyOtherDetails(personalInfo);
			break;

		default:

			throw new RuntimeException("Invalid User Type : " + userType);
		}

		users.clickOnViewBackToListButton();
	}

	private void verifySEEmployeeDetails(Map<String, String> data) {

		users.verifyFieldValue("SESA ID", "SE" + data.get("SESAID"));

		users.verifyFieldValue("First Name", data.get("FirstName"));

		users.verifyFieldValue("Last Name", data.get("LastName"));

		users.verifyFieldValue("Email", data.get("Email"));

		users.verifyFieldValue("User Role", data.get("UserRole"));
	}

	private void verifyPartnerDetails(Map<String, String> data) {

		users.verifyFieldValue("Partner", data.get("Partner"));

		users.verifyFieldValue("First Name", data.get("FirstName"));

		users.verifyFieldValue("Last Name", data.get("LastName"));

		users.verifyFieldValue("Email", data.get("Email"));

		users.verifyFieldValue("User Role", data.get("UserRole"));
	}

	private void verifyOtherDetails(Map<String, String> data) {

		users.verifyFieldValue("Employee Code", data.get("EmployeeCode"));

		users.verifyFieldValue("First Name", data.get("FirstName"));

		users.verifyFieldValue("Last Name", data.get("LastName"));

		users.verifyFieldValue("Email", data.get("Email"));

		users.verifyFieldValue("User Role", data.get("UserRole"));
	}

	public void validateChangePasswordNegativeScenarios(UserScenario scenario) {

		String email = scenario.getUserDetails().getPersonalInformation().get("Email");

		String password = scenario.getUserDetails().getPersonalInformation().get("Password");

		users.goToUsers();

		if (!users.isUserPresentInList("Email", email)) {
			throw new RuntimeException("User not found for password validation : " + email);
		}

		users.clickActionMenu(email);

		users.clickChangePassword();

		// Scenario 1: Password mismatch
		users.inputSetNewPassword(password);
		users.inputConfirmNewPassword(password + 1);

		users.clickOnUpdatePasswordButton();

		ToastResponse toast = users.captureToast();
		
		Assert.assertNotNull(toast, "Toast was not displayed");

		Assert.assertEquals(toast.getMessage(), "Passwords do not match.", "Mismatch password validation failed");

		users.waitForToastToDisappear();

		Assert.assertFalse(users.isChangePasswordDialogDisplayed(), "Password dialog should close after mismatch");
		// Scenario 2: Both fields empty

		users.clickChangePassword();

		users.clickOnUpdatePasswordButton();

		toast = users.captureToast();
		
		Assert.assertNotNull(toast, "Toast was not displayed");

		Assert.assertEquals(toast.getMessage(), "Password must be at least 8 characters long.",
				"Empty password validation failed");

		// Dialog should remain open
		Assert.assertTrue(users.isChangePasswordDialogDisplayed(), "Password dialog closed unexpectedly");
		users.clickOnCancelPasswordButton();
		Assert.assertFalse(users.isChangePasswordDialogDisplayed(), "Password dialog should close after mismatch");

	}

	public void verifyUserSearch(UserScenario scenario) {

		String email = scenario.getUserDetails().getPersonalInformation().get("Email");

		users.goToUsers();

		Assert.assertTrue(users.isUserPresentInList("Email", email), "User not found after search : " + email);
	}

	public void verifyInvalidUserSearch() {

		String invalidEmail = "invalid.user@test.com";

		users.goToUsers();

		users.searchUser(invalidEmail);

		Assert.assertTrue(users.isNoDataFoundDisplayed(), "No Data Found message not displayed");
	}

	public void verifyDeleteNonExistingUser() {

		String email = "invalid@test.com";

		users.goToUsers();

		Assert.assertFalse(users.isUserPresentInList("Email", email), "Invalid user exists");
	}

	public void verifyViewNonExistingUser() {

		String email = "invalid@test.com";

		users.goToUsers();

		Assert.assertFalse(users.isUserPresentInList("Email", email), "Invalid user found");
	}

}

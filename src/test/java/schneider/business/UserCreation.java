package schneider.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import schneider.pageobjects.Users;
import schneider.pojo.MasterData;
import schneider.pojo.UserDetails;
import schneider.pojo.UserScenario;
import schneider.pojo.UsersTestData;
import schneider.utils.ToastResponse;

public class UserCreation {

	private Users users;

	public UserCreation(Users users) {

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

}

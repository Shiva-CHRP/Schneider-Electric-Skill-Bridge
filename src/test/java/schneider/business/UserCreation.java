package schneider.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import schneider.pageobjects.Users;
import schneider.pojo.MasterData;
import schneider.pojo.UserDetails;
import schneider.pojo.UsersTestData;

public class UserCreation {

	private Users users;

	public UserCreation(Users users) {

		this.users = users;

	}

	public void createUser(String userType, UsersTestData data, MasterData masterData) throws InterruptedException {

		users.goToUsers();
		users.clickOnAddUser();
		// System.out.println("UserType = [" + userType + "]");
		users.selectUserType(userType);
		UserDetails userDetails = data.getUserType().get(userType);
		if (userDetails == null) {
			throw new RuntimeException("User Type data not found in JSON: " + userType);
		}
		Map<String, String> personalInfo = userDetails.getPersonalInformation();
		if (personalInfo == null || personalInfo.isEmpty()) {
			throw new RuntimeException("Personal Information data missing for User Type: " + userType);
		}
		switch (userType) {

		case "SE Employee":

			createSEEmployee(personalInfo, masterData);

			break;

		case "Partner":

			createPartner(personalInfo, masterData);

			break;

		case "Other":

			createOther(personalInfo, masterData);

			break;

		default:

			throw new RuntimeException("Invalid User Type : " + userType);

		}
		users.createUser(); 
		
		validateCreatedUser(userType, personalInfo);
		users.clickDoneAfterUserCreation();
	}

	private void createSEEmployee(Map<String, String> data, MasterData masterData) throws InterruptedException {

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

			users.selectCountries(masterData.getCountry());

		} else {

			users.selectCountry(masterData.getCountry());

		}
		// users.selectCountries(masterData.getCountry());

		// users.selectClusters(masterData.getCluster());

		// users.selectZones(masterData.getZone());

	}

	private void createPartner(Map<String, String> data, MasterData masterData) {

		// Personal Information

		users.selectPartner(masterData.getPartner());

		users.verifyPartenerbFOId(data.get("BFOID"));

		users.verifyPartenerUserRole(data.get("UserRole"));

		users.inputFirstName(data.get("FirstName"));

		users.inputLastName(data.get("LastName"));

		users.inputEmail(data.get("Email"));

		// Organization

		users.selectOfferTypes(masterData.getOfferType());

		users.selectCategories(masterData.getCategory());

		users.selectSubCategories(masterData.getSubCategory());

		// Location

		users.selectCountry(masterData.getCountry());

		// users.selectClusterPartner(masterData.getCluster());

		// users.selectZonePartner(masterData.getZone());
	}

	private void createOther(Map<String, String> data, MasterData masterData) {

		users.inputOtherEmployeeCode(data.get("EmployeeCode"));

		users.inputFirstName(data.get("FirstName"));

		users.inputLastName(data.get("LastName"));

		users.inputEmail(data.get("Email"));

		users.selectUserRoleOther(data.get("UserRole"));

		users.selectDepartmentOther(masterData.getUserRoleDepartment());

		users.selectOfferTypes(masterData.getOfferType());

		users.selectCategories(masterData.getCategory());

		users.selectSubCategories(masterData.getSubCategory());

		users.selectCountry(masterData.getCountry());

		// users.selectClusters(masterData.getCluster());

		// users.selectZones(masterData.getZone());

	}

	private boolean isMultiCountryRole(String userRole) {

		return userRole.equalsIgnoreCase("Admin") || userRole.equalsIgnoreCase("Trainer")
				|| userRole.equalsIgnoreCase("Admin+Trainer");
	}

	private List<String> getDepartmentsByUserRole(String userRole, MasterData masterData) {

		List<String> departments = new ArrayList<>();

		boolean hasAdmin = userRole.contains("Admin");
		boolean hasTrainer = userRole.contains("Trainer");
		boolean hasUser = userRole.contains("User");

		// Admin or Trainer always needs PSP3
		if (hasAdmin || hasTrainer) {
			departments.add(masterData.getAdminRoleDepartment());
		}

		// User role needs PSP1
		if (hasUser) {
			departments.add(masterData.getUserRoleDepartment());
		}

		return departments;
	}
	
	private void validateCreatedUser(String userType, Map<String, String> data) {

	    String actualId = users.getCreatedUserId();
	    String actualEmail = users.getCreatedUserEmail();
	    String actualPassword = users.getCreatedUserPassword();

	    String expectedId;

	    switch (userType) {

	    case "SE Employee":
	        expectedId = "SESA" + data.get("SESAID");
	        break;

	    case "Partner":
	        expectedId = data.get("BFOID");
	        break;

	    case "Other":
	        expectedId = data.get("EmployeeCode");
	        break;

	    default:
	        throw new RuntimeException("Unknown User Type: " + userType);
	    }

	    Assert.assertEquals(actualId, expectedId, "User ID mismatch");
	    Assert.assertEquals(actualEmail, data.get("Email"), "Email mismatch");
	    Assert.assertFalse(actualPassword.isBlank(), "Password was not generated");

	    System.out.println("Created User ID      : " + actualId);
	    System.out.println("Created User Email   : " + actualEmail);
	    System.out.println("Generated Password   : " + actualPassword);
	}

}

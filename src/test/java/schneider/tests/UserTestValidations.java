package schneider.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import schneider.annotations.TestInfo;
import schneider.business.ContentHubManager;
import schneider.business.UsersManagement;
import schneider.listenr.Listener;
import schneider.pojo.CategoryData;
import schneider.pojo.ClusterData;
import schneider.pojo.ContentHubScenario;
import schneider.pojo.ContentHubTestData;
import schneider.pojo.DepartmentData;
import schneider.pojo.MasterData;
import schneider.pojo.OfferData;
import schneider.pojo.PartnerData;
import schneider.pojo.UserScenario;
import schneider.pojo.UsersTestData;
import schneider.pojo.ZoneData;
import schneider.testcomponents.BaseTest;
import schneider.utils.TestDataUtil;
import schneider.utils.ToastResponse;

@Listeners(Listener.class)
public class UserTestValidations extends BaseTest {
	MasterData masterData;
	String SE_Employee;
	String Partner;
	String actualOfferType;
	String actualCategory;
	String actualSubCategory;
	String actualUserRoleDepartment;
	String actualTrainerRoleDepartment;
	String actualAdminRoleDepartment;
	String actualZone;
	String actualCluster;
	String actualCountry;
	String actualPartner;
	String groupName;

	@Test(priority = 1, groups = { "Smoke", "Regression" })
	@TestInfo(module = "Login", description = "Verify successful login using a valid email address and password.", priority = "Critical")
	public void validate_Login_To_The_Admin_Portal() throws InterruptedException {
		loginPage.enterUsername(TestDataUtil.getData("loginValidation.validLogin.username"));
		loginPage.enterPassword(TestDataUtil.getData("loginValidation.validLogin.password"));
		loginPage.clickLogin();
		loginPage.dashboardName();
	}

	@Test(priority = 2, groups = "Smoke")
	@TestInfo(module = "User", description = "Verify navigation to the User Management screen.", priority = "Medium")
	public void navigate_to_Users() {

		users.goToUsers();
		users.usersAddButtonEnable();

	}

	@Test(priority = 3, dataProvider = "offerHierarchy", groups = { "Smoke", "Regression" })
	@TestInfo(module = "Master", description = "Verify whether the Offer Type exists in the Offer Type Master; if not found, create a new Offer Type.", priority = "Medium")
	public void get_the_Offer(OfferData data) {
		offerType.goToOfferTypePage();
		offerType.goToFirstPage();
		String expectedOfferType = data.getOfferName();
		actualOfferType = offerType.getOfferTypeFromList("Offer Type", expectedOfferType);
		if (actualOfferType == null) {

			offerType.createOfferType(expectedOfferType);
			offerType.createOffer();
			offerType.goToOfferTypePage();
			offerType.goToFirstPage();
			actualOfferType = offerType.getOfferTypeFromList("Offer Type", expectedOfferType);
			Assert.assertNotNull(actualOfferType, "Offer Type creation failed: " + expectedOfferType);

		} else {

			// System.out.println("Offer Type already exists: " + actualOfferType);

			Assert.assertEquals(actualOfferType, expectedOfferType);
		}

	}

	@Test(priority = 4, dataProvider = "offerHierarchy", groups = { "Smoke", "Regression" })
	@TestInfo(module = "Master", description = "Verify whether the Category exists in the Category Master; if not found, create a new Category.", priority = "Medium")
	public void get_the_Category(OfferData data) {
		offerType.goToOfferTypePage();
		actualOfferType = data.getOfferName();
		ensureOfferTypeExists(actualOfferType);
		for (CategoryData categoryData : data.getCategories()) {
			category.goToCategory();
			String ecpectedCategoryName = categoryData.getCategoryName();
			actualCategory = category.getCategoryFromList("Category", ecpectedCategoryName);

			if (actualCategory == null) {
				category.createCategory(ecpectedCategoryName);
				category.selectOfferType(actualOfferType);
				category.saveCategory();
				category.goToCategory();
				category.goToFirstPage();
				actualCategory = category.getCategoryFromList("Category", ecpectedCategoryName);
				Assert.assertNotNull(actualCategory, "Category creation failed :" + ecpectedCategoryName);
			} else {

				// System.out.println("Category already exists: " + actualCategory);

				Assert.assertEquals(actualCategory, ecpectedCategoryName);
			}
		}
	}

	@Test(priority = 5, dataProvider = "offerHierarchy", groups = { "Smoke", "Regression" })
	@TestInfo(module = "Master", description = "Verify whether the Sub Category exists in the Sub Category Master; if not found, create a new Sub Category.", priority = "Medium")
	public void get_the_Sub_Category(OfferData data) {
		offerType.goToOfferTypePage();
		actualOfferType = data.getOfferName();
		ensureOfferTypeExists(actualOfferType);
		for (CategoryData categoryData : data.getCategories()) {
			category.goToCategory();
			actualCategory = categoryData.getCategoryName();
			ensureCategoryExists(actualCategory, actualOfferType);
			subCategory.goToSubCategory();
			for (String expectedSubCategoryName : categoryData.getSubCategories()) {
				actualSubCategory = subCategory.getSubCategoryFromList("Subcategory", expectedSubCategoryName);

				if (actualSubCategory == null) {
					subCategory.createSubCategory(expectedSubCategoryName);
					subCategory.selectCategory(actualCategory);
					subCategory.saveSubCategory();
					subCategory.goToSubCategory();
					subCategory.goToFirstPage();
					actualSubCategory = subCategory.getSubCategoryFromList("Subcategory", expectedSubCategoryName);
					Assert.assertNotNull(actualSubCategory, "Sub Category creation failed :" + expectedSubCategoryName);
				} else {

					// System.out.println("Sub Category already exists: " + actualSubCategory);

					Assert.assertEquals(actualSubCategory, expectedSubCategoryName);
				}

			}
		}
	}

	@Test(priority = 6, dataProvider = "departmentHierarchy", groups = { "Smoke", "Regression" })
	@TestInfo(module = "Master", description = "Verify whether the Department exists in the Department Master; if not found, create a new Department.", priority = "Medium")
	public void get_the_Department(DepartmentData data) {
		department.goToDepartment();
		actualUserRoleDepartment = createDepartmentIfNotExists(data.getUserRoleDepartment());

		actualTrainerRoleDepartment = createDepartmentIfNotExists(data.getTrainerRoleDepartment());

		actualAdminRoleDepartment = createDepartmentIfNotExists(data.getAdminRoleDepartment());

	}

	@Test(priority = 7, dataProvider = "zoneHierarchy", groups = { "Smoke", "Regression" })
	@TestInfo(module = "Master", description = "Verify whether the Zone exists in the Zone Master; if not found, create a new Zone.", priority = "Medium")
	public void get_the_Zone(ZoneData data) {
		zone.goToZone();
		zone.goToFirstPage();
		String expectedZone = data.getZoneName();
		actualZone = zone.getZoneFromList("Zone", expectedZone);

		if (actualZone == null) {
			zone.createZone(expectedZone);
			zone.createZone();
			zone.goToZone();
			zone.goToFirstPage();
			actualZone = zone.getZoneFromList("Zone", expectedZone);
			Assert.assertNotNull(actualZone, "Zone creation failed: " + expectedZone);
		} else {

			// System.out.println("Zone already exists: " + actualZone);

			Assert.assertEquals(actualZone, expectedZone);
		}

	}

	@Test(priority = 8, dataProvider = "zoneHierarchy", groups = { "Smoke", "Regression" })
	@TestInfo(module = "Master", description = "Verify whether the Cluster exists in the Cluster Master; if not found, create a new Cluster.", priority = "Medium")
	public void get_the_Cluster(ZoneData data) {
		zone.goToZone();
		actualZone = data.getZoneName();
		ensureZoneExists(actualZone);
		for (ClusterData clusterData : data.getClusters()) {
			cluster.goToClusterPage();
			String ecpectedClusterName = clusterData.getClusterName();
			actualCluster = cluster.getClusterFromList("Cluster", ecpectedClusterName);
			if (actualCluster == null) {
				cluster.createCluster(ecpectedClusterName);
				cluster.selectZoneID(actualZone);
				cluster.saveCluster();
				cluster.goToClusterPage();
				cluster.goToFirstPage();
				actualCluster = cluster.getClusterFromList("Cluster", ecpectedClusterName);
				Assert.assertNotNull(actualCluster, "Cluster creation failed :" + ecpectedClusterName);
			} else {

				// System.out.println("Category already exists: " + actualCategory);

				Assert.assertEquals(actualCluster, ecpectedClusterName);
			}

		}
	}

	@Test(priority = 9, dataProvider = "zoneHierarchy", groups = { "Smoke", "Regression" })
	@TestInfo(module = "Master", description = "Verify whether the Country exists in the Country Master; if not found, create a new Country.", priority = "Medium")
	public void get_the_Country(ZoneData data) {
		zone.goToZone();
		actualZone = data.getZoneName();
		ensureZoneExists(actualZone);
		for (ClusterData clusterData : data.getClusters()) {
			cluster.goToClusterPage();
			actualCluster = clusterData.getClusterName();
			ensureClusterExists(actualCluster, actualZone);
			country.goToCountryPage();
			for (String expectedCountryName : clusterData.getCountries()) {
				actualCountry = country.getClusterFromList("Country", expectedCountryName);
				if (actualCountry == null) {
					country.createCountry(expectedCountryName);
					country.selectZone(actualZone);
					country.selectCluster(actualCluster);
					country.saveCountry();
					country.goToCountryPage();
					country.goToFirstPage();
					actualCountry = country.getClusterFromList("Country", expectedCountryName);
					Assert.assertNotNull(actualCountry, "Country creation failed :" + expectedCountryName);
				} else {

					// System.out.println("Category already exists: " + actualCategory);

					Assert.assertEquals(actualCountry, expectedCountryName);
				}
			}
		}
	}

	@Test(priority = 10, dataProvider = "partnerHierarchy", groups = { "Smoke", "Regression" })
	@TestInfo(module = "Master", description = "Verify whether the Partner exists in the Partner Master; if not found, create a new Partner.", priority = "Medium")
	public void get_the_Partner(PartnerData data) {
		partner.goToPartner();
		String expectedPartner = data.getPartnerName();
		String bFOId = data.getbFOId();
		actualPartner = partner.getPartnerFromList("Partner Name", expectedPartner);
		if (actualPartner == null) {
			partner.createPartner(expectedPartner);
			partner.inputbFOId(bFOId);
			partner.selectZone(actualZone);
			partner.selectCluster(actualCluster);
			partner.selectCountry(actualCountry);
			partner.savePartner();
			partner.goToFirstPage();
			actualPartner = partner.getPartnerFromList("Partner Name", expectedPartner);
			Assert.assertNotNull(actualPartner, "Partner creation failed: " + expectedPartner);

		} else {

			// System.out.println("Partner already exists: " + actualPartner);

			Assert.assertEquals(actualPartner, expectedPartner);
		}
	}

	@Test(priority = 11, dependsOnMethods = { "get_the_Offer", "get_the_Category", "get_the_Sub_Category",
			"get_the_Department", "get_the_Zone", "get_the_Cluster", "get_the_Country", "get_the_Partner" })
	@TestInfo(module = "Master", description = "Verify the availability of the required master data and create it if it does not exist before creating users.", priority = "Critical")
	public void prepareUserMasterData() {

		masterData = new MasterData();

		masterData.setOfferType(actualOfferType);

		masterData.setCategory(actualCategory);

		masterData.setSubCategory(actualSubCategory);

		masterData.setUserRoleDepartment(actualUserRoleDepartment);

		masterData.setTrainerRoleDepartment(actualTrainerRoleDepartment);

		masterData.setAdminRoleDepartment(actualAdminRoleDepartment);

		masterData.setZone(actualZone);

		masterData.setCluster(actualCluster);

		masterData.setCountry(actualCountry);

		masterData.setPartner(actualPartner);

	}

	@Test(priority = 12, groups = { "Smoke", "Regression" })
	@TestInfo(module = "User", description = "Verify the successful creation of an SE Employee user with the Admin role when valid SESA ID, personal details, email address, organization, category, and location are provided", priority = "Critical")
	public void verifySEAdminUserCreation() throws InterruptedException {

		UsersManagement userCreation = new UsersManagement(users);

		UserScenario scenario = getUserScenario("userScenarios","SE_Admin");

		userCreation.createUser(scenario, masterData);

	}

	@Test(priority = 13, groups = { "Smoke", "Regression" })
	@TestInfo(module = "User", description = "Verify the successful creation of an SE Employee user with the Trainer role when valid SESA ID, personal details, email address, organization, category, and location are provided", priority = "Critical")
	public void verifySETrainerUserCreation() throws InterruptedException {

		UsersManagement userCreation = new UsersManagement(users);

		UserScenario scenario = getUserScenario("userScenarios","SE_Trainer");

		userCreation.createUser(scenario, masterData);

	}

	@Test(priority = 14, groups = { "Smoke", "Regression" })
	@TestInfo(module = "User", description = "Verify the successful creation of an SE Employee user with the User role when valid SESA ID, personal details, email address, organization, category, and location are provided", priority = "Critical")
	public void verifySEUserUserCreation() throws InterruptedException {

		UsersManagement userCreation = new UsersManagement(users);

		UserScenario scenario = getUserScenario("userScenarios","SE_User");

		userCreation.createUser(scenario, masterData);

	}

	@Test(priority = 15, groups = { "Smoke", "Regression" })
	@TestInfo(module = "User", description = "Verify the successful creation of an SE Employee user with the Admin & Trainer role when valid SESA ID, personal details, email address, organization, category, and location are provided", priority = "Critical")
	public void verifySEAdminTrainerUserCreation() throws InterruptedException {

		UsersManagement userCreation = new UsersManagement(users);

		UserScenario scenario = getUserScenario("userScenarios","SE_Admin_Trainer");

		userCreation.createUser(scenario, masterData);

	}

	@Test(priority = 16, groups = { "Smoke", "Regression" })
	@TestInfo(module = "User", description = "Verify the successful creation of an SE Employee user with the Admin & User role when valid SESA ID, personal details, email address, organization, category, and location are provided", priority = "Critical")
	public void verifySEAdminUserUserCreation() throws InterruptedException {

		UsersManagement userCreation = new UsersManagement(users);

		UserScenario scenario = getUserScenario("userScenarios","SE_Admin_User");

		userCreation.createUser(scenario, masterData);

	}

	@Test(priority = 17, groups = { "Smoke", "Regression" })
	@TestInfo(module = "User", description = "Verify the successful creation of an SE Employee user with the Trainer & User role when valid SESA ID, personal details, email address, organization, category, and location are provided", priority = "Critical")
	public void verifySETrainerUserUserCreation() throws InterruptedException {

		UsersManagement userCreation = new UsersManagement(users);

		UserScenario scenario = getUserScenario("userScenarios","SE_Trainer_User");

		userCreation.createUser(scenario, masterData);

	}

	@Test(priority = 18, groups = { "Smoke", "Regression" })
	@TestInfo(module = "User", description = "Verify the successful creation of an SE Employee user with the Admin, Trainer & User role when valid SESA ID, personal details, email address, organization, category, and location are provided", priority = "Critical")
	public void verifySEAdminTrainerUserUserCreation() throws InterruptedException {

		UsersManagement userCreation = new UsersManagement(users);

		UserScenario scenario = getUserScenario("userScenarios","SE_Admin_Trainer_User");

		userCreation.createUser(scenario, masterData);

	}

	@Test(priority = 19, groups = { "Smoke", "Regression" })
	@TestInfo(module = "User", description = "Verify the successful creation of an Partner user with the User role when valid Partner, bFO ID, personal details, email address, organization, category, and location are provided", priority = "Critical")
	public void verifyParterUserCreation() throws InterruptedException {

		UsersManagement userCreation = new UsersManagement(users);

		UserScenario scenario = getUserScenario("userScenarios","Partner");

		userCreation.createUser(scenario, masterData);

	}

	@Test(priority = 20, groups = { "Smoke", "Regression" })
	@TestInfo(module = "User", description = "Verify the successful creation of an Other user with the Trainer role when valid Employee code, personal details, email address, organization, category, and location are provided", priority = "Critical")
	public void verifyOtherTrainerUserCreation() throws InterruptedException {

		UsersManagement userCreation = new UsersManagement(users);

		UserScenario scenario = getUserScenario("userScenarios","Other_Trainer");

		userCreation.createUser(scenario, masterData);

	}

	@Test(priority = 21, groups = { "Smoke", "Regression" })
	@TestInfo(module = "User", description = "Verify the successful creation of an Other user with the User role when valid Employee code, personal details, email address, organization, category, and location are provided", priority = "Critical")
	public void verifyOtherUserCreation() throws InterruptedException {

		UsersManagement userCreation = new UsersManagement(users);

		UserScenario scenario = getUserScenario("userScenarios","Other_User");

		userCreation.createUser(scenario, masterData);

	}

	@Test(priority = 22, groups = { "Smoke", "Regression" })
	@TestInfo(module = "Master", description = "Verify the successful creation of a Groups in the Group Master", priority = "Critical")
	public void verifyGroupMasterCreation() throws InterruptedException {

		groupMaster.goToGroupMaster();
		groupMaster.clickOnAddGroup();
		groupName = actualOfferType + " Group";
		groupMaster.inputGroupName(groupName);
		groupMaster.selectOfferType(actualOfferType);
		groupMaster.selectCategories("Select All");
		groupMaster.selectSubCategories("Select All");
		groupMaster.selectZone("Select All");
		groupMaster.selectCluster("Select All");
		groupMaster.selectCountries("Select All");
		groupMaster.selectFirstRow();
		groupMaster.clickCancel();

	}

	/*
	@Test(priority = 23, groups = { "Smoke", "Regression" })
	@TestInfo(module = "Content Hub", description = "Verify the successful creation of a Content for Curriculum", priority = "Critical")
	public void verifyContentCreation() throws InterruptedException {

		ContentHubManager contentHubCreation = new ContentHubManager(contentHub);

		ContentHubScenario scenario = getContentScenario("");

		contentHubCreation.createContentHubData(scenario);

	}*/

	@Test(priority = 24, groups = { "Smoke", "Regression" })
	@TestInfo(module = "Login", description = "Validate successful user logout and redirection to the Login screen.", priority = "Critical")
	public void validateLogoutFromApplication() {
		loginPage.clickLogOut();
	}

	public void ensureOfferTypeExists(String offerTypeName) {

		offerType.goToOfferTypePage();

		boolean exists = offerType.isOfferPresentInList("Offer Type", offerTypeName);

		if (!exists) {

			offerType.createOfferType(offerTypeName);
			offerType.createOffer();

			ToastResponse toast = toastUtils.captureToast();

			Assert.assertEquals(toast.getMessage(), "Offer Type created successfully!");

			toastUtils.waitForToastToDisappear();
		}
	}

	public void ensureCategoryExists(String categoryName, String offerTypeName) {

		ensureOfferTypeExists(offerTypeName);

		category.goToCategory();

		boolean exists = category.isCategoryPresentInList("Category", categoryName);

		if (!exists) {

			category.createCategory(categoryName);
			category.selectOfferType(offerTypeName);
			category.saveCategory();

			ToastResponse toast = category.captureToast();

			Assert.assertEquals(toast.getMessage(), "Category created successfully!");

			toastUtils.waitForToastToDisappear();
		}
	}

	public void ensureZoneExists(String zones) {
		zone.goToZone();
		boolean exists = zone.isZonePresentInList("Zone", zones);
		if (!exists) {
			zone.createZone(zones);
			zone.createZone();
			ToastResponse toast = toastUtils.captureToast();

			Assert.assertEquals(toast.getMessage(), "Zone created successfully!");

			toastUtils.waitForToastToDisappear();
		}

	}

	public void ensureClusterExists(String clusterName, String zones) {
		ensureZoneExists(zones);
		cluster.goToClusterPage();
		boolean exists = cluster.isClusterPresentInList("Cluster", clusterName);
		if (!exists) {
			cluster.createCluster(clusterName);
			cluster.selectZoneID(zones);
			cluster.saveCluster();
			ToastResponse toast = cluster.captureToast();

			Assert.assertEquals(toast.getMessage(), "Cluster created successfully!");

			toastUtils.waitForToastToDisappear();
		}
	}

	@DataProvider(name = "offerHierarchy")
	public Object[][] offerHierarchy() {

		String path = System.getProperty("user.dir") + "/src/main/resources/testdata/offerHierarchy.json";

		List<OfferData> dataList = TestDataUtil.getOfferHierarchy(path);

		Object[][] data = new Object[dataList.size()][1];

		for (int i = 0; i < dataList.size(); i++) {
			data[i][0] = dataList.get(i);
		}

		return data;
	}

	@DataProvider(name = "zoneHierarchy")
	public Object[][] zoneHierarchy() {

		String path = System.getProperty("user.dir") + "/src/main/resources/testdata/zoneHierarchy.json";

		List<ZoneData> dataList = TestDataUtil.getZoneHierarchy(path);

		Object[][] data = new Object[dataList.size()][1];

		for (int i = 0; i < dataList.size(); i++) {
			data[i][0] = dataList.get(i);
		}

		return data;
	}

	@DataProvider(name = "departmentHierarchy")
	public Object[][] departmentHierarchy() {

		String path = System.getProperty("user.dir") + "/src/main/resources/testdata/departmentHierarchy.json";

		List<DepartmentData> dataList = TestDataUtil.getDepartmentHierarchy(path);

		Object[][] data = new Object[dataList.size()][1];

		for (int i = 0; i < dataList.size(); i++) {
			data[i][0] = dataList.get(i);
		}

		return data;
	}

	@DataProvider(name = "partnerHierarchy")
	public Object[][] partnerHierarchy() {

		String path = System.getProperty("user.dir") + "/src/main/resources/testdata/partnerHierarchy.json";

		List<PartnerData> dataList = TestDataUtil.getPartnerHierarchy(path);

		Object[][] data = new Object[dataList.size()][1];

		for (int i = 0; i < dataList.size(); i++) {
			data[i][0] = dataList.get(i);
		}

		return data;
	}

	@DataProvider(name = "userTestData")
	public Object[][] userTestData() {

		String path = System.getProperty("user.dir") + "/src/main/resources/testdata/UserTestData.json";

		UsersTestData data = TestDataUtil.getUserTestData(path);

		return data.getUserScenarios().entrySet().stream()
				.map(entry -> new Object[] { entry.getKey(), entry.getValue() }).toArray(Object[][]::new);
	}

	@DataProvider(name = "contentHubTestData")
	public Object[][] contentHubTestData() {

		String path = System.getProperty("user.dir") + "/src/main/resources/testdata/contentHubTestData.json";

		ContentHubTestData data = TestDataUtil.getContentHubTestData(path);

		return data.getContentHubScenarios().entrySet().stream()
				.map(entry -> new Object[] { entry.getKey(), entry.getValue() }).toArray(Object[][]::new);
	}

	private String createDepartmentIfNotExists(String departmentName) {

		department.goToFirstPage();

		String actualDepartment = department.getDepartmentFromList("Department Name", departmentName);

		if (actualDepartment == null) {

			department.createDepartment(departmentName);
			department.saveDepartment();

			department.goToDepartment();
			department.goToFirstPage();

			actualDepartment = department.getDepartmentFromList("Department Name", departmentName);

			Assert.assertNotNull(actualDepartment, "Department creation failed: " + departmentName);

		} else {

			// System.out.println("Department already exists: " + actualDepartment);
			Assert.assertEquals(actualDepartment, departmentName);
		}

		return actualDepartment;
	}

	private UserScenario getUserScenario(String sectionName, String scenarioName) {

		String path = System.getProperty("user.dir") + "/src/main/resources/testdata/UserTestData.json";

		UsersTestData data = TestDataUtil.getUserTestData(path);

		UserScenario scenario = null;

		switch (sectionName) {

		case "userScenarios":
			scenario = data.getUserScenarios().get(scenarioName);
			break;

		case "editUserScenarios":
			scenario = data.getEditUserScenarios().get(scenarioName);
			break;

		case "mandatoryValidationScenarios":
			scenario = data.getMandatoryValidationScenarios().get(scenarioName);
			break;

		case "changePasswordScenarios":
			scenario = data.getChangePasswordScenarios().get(scenarioName);
			break;

		default:
			throw new RuntimeException("Invalid JSON Section : " + sectionName);
		}

		if (scenario == null) {
			throw new RuntimeException("Scenario not found : " + scenarioName + " under section : " + sectionName);
		}

		return scenario;
	}

	private ContentHubScenario getContentScenario(String scenarioName) {

		String path = System.getProperty("user.dir") + "/src/main/resources/testdata/contentHubTestData.json";

		ContentHubTestData data = TestDataUtil.getContentHubTestData(path);

		ContentHubScenario scenario = data.getContentHubScenarios().get(scenarioName);

		if (scenario == null) {
			throw new RuntimeException("Scenario not found: " + scenarioName);
		}

		return scenario;
	}

}

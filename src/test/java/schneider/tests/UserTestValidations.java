package schneider.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import schneider.annotations.TestInfo;
import schneider.business.UserCreation;
import schneider.listenr.Listener;
import schneider.pojo.CategoryData;
import schneider.pojo.ClusterData;
import schneider.pojo.DepartmentData;
import schneider.pojo.MasterData;
import schneider.pojo.OfferData;
import schneider.pojo.PartnerData;
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

	@Test(priority = 1, groups = { "Smoke", "Regression" })
	@TestInfo(module = "Login", description = "Login using valid Email and Password", priority = "Critical")
	public void validate_Login_To_The_Admin_Portal() throws InterruptedException {
		loginPage.enterUsername(TestDataUtil.getData("loginValidation.validLogin.username"));
		loginPage.enterPassword(TestDataUtil.getData("loginValidation.validLogin.password"));
		loginPage.clickLogin();
		loginPage.dashboardName();
	}

	@Test(priority = 2, groups = "Smoke")
	@TestInfo(module = "User", description = "Navigation to the Users Management Screen", priority = "Medium")
	public void navigate_to_Users() {

		users.goToUsers();
		users.usersAddButtonEnable();

	}

	@Test(priority = 3, dataProvider = "offerHierarchy", groups = { "Smoke", "Regression" })
	@TestInfo(module = "Master", description = "Getting the Offer Type data from Offer Type Master", priority = "Medium")
	public void get_the_Offer(OfferData data) {
		offerType.goToOfferTypePage();
		offerType.goToFirstPage();
		String expectedOfferType = data.getOfferName();
		actualOfferType = offerType.getOfferTypeFromList("Offer Type", expectedOfferType);
		if (actualOfferType == null) {

			// System.out.println("Offer Type not found. Creating: " + expectedOfferType);

			offerType.createOfferType(expectedOfferType);
			offerType.createOffer();
			offerType.goToOfferTypePage();
			offerType.goToFirstPage();
			actualOfferType = offerType.getOfferTypeFromList("Offer Type", expectedOfferType);
			Assert.assertNotNull(actualOfferType, "Offer Type creation failed: " + expectedOfferType);
			//masterData.setOfferType(expectedOfferType);

		} else {

			System.out.println("Offer Type already exists: " + actualOfferType);

			Assert.assertEquals(actualOfferType, expectedOfferType);
		}

	}

	@Test(priority = 4, dataProvider = "offerHierarchy", groups = { "Smoke", "Regression" })
	@TestInfo(module = "Master", description = "Getting the Category data from Category Master", priority = "Medium")
	public void get_the_Category(OfferData data) {
		offerType.goToOfferTypePage();
		actualOfferType = data.getOfferName();
		ensureOfferTypeExists(actualOfferType);
		for (CategoryData categoryData : data.getCategories()) {
			category.goToCategory();
			String ecpectedCategoryName = categoryData.getCategoryName();
			actualCategory = category.getCategoryFromList("Category", ecpectedCategoryName);

			if (actualCategory == null) {
				// System.out.println("Category not found. Creating: " + ecpectedCategoryName);
				category.createCategory(ecpectedCategoryName);
				category.selectOfferType(actualOfferType);
				category.saveCategory();
				category.goToCategory();
				category.goToFirstPage();
				actualCategory = category.getCategoryFromList("Category", ecpectedCategoryName);
				Assert.assertNotNull(actualCategory, "Category creation failed :" + ecpectedCategoryName);
			} else {

				System.out.println("Category already exists: " + actualCategory);

				Assert.assertEquals(actualCategory, ecpectedCategoryName);
			}
			//masterData.setCategory(ecpectedCategoryName);

		}
	}

	@Test(priority = 5, dataProvider = "offerHierarchy", groups = { "Smoke", "Regression" })
	@TestInfo(module = "Master", description = "Getting the Sub Category data from Sub Category Master", priority = "Medium")
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
					// System.out.println("Sub category not found. Creating: " +
					// expectedSubCategoryName);
					subCategory.createSubCategory(expectedSubCategoryName);
					subCategory.selectCategory(actualCategory);
					subCategory.saveSubCategory();
					subCategory.goToSubCategory();
					subCategory.goToFirstPage();
					actualSubCategory = subCategory.getSubCategoryFromList("Subcategory", expectedSubCategoryName);
					Assert.assertNotNull(actualSubCategory, "Sub Category creation failed :" + expectedSubCategoryName);
				} else {

					System.out.println("Sub Category already exists: " + actualSubCategory);

					Assert.assertEquals(actualSubCategory, expectedSubCategoryName);
				}
				//masterData.setSubCategory(expectedSubCategoryName);

			}
		}
	}

	@Test(priority = 6, dataProvider = "departmentHierarchy", groups = { "Smoke", "Regression" })
	@TestInfo(module = "Master", description = "Getting the Department data from Department Master", priority = "Medium")
	public void get_the_Department(DepartmentData data) {
		department.goToDepartment();
		actualUserRoleDepartment =
	            createDepartmentIfNotExists(data.getUserRoleDepartment());

	    actualTrainerRoleDepartment =
	            createDepartmentIfNotExists(data.getTrainerRoleDepartment());

	    actualAdminRoleDepartment =
	            createDepartmentIfNotExists(data.getAdminRoleDepartment());

	}

	@Test(priority = 7, dataProvider = "zoneHierarchy", groups = { "Smoke", "Regression" })
	@TestInfo(module = "Master", description = "Getting the Zones data from Zone Master", priority = "Medium")
	public void get_the_Zone(ZoneData data) {
		zone.goToZone();
		zone.goToFirstPage();
		String expectedZone = data.getZoneName();
		actualZone = zone.getZoneFromList("Zone", expectedZone);

		if (actualZone == null) {
			zone.createZone(actualZone);
			zone.createZone();
			zone.goToZone();
			zone.goToFirstPage();
			actualZone = zone.getZoneFromList("Zone", expectedZone);
			Assert.assertNotNull(actualZone, "Zone creation failed: " + expectedZone);
		} else {

			System.out.println("Zone already exists: " + actualZone);

			Assert.assertEquals(actualZone, expectedZone);
		}
		//masterData.setZone(expectedZone);

	}

	@Test(priority = 8, dataProvider = "zoneHierarchy", groups = { "Smoke", "Regression" })
	@TestInfo(module = "Master", description = "Getting the Cluster data from Cluster Master", priority = "Medium")
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

				System.out.println("Category already exists: " + actualCategory);

				Assert.assertEquals(actualCluster, ecpectedClusterName);
			}
			//masterData.setCluster(ecpectedClusterName);
		}
	}

	@Test(priority = 9, dataProvider = "zoneHierarchy", groups = { "Smoke", "Regression" })
	@TestInfo(module = "Master", description = "Getting the Country data from Country Master", priority = "Medium")
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

					System.out.println("Category already exists: " + actualCategory);

					Assert.assertEquals(actualCountry, expectedCountryName);
				}
				//masterData.setCountry(expectedCountryName);
			}
		}
	}

	@Test(priority = 10, dataProvider = "partnerHierarchy", groups = { "Smoke", "Regression" })
	@TestInfo(module = "Master", description = "Getting the Partner data from Partner Master", priority = "Medium")
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

			System.out.println("Partner already exists: " + actualPartner);

			Assert.assertEquals(actualPartner, expectedPartner);
		}
		//masterData.setPartner(expectedPartner);
	}

	@Test(priority = 11, dependsOnMethods = { "get_the_Offer", "get_the_Category", "get_the_Sub_Category",
			"get_the_Department", "get_the_Zone", "get_the_Cluster", "get_the_Country", "get_the_Partner" })
	@TestInfo(module = "Master", description = "Preparing required fields masters data for Creating Users", priority = "Critical")
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

	@Test(priority = 12, dataProvider = "userTestData", groups = { "Smoke", "Regression" })
	@TestInfo(module = "User", description = "Create User based on User Type", priority = "Critical")
	public void create_user(UsersTestData data) throws InterruptedException {

		UserCreation userCreation = new UserCreation(users);

		userCreation.createUser("SE Employee", data, masterData);

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

		return new Object[][] { { data } };
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

	        Assert.assertNotNull(actualDepartment,
	                "Department creation failed: " + departmentName);

	    } else {

	        System.out.println("Department already exists: " + actualDepartment);
	        Assert.assertEquals(actualDepartment, departmentName);
	    }

	    return actualDepartment;
	}
}

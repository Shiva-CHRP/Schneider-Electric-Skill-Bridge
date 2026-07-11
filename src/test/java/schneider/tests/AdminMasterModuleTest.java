package schneider.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.google.gson.stream.JsonReader;

import schneider.annotations.TestInfo;
import schneider.assertions.ToastAssertions;
import schneider.listenr.Listener;
import schneider.pojo.CategoryData;
import schneider.pojo.OfferData;
import schneider.pojo.ZoneData;
import schneider.testcomponents.BaseTest;
import schneider.utils.ConfigReader;
import schneider.utils.TestDataUtil;
import schneider.utils.ToastResponse;

@Listeners(Listener.class)
public class AdminMasterModuleTest extends BaseTest {
	String offerTypeName;
	String categoryName;
	String subCategoryName;
	String departmentName = "Test Department";
	String toastType;
	String toastMessage;
	String username = ConfigReader.getUsername();
	String password = ConfigReader.getPassword();

	@Test(priority = 1)
	@TestInfo(module = "Login", description = "Login using valid Email and Password", priority = "Critical")
	public void validate_Login_To_The_Admin_Portal() throws InterruptedException {
		//loginPage.login(username, password);
		loginPage.enterUsername(TestDataUtil.getData("loginValidation.validLogin.username"));
		loginPage.enterPassword(TestDataUtil.getData("loginValidation.validLogin.password"));
		loginPage.clickLogin();
		loginPage.dashboardName();
	}

//	@BeforeMethod(alwaysRun = true)
//	public void login() throws InterruptedException {
//		if (!isUserLoggedIn()) {
//			loginPage.login(username, password);
//		}
//
//	}

	@Test(priority = 2, groups = {"offer_creation", "Regression"}, dataProvider = "offerHierarchy")
	@TestInfo(module = "Offer", description = "Verify Offer Creation with Valid Data", priority = "Critical")
	public void verify_Offer_Creation(OfferData data) {

		offerTypeName = data.getOfferName();

		offerType.goToOfferTypePage();
		offerType.createOfferType(offerTypeName);
		offerType.createOffer();

		ToastResponse toast = toastUtils.captureToast();
		String toastType = toast.getType();
		String toastMessage = toast.getMessage();
		if ("success".equalsIgnoreCase(toastType)) {

			softAssert.assertEquals(toastMessage, "Offer Type created successfully!", "Success toast mismatch");
			softAssert.assertTrue(offerType.isOfferPresentInList("Offer Type", offerTypeName),
					"Offer not found in listing screen");

		} else if ("error".equalsIgnoreCase(toastType)) {

			softAssert.assertEquals(toastMessage, "Business Group '" + offerTypeName + "' already exists",
					"Error toast mismatch");

		} else {

			softAssert.fail("Toast not displayed or locator not matching UI");
		}

		toastUtils.waitForToastToDisappear();

	}

	@Test(priority = 3, groups = {"category_creation","Regression"}, dataProvider = "offerHierarchy")
	@TestInfo(module = "Category", description = "Verify Category Creation with Valid Data", priority = "Critical")
	public void verify_Category_Creation(OfferData data) {

		offerType.goToOfferTypePage();
		offerTypeName = data.getOfferName();
		ensureOfferTypeExists(offerTypeName);

		for (CategoryData categoryData : data.getCategories()) {
			category.goToCategory();
			categoryName = categoryData.getCategoryName();
			category.createCategory(categoryName);
			category.selectOfferType(offerTypeName);
			category.saveCategory();
			ToastResponse toast2 = category.captureToast();
			String toastType2 = toast2.getType();
			String toastMessage2 = toast2.getMessage();
			if ("success".equalsIgnoreCase(toastType2)) {

				softAssert.assertEquals(toastMessage2, "Category created successfully!", "Success toast mismatch");
				softAssert.assertTrue(category.isCategoryPresentInList("Category", categoryName),
						"Category not found in listing screen");

			} else if ("error".equalsIgnoreCase(toastType2)) {

				softAssert.assertEquals(toastMessage2,
						"Offer type '" + categoryName + "' already exists in this business group",
						"Error toast mismatch");

			} else {

				softAssert.fail("Toast not displayed or locator not matching UI");
			}
			toastUtils.waitForToastToDisappear();
		}

	}

	@Test(priority = 4, groups = {"subcategory_creation","Regression"}, dataProvider = "offerHierarchy")
	@TestInfo(module = "Sub Category", description = "Verify Sub Category Creation with Valid Data", priority = "Critical")
	public void verify_Sub_Category_Creation(OfferData data) {
		offerType.goToOfferTypePage();
		offerTypeName = data.getOfferName();
		for (CategoryData categoryData : data.getCategories()) {

			category.goToCategory();
			categoryName = categoryData.getCategoryName();
			ensureCategoryExists(categoryName, offerTypeName);
			subCategory.goToSubCategory();
			for (String subCategoryName : categoryData.getSubCategories()) {
				subCategory.createSubCategory(subCategoryName);
				subCategory.selectCategory(categoryName);
				subCategory.saveSubCategory();
				ToastResponse toast4 = subCategory.captureToast();
				String toastType4 = toast4.getType();
				String toastMessage4 = toast4.getMessage();
				if ("success".equalsIgnoreCase(toastType4)) {

					softAssert.assertEquals(toastMessage4, "Sub Category created successfully!",
							"Success toast mismatch");
					softAssert.assertTrue(subCategory.isSubCategoryPresentInList("Subcategory", subCategoryName),
							"Sub Category not found in listing screen");

				} else if ("error".equalsIgnoreCase(toastType4)) {

					softAssert.assertEquals(toastMessage4,
							"Category '" + subCategoryName + "' already exists in this business group",
							"Error toast mismatch");

				} else {

					softAssert.fail("Toast not displayed or locator not matching UI");
				}
				toastUtils.waitForToastToDisappear();

			}

		}

	}

	@Test(priority = 5, groups = {"deletion", "Regression"}, dataProvider = "offerHierarchy")
	@TestInfo(module = "Offer", description = "Verify Offer Deletion", priority = "Critical")
	public void verify_Offer_Deletion(OfferData offer) {

		offerTypeName = offer.getOfferName();
		offerType.goToOfferTypePage();
		offerType.deleteOffer(offerTypeName);
		ToastResponse toast3 = toastUtils.captureToast();
		ToastAssertions.assertToastSuccess(toast3, offerTypeName + " deleted permanently",
				"Delete Success toast mismatch");
		offerType.waitForOfferToDisappear(offerTypeName);
		softAssert.assertFalse(offerType.isOfferPresentInList("Offer Type", offerTypeName),
				"Deleted offer still present in listing");

		softAssert.assertAll();
	}

	@Test(priority = 6, groups = {"department_creation", "Smoke"})
	@TestInfo(module = "Department", description = "Verify Department Creation with Valid Data", priority = "Critical")
	public void verify_Department_Creation() {

		department.goToDepartment();
		department.createDepartment(departmentName);
		department.saveDepartment();
		ToastResponse toast = toastUtils.captureToast();
		String toastType = toast.getType();
		String toastMessage = toast.getMessage();
		if ("success".equalsIgnoreCase(toastType)) {

			softAssert.assertEquals(toastMessage, "Department created successfully!", "Success toast mismatch");
			softAssert.assertTrue(department.isDepartmentPresentInList("Department Name", departmentName),
					"Department not found in listing screen");

		} else if ("error".equalsIgnoreCase(toastType)) {

			softAssert.assertEquals(toastMessage,
					"Department '" + departmentName + "' already exists in this organisation", "Error toast mismatch");

		} else {

			softAssert.fail("Toast not displayed or locator not matching UI");
		}

		toastUtils.waitForToastToDisappear();

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

	public boolean isUserLoggedIn() {

		return driver.getCurrentUrl().contains("admin");
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
}

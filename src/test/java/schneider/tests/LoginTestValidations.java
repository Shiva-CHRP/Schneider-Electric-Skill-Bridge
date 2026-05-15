package schneider.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import schneider.assertions.ToastAssertions;
import schneider.listenr.Listener;
import schneider.testcomponents.BaseTest;
import schneider.utils.ConfigReader;
import schneider.utils.TestDataUtil;
import schneider.utils.ToastResponse;
import schneider.utils.ToastUtils;

@Listeners(Listener.class)
public class LoginTestValidations extends BaseTest {

	String offerTypeName = "Panels - Test8";
	String categoryName = "Panels Category";
	String toastType;
	String toastMessage;
	String username = ConfigReader.getUsername();
	String password = ConfigReader.getPassword();

	@Test(priority = 1, groups = "validations")
	public void verify_Empty_Username_Validation() {
		loginPage.enterUsername(TestDataUtil.getData("loginValidation.emptyEmail.username"));
		loginPage.enterPassword(TestDataUtil.getData("loginValidation.emptyEmail.password"));
		loginPage.clickLogin();
		softAssert.assertEquals(loginPage.getEmailError(),
				TestDataUtil.getData("loginValidation.emptyEmail.expectedUsernameError"),
				"Username validation mismatch");
		loginPage.clearFields();
	}

	@Test(priority = 2, groups = "validations")
	public void verify_Password_Length_Validation() {
		loginPage.enterUsername(TestDataUtil.getData("loginValidation.passwordLength.username"));
		loginPage.enterPassword(TestDataUtil.getData("loginValidation.passwordLength.password"));
		loginPage.clickLogin();
		softAssert.assertEquals(loginPage.getPasswordLength(),
				TestDataUtil.getData("loginValidation.passwordLength.expectedPasswordLengthError"),
				"Password must be at least 6 characters");
		loginPage.clearFields();
	}

	@Test(priority = 3, groups = "validations")
	public void verify_Invalid_Credintals_Validation() {
		loginPage.enterUsername(TestDataUtil.getData("loginValidation.invalidCredintals.username"));
		loginPage.enterPassword(TestDataUtil.getData("loginValidation.invalidCredintals.password"));
		loginPage.clickLogin();
		softAssert.assertEquals(loginPage.getInvalidLoginError(),
				TestDataUtil.getData("loginValidation.invalidCredintals.expectedInvalidError"),
				"Invalid email or password.");
		loginPage.clearFields();
	}

	@Test(priority = 4, groups = "validations")
	public void verify_Empty_Password_Validation() throws InterruptedException {
		loginPage.enterUsername(TestDataUtil.getData("loginValidation.emptyPassword.username"));
		loginPage.enterPassword(TestDataUtil.getData("loginValidation.emptyPassword.password"));
		loginPage.clickLogin();
		softAssert.assertEquals(loginPage.getPasswordError(),
				TestDataUtil.getData("loginValidation.emptyPassword.expectedPasswordError"),
				"Password validation mismatch");
		loginPage.clearFields();
	}

	@Test(priority = 5, groups = "validations")
	public void verify_Login_With_Valid_Credentials() {
		loginPage.enterUsername(TestDataUtil.getData("loginValidation.validLogin.username"));
		loginPage.enterPassword(TestDataUtil.getData("loginValidation.validLogin.password"));
		loginPage.clickLogin();
		loginPage.dashboardName();
	}

	@Test  (priority = 6)
	public void verify_Offer_Creation_And_Delete() throws InterruptedException {
		loginPage.login(username, password);
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
		category.goToCategory();
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
					"Offer type '" + categoryName + "' already exists in this business group", "Error toast mismatch");

		} else {

			softAssert.fail("Toast not displayed or locator not matching UI");
		}
		toastUtils.waitForToastToDisappear();

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

}

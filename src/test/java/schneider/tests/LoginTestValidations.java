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
import schneider.utils.ToastUtils;

@Listeners(Listener.class)
public class LoginTestValidations extends BaseTest {

	String offerTypeName = "Panels - Test8";
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

	@Test(priority = 6)
	public void verify_Offer_Creation_And_Delete() throws InterruptedException {
		loginPage.login(username, password);
		offerType.goToOfferTypePage();
		offerType.createOfferType(offerTypeName);
		offerType.createOffer();
		ToastUtils toast = new ToastUtils(driver);
		toastType = toast.getToastType();
		toastMessage = toast.getToastMessage();
		if ("success".equalsIgnoreCase(toastType)) {

			softAssert.assertEquals("Offer Type created successfully!", toastMessage, "Success toast mismatch");
			softAssert.assertTrue(offerType.isOfferPresentInList("Offer Type", offerTypeName),
					"Offer not found in listing screen");

		} else if ("error".equalsIgnoreCase(toastType)) {

			softAssert.assertEquals("Business Group '" + offerTypeName + "' already exists", toastMessage,
					"Error toast mismatch");

		} else {

			softAssert.fail("Toast not displayed or locator not matching UI");
		}
		offerType.deleteOffer(offerTypeName);
		offerType.waitForOfferToDisappear(offerTypeName);
		ToastAssertions.assertToastSuccess(toast, offerTypeName + " deleted permanently");
		softAssert.assertFalse(offerType.isOfferPresentInList("Offer Type", offerTypeName),
				"Deleted offer still present in listing");
		softAssert.assertAll();

	}

}

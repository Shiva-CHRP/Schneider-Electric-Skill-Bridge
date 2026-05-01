package schneider.tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import schneider.data.Listener;
import schneider.pageobjects.LoginPage;
import schneider.testcomponents.BaseTest;
@Listeners(Listener.class)
public class LoginTest extends BaseTest{
	 @Test
	public void login() throws InterruptedException  {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("shrinivas.kulkarni@se.com", "Nivas@123");
	}
	 
	 
	 
	 @Test
	    public void testPass() {
	        ExtentTest test = extent.createTest("Test Case 1");
	        test.pass("Test passed successfully");
	    }

	    @Test
	    public void testFail() {
	        ExtentTest test = extent.createTest("Test Case 2");
	        test.fail("Test failed");
	    }
}

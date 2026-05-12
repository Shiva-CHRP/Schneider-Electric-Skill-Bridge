package schneider.pageobjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import schneider.abstractcomponent.AbstractComponent;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends AbstractComponent {

	public LoginPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//input[@type='email']")
	WebElement emailAddress;

	@FindBy(xpath = "//input[@type='password']")
	WebElement password;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement signinButton;
	
	@FindBy(xpath = "//h2[normalize-space()='Dashboard']")
	WebElement dashboardName;
	
	@FindBy(xpath = "//p[contains(text(),'Email is required')]")
	//By emailError = By.xpath("//p[contains(text(),'Email is required')]");
	//@FindBy(xpath = "//input[@name='email']/following::p[contains(text(),'Email is required')]")
	WebElement emailError;

	@FindBy(xpath = "//input[@name='password']/following::p[contains(text(),'Password is required')]")
	WebElement passwordError;

	@FindBy(xpath = "//div//span[contains(text(),'Invalid email or password')]")
	WebElement invalidLoginCredintals;

	@FindBy(xpath = "//div[.//input[@name='password']]//p[contains(text(),'Password must be at least 6 characters')]")
	WebElement passwordCharactersLength;

	public void login(String email, String pass) throws InterruptedException {
		waitElementToAppear(emailAddress);
		emailAddress.sendKeys(email);
		password.sendKeys(pass);
		waitElementToBeClickable(signinButton);
		signinButton.click();
	}

	public void enterUsername(String username) {
		waitForVisibility(emailAddress);
		waitElementToBeClickable(emailAddress);
		try {
			emailAddress.clear();
			}
		catch(Exception e){
			((JavascriptExecutor) driver).executeScript("arguments[0].value='';", emailAddress);
		}
		//emailAddress.clear();
		emailAddress.sendKeys(username);
	}

	public void enterPassword(String passwords) {
		waitForVisibility(password);
		waitElementToBeClickable(password);
		try {
			password.clear();
			}
		catch(Exception e){
			((JavascriptExecutor) driver).executeScript("arguments[0].value='';", password);
		}
		password.clear();
		password.sendKeys(passwords);
	}
	
	public void clickLogin() {
		signinButton.click();		
    }
	
	public void dashboardName() {
		waitElementToAppear(dashboardName);
	}

	public String getEmailError() {
		waitForVisibility(emailError);
		return emailError.getText();
		
	}

	public String getPasswordError() {
		waitForVisibility(passwordError);
		return passwordError.getText();
	}

	public String getPasswordLength() {
		waitForVisibility(passwordCharactersLength);
		return passwordCharactersLength.getText();
	}

	public String getInvalidLoginError() {
		return invalidLoginCredintals.getText();
	}
	
	public void clearFields() {
		emailAddress.clear();
		password.clear();
	}

}

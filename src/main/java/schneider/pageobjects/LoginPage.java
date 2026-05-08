package schneider.pageobjects;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import schneider.abstractcomponent.AbstractComponent;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
public class LoginPage extends AbstractComponent{

	public LoginPage(WebDriver driver) 
	{
		super(driver);
		PageFactory.initElements(driver, this);		
	}
	
	@FindBy(xpath = "//input[@type='email']")
	WebElement emailAddress;

	@FindBy(xpath = "//input[@type='password']")
	WebElement password;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement signinButton;

	    // Actions
	public void login(String email, String pass) throws InterruptedException 
	{
	    waitElementToAppear(emailAddress);
		emailAddress.sendKeys(email);
		password.sendKeys(pass);
		waitElementToBeClickable(signinButton);
		signinButton.click();	
	}

	public void goTo() {
		// TODO Auto-generated method stub
		driver.get("https://stage-ui.schneider.xrdashboard.com/login/Schneider");
	}
}

package schneider.pageobjects.user;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class UserCourseLibrary extends AbstractComponent{

	public UserCourseLibrary(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//a[@href='/user/course-library']")
	WebElement courseLibraryNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Course Library']")
	WebElement courseLibraryScreenName;

	public void goToUserCourseLibrary() {
		courseLibraryNavigation.click();
		waitElementToAppear(courseLibraryScreenName);
	}


}

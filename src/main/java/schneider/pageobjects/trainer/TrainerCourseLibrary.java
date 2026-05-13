package schneider.pageobjects.trainer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class TrainerCourseLibrary extends AbstractComponent{

	public TrainerCourseLibrary(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
	}
	
	@FindBy(xpath = "//a[@href='/trainer/courses']")
	WebElement courseLibraryNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Course Library']")
	WebElement courseLibraryScreenName;

	public void goToTrainerCourseLibrary() {
		courseLibraryNavigation.click();
		waitElementToAppear(courseLibraryScreenName);
	}

	
}

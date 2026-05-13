package schneider.pageobjects.trainer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class MyGroup extends AbstractComponent{

	public MyGroup(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
	}
	
	@FindBy(xpath = "//a[@href='/trainer/groups']")
	WebElement groupsNavigation;

	@FindBy(xpath = "//h2[normalize-space()='My Groups']")
	WebElement groupScreenName;

	public void goToMyGroup() {
		groupsNavigation.click();
		waitElementToAppear(groupScreenName);
	}

}

package schneider.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class GroupMaster extends AbstractComponent {

	public GroupMaster(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@href='/admin/group-master']")
	WebElement groupMasterNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Group Master']")
	WebElement groupMasterScreenName;

	public void goToGroupMaster() {
		groupMasterNavigation.click();
		waitElementToAppear(groupMasterScreenName);
	}

}

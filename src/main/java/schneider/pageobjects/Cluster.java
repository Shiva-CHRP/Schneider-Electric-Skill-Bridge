package schneider.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import schneider.abstractcomponent.AbstractComponent;

public class Cluster extends AbstractComponent {

	public Cluster(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@href='/admin/cluster']")
	WebElement clusterNavigation;

	@FindBy(xpath = "//h2[normalize-space()='Cluster Master']")
	WebElement clusterScreenName;

	public void goToClusterPage() {
		clusterNavigation.click();
		waitElementToAppear(clusterScreenName);
	}

}

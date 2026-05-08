package schneider.tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import schneider.data.Listener;
import schneider.testcomponents.BaseTest;
@Listeners(Listener.class)
public class SmokeTest extends BaseTest{
	@Test
	public void Smoke() throws InterruptedException {
		loginPage.goTo();
		loginPage.login("shrinivas.kulkarni@se.com", "Nivas@123");
		offerType.goToOfferTypePage();
		category.goToCategory();
		subCategory.goToSubCategory();
		department.goToDepartment();
		partner.goToPartner();
		zone.goToZone();
		cluster.goToClusterPage();
		country.goToCountryPage();
		groupMaster.goToGroupMaster();
	}

}

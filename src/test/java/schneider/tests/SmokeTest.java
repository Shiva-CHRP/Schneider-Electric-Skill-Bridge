package schneider.tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import schneider.listenr.Listener;
import schneider.testcomponents.BaseTest;
import schneider.utils.ConfigReader;
@Listeners(Listener.class)
public class SmokeTest extends BaseTest{
	String username = ConfigReader.getUsername();
	String password = ConfigReader.getPassword();
	@Test(groups="smoke")
	public void Smoke() throws InterruptedException {
		
		loginPage.login(username, password);
		offerType.goToOfferTypePage();
		category.goToCategory();
		subCategory.goToSubCategory();
		department.goToDepartment();
		partner.goToPartner();
		zone.goToZone();
		cluster.goToClusterPage();
		country.goToCountryPage();
		groupMaster.goToGroupMaster();
		users.goToUsers();
		assignedUsers.goToAssignedUsers();
		contentHub.goToContentHub();
		assessments.goToAssessment();
		questionBank.goToQuestionBank();
		bulkUpload.goToBulkUpload();
		questionStrategies.goToQuestionStrategies();
		reports.goToReports();
		external.goToExternal();
		events.goToEvents();
		certificates.goToCertificate();
		certificateTemplates.goToCertificateTemplates();
		settings.goToSettings();
		feedback.goToFeedback();
		dashboard.goToDashboard();
		dashboard.clickProfile();
		dashboard.clickSwitchAccount();
		dashboard.clickTrainerRole();
		myGroup.goToMyGroup();
		trainerAssignedUsers.goToTrainerAssignedUsers();
		trainerCourseLibrary.goToTrainerCourseLibrary();
		eventsCalendar.goToEventsCalendar();
		assessmentReports.goToAssessmentReports();
		trainerContentHub.goToTrainerContentHub();
		trainerFeedback.goToTrainerFeedback();
		trainerCertificates.goToTrainerCertificates();
		trainerSettings.goToTrainerSettings();
		trainerDashboard.goToTrainerDashboard();
		dashboard.clickTrainerProfile();
		dashboard.clickSwitchAccount();
		dashboard.clickUserRole();
		userCourseLibrary.goToUserCourseLibrary();
		userEvents.goToUserEvents();
		userAssessments.goToUserAssessments();
		userCertificates.goToUserCertificates();
		userFeedback.goToUserFeedback();
		userDashboard.goToUserDashboard();
		dashboard.clickUserProfile();
		dashboard.clickSwitchAccount();
		dashboard.clickAdminRole();
		dashboard.clickLogout();
	}

}

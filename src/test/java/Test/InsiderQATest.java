package Test;

import Base.BaseTest;
import Pages.HomePage;
import Pages.CareersPage;
import Pages.QAPositionsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class InsiderQATest extends BaseTest {

    @Test
    public void verifyInsiderQAPositions() {
        logger.info("Starting Insider QA Positions Test");

        // Step 1: Navigate to Insider homepage and verify
        navigateToUrl("https://useinsider.com/");
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageOpened(), "Home page should be opened");
        logger.info("Successfully verified Insider homepage");
        sleep(3000);
        

        // Step 1.1: Click Company menu and navigate to Careers
        CareersPage careersPage = homePage.navigateToCareers();
        logger.info("Successfully navigated to Careers through Company menu");
        sleep(3000);
        

        // Step 2: Navigate to QA Careers page
        navigateToUrl("https://useinsider.com/careers/quality-assurance/");
        Assert.assertTrue(careersPage.verifyCareerPageSections(), 
            "All career page sections should be visible");
        logger.info("Successfully verified Careers page sections");
        sleep(3000);
        

        // Step 3: Click Find your dream job
        QAPositionsPage qaPositionsPage = new QAPositionsPage(driver);
        qaPositionsPage.clickQAJob();
        logger.info("Clicked See all QA jobs button");
        sleep(3000);
        

        // Step 4: Clear location filter
        qaPositionsPage.clearLocationFilter();
        logger.info("Cleared location filter");
        sleep(3000);

        // Step 5: Select Turkey location
        qaPositionsPage.selectTurkeyLocation();
        logger.info("Selected Turkey location");
        sleep(3000);

        // Step 6: Select Quality Assurance department
        qaPositionsPage.selectQADepatment();
        logger.info("Selected Quality Assurance department");
        sleep(5000);

        // Step 7: Check positions and proceed if available
        qaPositionsPage.checkPositionsAndProceed();
        logger.info("Checked positions and proceeded if available");
        sleep(3000);

        logger.info("Verified Insider QA Positions Test completed");
    }
}
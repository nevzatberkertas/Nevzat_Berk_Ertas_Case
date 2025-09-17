package Pages;

import Base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class QAPositionsPage extends BasePage {
    // Locators
    private final By findQAJobButton = By.xpath("//*[@id=\"page-head\"]/div/div/div[1]/div/div/a");
    private final By locationFilter = By.cssSelector("span.select2-selection.select2-selection--single");
    private final By departmentFilter = By.xpath("//*[@id=\"select2-filter-by-department-container\"]");
    private final By clearLocationButton = By.cssSelector("span.select2-selection__clear");
    private final By noPositionsAvailable = By.cssSelector("p.no-job-result");
    private final By turkeyOption = By.xpath("//li[contains(@id, 'select2-filter-by-location-result') and contains(text(), 'Istanbul, Turkiye')]");
    private final By qaOption = By.xpath("//li[contains(@id, 'select2-filter-by-department-result') and contains(text(), 'Quality Assurance')]");
    private final By positionWrapper = By.cssSelector(".position-list-item-wrapper");
    private final By viewRoleButton = By.cssSelector(".position-list-item-wrapper a.btn");
    private final By locationDropdownArrow = By.cssSelector("span.select2-selection__arrow");
    public QAPositionsPage(WebDriver driver) {
        super(driver);
    }

    public void clickQAJob() {
        logger.info("Clicking See all QA job button");
        waitForElementClickable(findQAJobButton);
        click(findQAJobButton);
    }

    public void clearLocationFilter() {
        logger.info("Clearing location filter");
        waitForElementClickable(locationFilter);
        click(locationFilter);
        waitForElementClickable(clearLocationButton);
        click(clearLocationButton);
    }

    public void selectTurkeyLocation() {
        logger.info("Selecting Turkey location");
        click(locationDropdownArrow);
        click(locationFilter);
        waitForElementClickable(turkeyOption);
        click(turkeyOption);
    }

    public void selectQADepatment() {
        logger.info("Selecting QA department");
        click(locationDropdownArrow);
        click(departmentFilter);
        waitForElementClickable(qaOption);
        click(qaOption);
    }

    public String checkPositionsAndProceed() {
        logger.info("Checking for available positions");
        try {
            if (isElementDisplayed(noPositionsAvailable)) {
                logger.info("No positions are available");
                return "No positions available";
            } else {
                logger.info("Positions are available, hovering and clicking View Role");

                WebElement wrapper = driver.findElement(positionWrapper);
                Actions actions = new Actions(driver);
                actions.moveToElement(wrapper).perform();

                waitForElementClickable(viewRoleButton);
                click(viewRoleButton);

                return "Positions found";
            }
        } catch (Exception e) {
            logger.error("Error while checking positions: " + e.getMessage());
            return "Error: " + e.getMessage();
        }
    }
}
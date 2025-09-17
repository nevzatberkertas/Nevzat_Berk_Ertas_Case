package Pages;

import Base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CareersPage extends BasePage {
    // Locators
    private final By qaTitle = By.xpath("//h1[contains(@class,'big-title') and normalize-space()='Quality Assurance']");
    private final By qaSeeAllBtn = By.xpath("//a[contains(@class,'btn') and contains(@class,'btn-outline-secondary') and contains(normalize-space(),'See all QA jobs') and contains(@href,'/careers/open-positions/') and contains(@href,'department=qualityassurance')]");
    private final By qaHeroImg = By.xpath("//img[contains(@class,'align-self-center') and (contains(@src,'/assets/media/2021/03/qa.png') or contains(@srcset,'/assets/media/2021/03/qa.png'))]");
    private final By seeAllQAJobsButton = By.xpath("//*[@id=\"page-head\"]/div/div/div[1]/div/div/a");
    private final By findDreamJobButton = By.xpath("//*[@id=\"career-position-list\"]/div/div/div[2]/div/a");
    
    public CareersPage(WebDriver driver) {
        super(driver);
    }

    private static final String EXPECTED_QA_JOBS_URL = "https://useinsider.com/careers/open-positions/?department=qualityassurance";

    public boolean verifyCareerPageSections() {

        WebDriverWait localWait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // 1) Başlık
            WebElement titleEl = localWait.until(ExpectedConditions.visibilityOfElementLocated(qaTitle));
            String titleText = titleEl.getText().trim();
            if (!"Quality Assurance".equals(titleText)) {
                return false;
            }

            // 2) Buton
            WebElement btnEl = localWait.until(ExpectedConditions.elementToBeClickable(qaSeeAllBtn));
            String btnText = btnEl.getText().trim();
            String href = btnEl.getAttribute("href");
            if (!btnText.equalsIgnoreCase("See all QA jobs")) {
                return false;
            }
            if (href == null || !href.equals(EXPECTED_QA_JOBS_URL)) {
                return false;
            }

            WebElement imgEl = localWait.until(ExpectedConditions.visibilityOfElementLocated(qaHeroImg));
            String src = imgEl.getAttribute("src");
            String srcset = imgEl.getAttribute("srcset");
            boolean hasQaPng = (src != null && src.contains("/assets/media/2021/03/qa.png"))
                    || (srcset != null && srcset.contains("/assets/media/2021/03/qa.png"));
            if (!hasQaPng) {
                return false;
            }

            return true;

        } catch (TimeoutException te) {
            return false;
        } catch (Exception e) {
            logger.error("Error while verifying QA Careers hero", e);
            return false;
        }
    }
}
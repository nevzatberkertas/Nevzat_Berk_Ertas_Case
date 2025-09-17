package Pages;

import Base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class HomePage extends BasePage {
    // Locators
    private final By companyAnchorInsideLi = By.cssSelector("a#navbarDropdownMenuLink, a.nav-link.dropdown-toggle");
    private final By navItemDropdowns = By.cssSelector("li.nav-item.dropdown");
    private final By dropdownMenu = By.cssSelector(".dropdown-menu");

    public HomePage(WebDriver driver) {
        super(driver);
        waitForPageLoad();
    }

    private void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    public boolean isHomePageOpened() {
        logger.info("Checking if home page is opened");
        return driver.getTitle().contains("Insider");
    }

    public CareersPage navigateToCareers() {
        return navigateToCareersFromNthMenu(5); // 0-based index: 5 => 6. öğe
    }

    public CareersPage navigateToCareersFromNthMenu(int index) {

        WebDriverWait localWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Actions actions = new Actions(driver);

        List<WebElement> menus = localWait.until(d -> {
            List<WebElement> els = d.findElements(navItemDropdowns);
            return (els != null && !els.isEmpty()) ? els : null;
        });

        WebElement targetLi = menus.stream()
                .filter(WebElement::isDisplayed)
                .collect(Collectors.toList())
                .get(index);

        WebElement anchor = targetLi.findElement(companyAnchorInsideLi);

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", anchor);
        actions.moveToElement(anchor).pause(Duration.ofMillis(250)).perform();

        try {
            localWait.until(d -> {
                String expanded = anchor.getAttribute("aria-expanded");
                boolean hasShow = hasShowClass(targetLi);
                return "true".equalsIgnoreCase(expanded) || hasShow;
            });
        } catch (TimeoutException te) {
            safeClick(anchor);
        }

        WebElement dd = localWait.until(d -> {
            try {
                WebElement m = targetLi.findElement(dropdownMenu);
                return (m.isDisplayed()) ? m : null;
            } catch (NoSuchElementException e) {
                return null;
            }
        });

        By careersRel = By.xpath(".//a[contains(@class,'dropdown-sub') and " +
                "(contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'careers') " +
                "or contains(@href,'/careers'))]");

        WebElement careers = localWait.until(d -> {
            try {
                WebElement el = dd.findElement(careersRel);
                return (el.isDisplayed() && el.isEnabled()) ? el : null;
            } catch (NoSuchElementException e) {
                return null;
            }
        });

        try {
            actions.moveToElement(careers).pause(Duration.ofMillis(120)).click().perform();
        } catch (ElementClickInterceptedException | MoveTargetOutOfBoundsException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", careers);
        }

        logger.info("Clicked Careers link");
        return new CareersPage(driver);
    }


    private boolean hasShowClass(WebElement li) {
        try {
            WebElement dm = li.findElement(dropdownMenu);
            String cls = dm.getAttribute("class");
            return cls != null && cls.contains("show");
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private void safeClick(WebElement el) {
        try {
            el.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
    }
}

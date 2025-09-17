package Base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Arrays;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected WebDriver driver;
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);
    protected static boolean isDebugMode = false;

    @BeforeMethod
    public void setUp() {
        logger.info("Initializing WebDriver" + (isDebugMode ? " in debug mode" : ""));
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-gpu");
        options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        driver = new ChromeDriver(options);
        logger.info("WebDriver initialized successfully");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            logger.info("Automation completed, closing WebDriver");
            driver.quit();
        }
    }

    protected void navigateToUrl(String url) {
        logger.info("Navigating to URL: " + url);
        driver.get(url);
    }

    public static void sleep(Integer time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
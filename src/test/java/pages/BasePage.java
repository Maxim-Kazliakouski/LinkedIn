package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    WebDriver browser;
    static WebDriverWait wait;

    public BasePage(WebDriver browser) {
        this.browser = browser;
        wait = new WebDriverWait(browser, Duration.ofSeconds(5));
        PageFactory.initElements(browser, this);
    }

    public final static String BASE_URL = "https://www.linkedin.com/";

    public abstract boolean isOpened();

    public static boolean waitForVisibility(WebElement webElement) {
        try {
            wait.until(ExpectedConditions.visibilityOf(webElement));
            return true;
        } catch (TimeoutException ex) {
            ex.getStackTrace();
            return false;
        }
    }

    public static boolean waitForElementClickable(WebElement webElement) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(webElement));
            return true;
        } catch (TimeoutException ex) {
            return false;
        }
    }

    public static void waitForPageLoaded() {
        new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
            }
        };
    }

    public void clickJS(By locator) {
        ((JavascriptExecutor) browser).executeScript("arguments[0].click();", browser.findElement(locator));
    }
}
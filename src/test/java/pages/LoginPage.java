package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import steps.LoginPageSteps;
import tests.base.BaseWithStepsTest;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class LoginPage extends BasePage {
    private final String LOGIN_PAGE_URL = BASE_URL + "login/ru?trk=homepage-basic_intl-segments-login";
    public BaseWithStepsTest baseWithStepsTest;
    @FindBy(xpath = "//button[@type='submit']")
    WebElement loginButton;
    @FindBy(id = "username")
    WebElement USERNAME;
    @FindBy(id = "password")
    WebElement PASSWORD;

    public LoginPage(WebDriver browser) {
        super(browser);
    }

    public void open(){
        browser.get(LOGIN_PAGE_URL);
    }

    @Override
    public boolean isOpened() {
        return waitForVisibility(loginButton);
    }

    public void login(String username, String password) {
        USERNAME.sendKeys(username);
        waitForVisibility(PASSWORD);
        PASSWORD.sendKeys(password);
        waitForVisibility(loginButton);
        loginButton.click();
    }
}


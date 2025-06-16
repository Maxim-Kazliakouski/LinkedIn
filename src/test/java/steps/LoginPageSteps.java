package steps;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.HomePage;
import pages.LoginPage;

import static org.testng.Assert.assertTrue;

public class LoginPageSteps {

    private static final Logger log = LoggerFactory.getLogger(LoginPageSteps.class);
    LoginPage loginPage;
    HomePage homePage;

    public LoginPageSteps(WebDriver browser) {
        loginPage = new LoginPage(browser);
        homePage = new HomePage(browser);
    }
    /*
    method to display the data in Allure report with which the user was created
     */

//    @Step("Parameters for created user:")
//    public void dataForUser(String firstName, String lastName, String phone, String address, String leadSourceOption, String salutation) {
//    }

    @Step("Go to the create contact page")
    public LoginPageSteps goToLoginPage() {
        log.info("Go to Login Page");
        loginPage.open();
        assertTrue(loginPage.isOpened(), "Login page hadn't opened!");
        log.info("Login Page is opened");
        return this;
    }

    @Step("Login")
    public void login(String username, String password) {
        log.info("Trying to login");
        loginPage.login(username, password);
        assertTrue(homePage.isOpened(), "Home page hadn't opened!");
        log.info("Successful login");
    }
}
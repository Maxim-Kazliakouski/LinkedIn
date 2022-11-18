package steps;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import pages.ContactsPage;
import pages.HomePage;

import static org.testng.Assert.assertTrue;
@Log4j2
public class HomePageSteps {
    HomePage homePage;
    ContactsPage contactsPage;

    public HomePageSteps(WebDriver browser){
        homePage = new HomePage(browser);
        contactsPage = new ContactsPage(browser);
    }

    @Step("Go to the 'Contacts' page")
    public void goToContactsPage(){
        log.info("Go to Contacts page");
        contactsPage.open();
        assertTrue(contactsPage.isOpened(), "'Contacts' page hadn't opened!");
        log.info("Contact page is opened");
    }
}

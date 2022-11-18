package steps;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import pages.ContactsPage;
import pages.HomePage;

public class ContactsPageSteps {
    HomePage homePage;
    ContactsPage contactsPage;

    public ContactsPageSteps(WebDriver browser){
        homePage = new HomePage(browser);
        contactsPage = new ContactsPage(browser);
    }

    @Step("Create connections")
    public void createConnection(String...params){
        contactsPage.createConnectionAndGetAmountOfConnections(params);
    }
}

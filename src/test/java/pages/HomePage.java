package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
    @FindBy(xpath = "//h3[text()='Maxim Kazliakouski']")
    WebElement userNameAtHomePage;

    public HomePage(WebDriver browser) {
        super(browser);
    }

    @Override
    public boolean isOpened() {
        return waitForVisibility(userNameAtHomePage);
    }

    public void goToContactsPage(){
        String CONTACT_PAGE_URL = BASE_URL + "mynetwork/";
        browser.get(CONTACT_PAGE_URL);
    }
}

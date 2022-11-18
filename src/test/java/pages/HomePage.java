package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
    @FindBy(xpath = "//div[@class='t-16 t-black t-bold']")
    WebElement userNameAtHomePage;
    private final String CONTACT_PAGE_URL = BASE_URL + "mynetwork/";

    public HomePage(WebDriver browser) {
        super(browser);
    }

    @Override
    public boolean isOpened() {
        return waitForVisibility(userNameAtHomePage);
    }

    public void goToContactsPage(){
        browser.get(CONTACT_PAGE_URL);
    }
}

package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;

@Log4j2
public class ContactsPage extends BasePage {
    private static final Logger log = LoggerFactory.getLogger(ContactsPage.class);
    private final By ALL_CONTACTS = By.xpath("//span[@class='discover-person-card__occupation t-14 t-black--light t-normal']");
    @FindBy(xpath = "//section[@class='mn-community-summary']/div/h2")
    WebElement titleContactPageMax;
    @FindBy(xpath = "//section[@class='mn-community-summary']/div")
    WebElement titleContactPageNatali;
    @FindBy(xpath = "//div[@class='mn-community-summary__entity-info'][text()='Connections']//following-sibling::div[@class='pl3']")
    WebElement amountOfConnectionsMax;
    @FindBy(xpath = "//div[@class='mn-community-summary__entity-info'][text()='Контакты']//following-sibling::div[@class='pl3']")
    WebElement amountOfConnectionsNatali;
    private final String CONTACT_PAGE_URL = BASE_URL + "mynetwork/";

    public ContactsPage(WebDriver browser) {
        super(browser);
    }

    public void open() {
        browser.get(CONTACT_PAGE_URL);
    }

    @Override
    public boolean isOpened() {
        if (System.getProperty("usernameChrome").contains("nkazliakouskaya@gmail.com")) {
            return waitForVisibility(titleContactPageNatali);
        } else return waitForVisibility(titleContactPageMax);
    }

    public WebElement getUserName(String spec) {
        return browser.findElement(By.xpath(format(
                "//span[text()='%s']//..//span[@class='discover-person-card__name t-16 t-black t-bold']", spec)));
    }

    public String getWorkStatus(String userName) {
        return browser.findElement(By.xpath(format("//span[text()='%s']//..//span[@class='discover-person-card__name t-16 t-black t-bold']//..//..//img", userName))).getAttribute("alt");
    }

    public boolean ghostPerson(String userName) {
        boolean result = false;
        try {
            System.out.println("!!!NOT GHOST PERSON --> CAN BE ADDED!!!");
            if (!getWorkStatus(userName).contains("is open to work")) {
                result = true;
            }
            return result;
        } catch (Exception er) {
            System.out.println("---> GHOST PERSON <--- CAN'T BE ADDED!!!");
            By.xpath(format("//span[text()='%s']//..//span[@class='discover-person-card__name t-16 t-black t-bold']//..//div[contains(@class,'ghost-entity')]", userName));
            return false;
        }
    }

    public By getButtonBySpecialization(String spec) {
        return By.xpath(format("//span[@class='discover-person-card__occupation t-14 t-black--light t-normal'][text()='%s']//..//..//..//footer//button", spec));
    }

    public WebElement pendingButton(String spec) {
        return browser.findElement(By.xpath(format("//span[text()='%s']//following::span[text()='Pending']", spec)));
    }

    public String connectionsAmount() {
        if (System.getProperty("usernameChrome").contains("nkazliakouskaya@gmail.com")) {
            return amountOfConnectionsNatali.getText();
        } else return amountOfConnectionsMax.getText();
    }

    public void createConnectionAndGetAmountOfConnections(String... searchingSpecialists) {
        ArrayList<String> desiredContacts = new ArrayList<>();
        for (String param : searchingSpecialists) {
            desiredContacts.add(param);
        }
        List<WebElement> contactQaList = browser.findElements(ALL_CONTACTS);
        ArrayList<String> contacts = new ArrayList<>();
        ArrayList<String> contactsForClick = new ArrayList<>();
        for (WebElement eachContact : contactQaList) {
            contacts.add(eachContact.getText());
        }
        Set<String> uniqueContacts = new HashSet<>(contacts);
        for (String specialization : uniqueContacts) {
            for (String eachUniqueContact : desiredContacts) {
                if (specialization.contains(eachUniqueContact)
                        && !specialization.contains("Junior")
                        && !specialization.contains("junior")
                        && ghostPerson(specialization)) {
//                        && !getWorkStatus(specialization).contains("is open to work"))
                    contactsForClick.add(specialization);
                    try {
//                        log.info(getWorkStatus(getUserName(specialization).getText()));
                        clickJS(getButtonBySpecialization(specialization));
                        waitForElementClickable(pendingButton(specialization));
                        log.info(getUserName(specialization).getText() + " --> " + specialization + " -- has been added");
                    } catch (Exception er) {
                        log.error("The contact '" + getUserName(specialization).getText() + "' hasn't been added \nor 'Pending button hasn't been shown'");
                    }
                }
            }
        }
//        System.out.println(contactsForClick);
        if (contactsForClick.size() == 0) {
            log.warn("There is no any contacts for adding");
        } else {
            log.info(format("The %s contact(s) has been added", contactsForClick.size()));
            log.info(format("Amount of connections in given moment: %s", connectionsAmount()));
        }
    }
}
//}
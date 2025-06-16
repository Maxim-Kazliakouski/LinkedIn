package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.lang.Thread.sleep;

@Log4j2
public class ContactsPage extends BasePage {
    private final By FIO = By.xpath("//span[@aria-hidden='true']");
    @FindBy(xpath = "//p[text()='Manage my network']")
    WebElement titleContactPageMax;
    @FindBy(xpath = "//section[@class='mn-community-summary']/div")
    WebElement titleContactPageNatali;
    @FindBy(xpath = "//a[contains(@aria-label,'connections')]/span[3]/span")
    WebElement amountOfConnectionsMax;
    @FindBy(xpath = "//div[@class='mn-community-summary__entity-info'][text()='Контакты']//following-sibling::div[@class='pl3']")
    WebElement amountOfConnectionsNatali;

    public ContactsPage(WebDriver browser) {
        super(browser);
    }

    public void open() {
        String CONTACT_PAGE_URL = BASE_URL + "mynetwork/";
        browser.get(CONTACT_PAGE_URL);
    }

    @Override
    public boolean isOpened() {
        if (System.getProperty("usernameChrome").contains("nkazliakouskaya@gmail.com")) {
            return waitForVisibility(titleContactPageNatali);
        } else return waitForVisibility(titleContactPageMax);
//        return waitForVisibility(titleContactPageMax);
    }

    public List<String> getUserNames() {
        List<WebElement> nameElements = browser.findElements(FIO);
        return nameElements.stream()
                .filter(element -> !element.getText().contains("mutual"))
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public String getWorkStatus(String userName) {
        try {
            WebElement element = browser.findElement(By.xpath(format("//span[text()=\"%s\"]//..//..//..//div[1]", userName)));
            String attribute = element.getAttribute("aria-label");
            return attribute != null ? attribute : "";
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    public Map<String, String> getSpecializationMapByFio() {
        Map<String, String> nameToSpecializationMap = new HashMap<>();
        List<String> userNames = getUserNames(); // Сохраняем результат в переменную

        for (String userName : userNames) {
            System.out.println(userName);
            try {
                WebElement specializationElement = browser.findElement(
                        By.xpath(format("//span[text()=\"%s\"]//following::p[1]", userName))
                );

                String specializationText = specializationElement.getText();
                String workStatus = getWorkStatus(userName);

                if (workStatus != null && workStatus.contains("open to work")) {
                    log.info("This contact {} is open to work", userName);
                }

                boolean isNotJunior = !specializationText.toLowerCase().contains("junior");
                boolean isNotOpenToWork = workStatus == null || !workStatus.contains("open to work");

                if (isNotJunior && isNotOpenToWork) {
                    nameToSpecializationMap.put(userName, specializationText);
                }

            } catch (NoSuchElementException e) {
                log.error("Element not found for user: {}", userName);
            }
        }

        return nameToSpecializationMap;
    }

    public void inviteAllFromMap(Map<String, String> nameToSpecializationMap) {
        if (nameToSpecializationMap == null || nameToSpecializationMap.isEmpty()) {
            log.info("Мапа пустая или null. Нет контактов для приглашения.");
            return;
        }

        for (String name : nameToSpecializationMap.keySet()) {
            try {

                // Формируем локатор для кнопки приглашения
                By inviteButtonLocator = By.xpath(String.format("//button[@aria-label=\"Invite %s to connect\"]", name));

                // Ищем кнопку и кликаем
                WebElement inviteButton = browser.findElement(inviteButtonLocator);
//                inviteButton.click();

                log.info("Отправлено приглашение для: {}", name);

                // Можно добавить задержку, если нужно (например, для стабильности)
//                sleep(1000);

            } catch (NoSuchElementException e) {
                log.error("❌ Не найдена кнопка приглашения для: {}, т.к. есть кнопка 'Follow'", name);
            } catch (Exception e) {
                log.error("⚠️ Ошибка при обработке контакта: {} -> {}", name, e.getMessage());
            }
        }
    }

    public String getConnectionsAmount() {
        if (System.getProperty("usernameChrome").contains("nkazliakouskaya@gmail.com")) {
            return amountOfConnectionsNatali.getText();
        } else return amountOfConnectionsMax.getText();
//        return amountOfConnectionsMax.getText();
    }

    public void createConnectionAndGetAmountOfConnections(String... searchingSpecialists) {
        Map<String, String> specializationMap = getSpecializationMapByFio(); // Сохраняем результат в переменную

        inviteAllFromMap(specializationMap);

        // Логирование результатов
        specializationMap.forEach((key, value) ->
                log.info("{} → {}", key, value));

        log.info("The {} contact(s) has been added", specializationMap.size());
        log.info("Amount of connections at the moment: {}", getConnectionsAmount());
    }

    public void scrollToBottomOfThePage() throws InterruptedException {
        browser.findElement(By.xpath("(//p[contains(text(),'People you may know')])[1]")).click();
        sleep(5000);
        browser.findElement(By.tagName("body")).sendKeys(Keys.END);
        sleep(5000);
    }
}
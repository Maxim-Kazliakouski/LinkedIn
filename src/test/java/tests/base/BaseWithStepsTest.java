package tests.base;

import com.github.javafaker.Faker;
import config.TestPropertyReader;
//import io.github.bonigarcia.wdm.WebDriverManager;
//import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestContext;
import org.testng.annotations.*;

import steps.ContactsPageSteps;
import steps.HomePageSteps;
import steps.LoginPageSteps;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import org.apache.commons.io.FileUtils;

@Listeners(TestListener.class)
public abstract class BaseWithStepsTest {
    public WebDriver browser;
    public Faker faker;
    public LoginPageSteps loginPageSteps;
    public HomePageSteps homePageSteps;
    public ContactsPageSteps contactsPageSteps;
    private String username;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    private String password;

    @Parameters({"browserType", "headlessMode", "isLogin"})
    @BeforeMethod(description = "Opening browser")
    public void setup(@Optional("chrome") String browserType, ITestContext testContext,
                      @Optional("false") String headlessMode,
                      @Optional("true") String isLogin) {
        if (browserType.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", "C:\\Windows\\System32\\chromedriver.exe");
//            WebDriverManager.chromedriver().driverVersion("116.0.5845.96").setup();
//            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("window-size=1920x1080");
            options.addArguments("--disable-notifications");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.setHeadless(headlessMode.equals("false"));

            browser = new ChromeDriver(options);
            browser.manage().deleteAllCookies();
//            username = TestPropertyReader.getProperty("username");
//            password = TestPropertyReader.getProperty("password");
//            properties for getting data from command line
            username = System.getProperty("usernameChrome");
            password = System.getProperty("passwordChrome");
        } else if (browserType.equals("firefox")) {
//            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("window-size=1920х1080");
            options.addArguments("--disable-notifications");
//            options.addArguments("--remote-allow-origins=*");
            options.addPreference("dom.webnotifications.enabled", false);
            options.setHeadless(headlessMode.equals("true"));
            browser = new FirefoxDriver(options);
            browser.manage().deleteAllCookies();
            username = TestPropertyReader.getProperty("username");
            password = TestPropertyReader.getProperty("password");
//            username = System.getProperty("usernameFirefox");
//            password = System.getProperty("passwordFirefox");
        }
        // для связки с скриншотами в TestListener
        testContext.setAttribute("browser", browser);
        faker = new Faker();
        loginPageSteps = new LoginPageSteps(browser);
        homePageSteps = new HomePageSteps(browser);
        contactsPageSteps = new ContactsPageSteps(browser);
//        loginSteps = new LoginSteps(browser);
//        homePage = new HomePage(browser);
//        createContactSteps = new CreateContactSteps(browser);
//        forgotYourPasswordPage = new ForgotYourPasswordPage(browser);
        browser.manage().window().maximize();
        browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterMethod(alwaysRun = true, description = "closing browser")
    public void tearDown() {
        if (browser != null) {
            browser.quit();
        }
    }

//    public void takeSreenshoot() {
//        WebDriver driver = new ChromeDriver();
//        TakesScreenshot screenshot = (TakesScreenshot) driver;
//        File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
//        File destFile = new File("path/to/save/screenshot.png");
//
//        try {
//            // Копируем скриншот в указанное место
//            FileUtils.copyFile(srcFile, destFile);
//            System.out.println("Скриншот сохранен в: " + destFile.getAbsolutePath());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void makeScreenShoot() throws IOException {
        TakesScreenshot scrShot = ((TakesScreenshot) browser);
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy::h-m-s");
        Date date = new Date();
        File DestFile = new File("src/test/java/tests/screenshoots/image:" + dateFormat.format(date) + ".jpg");
        FileUtils.copyFile(SrcFile, DestFile);
    }
}
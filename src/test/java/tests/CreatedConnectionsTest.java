package tests;

import com.sun.javafx.runtime.SystemProperties;
import config.TestPropertyReader;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;
import tests.base.BaseWithStepsTest;

@Log4j2
public class CreatedConnectionsTest extends BaseWithStepsTest {
    @Test
    public void createNewConnections() {
        loginPageSteps
                .goToLoginPage()
                .login(getUsername(), getPassword());
        homePageSteps
                .goToContactsPage();
        /*
        old realization with list of specialities
        */
//        contactsPageSteps.createConnection(
//                "Automation", "automation", "AQA", "aqa",
//                "qa", "QA", "Qa", "Test Engineer", "PM", "Project Manager",
//                "hr", "HR", "Manager", "resource", "Resource", "UI/UX",
//                "Recruiter", "recruiter", "testing"
//        );
//                .createConnection("Automation", "automation", "AQA", "aqa", "qa", "QA", "Qa", "Test Engineer", "PM", "Project Manager", "hr", "HR", "Manager", "resource", "Resource", "UI/UX", "Recruiter", "recruiter", "testing");

        /*
        new realization with list of specialities from Jenkins
        */
        String[] params = System.getProperty("specialitiesForAdding").split(", ");
        contactsPageSteps
                .createConnection(params);
    }
}
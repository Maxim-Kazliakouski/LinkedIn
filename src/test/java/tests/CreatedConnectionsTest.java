package tests;

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
        contactsPageSteps.createConnection(
                "Automation", "automation", "AQA", "aqa",
                "qa", "QA", "Qa", "Test Engineer", "PM", "Project Manager",
                "hr", "HR", "resource", "Resource", "UI/UX",
                "Recruiter", "recruiter"
        );
    }
}


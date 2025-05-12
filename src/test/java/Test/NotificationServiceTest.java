package Test;

import io.cucumber.java.en.*;
import services.NotificationService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationServiceTest {

    private NotificationService notificationService;
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalErr = System.err;

    @Given("the system initializes the notification service")
    public void initializeNotificationService() {
        notificationService = new NotificationService();
        System.setErr(new PrintStream(errContent));
    }

    @When("the system sets email {string} and password {string}")
    public void systemSetsCredentials(String email, String password) {
        notificationService.setCredentials(email, password);
    }

    @Then("the service should be marked as configured")
    public void serviceShouldBeConfigured() {
        assertTrue(notificationService.isConfigured(), "Service should be configured but is not.");
    }

    @Then("the service should not be configured")
    public void serviceShouldNotBeConfigured() {
        assertFalse(notificationService.isConfigured(), "Service should not be configured.");
    }

    @When("an email is sent to {string} with subject {string} and body {string}")
    public void sendEmail(String to, String subject, String body) {
        notificationService.sendEmail(to, subject, body);
    }

    @Then("an error message should be logged")
    public void errorMessageShouldBeLogged() {
        System.setErr(originalErr);
        assertTrue(errContent.toString().contains("Failed to send email"), "Expected error not logged.");
    }
}

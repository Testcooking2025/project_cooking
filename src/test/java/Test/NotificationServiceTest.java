package Test;

import io.cucumber.java.en.*;
import services.NotificationService;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationServiceTest {

    private NotificationService notificationService;

    @Given("the system initializes the notification service")
    public void initializeNotificationService() {
        notificationService = new NotificationService();
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
}

package Test;

import io.cucumber.java.en.*;
import models.RestockRequest;
import services.SupplierNotificationService;

import static org.junit.jupiter.api.Assertions.*;
import io.cucumber.datatable.DataTable;

import java.util.*;

public class SupplierNotifications {

    private SupplierNotificationService service;
    private List<String> manualRequests;

    @Given("the inventory stock with threshold is:")
    public void theInventoryWithThreshold(DataTable table) {
        service = new SupplierNotificationService();
        for (Map<String, String> row : table.asMaps()) {
            String name = row.get("Ingredient");
            int quantity = Integer.parseInt(row.get("Quantity"));
            int threshold = Integer.parseInt(row.get("Threshold"));
            service.addIngredient(name, quantity, threshold);
        }
    }

    @When("the system checks inventory levels")
    public void theSystemChecksInventoryLevels() {
        if (service == null) service = new SupplierNotificationService(); // safety check
        service.checkStockLevels();
    }

    @Then("the system should notify supplier to restock:")
    public void theSystemShouldNotifySupplier(DataTable table) {
        List<String> expected = table.asList();
        List<String> actual = service.getLowStockNotifications();
        assertEquals(expected, actual);
    }

    @Then("no supplier notification should be sent")
    public void noSupplierNotificationShouldBeSent() {
        List<String> actual = service.getLowStockNotifications();
        assertTrue(actual.isEmpty(), "Unexpected supplier notifications sent.");
    }

    @When("the chef requests restocking for {string}")
    public void theChefRequestsRestockingFor(String ingredient) {
        if (service == null) service = new SupplierNotificationService();
        if (manualRequests == null) manualRequests = new ArrayList<>();
        service.requestManualRestock(ingredient);
        manualRequests.add(ingredient);
    }

    @Then("the system should send manual restock request for {string}")
    public void theSystemShouldSendManualRestockRequestFor(String expectedIngredient) {
        List<RestockRequest> actualRequests = service.getManualRequests();
        boolean found = actualRequests.stream()
                .anyMatch(r -> r.getIngredient().equalsIgnoreCase(expectedIngredient));
        assertTrue(found, "Manual request not sent for: " + expectedIngredient);
    }
}

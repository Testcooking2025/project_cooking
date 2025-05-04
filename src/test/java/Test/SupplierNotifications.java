package Test;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class SupplierNotifications {

    static class IngredientStock {
        String name;
        int quantity;
        int threshold;

        IngredientStock(String name, int quantity, int threshold) {
            this.name = name;
            this.quantity = quantity;
            this.threshold = threshold;
        }
    }

    private final List<IngredientStock> inventory = new ArrayList<>();
    private final List<String> supplierNotifications = new ArrayList<>();
    private final List<String> manualRequests = new ArrayList<>();

    // ✅ حلّ التكرار بتغيير الجملة
    @Given("the inventory stock with threshold is:")
    public void theInventoryWithThreshold(io.cucumber.datatable.DataTable table) {
        inventory.clear();
        List<Map<String, String>> rows = table.asMaps();
        for (Map<String, String> row : rows) {
            inventory.add(new IngredientStock(
                    row.get("Ingredient"),
                    Integer.parseInt(row.get("Quantity")),
                    Integer.parseInt(row.get("Threshold"))
            ));
        }
    }

    @When("the system checks inventory levels")
    public void theSystemChecksInventoryLevels() {
        supplierNotifications.clear();
        for (IngredientStock item : inventory) {
            if (item.quantity < item.threshold) {
                supplierNotifications.add(item.name);
            }
        }
    }

    @Then("the system should notify supplier to restock:")
    public void theSystemShouldNotifySupplier(io.cucumber.datatable.DataTable table) {
        List<String> expected = table.asList();
        assertEquals(expected, supplierNotifications);
    }

    @Then("no supplier notification should be sent")
    public void noSupplierNotificationShouldBeSent() {
        assertTrue(supplierNotifications.isEmpty(), "Unexpected supplier notifications sent.");
    }

    @When("the chef requests restocking for {string}")
    public void theChefRequestsRestockingFor(String ingredient) {
        manualRequests.add(ingredient);
    }

    @Then("the system should send manual restock request for {string}")
    public void theSystemShouldSendManualRestockRequestFor(String expectedIngredient) {
        assertTrue(manualRequests.contains(expectedIngredient), "Manual request not sent for: " + expectedIngredient);
    }
}

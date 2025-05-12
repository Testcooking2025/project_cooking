package Test;

import io.cucumber.java.en.*;
import models.LowStockAlert;
import services.InventoryAlertService;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class LowInventoryAlerts {

    private final InventoryAlertService alertService = new InventoryAlertService();

    @Given("the inventory with minimum levels is:")
    public void inventoryWithMinimumLevels(io.cucumber.datatable.DataTable table) {
        for (Map<String, String> row : table.asMaps()) {
            String name = row.get("Ingredient");
            int quantity = Integer.parseInt(row.get("Quantity"));
            int minimum = Integer.parseInt(row.get("Minimum"));
            alertService.addIngredient(name, quantity, minimum);
        }
    }

    @When("the system scans inventory")
    public void theSystemScansInventory() {
        alertService.scanForLowInventory();
    }

    @Then("the chef should be alerted about:")
    public void theChefShouldBeAlertedAbout(io.cucumber.datatable.DataTable table) {
        List<String> expected = table.asList(); // [Chicken]

        List<String> actual = alertService.getLowInventoryAlerts().stream()
                .map(LowStockAlert::getIngredientName) // extract ingredient name from alert
                .toList();

        assertEquals(expected, actual, "Alert list does not match expected low inventory items.");
    }

    @Then("the chef should receive no alerts")
    public void theChefShouldReceiveNoAlerts() {
        assertTrue(alertService.getLowInventoryAlerts().isEmpty(), "Chef received unexpected alerts.");
    }
}

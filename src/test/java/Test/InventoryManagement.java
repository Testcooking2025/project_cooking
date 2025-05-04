package Test;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class InventoryManagement {

    private final Map<String, Integer> inventory = new HashMap<>();
    private final List<String> alerts = new ArrayList<>();
    private boolean allIngredientsAvailable = true;

    @Given("the inventory contains:")
    public void theInventoryContains(io.cucumber.datatable.DataTable table) {
        inventory.clear();
        for (Map<String, String> row : table.asMaps()) {
            inventory.put(row.get("Ingredient"), Integer.parseInt(row.get("Quantity")));
        }
    }

    @When("the chef checks if the kitchen has enough of:")
    public void theChefChecksAvailability(io.cucumber.datatable.DataTable table) {
        allIngredientsAvailable = true;
        alerts.clear();

        for (Map<String, String> row : table.asMaps()) {
            String ingredient = row.get("Ingredient");
            int needed = Integer.parseInt(row.get("Quantity"));
            int available = inventory.getOrDefault(ingredient, 0);

            if (available < needed) {
                allIngredientsAvailable = false;
                alerts.add("Not enough " + ingredient + " in inventory");
            }
        }
    }

    @Then("the system should confirm ingredients are available")
    public void confirmIngredientsAvailable() {
        assertTrue(allIngredientsAvailable, "Some ingredients are missing.");
    }

    @Then("the system should display inventory alert: {string}")
    public void theSystemShouldDisplayInventoryAlert(String expected) {
        assertTrue(alerts.contains(expected), "Expected alert not found: " + expected);
    }

    @When("the chef prepares a meal using:")
    public void theChefPreparesMeal(io.cucumber.datatable.DataTable table) {
        for (Map<String, String> row : table.asMaps()) {
            String ingredient = row.get("Ingredient");
            int used = Integer.parseInt(row.get("Quantity"));
            inventory.put(ingredient, inventory.getOrDefault(ingredient, 0) - used);
        }
    }

    @Then("the inventory should be updated to:")
    public void inventoryShouldBeUpdated(io.cucumber.datatable.DataTable table) {
        for (Map<String, String> row : table.asMaps()) {
            String ingredient = row.get("Ingredient");
            int expectedQty = Integer.parseInt(row.get("Quantity"));
            int actualQty = inventory.getOrDefault(ingredient, -1);
            assertEquals(expectedQty, actualQty, "Mismatch for " + ingredient);
        }
    }
}

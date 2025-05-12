package Test;

import io.cucumber.java.en.*;
import services.InventoryManager;

import static org.junit.jupiter.api.Assertions.*;
import io.cucumber.datatable.DataTable;

import java.util.*;

public class InventoryManagement {

    private final InventoryManager manager = new InventoryManager();
    private boolean allIngredientsAvailable = true;
    private List<String> alerts = new ArrayList<>();

    @Given("the inventory contains:")
    public void theInventoryContains(DataTable table) {
        manager.clearInventory();
        for (Map<String, String> row : table.asMaps()) {
            String ingredient = row.get("Ingredient");
            int quantity = Integer.parseInt(row.get("Quantity"));
            manager.addIngredient(ingredient, quantity);
        }
    }

    @When("the chef checks if the kitchen has enough of:")
    public void theChefChecksAvailability(DataTable table) {
        allIngredientsAvailable = true;
        alerts.clear();

        for (Map<String, String> row : table.asMaps()) {
            String ingredient = row.get("Ingredient");
            int needed = Integer.parseInt(row.get("Quantity"));
            if (!manager.hasEnough(ingredient, needed)) {
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
    public void theChefPreparesMeal(DataTable table) {
        for (Map<String, String> row : table.asMaps()) {
            String ingredient = row.get("Ingredient");
            int used = Integer.parseInt(row.get("Quantity"));
            manager.consumeIngredient(ingredient, used);
        }
    }

    @Then("the inventory should be updated to:")
    public void inventoryShouldBeUpdated(DataTable table) {
        for (Map<String, String> row : table.asMaps()) {
            String ingredient = row.get("Ingredient");
            int expectedQty = Integer.parseInt(row.get("Quantity"));
            int actualQty = manager.getQuantity(ingredient);
            assertEquals(expectedQty, actualQty, "Mismatch for " + ingredient);
        }
    }
}

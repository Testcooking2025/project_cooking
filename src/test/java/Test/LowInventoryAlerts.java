package Test;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class LowInventoryAlerts {

    static class InventoryItem {
        String name;
        int quantity;
        int minimum;

        InventoryItem(String name, int quantity, int minimum) {
            this.name = name;
            this.quantity = quantity;
            this.minimum = minimum;
        }
    }

    private final List<InventoryItem> inventory = new ArrayList<>();
    private final List<String> chefAlerts = new ArrayList<>();

    @Given("the inventory with minimum levels is:")
    public void inventoryWithMinimumLevels(io.cucumber.datatable.DataTable table) {
        inventory.clear();
        for (Map<String, String> row : table.asMaps()) {
            inventory.add(new InventoryItem(
                    row.get("Ingredient"),
                    Integer.parseInt(row.get("Quantity")),
                    Integer.parseInt(row.get("Minimum"))
            ));
        }
    }

    @When("the system scans inventory")
    public void theSystemScansInventory() {
        chefAlerts.clear();
        for (InventoryItem item : inventory) {
            if (item.quantity < item.minimum) {
                chefAlerts.add(item.name);
            }
        }
    }

    @Then("the chef should be alerted about:")
    public void theChefShouldBeAlertedAbout(io.cucumber.datatable.DataTable table) {
        List<String> expected = table.asList();
        assertEquals(expected, chefAlerts, "Alert list does not match expected low inventory items.");
    }

    @Then("the chef should receive no alerts")
    public void theChefShouldReceiveNoAlerts() {
        assertTrue(chefAlerts.isEmpty(), "Chef received unexpected alerts.");
    }
}

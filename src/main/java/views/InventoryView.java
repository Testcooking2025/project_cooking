package views;

import models.InventoryItem;

import java.util.List;

/**
 * Displays the current status of inventory.
 */
public class InventoryView {

    public void displayInventory(List<InventoryItem> items) {
        System.out.println("\n--- Inventory Status ---");
        for (InventoryItem item : items) {
            System.out.printf("%s - Quantity: %d%n", item.getName(), item.getQuantity());
        }
    }
}

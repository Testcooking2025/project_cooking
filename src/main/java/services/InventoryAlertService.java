package services;

import models.LowStockAlert;

import java.util.*;

/**
 * Service for monitoring inventory levels and generating low stock alerts.
 */
public class InventoryAlertService {

    // Internal representation of inventory items
    private static class InventoryItem {
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
    private final List<LowStockAlert> lowInventoryAlerts = new ArrayList<>();

    /**
     * Adds an item to the inventory with its quantity and minimum threshold.
     * Used internally or in production logic.
     */
    public void addInventoryItem(String name, int quantity, int minimum) {
        inventory.add(new InventoryItem(name, quantity, minimum));
    }

    /**
     * Alias for test compatibility with step definition wording.
     */
    public void addIngredient(String name, int quantity, int minimum) {
        addInventoryItem(name, quantity, minimum);
    }

    /**
     * Scans all items and generates alerts for items below their minimum.
     */
    public void scanInventory() {
        lowInventoryAlerts.clear();
        for (InventoryItem item : inventory) {
            if (item.quantity < item.minimum) {
                lowInventoryAlerts.add(new LowStockAlert(item.name, item.quantity, item.minimum));
            }
        }
    }

    /**
     * Alias for test compatibility with step definition wording.
     */
    public void scanForLowInventory() {
        scanInventory();
    }

    /**
     * Returns human-readable low-stock alert messages.
     */
    public List<String> getLowInventoryMessages() {
        List<String> messages = new ArrayList<>();
        for (LowStockAlert alert : lowInventoryAlerts) {
            messages.add(alert.toString());
        }
        return messages;
    }

    /**
     * Returns structured alert objects for test validation.
     */
    public List<LowStockAlert> getLowInventoryAlerts() {
        return new ArrayList<>(lowInventoryAlerts);
    }
}

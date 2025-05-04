package services;

import models.InventoryItem;

import java.util.*;

/**
 * Service for managing kitchen inventory, including loading stock data,
 * checking ingredient availability, and updating quantities.
 */
public class InventoryManager {

    private final Map<String, InventoryItem> inventory = new HashMap<>();
    private final List<String> alerts = new ArrayList<>();

    /**
     * Loads initial inventory data into the system.
     *
     * @param initialData A map of ingredient names to their quantities.
     */
    public void loadInventory(Map<String, Integer> initialData) {
        inventory.clear();
        for (Map.Entry<String, Integer> entry : initialData.entrySet()) {
            inventory.put(entry.getKey(), new InventoryItem(entry.getKey(), entry.getValue()));
        }
    }

    /**
     * Checks if the requested ingredients are available in sufficient quantities.
     * If not, appropriate alerts are recorded.
     *
     * @param requested A map of ingredient names to required quantities.
     * @return True if all requested ingredients are available; false otherwise.
     */
    public boolean checkAvailability(Map<String, Integer> requested) {
        alerts.clear();
        boolean allAvailable = true;

        for (Map.Entry<String, Integer> entry : requested.entrySet()) {
            String name = entry.getKey();
            int needed = entry.getValue();
            InventoryItem item = inventory.get(name);
            int available = item != null ? item.getQuantity() : 0;

            if (available < needed) {
                alerts.add("Not enough " + name + " in inventory");
                allAvailable = false;
            }
        }
        return allAvailable;
    }

    /**
     * Deducts the used amount of ingredients from the inventory.
     *
     * @param used A map of ingredient names to the quantities used.
     */
    public void useIngredients(Map<String, Integer> used) {
        for (Map.Entry<String, Integer> entry : used.entrySet()) {
            String name = entry.getKey();
            int amount = entry.getValue();
            InventoryItem item = inventory.get(name);
            if (item != null) {
                item.deduct(amount);
            }
        }
    }

    /**
     * Returns a snapshot of the current inventory levels.
     *
     * @return A map of ingredient names to their current quantities.
     */
    public Map<String, Integer> getInventorySnapshot() {
        Map<String, Integer> snapshot = new HashMap<>();
        for (InventoryItem item : inventory.values()) {
            snapshot.put(item.getName(), item.getQuantity());
        }
        return snapshot;
    }

    /**
     * Returns the list of alerts generated during availability checks.
     *
     * @return A list of alert messages.
     */
    public List<String> getAlerts() {
        return alerts;
    }
}

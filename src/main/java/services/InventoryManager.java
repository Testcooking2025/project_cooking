package services;

import models.InventoryItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages the kitchen inventory of ingredients and their quantities using InventoryItem model.
 */
public class InventoryManager {

    private final Map<String, InventoryItem> inventory = new HashMap<>();

    /**
     * Clears all items from the inventory.
     */
    public void clearInventory() {
        inventory.clear();
    }

    /**
     * Adds a new ingredient or updates its quantity in the inventory.
     *
     * @param name     The name of the ingredient.
     * @param quantity The amount to set.
     */
    public void addIngredient(String name, int quantity) {
        inventory.put(name, new InventoryItem(name, quantity));
    }

    /**
     * Checks if the inventory has enough of the given ingredient.
     *
     * @param name     The name of the ingredient.
     * @param required The amount needed.
     * @return true if available in sufficient quantity, false otherwise.
     */
    public boolean hasEnough(String name, int required) {
        return inventory.containsKey(name) && inventory.get(name).getQuantity() >= required;
    }

    /**
     * Decreases the quantity of the specified ingredient after usage.
     *
     * @param name The name of the ingredient.
     * @param used The amount to deduct.
     */
    public void consumeIngredient(String name, int used) {
        if (inventory.containsKey(name)) {
            inventory.get(name).deduct(used);
        }
    }

    /**
     * Gets the current quantity of a specific ingredient.
     *
     * @param name The name of the ingredient.
     * @return Quantity available, or 0 if not found.
     */
    public int getQuantity(String name) {
        return inventory.containsKey(name) ? inventory.get(name).getQuantity() : 0;
    }

    /**
     * Returns the full inventory map.
     *
     * @return A map of all ingredients and their InventoryItem objects.
     */
    public Map<String, InventoryItem> getInventorySnapshot() {
        return new HashMap<>(inventory);
    }
}

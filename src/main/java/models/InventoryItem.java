package models;

/**
 * Represents an inventory item with a name and quantity.
 * Supports operations such as deduction and restocking.
 */
public class InventoryItem {

    private final String name;
    private int quantity;

    /**
     * Constructs an inventory item with the specified name and quantity.
     *
     * @param name     The name of the ingredient (e.g., "Chicken").
     * @param quantity The current quantity in stock.
     */
    public InventoryItem(String name, int quantity) {
        this.name = name.trim();
        this.quantity = quantity;
    }

    /**
     * Returns the name of the inventory item.
     *
     * @return The name of the item.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the current quantity in stock.
     *
     * @return The available quantity.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Deducts a specified amount from the stock.
     * Ensures quantity does not go below zero.
     *
     * @param amount The amount to deduct.
     */
    public void deduct(int amount) {
        quantity = Math.max(0, quantity - amount);
    }

    /**
     * Adds a specified amount to the stock.
     *
     * @param amount The amount to restock.
     */
    public void restock(int amount) {
        quantity += amount;
    }

    /**
     * Returns a string representation of the inventory item.
     *
     * @return A string like "Chicken: 2"
     */
    @Override
    public String toString() {
        return name + ": " + quantity;
    }
}

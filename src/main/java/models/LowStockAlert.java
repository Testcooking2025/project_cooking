package models;

/**
 * Represents an alert triggered when an ingredient's stock falls below a defined threshold.
 */
public class LowStockAlert {

    private final String ingredientName;

    /**
     * Constructs a low stock alert for the specified ingredient.
     *
     * @param ingredientName The name of the ingredient that is low in stock.
     */
    public LowStockAlert(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    /**
     * Returns the name of the ingredient associated with this alert.
     *
     * @return The ingredient name.
     */
    public String getIngredientName() {
        return ingredientName;
    }

    /**
     * Returns a readable description of the alert.
     *
     * @return String representing the alert message.
     */
    @Override
    public String toString() {
        return "Low stock alert for " + ingredientName;
    }
}

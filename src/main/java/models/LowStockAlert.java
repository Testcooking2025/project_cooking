package models;

/**
 * Represents a low stock alert for a specific ingredient.
 */
public class LowStockAlert {

    private final String ingredientName;
    private final int currentQuantity;
    private final int minimumRequired;

    public LowStockAlert(String ingredientName, int currentQuantity, int minimumRequired) {
        this.ingredientName = ingredientName;
        this.currentQuantity = currentQuantity;
        this.minimumRequired = minimumRequired;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public int getMinimumRequired() {
        return minimumRequired;
    }

    @Override
    public String toString() {
        return "LowStockAlert: " + ingredientName +
                " is below minimum. Current: " + currentQuantity +
                ", Minimum: " + minimumRequired;
    }
}

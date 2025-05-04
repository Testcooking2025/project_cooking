package models;

/**
 * Represents a restocking request for a specific ingredient.
 * The request can be either manual or automatic.
 */
public class RestockRequest {

    private final String ingredientName;
    private final boolean manual;

    /**
     * Constructs a new restocking request for the specified ingredient.
     *
     * @param ingredientName The name of the ingredient to be restocked.
     * @param manual         True if the restocking request is manual; false if it is automatic.
     */
    public RestockRequest(String ingredientName, boolean manual) {
        this.ingredientName = ingredientName;
        this.manual = manual;
    }

    /**
     * Returns the name of the ingredient associated with the restocking request.
     restocking
     * @return The ingredient name.
     */
    public String getIngredientName() {
        return ingredientName;
    }

    /**
     * Indicates whether the restocking request was made manually.
     *
     * @return True if the request is manual, false if automatic.
     */
    public boolean isManual() {
        return manual;
    }

    /**
     * Returns a readable description of the restocking request.
     *
     * @return A string representing the request type and ingredient.
     */
    @Override
    public String toString() {
        return (manual ? "Manual" : "Auto") + " restock request for " + ingredientName;
    }
}

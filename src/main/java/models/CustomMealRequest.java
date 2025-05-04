package models;

import java.util.List;

/**
 * Represents a custom meal request made by a customer.
 * The request includes a list of selected ingredients.
 */
public class CustomMealRequest {

    private final List<Ingredient> selectedIngredients;

    /**
     * Constructs a custom meal request with the provided list of ingredients.
     *
     * @param selectedIngredients A list of ingredients chosen by the customer.
     */
    public CustomMealRequest(List<Ingredient> selectedIngredients) {
        this.selectedIngredients = selectedIngredients;
    }

    /**
     * Returns the list of ingredients selected in this custom meal request.
     *
     * @return The list of selected ingredients.
     */
    public List<Ingredient> getSelectedIngredients() {
        return selectedIngredients;
    }
}

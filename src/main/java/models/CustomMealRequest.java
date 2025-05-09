package models;

import java.util.List;

public class CustomMealRequest {
    private final String customer;
    private final String mealName;
    private final List<Ingredient> selectedIngredients;

    public CustomMealRequest(String customer, String mealName, List<Ingredient> selectedIngredients) {
        this.customer = customer;
        this.mealName = mealName;
        this.selectedIngredients = selectedIngredients;
    }

    public String getCustomer() {
        return customer;
    }

    public String getMealName() {
        return mealName;
    }

    public List<Ingredient> getSelectedIngredients() {
        return selectedIngredients;
    }
}

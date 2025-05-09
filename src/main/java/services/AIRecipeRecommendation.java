package services;

import java.util.*;

public class AIRecipeRecommendation {

    public List<String> recommendRecipes(String customerName) {
        // Mocked preferences for demonstration purposes
        String preference = customerName.equalsIgnoreCase("Ali") ? "Vegan" : "General";
        List<String> availableIngredients = Arrays.asList("Tomato", "Basil", "Pasta", "Rice", "Tofu");

        List<String> recommendations = new ArrayList<>();

        if (preference.equalsIgnoreCase("Vegan")) {
            if (availableIngredients.contains("Tofu")) {
                recommendations.add("Grilled Tofu with Rice");
            }
            if (availableIngredients.contains("Tomato") && availableIngredients.contains("Pasta")) {
                recommendations.add("Vegan Spaghetti with Tomato Sauce");
            }
        } else {
            if (availableIngredients.contains("Chicken")) {
                recommendations.add("Chicken and Rice Bowl");
            }
            if (availableIngredients.contains("Tomato")) {
                recommendations.add("Tomato Basil Pasta");
            }
        }

        if (recommendations.isEmpty()) {
            recommendations.add("Mixed Vegetable Salad"); // default fallback
        }

        return recommendations;
    }
}

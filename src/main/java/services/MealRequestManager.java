package services;

import models.CustomMealRequest;
import models.Ingredient;

import java.util.*;

/**
 * Service that manages custom meal creation requests.
 * It validates ingredient availability and incompatibility rules.
 */
public class MealRequestManager {

    private final Set<Ingredient> availableIngredients = new HashSet<>();
    private final Map<Ingredient, Set<Ingredient>> incompatibilityMap = new HashMap<>();

    /**
     * Sets the list of currently available ingredients in the system.
     *
     * @param ingredientNames List of ingredient names available in the kitchen.
     */
    public void setAvailableIngredients(List<String> ingredientNames) {
        availableIngredients.clear();
        for (String name : ingredientNames) {
            availableIngredients.add(new Ingredient(name));
        }
    }

    /**
     * Defines incompatibility rules between ingredients.
     * For example, Fish and Cheese may not be allowed together.
     *
     * @param rules A list of ingredient name pairs that cannot be used together.
     */
    public void defineIncompatibilities(List<List<String>> rules) {
        incompatibilityMap.clear();
        for (List<String> pair : rules) {
            Ingredient a = new Ingredient(pair.get(0));
            Ingredient b = new Ingredient(pair.get(1));

            incompatibilityMap.computeIfAbsent(a, k -> new HashSet<>()).add(b);
            incompatibilityMap.computeIfAbsent(b, k -> new HashSet<>()).add(a);
        }
    }

    /**
     * Validates the selected ingredients of a custom meal.
     * It checks for availability and any defined incompatibility rules.
     *
     * @param request The {@link CustomMealRequest} to validate.
     * @return A {@link ValidationResult} containing acceptance status and messages.
     */
    public ValidationResult validateCustomMeal(CustomMealRequest request) {
        List<String> errors = new ArrayList<>();
        List<Ingredient> selected = request.getSelectedIngredients();

        // Check availability
        for (Ingredient ing : selected) {
            if (!availableIngredients.contains(ing)) {
                errors.add(ing.getName() + " is not available");
            }
        }

        // Check incompatibility
        for (int i = 0; i < selected.size(); i++) {
            for (int j = i + 1; j < selected.size(); j++) {
                Ingredient ing1 = selected.get(i);
                Ingredient ing2 = selected.get(j);
                if (incompatibilityMap.getOrDefault(ing1, Set.of()).contains(ing2)) {
                    errors.add(ing1.getName() + " and " + ing2.getName() + " cannot be combined");
                }
            }
        }

        return new ValidationResult(errors.isEmpty(), errors);
    }

    /**
     * Represents the result of a custom meal validation.
     * Includes acceptance flag and list of validation error messages (if any).
     */
    public static class ValidationResult {
        private final boolean accepted;
        private final List<String> messages;

        /**
         * Constructor.
         *
         * @param accepted True if the meal is valid, false otherwise.
         * @param messages List of error or confirmation messages.
         */
        public ValidationResult(boolean accepted, List<String> messages) {
            this.accepted = accepted;
            this.messages = messages;
        }

        /**
         * @return True if the custom meal is accepted.
         */
        public boolean isAccepted() {
            return accepted;
        }

        /**
         * @return List of validation messages.
         */
        public List<String> getMessages() {
            return messages;
        }
    }
}

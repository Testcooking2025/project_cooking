package models;

import java.util.*;

public class CustomMealRequest {

    private final Set<String> availableIngredients = new HashSet<>();
    private final List<String> selectedIngredients = new ArrayList<>();
    private final Map<String, Set<String>> incompatibilityRules = new HashMap<>();
    private final List<String> validationMessages = new ArrayList<>();
    private boolean accepted = true;

    public void setAvailableIngredients(Set<String> ingredients) {
        availableIngredients.clear();
        availableIngredients.addAll(ingredients);
    }

    public void addIngredient(String ingredient) {
        selectedIngredients.add(ingredient);
    }

    public void clearSelectedIngredients() {
        selectedIngredients.clear();
        validationMessages.clear();
        accepted = true;
    }

    public void addIncompatibilityRule(String ing1, String ing2) {
        incompatibilityRules.computeIfAbsent(ing1, k -> new HashSet<>()).add(ing2);
        incompatibilityRules.computeIfAbsent(ing2, k -> new HashSet<>()).add(ing1);
    }

    // ✅ تسمح بتمرير جميع القواعد من خدمة خارجية مثل MealRequestManager
    public void setIncompatibilityRules(Map<String, Set<String>> rules) {
        incompatibilityRules.clear();
        for (Map.Entry<String, Set<String>> entry : rules.entrySet()) {
            incompatibilityRules.put(entry.getKey(), new HashSet<>(entry.getValue()));
        }
    }

    public void validate() {
        accepted = true;
        validationMessages.clear();

        // Check availability
        for (String ingredient : selectedIngredients) {
            if (!availableIngredients.contains(ingredient)) {
                validationMessages.add(ingredient + " is not available");
                accepted = false;
            }
        }

        // Check incompatibilities
        for (int i = 0; i < selectedIngredients.size(); i++) {
            for (int j = i + 1; j < selectedIngredients.size(); j++) {
                String ing1 = selectedIngredients.get(i);
                String ing2 = selectedIngredients.get(j);

                if (incompatibilityRules.containsKey(ing1) &&
                        incompatibilityRules.get(ing1).contains(ing2)) {
                    validationMessages.add(ing1 + " and " + ing2 + " cannot be combined");
                    accepted = false;
                }
            }
        }
    }

    public boolean isAccepted() {
        return accepted;
    }

    public List<String> getValidationMessages() {
        return new ArrayList<>(validationMessages);
    }

    public Map<String, Set<String>> getIncompatibilityMap() {
        return incompatibilityRules;
    }
}

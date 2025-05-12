package services;

import models.CustomMealRequest;

import java.util.*;

/**
 * Manages all custom meal requests and global incompatibility rules.
 */
public class MealRequestManager {

    private final List<CustomMealRequest> storedRequests = new ArrayList<>();
    private final Map<String, Set<String>> incompatibilityRules = new HashMap<>();

    /**
     * Stores a validated and accepted meal request.
     *
     * @param request A validated CustomMealRequest.
     */
    public void submitRequest(CustomMealRequest request) {
        storedRequests.add(request);
    }

    /**
     * Returns a copy of all stored requests.
     */
    public List<CustomMealRequest> getAllRequests() {
        return new ArrayList<>(storedRequests);
    }

    /**
     * Adds a bidirectional incompatibility rule between two ingredients.
     *
     * @param ing1 First ingredient.
     * @param ing2 Second ingredient.
     */
    public void addIncompatibilityRule(String ing1, String ing2) {
        incompatibilityRules.computeIfAbsent(ing1, k -> new HashSet<>()).add(ing2);
        incompatibilityRules.computeIfAbsent(ing2, k -> new HashSet<>()).add(ing1);
    }

    /**
     * Returns all defined incompatibility rules.
     *
     * @return Map of ingredient to set of incompatible ingredients.
     */
    public Map<String, Set<String>> getAllIncompatibilityRules() {
        return incompatibilityRules;
    }
}

package services;

import models.RestockRequest;

import java.util.*;

/**
 * Service for managing restocking logic and supplier notifications.
 * It supports both automatic and manual restock requests based on inventory levels.
 */
public class SupplierNotificationService {

    private final List<RestockRequest> restockRequests = new ArrayList<>();

    /**
     * Scans the inventory data and creates automatic restock requests
     * for any item whose quantity is below the defined threshold.
     *
     * @param inventoryData A list of ingredient entries with "Ingredient", "Quantity", and "Threshold".
     */
    public void checkInventoryLevelsWithThreshold(List<Map<String, String>> inventoryData) {
        restockRequests.clear();
        for (Map<String, String> row : inventoryData) {
            String name = row.get("Ingredient");
            int quantity = Integer.parseInt(row.get("Quantity"));
            int threshold = Integer.parseInt(row.get("Threshold"));

            if (quantity < threshold) {
                restockRequests.add(new RestockRequest(name, false));
            }
        }
    }

    /**
     * Adds a manual restock request for the given ingredient.
     *
     * @param ingredientName The name of the ingredient to restock manually.
     */
    public void requestManualRestock(String ingredientName) {
        restockRequests.add(new RestockRequest(ingredientName, true));
    }

    /**
     * Retrieves a list of ingredients that triggered automatic restock requests.
     *
     * @return A list of ingredient names that need automatic restocking.
     */
    public List<String> getAutoRestockIngredients() {
        List<String> result = new ArrayList<>();
        for (RestockRequest request : restockRequests) {
            if (!request.isManual()) {
                result.add(request.getIngredientName());
            }
        }
        return result;
    }

    /**
     * Checks if a manual restock request has been sent for a specific ingredient.
     *
     * @param ingredient The ingredient name to check.
     * @return True if a manual request was sent, false otherwise.
     */
    public boolean wasManualRequestSent(String ingredient) {
        return restockRequests.stream()
                .anyMatch(r -> r.isManual() && r.getIngredientName().equalsIgnoreCase(ingredient));
    }

    /**
     * Clears all stored restock requests (both manual and automatic).
     */
    public void clearRequests() {
        restockRequests.clear();
    }
}

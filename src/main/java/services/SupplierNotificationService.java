package services;

import models.RestockRequest;

import java.util.*;

/**
 * Service responsible for checking inventory levels and notifying suppliers.
 */
public class SupplierNotificationService {

    private static class Ingredient {
        String name;
        int quantity;
        int threshold;

        Ingredient(String name, int quantity, int threshold) {
            this.name = name;
            this.quantity = quantity;
            this.threshold = threshold;
        }
    }

    private final List<Ingredient> inventory = new ArrayList<>();
    private final List<String> lowStockNotifications = new ArrayList<>();
    private final List<RestockRequest> manualRequests = new ArrayList<>();

    public void addIngredient(String name, int quantity, int threshold) {
        inventory.add(new Ingredient(name, quantity, threshold));
    }

    public void checkStockLevels() {
        lowStockNotifications.clear();
        for (Ingredient item : inventory) {
            if (item.quantity < item.threshold) {
                lowStockNotifications.add(item.name);
            }
        }
    }

    public List<String> getLowStockNotifications() {
        return new ArrayList<>(lowStockNotifications);
    }

    /**
     * Records a manual restock request using a RestockRequest object.
     */
    public void requestManualRestock(String ingredient) {
        manualRequests.add(new RestockRequest(ingredient));
    }

    /**
     * Returns a list of RestockRequest objects.
     */
    public List<RestockRequest> getManualRequests() {
        return new ArrayList<>(manualRequests);
    }

    /**
     * Returns just the ingredient names from the restock requests.
     */
    public List<String> getManualRequestIngredients() {
        List<String> names = new ArrayList<>();
        for (RestockRequest request : manualRequests) {
            names.add(request.getIngredient());
        }
        return names;
    }
}

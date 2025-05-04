package services;

import models.LowStockAlert;

import java.util.*;

/**
 * Service responsible for scanning inventory levels and generating alerts
 * for ingredients that fall below their minimum thresholds.
 */
public class InventoryAlertService {

    /**
     * Scans the inventory and returns a list of alerts for ingredients
     * that have quantities below their defined minimums.
     *
     * @param inventoryData A list of maps where each map represents an ingredient
     *                      with keys: "Ingredient", "Quantity", "Minimum".
     * @return A list of {@link LowStockAlert} objects for low inventory items.
     */
    public List<LowStockAlert> scanForLowInventory(List<Map<String, String>> inventoryData) {
        List<LowStockAlert> alerts = new ArrayList<>();

        for (Map<String, String> row : inventoryData) {
            String name = row.get("Ingredient");
            int quantity = Integer.parseInt(row.get("Quantity"));
            int minimum = Integer.parseInt(row.get("Minimum"));

            if (quantity < minimum) {
                alerts.add(new LowStockAlert(name));
            }
        }

        return alerts;
    }

    /**
     * Extracts the ingredient names from a list of alerts.
     *
     * @param alerts A list of {@link LowStockAlert} objects.
     * @return A list of ingredient names that triggered alerts.
     */
    public List<String> extractAlertNames(List<LowStockAlert> alerts) {
        List<String> names = new ArrayList<>();
        for (LowStockAlert alert : alerts) {
            names.add(alert.getIngredientName());
        }
        return names;
    }
}

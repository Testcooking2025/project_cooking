package services;

import models.OrderHistory;

import java.util.*;

/**
 * Manages order history for customers and supports tracking and analysis.
 */
public class OrderHistoryManager {

    private final Map<String, OrderHistory> historyMap = new HashMap<>();

    /**
     * Adds an order (meal) to a customer's history.
     *
     * @param customer Name of the customer.
     * @param meal     Name of the ordered meal.
     */
    public void addOrder(String customer, String meal) {
        OrderHistory history = historyMap.getOrDefault(customer, new OrderHistory(customer));
        history.addMeal(meal);
        historyMap.put(customer, history);
    }

    /**
     * Retrieves the order history for a customer.
     *
     * @param customer Name of the customer.
     * @return List of meals (can be empty).
     */
    public List<String> getOrderHistory(String customer) {
        return historyMap.containsKey(customer)
                ? historyMap.get(customer).getMeals()
                : Collections.emptyList();
    }

    /**
     * Retrieves a map of customer names to their total number of orders.
     *
     * @return Map of customer to order count.
     */
    public Map<String, Integer> getOrderStatistics() {
        Map<String, Integer> stats = new HashMap<>();
        for (Map.Entry<String, OrderHistory> entry : historyMap.entrySet()) {
            stats.put(entry.getKey(), entry.getValue().getOrderCount());
        }
        return stats;
    }

    /**
     * Clears all order history (used in testing).
     */
    public void clear() {
        historyMap.clear();
    }
}

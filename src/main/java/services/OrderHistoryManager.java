package services;

import models.OrderHistory;

import java.util.*;

/**
 * Service for managing customer order history.
 * Provides functionality to record orders, retrieve history,
 * suggest meal plans, and compute order statistics.
 */
public class OrderHistoryManager {

    private final Map<String, OrderHistory> historyMap = new HashMap<>();

    /**
     * Records a single meal order for a specific customer.
     *
     * @param customer The name of the customer.
     * @param meal     The name of the meal ordered.
     */
    public void recordOrder(String customer, String meal) {
        historyMap.computeIfAbsent(customer, OrderHistory::new).addOrder(meal);
    }

    /**
     * Records multiple meal orders for a customer in bulk.
     *
     * @param customer The name of the customer.
     * @param meals    A list of meal names ordered.
     */
    public void recordBulkOrders(String customer, List<String> meals) {
        historyMap.putIfAbsent(customer, new OrderHistory(customer));
        meals.forEach(meal -> historyMap.get(customer).addOrder(meal));
    }

    /**
     * Retrieves the list of past meals ordered by the specified customer.
     *
     * @param customer The name of the customer.
     * @return A list of past meals ordered.
     */
    public List<String> getPastOrders(String customer) {
        return historyMap.getOrDefault(customer, new OrderHistory(customer)).getPastOrders();
    }

    /**
     * Suggests a meal plan based on a customer's past orders.
     * Currently, returns the same meals from the customer's order history.
     *
     * @param customer The name of the customer.
     * @return A list of suggested meals.
     */
    public List<String> suggestMealPlan(String customer) {
        return getPastOrders(customer);
    }

    /**
     * Calculates the total number of orders per customer.
     *
     * @return A map of customer names to their respective number of orders.
     */
    public Map<String, Integer> getOrderStatistics() {
        Map<String, Integer> stats = new HashMap<>();
        for (OrderHistory history : historyMap.values()) {
            stats.put(history.getCustomerName(), history.getOrderCount());
        }
        return stats;
    }
}

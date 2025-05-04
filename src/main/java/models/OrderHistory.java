package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the order history of a specific customer,
 * including all previously placed meals.
 */
public class OrderHistory {

    private final String customerName;
    private final List<String> pastOrders;

    /**
     * Constructs an order history for the given customer.
     *
     * @param customerName The name of the customer.
     */
    public OrderHistory(String customerName) {
        this.customerName = customerName;
        this.pastOrders = new ArrayList<>();
    }

    /**
     * Adds a new meal for the customer's past orders.
     *
     * @param mealName The name of the meal ordered.
     */
    public void addOrder(String mealName) {
        pastOrders.add(mealName);
    }

    /**
     * Returns the list of all past orders for the customer.
     *
     * @return A list of meal names.
     */
    public List<String> getPastOrders() {
        return pastOrders;
    }

    /**
     * Returns the name of the customer associated with this order history.
     *
     * @return The customer's name.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Returns the number of past orders.
     *
     * @return The total number of orders placed by the customer.
     */
    public int getOrderCount() {
        return pastOrders.size();
    }
}

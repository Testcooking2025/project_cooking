package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the order history of a customer, including past meals.
 */
public class OrderHistory {

    private final String customerName;
    private final List<String> meals = new ArrayList<>();

    public OrderHistory(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void addMeal(String meal) {
        meals.add(meal);
    }

    public List<String> getMeals() {
        return new ArrayList<>(meals);
    }

    public int getOrderCount() {
        return meals.size();
    }
}

package Test;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class OrderHistory {

    private final Map<String, List<String>> orderHistory = new HashMap<>();
    private List<String> viewedOrders = new ArrayList<>();
    private List<String> suggestedMeals = new ArrayList<>();
    private final Map<String, Integer> orderStats = new HashMap<>();
    private String currentCustomer;

    // ========== Scenario 1: Customer views past orders ==========
    @Given("a customer named {string} has placed the following orders:")
    public void aCustomerNamedHasPlacedTheFollowingOrders(String customerName, io.cucumber.datatable.DataTable dataTable) {
        currentCustomer = customerName;
        List<String> orders = dataTable.asList();
        orderHistory.put(customerName, new ArrayList<>(orders));
    }

    @When("the customer views their past orders")
    public void theCustomerViewsTheirPastOrders() {
        viewedOrders = orderHistory.getOrDefault(currentCustomer, new ArrayList<>());
    }

    @Then("the system should show the following orders:")
    public void theSystemShouldShowTheFollowingOrders(io.cucumber.datatable.DataTable expectedTable) {
        List<String> expected = expectedTable.asList();
        assertEquals(expected, viewedOrders);
    }

    // ========== Scenario 2: Chef suggests meal plan ==========
    @When("the chef requests a personalized meal plan")
    public void theChefRequestsAPersonalizedMealPlan() {
        suggestedMeals = new ArrayList<>(orderHistory.getOrDefault(currentCustomer, new ArrayList<>()));
    }

    @Then("the system should suggest meals similar to:")
    public void theSystemShouldSuggestMealsSimilarTo(io.cucumber.datatable.DataTable expectedTable) {
        List<String> expected = expectedTable.asList();
        assertEquals(expected, suggestedMeals);
    }

    // ========== Scenario 3: Admin analyzes order trends ==========
    @Given("the system stores the following customer orders:")
    public void theSystemStoresTheFollowingCustomerOrders(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            String customer = row.get("Customer");
            String order = row.get("Order");
            orderHistory.computeIfAbsent(customer, k -> new ArrayList<>()).add(order);
        }
    }

    @When("the admin analyzes order trends")
    public void theAdminAnalyzesOrderTrends() {
        orderStats.clear();
        for (Map.Entry<String, List<String>> entry : orderHistory.entrySet()) {
            orderStats.put(entry.getKey(), entry.getValue().size());
        }
    }

    @Then("the system should provide statistics including:")
    public void theSystemShouldProvideStatisticsIncluding(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> expected = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : expected) {
            String customer = row.get("Customer");
            int expectedCount = Integer.parseInt(row.get("Number of Orders"));
            assertEquals(expectedCount, orderStats.getOrDefault(customer, 0));
        }
    }
}

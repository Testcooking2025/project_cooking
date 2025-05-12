package Test;

import io.cucumber.java.en.*;
import models.CustomerProfile;
import services.CustomerProfileManager;
import services.OrderHistoryManager;

import static org.junit.jupiter.api.Assertions.*;
import io.cucumber.datatable.DataTable;

import java.util.*;

public class OrderHistory {

    private final OrderHistoryManager historyManager = new OrderHistoryManager();
    private final CustomerProfileManager profileManager = new CustomerProfileManager();
    private String currentCustomer;
    private List<String> viewedOrders = new ArrayList<>();
    private List<String> suggestedMeals = new ArrayList<>();
    private Map<String, Integer> computedStats = new HashMap<>();

    // ========== Scenario 1: Customer views past orders ==========
    @Given("a customer named {string} has placed the following orders:")
    public void aCustomerNamedHasPlacedTheFollowingOrders(String customerName, DataTable dataTable) {
        currentCustomer = customerName;
        for (String order : dataTable.asList()) {
            historyManager.addOrder(customerName, order);
        }
    }

    @When("the customer views their past orders")
    public void theCustomerViewsTheirPastOrders() {
        viewedOrders = historyManager.getOrderHistory(currentCustomer);
    }

    @Then("the system should show the following orders:")
    public void theSystemShouldShowTheFollowingOrders(DataTable expectedTable) {
        List<String> expected = expectedTable.asList();
        assertEquals(expected, viewedOrders);
    }

    // ========== Scenario 2: Chef suggests meal plan ==========
    @When("the chef requests a personalized meal plan")
    public void theChefRequestsAPersonalizedMealPlan() {
        suggestedMeals = historyManager.getOrderHistory(currentCustomer);
    }

    @Then("the system should suggest meals similar to:")
    public void theSystemShouldSuggestMealsSimilarTo(DataTable expectedTable) {
        List<String> expected = expectedTable.asList();
        assertEquals(expected, suggestedMeals);
    }

    // ========== Scenario 3: Admin analyzes order trends ==========
    @Given("the system stores the following customer orders:")
    public void theSystemStoresTheFollowingCustomerOrders(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            String customer = row.get("Customer");
            String order = row.get("Order");
            historyManager.addOrder(customer, order);
        }
    }

    @When("the admin analyzes order trends")
    public void theAdminAnalyzesOrderTrends() {
        computedStats = historyManager.getOrderStatistics();
    }

    @Then("the system should provide statistics including:")
    public void theSystemShouldProvideStatisticsIncluding(DataTable dataTable) {
        List<Map<String, String>> expected = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : expected) {
            String customer = row.get("Customer");
            int count = Integer.parseInt(row.get("Number of Orders")); // ✅ التصحيح هنا
            assertEquals(count, computedStats.getOrDefault(customer, 0));
        }
    }

    // ========== Scenario 4: Chef views dietary info ==========
    @Given("a customer placed an order")
    public void aCustomerPlacedAnOrder() {
        currentCustomer = "Ali";
        CustomerProfile profile = new CustomerProfile(currentCustomer);
        profile.addDietaryPreference("Vegan");
        profile.addAllergy("Peanuts");
        profileManager.addProfile(profile);
        historyManager.addOrder(currentCustomer, "Vegan Bowl");
    }

    @When("the chef views the order details")
    public void theChefViewsTheOrderDetails() {
        // No logic needed: state already prepared
    }

    @Then("the system should display the customer's dietary preferences and allergies")
    public void theSystemShouldDisplayTheCustomerSDietaryPreferencesAndAllergies() {
        CustomerProfile profile = profileManager.getProfile(currentCustomer);
        assertNotNull(profile, "Profile not found for customer");
        assertTrue(profile.getDietaryPreferences().contains("Vegan"), "Missing dietary preference: Vegan");
        assertTrue(profile.getAllergies().contains("Peanuts"), "Missing allergy: Peanuts");
    }
}

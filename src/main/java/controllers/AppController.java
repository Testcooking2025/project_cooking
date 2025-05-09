package controllers;

import services.*;
import views.ConsoleView;

import java.util.*;

public class AppController {

    private final CustomerProfileManager customerProfileManager = new CustomerProfileManager();
    private final IngredientSubstitutionEngine substitutionEngine = new IngredientSubstitutionEngine();
    private final InventoryAlertService inventoryAlertService = new InventoryAlertService();
    private final InventoryManager inventoryManager = new InventoryManager();
    private final InvoiceManager invoiceManager = new InvoiceManager();
    private final KitchenTaskManager kitchenTaskManager = new KitchenTaskManager();
    private final MealRequestManager mealRequestManager = new MealRequestManager();
    private final OrderHistoryManager orderHistoryManager = new OrderHistoryManager();
    private final SupplierNotificationService supplierNotificationService = new SupplierNotificationService();
    private final AIRecipeRecommendation aiRecipeRecommendation = new AIRecipeRecommendation();
    private final ConsoleView console = new ConsoleView();

    // Initialize demo data for testing purposes
    public void setupDemoData() {
        customerProfileManager.createCustomer("Ali");
        customerProfileManager.addDietaryPreference("Ali", "Vegan");
        customerProfileManager.addAllergy("Ali", "Peanuts");

        invoiceManager.createInvoice("Ali", 30.0);

        Map<String, Integer> stock = Map.of("Chicken", 2, "Rice", 10);
        inventoryManager.loadInventory(stock);

        List<Map<String, String>> invData = new ArrayList<>();
        invData.add(Map.of("Ingredient", "Chicken", "Quantity", "2", "Minimum", "5"));
        inventoryAlertService.scanForLowInventory(invData);

        orderHistoryManager.recordOrder("Ali", "Vegan Salad");
        orderHistoryManager.recordOrder("Ali", "Vegan Burger");

        kitchenTaskManager.createTask("Prepare Salad", "Ali");

        supplierNotificationService.requestManualRestock("Onion");
    }

    // Display demo output (for testing purposes)
    public void displayDemoOutput() {
        console.showList("Filtered Meals for Ali", customerProfileManager.getFilteredMeals("Ali"));

        console.separator();
        console.showMessage("Invoices:");
        invoiceManager.getAllInvoices().forEach(inv ->
                console.showInvoice(inv.getCustomer(), inv.getAmount(), inv.getStatus()));

        console.separator();
        console.showMap("Inventory Snapshot", inventoryManager.getInventorySnapshot());

        console.separator();
        console.showMessage("Kitchen Tasks:");
        kitchenTaskManager.getAllTasks().forEach(task ->
                console.showMessage("Task: " + task.getName() + " | Assigned to: " + task.getAssignedStaff() + " | Status: " + task.getStatus()));

        console.separator();
        console.showMap("Order Statistics", orderHistoryManager.getOrderStatistics());

        console.separator();
        boolean sent = supplierNotificationService.wasManualRequestSent("Onion");
        console.showMessage("Manual restock request for Onion sent? " + (sent ? "Yes" : "No"));
    }

    // Getter methods for services used in Main
    public CustomerProfileManager getCustomerProfileManager() { return customerProfileManager; }
    public InvoiceManager getInvoiceManager() { return invoiceManager; }
    public InventoryManager getInventoryManager() { return inventoryManager; }
    public KitchenTaskManager getKitchenTaskManager() { return kitchenTaskManager; }
    public OrderHistoryManager getOrderHistoryManager() { return orderHistoryManager; }
    public SupplierNotificationService getSupplierNotificationService() { return supplierNotificationService; }
    public AIRecipeRecommendation getAiRecipeRecommendation() { return aiRecipeRecommendation; }
    public MealRequestManager getMealRequestManager() { return mealRequestManager; }
}

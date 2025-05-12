package controllers;

import models.CustomMealRequest;
import services.*;
import views.*;

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
    private final InvoiceView invoiceView = new InvoiceView();
    private final MealOptionsView mealOptionsView = new MealOptionsView();
    private final TaskBoardView taskBoardView = new TaskBoardView();
    private final InventoryView inventoryView = new InventoryView();

    public void setupDemoData() {
        customerProfileManager.createCustomer("Ali");
        customerProfileManager.addDietaryPreference("Ali", "Vegan");
        customerProfileManager.addAllergy("Ali", "Peanuts");

        invoiceManager.createInvoice("Ali", 30.0);

        Map<String, Integer> stock = Map.of("Chicken", 2, "Rice", 10);
        stock.forEach((k, v) -> inventoryManager.addIngredient(k, v));

        kitchenTaskManager.createTask("Prepare Salad", "Ali");

        orderHistoryManager.addOrder("Ali", "Vegan Salad");
        orderHistoryManager.addOrder("Ali", "Vegan Burger");

        supplierNotificationService.requestManualRestock("Onion");
        inventoryAlertService.scanInventory();
    }

    public void displayDemoOutput() {
        console.separator();
        console.showList("Filtered Meals for Ali", customerProfileManager.getFilteredMeals("Ali"));

        console.separator();
        invoiceView.displayInvoices(invoiceManager.getAllInvoices());

        console.separator();
        inventoryView.displayInventory(new ArrayList<>(inventoryManager.getInventorySnapshot().values()));

        console.separator();
        taskBoardView.displayTasks(kitchenTaskManager.getAllTasks());

        console.separator();
        console.showMap("Order Statistics", orderHistoryManager.getOrderStatistics());

        console.separator();
        boolean sent = supplierNotificationService.getManualRequestIngredients().contains("Onion");
        console.showMessage("Manual restock request for Onion sent? " + (sent ? "Yes" : "No"));
    }

    public void displayAllInvoices() {
        invoiceView.displayInvoices(invoiceManager.getAllInvoices());
    }

    public void submitMealRequest(String[] ingredients) {
        CustomMealRequest request = new CustomMealRequest();


        request.setAvailableIngredients(inventoryManager.getInventorySnapshot().keySet());
        request.setIncompatibilityRules(mealRequestManager.getAllIncompatibilityRules());

        for (String ing : ingredients) {
            request.addIngredient(ing.trim());
        }

        request.validate();
        if (request.isAccepted()) {
            mealRequestManager.submitRequest(request);
            console.showMessage("Meal request accepted.");
        } else {
            console.showMessage("Meal request rejected. Reasons:");
            request.getValidationMessages().forEach(console::showMessage);
        }
    }

    public void displayAllKitchenTasks() {
        taskBoardView.displayTasks(kitchenTaskManager.getAllTasks());
    }

    public void displayInventoryStatus() {
        inventoryView.displayInventory(new ArrayList<>(inventoryManager.getInventorySnapshot().values()));
    }

    // Getters
    public CustomerProfileManager getCustomerProfileManager() { return customerProfileManager; }
    public InvoiceManager getInvoiceManager() { return invoiceManager; }
    public InventoryManager getInventoryManager() { return inventoryManager; }
    public KitchenTaskManager getKitchenTaskManager() { return kitchenTaskManager; }
    public OrderHistoryManager getOrderHistoryManager() { return orderHistoryManager; }
    public SupplierNotificationService getSupplierNotificationService() { return supplierNotificationService; }
    public AIRecipeRecommendation getAiRecipeRecommendation() { return aiRecipeRecommendation; }
    public MealRequestManager getMealRequestManager() { return mealRequestManager; }
}

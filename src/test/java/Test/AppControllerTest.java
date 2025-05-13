package Test;

import controllers.AppController;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class AppControllerTest {

    private final AppController controller = new AppController();

    // ----------- CUCUMBER SCENARIOS -----------

    @Given("the available inventory is:")
    public void loadInventory(io.cucumber.datatable.DataTable table) {
        Map<String, Integer> stock = new HashMap<>();
        for (List<String> row : table.asLists()) {
            stock.put(row.get(0), Integer.parseInt(row.get(1)));
        }
        stock.forEach((k, v) -> controller.getInventoryManager().addIngredient(k, v));
    }

    @And("the incompatibility rules are:")
    public void loadRules(io.cucumber.datatable.DataTable table) {
        for (List<String> row : table.asLists()) {
            controller.getMealRequestManager().addIncompatibilityRule(row.get(0), row.get(1));
        }
    }

    @When("the user submits a meal request with:")
    public void submitMeal(io.cucumber.datatable.DataTable table) {
        List<String> ingredients = table.asList();
        controller.submitMealRequest(ingredients.toArray(new String[0]));
    }

    @Then("the system should accept the request")
    public void requestAccepted() {
        assertEquals(1, controller.getMealRequestManager().getAllRequests().size());
    }

    @Given("the following tasks are loaded:")
    public void loadTasks(io.cucumber.datatable.DataTable table) {
        for (Map<String, String> row : table.asMaps()) {
            controller.getKitchenTaskManager().createTask(row.get("Task"), row.get("Staff"));
        }
    }

    @When("the user requests to view kitchen tasks")
    public void viewTasks() {
        controller.displayAllKitchenTasks();
    }

    @Then("the system should display the kitchen task board")
    public void theSystemShouldDisplayTheKitchenTaskBoard() {
        controller.displayAllKitchenTasks();
    }

    @Given("demo data is initialized and displayed")
    public void demoDataIsInitializedAndDisplayed() {
        controller.setupDemoData();
        controller.displayDemoOutput();
    }

    @Then("the system displays all invoices and inventory")
    public void displayInvoicesAndInventory() {
        controller.displayAllInvoices();
        controller.displayInventoryStatus();
    }

    @Then("all managers in AppController should be initialized")
    public void testAllGetters() {
        assertNotNull(controller.getCustomerProfileManager());
        assertNotNull(controller.getInvoiceManager());
        assertNotNull(controller.getOrderHistoryManager());
        assertNotNull(controller.getSupplierNotificationService());
        assertNotNull(controller.getAiRecipeRecommendation());
        assertNotNull(controller.getInventoryManager());
        assertNotNull(controller.getKitchenTaskManager());
        assertNotNull(controller.getMealRequestManager());
    }

    // ----------- DIRECT JUNIT TESTS (Fallback) -----------

    @Test
    public void testSubmitMealRequestDirectly() {
        String[] ingredients = {"Rice", "Onion"};
        controller.submitMealRequest(ingredients);
    }

    @Test
    public void testSetupAndDisplay() {
        controller.setupDemoData();
        controller.displayDemoOutput();
    }

    @Test
    public void testDisplayViewsManually() {
        controller.displayAllInvoices();
        controller.displayInventoryStatus();
        controller.displayAllKitchenTasks();
    }

    @Test
    public void testAllGettersDirectly() {
        assertNotNull(controller.getCustomerProfileManager());
        assertNotNull(controller.getInvoiceManager());
        assertNotNull(controller.getInventoryManager());
        assertNotNull(controller.getKitchenTaskManager());
        assertNotNull(controller.getMealRequestManager());
        assertNotNull(controller.getOrderHistoryManager());
        assertNotNull(controller.getSupplierNotificationService());
        assertNotNull(controller.getAiRecipeRecommendation());
    }
}

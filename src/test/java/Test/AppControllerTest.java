package Test;

import controllers.AppController;
import io.cucumber.java.en.*;
import java.util.*;

public class AppControllerTest {

    private final AppController controller = new AppController();

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
        assert controller.getMealRequestManager().getAllRequests().size() == 1;
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
}

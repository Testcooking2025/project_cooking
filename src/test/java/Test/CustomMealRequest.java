package Test;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class CustomMealRequest {

    private final Set<String> availableIngredients = new HashSet<>();
    private final List<String> selectedIngredients = new ArrayList<>();
    private final List<String> incompatibleWarnings = new ArrayList<>();
    private final Map<String, Set<String>> incompatibilityRules = new HashMap<>();
    private final List<String> validationMessages = new ArrayList<>();
    private boolean mealAccepted = false;

    // ========== Define available ingredients ==========
    @Given("the following ingredients are available:")
    public void theFollowingIngredientsAreAvailable(io.cucumber.datatable.DataTable table) {
        availableIngredients.clear();
        availableIngredients.addAll(table.asList());
    }

    // ========== Define incompatibility rules between ingredients ==========
    @Given("the system has incompatible ingredient rules:")
    public void theSystemHasIncompatibleIngredientRules(io.cucumber.datatable.DataTable table) {
        incompatibilityRules.clear();
        for (List<String> row : table.asLists()) {
            String ing1 = row.get(0);
            String ing2 = row.get(1);

            incompatibilityRules.computeIfAbsent(ing1, k -> new HashSet<>()).add(ing2);
            incompatibilityRules.computeIfAbsent(ing2, k -> new HashSet<>()).add(ing1);
        }
    }

    // ========== Customer selects ingredients for the custom meal ==========
    @When("the customer selects the following ingredients:")
    public void theCustomerSelectsTheFollowingIngredients(io.cucumber.datatable.DataTable table) {
        selectedIngredients.clear();
        validationMessages.clear();
        incompatibleWarnings.clear();
        mealAccepted = true;

        List<String> ingredients = table.asList();
        selectedIngredients.addAll(ingredients);

        // Check availability
        for (String ingredient : ingredients) {
            if (!availableIngredients.contains(ingredient)) {
                validationMessages.add(ingredient + " is not available");
                mealAccepted = false;
            }
        }

        // Check incompatibility
        for (int i = 0; i < ingredients.size(); i++) {
            for (int j = i + 1; j < ingredients.size(); j++) {
                String ing1 = ingredients.get(i);
                String ing2 = ingredients.get(j);
                if (incompatibilityRules.containsKey(ing1) &&
                        incompatibilityRules.get(ing1).contains(ing2)) {
                    incompatibleWarnings.add(ing1 + " and " + ing2 + " cannot be combined");
                    mealAccepted = false;
                }
            }
        }
    }

    // ========== Meal accepted ==========
    @Then("the system should accept the custom meal")
    public void theSystemShouldAcceptTheCustomMeal() {
        assertTrue(mealAccepted, "Meal should be accepted, but it was rejected.");
    }

    @Then("confirm the selected ingredients are valid")
    public void confirmSelectedIngredientsValid() {
        assertTrue(validationMessages.isEmpty());
        assertTrue(incompatibleWarnings.isEmpty());
    }

    // ========== Meal rejected ==========
    @Then("the system should reject the custom meal")
    public void theSystemShouldRejectTheCustomMeal() {
        assertFalse(mealAccepted, "Meal should be rejected, but it was accepted.");
    }

    @Then("display a message: {string}")
    public void displayErrorMessage(String expectedMessage) {
        boolean found = validationMessages.contains(expectedMessage) || incompatibleWarnings.contains(expectedMessage);
        assertTrue(found, "Expected message not found: " + expectedMessage);
    }
}

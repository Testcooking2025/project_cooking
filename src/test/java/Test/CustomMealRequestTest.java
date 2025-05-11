package Test;

import io.cucumber.java.en.*;
import models.CustomMealRequest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class CustomMealRequest {

    private CustomMealRequest request;

    @Given("the following ingredients are available:")
    public void theFollowingIngredientsAreAvailable(io.cucumber.datatable.DataTable table) {
        request = new CustomMealRequest();
        Set<String> ingredients = new HashSet<>(table.asList());
        request.setAvailableIngredients(ingredients);
    }

    @Given("the system has incompatible ingredient rules:")
    public void theSystemHasIncompatibleIngredientRules(io.cucumber.datatable.DataTable table) {
        for (List<String> row : table.asLists()) {
            request.addIncompatibilityRule(row.get(0), row.get(1));
        }
    }

    @When("the customer selects the following ingredients:")
    public void theCustomerSelectsTheFollowingIngredients(io.cucumber.datatable.DataTable table) {
        request.clearSelectedIngredients();
        for (String ingredient : table.asList()) {
            request.addIngredient(ingredient);
        }
        request.validate();
    }

    @Then("the system should accept the custom meal")
    public void theSystemShouldAcceptTheCustomMeal() {
        assertTrue(request.isAccepted(), "Meal should be accepted, but it was rejected.");
    }

    @Then("confirm the selected ingredients are valid")
    public void confirmSelectedIngredientsValid() {
        assertTrue(request.getValidationMessages().isEmpty());
    }

    @Then("the system should reject the custom meal")
    public void theSystemShouldRejectTheCustomMeal() {
        assertFalse(request.isAccepted(), "Meal should be rejected, but it was accepted.");
    }

    @Then("display a message: {string}")
    public void displayErrorMessage(String expectedMessage) {
        assertTrue(request.getValidationMessages().contains(expectedMessage), "Expected message not found: " + expectedMessage);
    }
}

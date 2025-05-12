package Test;

import io.cucumber.java.en.*;
import models.CustomMealRequest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class CustomMealRequestTest {

    private CustomMealRequest request;

    @Given("the following ingredients are available:")
    public void theFollowingIngredientsAreAvailable(io.cucumber.datatable.DataTable table) {
        if (request == null) request = new CustomMealRequest();
        Set<String> available = new HashSet<>(table.asList());
        request.setAvailableIngredients(available);
    }

    @Given("the system has incompatible ingredient rules:")
    public void theSystemHasIncompatibleIngredientRules(io.cucumber.datatable.DataTable table) {
        if (request == null) request = new CustomMealRequest();  // لا تهيئه مجددًا إذا كانت موجودة
        for (List<String> row : table.asLists()) {
            request.addIncompatibilityRule(row.get(0), row.get(1));
        }
    }

    @When("the customer selects the following ingredients:")
    public void theCustomerSelectsTheFollowingIngredients(io.cucumber.datatable.DataTable table) {
        request.clearSelectedIngredients();  // فقط يمسح المكونات والرسائل، لا القواعد
        for (String ingredient : table.asList()) {
            request.addIngredient(ingredient);
        }
        request.validate();
    }

    @Then("the system should accept the custom meal")
    public void theSystemShouldAcceptTheCustomMeal() {
        assertTrue(request.isAccepted(), "Expected meal to be accepted.");
    }

    @Then("the system should reject the custom meal")
    public void theSystemShouldRejectTheCustomMeal() {
        assertFalse(request.isAccepted(), "Expected meal to be rejected.");
    }

    @Then("display a message: {string}")
    public void displayAMessage(String expected) {
        assertTrue(request.getValidationMessages().contains(expected), "Expected message not found: " + expected);
    }

    @Then("confirm the selected ingredients are valid")
    public void confirmTheSelectedIngredientsAreValid() {
        assertTrue(request.getValidationMessages().isEmpty(), "Expected no validation errors.");
    }

    @Then("the system should return validation messages including:")
    public void theSystemShouldReturnValidationMessagesIncluding(io.cucumber.datatable.DataTable expectedTable) {
        List<String> expected = expectedTable.asList();
        List<String> actual = request.getValidationMessages();
        for (String msg : expected) {
            assertTrue(actual.contains(msg), "Missing message: " + msg);
        }
    }


    @Then("debug incompatibility map")
    public void debugIncompatibilityMap() {
        System.out.println(">>> DEBUG: incompatibility map = " + request.getIncompatibilityMap());
    }
}

package Test;

import io.cucumber.java.en.*;
import models.CustomMealRequest;
import services.MealRequestManager;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class MealRequestManagerTest {

    private final MealRequestManager mealRequestManager = new MealRequestManager();
    private CustomMealRequest request;

    @Given("the following ingredients are available in the system:")
    public void ingredientsAvailableInSystem(io.cucumber.datatable.DataTable table) {
        request = new CustomMealRequest();
        Set<String> available = new HashSet<>(table.asList());
        request.setAvailableIngredients(available);
        mealRequestManager.getAllIncompatibilityRules().clear(); // reset for coverage
    }

    @And("the system has incompatibility rules:")
    public void theSystemHasIncompatibilityRules(io.cucumber.datatable.DataTable table) {
        for (List<String> row : table.asLists()) {
            mealRequestManager.addIncompatibilityRule(row.get(0), row.get(1));
        }
        request.setIncompatibilityRules(mealRequestManager.getAllIncompatibilityRules());
    }

    @When("the customer submits a meal request with:")
    public void theCustomerSubmitsAMealRequestWith(io.cucumber.datatable.DataTable table) {
        request.clearSelectedIngredients();
        for (String ingredient : table.asList()) {
            request.addIngredient(ingredient);
        }
        request.validate();

        if (request.isAccepted()) {
            mealRequestManager.submitRequest(request);
        }
    }

    @Then("the submitted request should be accepted")
    public void theSubmittedRequestShouldBeAccepted() {
        assertNotNull(request);
        assertTrue(request.isAccepted(), "Expected the submitted request to be accepted.");
    }

    @Then("the submitted request should be rejected")
    public void theSubmittedRequestShouldBeRejected() {
        assertNotNull(request);
        assertFalse(request.isAccepted(), "Expected the submitted request to be rejected.");
    }

    @Then("the total stored requests should be {int}")
    public void theTotalStoredRequestsShouldBe(int expectedCount) {
        assertEquals(expectedCount, mealRequestManager.getAllRequests().size(),
                "Mismatch in stored request count.");
    }

    @Then("debug incompatibility map from manager")
    public void debugIncompatibilityMapFromManager() {
        System.out.println(">>> DEBUG RULES: " + mealRequestManager.getAllIncompatibilityRules());
    }
}

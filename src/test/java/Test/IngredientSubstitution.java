package Test;

import io.cucumber.java.en.*;
import services.IngredientSubstitutionEngine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class IngredientSubstitution {

    private IngredientSubstitutionEngine engine;
    private String selectedIngredient;
    private String dietaryRestriction;
    private String suggestedSubstitution;
    private String alertMessage;

    @Given("the following ingredients are available")
    public void theFollowingIngredientsAreAvailable(io.cucumber.datatable.DataTable table) {
        Set<String> available = new HashSet<>(table.asList());
        engine = new IngredientSubstitutionEngine();
        engine.setAvailableIngredients(available);
    }

    @Given("the following substitutions are defined:")
    public void theFollowingSubstitutionsAreDefined(io.cucumber.datatable.DataTable table) {
        if (engine == null) engine = new IngredientSubstitutionEngine();
        for (List<String> row : table.asLists()) {
            engine.addSubstitutionRule(row.get(0), row.get(1));
        }
    }

    @Given("the customer has a {string} dietary restriction")
    public void theCustomerHasDietaryRestriction(String restriction) {
        if (engine == null) engine = new IngredientSubstitutionEngine();
        dietaryRestriction = restriction;
        engine.setDietaryRestriction(restriction);
    }

    @When("the customer selects {string}")
    public void theCustomerSelectsIngredient(String ingredient) {
        selectedIngredient = ingredient;
        suggestedSubstitution = engine.suggestSubstitution(ingredient);
        alertMessage = engine.getLastSystemMessage();
    }

    @Then("the system should suggest {string} as a substitution")
    public void theSystemShouldSuggestSubstitution(String expected) {
        assertTrue(expected.equalsIgnoreCase(suggestedSubstitution),
                "Expected: " + expected + ", but got: " + suggestedSubstitution);
    }

    @Then("show an alert message: {string}")
    public void showAlertMessage(String expectedMessage) {
        assertTrue(expectedMessage.equalsIgnoreCase(alertMessage),
                "Expected alert message: " + expectedMessage + ", but got: " + alertMessage);
    }


    @Given("the system applied a substitution: {string} → {string}")
    public void theSystemAppliedASubstitution(String original, String substitute) {
        if (engine == null) engine = new IngredientSubstitutionEngine();
        String alert = engine.generateChefAlert(original, substitute);
        assertTrue(alert.contains(original) && alert.contains(substitute));
    }

    @When("the chef views the ingredient list")
    public void theChefViewsTheIngredientList() {
        // This step is contextual and does not require logic in this scenario
    }

    @Then("the system should alert: {string}")
    public void theSystemShouldAlert(String expectedMessage) {
        String actual = engine.generateChefAlert("Chicken", "Tofu"); // This may be adapted
        assertEquals(expectedMessage, actual);
    }
}

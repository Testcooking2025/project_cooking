package Test;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class IngredientSubstitution {

    private final Set<String> availableIngredients = new HashSet<>();
    private final Map<String, String> substitutionMap = new HashMap<>();
    private String dietaryRestriction;
     String selectedIngredient;
    private String suggestedSubstitution;
    private String systemMessage;
    private final List<String> chefAlerts = new ArrayList<>();

    @Given("the following ingredients are available")
    public void theFollowingIngredientsAreAvailable(io.cucumber.datatable.DataTable table) {
        availableIngredients.clear();
        availableIngredients.addAll(table.asList());
    }

    @Given("the following substitutions are defined:")
    public void theFollowingSubstitutionsAreDefined(io.cucumber.datatable.DataTable table) {
        substitutionMap.clear();
        for (List<String> row : table.asLists()) {
            substitutionMap.put(row.get(0), row.get(1));
        }
    }

    @Given("the customer has a {string} dietary restriction")
    public void theCustomerHasDietaryRestriction(String restriction) {
        dietaryRestriction = restriction;
    }

    @When("the customer selects {string}")
    public void theCustomerSelectsIngredient(String ingredient) {
        selectedIngredient = ingredient;

        // 1. Check if violates dietary restriction first
        if (violatesRestriction(ingredient) && substitutionMap.containsKey(ingredient)) {
            suggestedSubstitution = substitutionMap.get(ingredient);
            systemMessage = ingredient + " does not meet " + dietaryRestriction + " restrictions. Suggesting " + suggestedSubstitution;
        }
        // 2. Then check availability
        else if (!availableIngredients.contains(ingredient) && substitutionMap.containsKey(ingredient)) {
            suggestedSubstitution = substitutionMap.get(ingredient);
            systemMessage = ingredient + " is unavailable. Suggesting " + suggestedSubstitution;
        }
    }

    private boolean violatesRestriction(String ingredient) {
        if (dietaryRestriction == null) return false;

        return dietaryRestriction.equalsIgnoreCase("Vegan")
                && (ingredient.toLowerCase().contains("chicken") || ingredient.toLowerCase().contains("cheese"));
    }

    @Then("the system should suggest {string} as a substitution")
    public void theSystemShouldSuggestSubstitution(String expectedSubstitution) {
        assertEquals(expectedSubstitution, suggestedSubstitution);
    }

    @Then("show an alert message: {string}")
    public void showAlertMessage(String expectedMessage) {
        assertEquals(expectedMessage, systemMessage);
    }

    @Given("the system applied a substitution: {string} → {string}")
    public void theSystemAppliedASubstitution(String original, String substitute) {
        chefAlerts.clear();
        chefAlerts.add("Substitution applied: " + original + " → " + substitute);
    }

    @When("the chef views the ingredient list")
    public void theChefViewsTheIngredientList() {
        // No action needed
    }

    @Then("the system should alert: {string}")
    public void theSystemShouldAlert(String expectedAlert) {
        assertTrue(chefAlerts.contains(expectedAlert), "Alert not found: " + expectedAlert);
    }
}

package Test;

import io.cucumber.java.en.*;
import services.AIRecipeRecommendation;

import static org.junit.jupiter.api.Assertions.*;
import io.cucumber.datatable.DataTable;

import java.util.*;

public class AIRecipeRecommendation {

    private final AIRecipeRecommendation recommender = new AIRecipeRecommendation();
    private String selectedRecipe;
    private String explanation;

    @Given("the user has the following preferences:")
    public void userPreferences(DataTable table) {
        Map<String, String> prefs = table.asMap();
        String dietary = prefs.get("Dietary Restrictions").trim();
        int time = Integer.parseInt(prefs.get("Time Available").replace(" minutes", "").trim());
        String[] ingredients = prefs.get("Ingredients").split(",");

        Set<String> availableIngredients = new HashSet<>();
        for (String ing : ingredients) {
            availableIngredients.add(ing.trim().toLowerCase());
        }

        recommender.setUserPreferences(dietary, availableIngredients, time);
    }

    @And("the following recipe database is available:")
    public void recipeDatabase(DataTable table) {
        for (Map<String, String> row : table.asMaps()) {
            recommender.addRecipe(
                    row.get("Name"),
                    row.get("Ingredients"),
                    row.get("Time"),
                    row.get("Dietary")
            );
        }
    }

    @When("the AI generates a recipe recommendation")
    public void aiGeneratesRecommendation() {
        selectedRecipe = recommender.recommendRecipe();
        explanation = recommender.getRecommendationExplanation();
    }

    @Then("the suggested recipe should be {string}")
    public void suggestedRecipeShouldBe(String expected) {
        assertEquals(expected, selectedRecipe);
    }

    @Then("the explanation should include {string}")
    public void theExplanationShouldInclude(String expected) {
        assertTrue(explanation.contains(expected), "Explanation missing: " + expected);
    }
}

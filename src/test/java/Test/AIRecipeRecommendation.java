package Test;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class AIRecipeRecommendation {

    static class Recipe {
        String name;
        List<String> ingredients;
        int timeMinutes;
        String dietary;

        Recipe(String name, String ingredients, String time, String dietary) {
            this.name = name;
            this.ingredients = Arrays.stream(ingredients.split(","))
                    .map(String::trim)
                    .map(String::toLowerCase)
                    .toList();
            this.timeMinutes = Integer.parseInt(time.replace(" minutes", "").trim());
            this.dietary = dietary.trim();
        }
    }

    private String dietaryRestriction;
    private final Set<String> availableIngredients = new HashSet<>();
    private int maxTime = 0;
    private final List<Recipe> recipeDB = new ArrayList<>();

    private String selectedRecipe = null;
    private String explanation = null;

    @Given("the user has the following preferences:")
    public void userPreferences(io.cucumber.datatable.DataTable table) {
        Map<String, String> prefs = table.asMap();
        dietaryRestriction = prefs.get("Dietary Restrictions").trim();
        maxTime = Integer.parseInt(prefs.get("Time Available").replace(" minutes", "").trim());

        String[] ingredients = prefs.get("Ingredients").split(",");
        for (String ing : ingredients) {
            availableIngredients.add(ing.trim().toLowerCase());
        }
    }

    @And("the following recipe database is available:")
    public void recipeDatabase(io.cucumber.datatable.DataTable table) {
        recipeDB.clear();
        for (Map<String, String> row : table.asMaps()) {
            recipeDB.add(new Recipe(
                    row.get("Name"),
                    row.get("Ingredients"),
                    row.get("Time"),
                    row.get("Dietary")
            ));
        }
    }

    @When("the AI generates a recipe recommendation")
    public void aiGeneratesRecommendation() {
        for (Recipe r : recipeDB) {
            if (!r.dietary.equalsIgnoreCase(dietaryRestriction)) continue;
            if (r.timeMinutes > maxTime) continue;

            boolean allIngredientsAvailable = availableIngredients.containsAll(r.ingredients);

            if (allIngredientsAvailable) {
                selectedRecipe = r.name;
                explanation = String.format(
                        "%s uses all of the user's available ingredients and fits within the time limit.",
                        selectedRecipe
                );
                return;
            }
        }

        selectedRecipe = null;
        explanation = "No suitable recipe found.";
    }

    @Then("the suggested recipe should be {string}")
    public void suggestedRecipeShouldBe(String expected) {
        assertEquals(expected, selectedRecipe);
    }

    @Then("the explanation should include {string}")
    public void explanationShouldInclude(String expectedPhrase) {
        assertNotNull(explanation);
        assertTrue(explanation.contains(expectedPhrase), "Explanation missing expected phrase.");
    }
}

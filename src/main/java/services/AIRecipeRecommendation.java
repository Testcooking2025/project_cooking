package services;

import java.util.*;

/**
 * AI service to recommend recipes based on user dietary preferences,
 * available ingredients, and time constraints.
 */
public class AIRecipeRecommendation {

    /**
     * Represents a recipe with name, ingredients, preparation time, and dietary tag.
     */
    private static class Recipe {
        String name;
        List<String> ingredients;
        int timeMinutes;
        String dietary;

        Recipe(String name, String ingredientsStr, String timeStr, String dietary) {
            this.name = name;
            this.ingredients = Arrays.stream(ingredientsStr.split(","))
                    .map(String::trim)
                    .map(String::toLowerCase)
                    .toList();
            this.timeMinutes = Integer.parseInt(timeStr.replace(" minutes", "").trim());
            this.dietary = dietary.trim().toLowerCase();
        }
    }

    private final List<Recipe> recipeDB = new ArrayList<>();
    private Set<String> availableIngredients = new HashSet<>();
    private String dietaryRestriction = "";
    private int timeAvailable = 0;
    private String explanation = "";
    private String selectedRecipe = null;

    /**
     * Sets the user preferences for dietary restrictions, available ingredients, and time.
     *
     * @param dietary    Dietary restriction (e.g., "Vegan")
     * @param ingredients Set of available ingredients
     * @param time        Time available in minutes
     */
    public void setUserPreferences(String dietary, Set<String> ingredients, int time) {
        this.dietaryRestriction = dietary.toLowerCase();
        this.availableIngredients = ingredients;
        this.timeAvailable = time;
    }

    /**
     * Adds a recipe to the system's database.
     *
     * @param name        Recipe name
     * @param ingredients Comma-separated ingredient list
     * @param time        Time string (e.g., "25 minutes")
     * @param dietary     Dietary category (e.g., "Vegan")
     */
    public void addRecipe(String name, String ingredients, String time, String dietary) {
        recipeDB.add(new Recipe(name, ingredients, time, dietary));
    }

    /**
     * Recommends the best matching recipe based on user preferences.
     *
     * @return The name of the recommended recipe or null if no match is found.
     */
    public String recommendRecipe() {
        for (Recipe r : recipeDB) {
            if (!r.dietary.equalsIgnoreCase(dietaryRestriction)) continue;
            if (r.timeMinutes > timeAvailable) continue;

            if (availableIngredients.containsAll(r.ingredients)) {
                selectedRecipe = r.name;
                explanation = String.format(
                        "%s uses all of the user's available ingredients and fits within the time limit.",
                        selectedRecipe
                );
                return selectedRecipe;
            }
        }

        selectedRecipe = null;
        explanation = "No suitable recipe found.";
        return null;
    }

    /**
     * Returns an explanation for the selected recipe recommendation.
     *
     * @return Explanation message.
     */
    public String getRecommendationExplanation() {
        return explanation;
    }
}

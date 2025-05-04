package services;

import models.SubstitutionRecord;
import models.SubstitutionRule;

import java.util.*;

/**
 * Engine responsible for managing ingredient substitutions based on availability
 * and dietary restrictions.
 */
public class IngredientSubstitutionEngine {

    private final Set<String> availableIngredients = new HashSet<>();
    private final Map<String, String> substitutionMap = new HashMap<>();
    private final List<SubstitutionRecord> appliedSubstitutions = new ArrayList<>();
    private String dietaryRestriction;

    /**
     * Sets the list of currently available ingredients.
     *
     * @param ingredients List of available ingredient names.
     */
    public void setAvailableIngredients(List<String> ingredients) {
        availableIngredients.clear();
        ingredients.forEach(i -> availableIngredients.add(i.trim()));
    }

    /**
     * Sets the substitution rules (original → substitute).
     *
     * @param rules List of substitution rules.
     */
    public void setSubstitutionRules(List<SubstitutionRule> rules) {
        substitutionMap.clear();
        for (SubstitutionRule rule : rules) {
            substitutionMap.put(rule.getOriginal(), rule.getSubstitute());
        }
    }

    /**
     * Sets the dietary restriction to evaluate ingredients against (e.g., "Vegan").
     *
     * @param restriction The dietary restriction keyword.
     */
    public void setDietaryRestriction(String restriction) {
        this.dietaryRestriction = restriction;
    }

    /**
     * Checks if a selected ingredient violates dietary rules or is unavailable,
     * and suggests a substitution if a rule exists.
     *
     * @param selectedIngredient The ingredient selected by the customer.
     * @return A substitution record if substitution is applied; null otherwise.
     */
    public SubstitutionRecord checkIngredient(String selectedIngredient) {
        appliedSubstitutions.clear();

        boolean violates = violatesRestriction(selectedIngredient);
        boolean unavailable = !availableIngredients.contains(selectedIngredient);
        boolean hasSubstitute = substitutionMap.containsKey(selectedIngredient);

        if ((violates || unavailable) && hasSubstitute) {
            String substitute = substitutionMap.get(selectedIngredient);
            String reason = violates
                    ? String.format("%s does not meet %s restrictions. Suggesting %s", selectedIngredient, dietaryRestriction, substitute)
                    : String.format("%s is unavailable. Suggesting %s", selectedIngredient, substitute);

            SubstitutionRecord record = new SubstitutionRecord(reason, selectedIngredient, substitute);
            appliedSubstitutions.add(record);
            return record;
        }

        return null; // no substitution applied
    }

    /**
     * Returns a list of all substitutions that were applied during the last check.
     *
     * @return List of applied substitution records.
     */
    public List<SubstitutionRecord> getAppliedSubstitutions() {
        return appliedSubstitutions;
    }

    /**
     * Checks if an ingredient violates the active dietary restriction.
     *
     * @param ingredient The name of the ingredient.
     * @return True if it violates the restriction, false otherwise.
     */
    private boolean violatesRestriction(String ingredient) {
        if (dietaryRestriction == null) return false;

        String ing = ingredient.toLowerCase();
        return dietaryRestriction.equalsIgnoreCase("Vegan") &&
                (ing.contains("chicken") || ing.contains("cheese") || ing.contains("meat"));
    }
}

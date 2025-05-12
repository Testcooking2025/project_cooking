package services;

import models.SubstitutionRule;
import models.SubstitutionRecord;

import java.util.*;

/**
 * Suggests ingredient substitutions based on dietary restrictions and availability.
 */
public class IngredientSubstitutionEngine {

    private final Set<String> availableIngredients = new HashSet<>();
    private final List<SubstitutionRule> substitutionRules = new ArrayList<>();
    private final List<SubstitutionRecord> appliedSubstitutions = new ArrayList<>();
    private String dietaryRestriction;
    private String lastSystemMessage;

    public void setAvailableIngredients(Set<String> ingredients) {
        availableIngredients.clear();
        availableIngredients.addAll(ingredients);
    }

    public void addSubstitutionRule(String original, String substitute) {
        substitutionRules.add(new SubstitutionRule(original, substitute));
    }

    public void setDietaryRestriction(String restriction) {
        this.dietaryRestriction = restriction.toLowerCase();
    }

    public String suggestSubstitution(String ingredient) {
        appliedSubstitutions.clear();
        String normalized = ingredient.toLowerCase();

        if (violatesRestriction(normalized)) {
            SubstitutionRule rule = findRule(normalized);
            if (rule != null) {
                lastSystemMessage = ingredient + " does not meet " + dietaryRestriction + " restrictions. Suggesting " + rule.getSubstitute();
                appliedSubstitutions.add(new SubstitutionRecord(rule.getOriginal(), rule.getSubstitute()));
                return rule.getSubstitute();
            }
        }

        if (!availableIngredients.contains(normalized)) {
            SubstitutionRule rule = findRule(normalized);
            if (rule != null) {
                lastSystemMessage = ingredient + " is unavailable. Suggesting " + rule.getSubstitute();
                appliedSubstitutions.add(new SubstitutionRecord(rule.getOriginal(), rule.getSubstitute()));
                return rule.getSubstitute();
            }
        }

        lastSystemMessage = ingredient + " is acceptable.";
        return null;
    }

    private SubstitutionRule findRule(String ingredient) {
        for (SubstitutionRule rule : substitutionRules) {
            if (rule.getOriginal().equalsIgnoreCase(ingredient)) {
                return rule;
            }
        }
        return null;
    }

    public String getLastSystemMessage() {
        return lastSystemMessage;
    }

    public List<SubstitutionRecord> getAppliedSubstitutions() {
        return new ArrayList<>(appliedSubstitutions);
    }

    private boolean violatesRestriction(String ingredient) {
        if (dietaryRestriction == null) return false;
        return dietaryRestriction.equalsIgnoreCase("vegan") &&
                (ingredient.contains("chicken") || ingredient.contains("meat") ||
                        ingredient.contains("cheese") || ingredient.contains("fish"));
    }

    /**
     * Returns a formatted chef alert when a substitution occurs.
     *
     * @param original   The original ingredient replaced.
     * @param substitute The substitute used.
     * @return A human-readable substitution message.
     */
    public String generateChefAlert(String original, String substitute) {
        return "Substitution applied: " + original + " → " + substitute;
    }
}

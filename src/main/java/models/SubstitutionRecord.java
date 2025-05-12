package models;

import java.time.LocalDateTime;

/**
 * Represents a record of an ingredient substitution applied to a request.
 */
public class SubstitutionRecord {

    private final String ingredientReplaced;
    private final String ingredientUsed;
    private final LocalDateTime timestamp;

    public SubstitutionRecord(String ingredientReplaced, String ingredientUsed) {
        this.ingredientReplaced = ingredientReplaced;
        this.ingredientUsed = ingredientUsed;
        this.timestamp = LocalDateTime.now();
    }

    public String getIngredientReplaced() {
        return ingredientReplaced;
    }

    public String getIngredientUsed() {
        return ingredientUsed;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Substitution: " + ingredientReplaced + " → " + ingredientUsed + " at " + timestamp;
    }
}

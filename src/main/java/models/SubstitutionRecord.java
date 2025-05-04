package models;

/**
 * Represents a substitution that was applied to an ingredient,
 * including the reason and the original and substitute ingredients.
 */
public class SubstitutionRecord {

    private final String reason;
    private final String original;
    private final String substitute;

    /**
     * Constructs a substitution record with a reason and the involved ingredients.
     *
     * @param reason     The reason for the substitution (e.g., "Unavailable", "Violates restriction").
     * @param original   The original ingredient that was replaced.
     * @param substitute The ingredient used as a replacement.
     */
    public SubstitutionRecord(String reason, String original, String substitute) {
        this.reason = reason;
        this.original = original;
        this.substitute = substitute;
    }

    /**
     * Returns the reason why the substitution was made.
     *
     * @return The substitution reason.
     */
    public String getReason() {
        return reason;
    }

    /**
     * Returns the original ingredient that was substituted.
     *
     * @return The original ingredient.
     */
    public String getOriginal() {
        return original;
    }

    /**
     * Returns the substitute ingredient used.
     *
     * @return The substitute ingredient.
     */
    public String getSubstitute() {
        return substitute;
    }

    /**
     * Returns a formatted alert message describing the substitution.
     *
     * @return A string like: "Substitution applied: Cheese → Vegan Cheese".
     */
    public String getAlertMessage() {
        return String.format("Substitution applied: %s → %s", original, substitute);
    }
}

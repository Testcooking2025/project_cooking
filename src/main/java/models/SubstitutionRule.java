package models;

/**
 * Represents a predefined rule for substituting one ingredient with another.
 */
public class SubstitutionRule {

    private final String original;
    private final String substitute;

    /**
     * Constructs a substitution rule mapping an original ingredient to a substitute.
     *
     * @param original   The original ingredient that may need to be substituted.
     * @param substitute The substitute ingredient to use instead.
     */
    public SubstitutionRule(String original, String substitute) {
        this.original = original;
        this.substitute = substitute;
    }

    /**
     * Returns the original ingredient that should be substituted.
     *
     * @return The original ingredient.
     */
    public String getOriginal() {
        return original;
    }

    /**
     * Returns the substitute ingredient defined by the rule.
     *
     * @return The substitute ingredient.
     */
    public String getSubstitute() {
        return substitute;
    }
}

package models;

/**
 * Represents a rule for substituting one ingredient with another.
 */
public class SubstitutionRule {

    private final String original;
    private final String substitute;

    public SubstitutionRule(String original, String substitute) {
        this.original = original.toLowerCase().trim();
        this.substitute = substitute.toLowerCase().trim();
    }

    public String getOriginal() {
        return original;
    }

    public String getSubstitute() {
        return substitute;
    }
}

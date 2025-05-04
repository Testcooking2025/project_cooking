package models;

/**
 * Represents a single ingredient with a name.
 * Ingredient comparison is case-insensitive.
 */
public class Ingredient {

    private final String name;

    /**
     * Constructs an ingredient with the specified name.
     *
     * @param name The name of the ingredient (e.g., "Chicken", "Tomato").
     */
    public Ingredient(String name) {
        this.name = name.trim();
    }

    /**
     * Returns the name of the ingredient.
     *
     * @return The ingredient name.
     */
    public String getName() {
        return name;
    }

    /**
     * Checks if two ingredients are equal based on their names (case-insensitive).
     *
     * @param obj The object to compare to.
     * @return True if the names match (ignoring case), otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Ingredient other)) return false;
        return this.name.equalsIgnoreCase(other.name);
    }

    /**
     * Returns the hash code of the ingredient name (in lowercase).
     *
     * @return Hash code based on the ingredient name.
     */
    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }
}

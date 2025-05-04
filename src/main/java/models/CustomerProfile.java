package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer's profile containing dietary preferences and allergies.
 */
public class CustomerProfile {

    private final String name;
    private final List<String> dietaryPreferences;
    private final List<String> allergies;

    /**
     * Constructs a new customer profile with the specified name.
     *
     * @param name The customer's name.
     */
    public CustomerProfile(String name) {
        this.name = name;
        this.dietaryPreferences = new ArrayList<>();
        this.allergies = new ArrayList<>();
    }

    /**
     * Returns the customer's name.
     *
     * @return The name of the customer.
     */
    public String getName() {
        return name;
    }

    /**
     * Adds a dietary preference to the customer's profile.
     *
     * @param preference The dietary preference to add (e.g., Vegan, Gluten-Free).
     */
    public void addDietaryPreference(String preference) {
        dietaryPreferences.add(preference);
    }

    /**
     * Adds an allergy to the customer's profile.
     *
     * @param allergy The allergen to add (e.g., Peanuts, Dairy).
     */
    public void addAllergy(String allergy) {
        allergies.add(allergy);
    }

    /**
     * Returns the list of dietary preferences for the customer.
     *
     * @return List of dietary preferences.
     */
    public List<String> getDietaryPreferences() {
        return dietaryPreferences;
    }

    /**
     * Returns the list of allergies for the customer.
     *
     * @return List of allergies.
     */
    public List<String> getAllergies() {
        return allergies;
    }
}

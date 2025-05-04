package services;

import models.CustomerProfile;

import java.util.*;

/**
 * Service responsible for managing customer profiles,
 * including dietary preferences, allergies, and meal filtering.
 */
public class CustomerProfileManager {

    private final Map<String, CustomerProfile> profiles = new HashMap<>();

    private final List<String> mealOptions = Arrays.asList(
            "Vegan Salad",
            "Beef Burger",
            "Peanut Curry"
    );

    /**
     * Creates a new customer profile if one does not already exist.
     *
     * @param name The name of the customer.
     */
    public void createCustomer(String name) {
        profiles.putIfAbsent(name, new CustomerProfile(name));
    }

    /**
     * Adds a dietary preference to the specified customer's profile.
     *
     * @param name       The name of the customer.
     * @param preference The dietary preference (e.g., "Vegan").
     */
    public void addDietaryPreference(String name, String preference) {
        createCustomer(name);
        profiles.get(name).addDietaryPreference(preference);
    }

    /**
     * Adds an allergy to the specified customer's profile.
     *
     * @param name    The name of the customer.
     * @param allergy The allergy to be added (e.g., "Peanuts").
     */
    public void addAllergy(String name, String allergy) {
        createCustomer(name);
        profiles.get(name).addAllergy(allergy);
    }

    /**
     * Retrieves the profile associated with the given customer name.
     *
     * @param name The name of the customer.
     * @return The CustomerProfile object, or null if not found.
     */
    public CustomerProfile getProfile(String name) {
        return profiles.get(name);
    }

    /**
     * Filters available meals based on the customer's dietary preferences and allergies.
     *
     * @param name The name of the customer.
     * @return A list of meals that match preferences and avoid allergens.
     */
    public List<String> getFilteredMeals(String name) {
        CustomerProfile profile = profiles.get(name);
        if (profile == null) return new ArrayList<>();

        List<String> result = new ArrayList<>();

        for (String meal : mealOptions) {
            boolean matchesPreference = profile.getDietaryPreferences().stream()
                    .anyMatch(pref -> meal.toLowerCase().contains(pref.toLowerCase()));

            boolean containsAllergen = profile.getAllergies().stream()
                    .anyMatch(allergen -> meal.toLowerCase().contains(allergen.toLowerCase()));

            if (matchesPreference && !containsAllergen) {
                result.add(meal);
            }
        }
        return result;
    }

    /**
     * Returns the customer's dietary preference and allergy (if present) as a map.
     * Only the first preference and allergy are included.
     *
     * @param name The name of the customer.
     * @return A map containing "diet" and/or "allergy" keys.
     */
    public Map<String, String> getOrderPreferences(String name) {
        CustomerProfile profile = profiles.get(name);
        if (profile == null) return Collections.emptyMap();

        Map<String, String> result = new HashMap<>();
        if (!profile.getDietaryPreferences().isEmpty())
            result.put("diet", profile.getDietaryPreferences().getFirst());
        if (!profile.getAllergies().isEmpty())
            result.put("allergy", profile.getAllergies().getFirst());

        return result;
    }
}

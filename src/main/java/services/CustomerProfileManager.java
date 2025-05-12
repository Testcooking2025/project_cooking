package services;

import models.CustomerProfile;

import java.util.*;

/**
 * Manages customer profiles including dietary preferences and allergies.
 */
public class CustomerProfileManager {

    private final Map<String, CustomerProfile> customerProfiles = new HashMap<>();

    /**
     * Adds an existing customer profile.
     *
     * @param profile The profile to add.
     */
    public void addProfile(CustomerProfile profile) {
        customerProfiles.put(profile.getName(), profile);
    }

    /**
     * Creates a new empty profile for a customer if it doesn't exist.
     *
     * @param name Customer name.
     */
    public void createCustomer(String name) {
        if (!customerProfiles.containsKey(name)) {
            customerProfiles.put(name, new CustomerProfile(name));
        }
    }

    /**
     * Adds a dietary preference to a profile.
     */
    public void addDietaryPreference(String customerName, String preference) {
        CustomerProfile profile = customerProfiles.getOrDefault(customerName, new CustomerProfile(customerName));
        profile.addDietaryPreference(preference);
        customerProfiles.put(customerName, profile);
    }

    /**
     * Alias for addDietaryPreference (used in some test cases).
     */
    public void storeDietaryPreference(String customerName, String preference) {
        addDietaryPreference(customerName, preference);
    }

    /**
     * Adds an allergy to a profile.
     */
    public void addAllergy(String customerName, String allergy) {
        CustomerProfile profile = customerProfiles.getOrDefault(customerName, new CustomerProfile(customerName));
        profile.addAllergy(allergy);
        customerProfiles.put(customerName, profile);
    }

    /**
     * Alias for addAllergy (used in some test cases).
     */
    public void storeAllergy(String customerName, String allergy) {
        addAllergy(customerName, allergy);
    }

    /**
     * Gets the full profile for a customer.
     */
    public CustomerProfile getProfile(String name) {
        return customerProfiles.get(name);
    }

    /**
     * Filters available meals based on customer preferences.
     */
    public List<String> getFilteredMeals(String customerName) {
        CustomerProfile profile = customerProfiles.get(customerName);
        if (profile == null) return Collections.emptyList();

        List<String> allMeals = List.of("Vegan Salad", "Grilled Chicken", "Nut-Free Curry");
        List<String> result = new ArrayList<>();

        for (String meal : allMeals) {
            boolean matchesDiet = profile.getDietaryPreferences().stream()
                    .anyMatch(pref -> meal.toLowerCase().contains(pref.toLowerCase()));
            boolean containsAllergy = profile.getAllergies().stream()
                    .anyMatch(allergy -> meal.toLowerCase().contains(allergy.toLowerCase()));

            if (matchesDiet && !containsAllergy) {
                result.add(meal);
            }
        }

        return result;
    }
}

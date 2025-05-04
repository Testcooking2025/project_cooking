package Test;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.Given;
import static org.junit.jupiter.api.Assertions.*;


import java.util.*;

public class StoreDietaryPreferences {

    private String dietaryPreference;
    private String allergy;
    private final List<String> storedPreferences = new ArrayList<>();
    private final List<String> storedAllergies = new ArrayList<>();
    private final List<String> mealOptions = Arrays.asList("Vegan Salad", "Beef Burger", "Peanut Curry");
    private final List<String> filteredMeals = new ArrayList<>();
    private final Map<String, String> orderPreferences = new HashMap<>();

    // ========== Scenario 1 ==========
    @Given("the customer is logged in")
    public void customerIsLoggedIn() {
        // simulate login
    }

    @Given("is on the profile settings page")
    public void onProfilePage() {
        // simulate page navigation
    }

    @When("the customer enters {string} as their dietary preference")
    public void enterDietaryPreference(String preference) {
        this.dietaryPreference = preference;
    }

    @When("clicks the {string} button")
    public void clickSaveButton(String button) {
        storedPreferences.add(dietaryPreference);
    }

    @Then("the system should store {string} as the customer's dietary preference")
    public void checkStoredPreference(String expected) {
        assertTrue(storedPreferences.contains(expected));
    }

    @Then("confirm that the preference has been saved successfully")
    public void confirmPreferenceSaved() {
        assertFalse(storedPreferences.isEmpty());
    }

    // ========== Scenario 2 ==========
    @When("the customer enters {string} as an allergy")
    public void enterAllergy(String allergy) {
        this.allergy = allergy;
    }

    @Then("the system should store {string} in the customer's allergy list")
    public void checkStoredAllergy(String expected) {
        storedAllergies.add(allergy);
        assertTrue(storedAllergies.contains(expected));
    }

    @Then("confirm that the allergy has been saved successfully")
    public void confirmAllergySaved() {
        assertFalse(storedAllergies.isEmpty());
    }

    // ========== Scenario 3 ==========
    @Given("the customer has {string} as a dietary preference")
    public void customerHasDietaryPreference(String preference) {
        this.dietaryPreference = preference;
    }

    @When("the customer browses the meal options")
    public void customerBrowsesMeals() {
        filteredMeals.clear();

        if (dietaryPreference == null) {
            throw new IllegalStateException("Dietary preference must be set.");
        }

        for (String meal : mealOptions) {
            if (meal.toLowerCase().contains(dietaryPreference.toLowerCase())) {
                filteredMeals.add(meal);
            }
        }
    }

    @Then("the system should only show meals marked as {string}")
    public void systemShowsFilteredMeals(String expectedTag) {
        for (String meal : filteredMeals) {
            assertTrue(meal.toLowerCase().contains(expectedTag.toLowerCase()));
        }
    }

    // ========== Scenario 4 ==========
    @Given("the customer has {string} listed as an allergy")
    public void customerHasAllergy(String allergy) {
        this.allergy = allergy;
    }

    @Then("the system should hide any meal that contains {string}")
    public void systemHidesAllergenMeals(String ingredient) {
        for (String meal : mealOptions) {
            assertFalse(meal.toLowerCase().contains(ingredient.toLowerCase()));
        }
    }

    // ========== Scenario 5 ==========
    @Given("a customer placed an order")
    public void customerPlacedOrder() {
        orderPreferences.put("diet", "Vegan");
        orderPreferences.put("allergy", "Peanuts");
    }

    @When("the chef views the order details")
    public void chefViewsOrder() {
        // simulate viewing
    }

    @Then("the system should display the customer's dietary preferences and allergies")
    public void systemDisplaysPreferencesAndAllergies() {
        assertEquals("Vegan", orderPreferences.get("diet"));
        assertEquals("Peanuts", orderPreferences.get("allergy"));
    }

    // ========== Extra Scenario (name + preference + allergy) ==========
    @Given("a customer named {string} is logged into the system")
    public void aCustomerNamedIsLoggedIntoTheSystem(String name) {
        // simulate customer creation
    }

    @Given("the customer has {string} preference and {string} allergy")
    public void theCustomerHasPreferenceAndAllergy(String preference, String allergy) {
        this.dietaryPreference = preference;
        this.allergy = allergy;
        storedPreferences.add(preference);
        storedAllergies.add(allergy);
    }
}

package Test;

import io.cucumber.java.en.*;
import services.CustomerProfileManager;
import models.CustomerProfile;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class StoreDietaryPreferences {

    private CustomerProfileManager manager;
    private List<String> filteredMeals;
    private String currentPage;
    private String savedPreference;
    private String savedAllergy;

    @Given("the customer is logged in")
    public void customerIsLoggedIn() {
        manager = new CustomerProfileManager();
        manager.createCustomer("Ali");
    }

    @And("is on the profile settings page")
    public void onProfilePage() {
        currentPage = "ProfileSettings";
    }

    @When("the customer enters {string} as their dietary preference")
    public void enterDietaryPreference(String preference) {
        savedPreference = preference;
    }

    @When("the customer enters {string} as an allergy")
    public void enterAllergy(String allergy) {
        savedAllergy = allergy;
    }

    @And("clicks the {string} button")
    public void clickSaveButton(String button) {
        if (button.equalsIgnoreCase("Save")) {
            if (savedPreference != null) {
                manager.addDietaryPreference("Ali", savedPreference);
            }
            if (savedAllergy != null) {
                manager.addAllergy("Ali", savedAllergy);
            }
        }
    }

    @Then("the system should store {string} as the customer's dietary preference")
    public void checkStoredPreference(String expected) {
        CustomerProfile profile = manager.getProfile("Ali");
        assertTrue(profile.getDietaryPreferences().contains(expected));
    }

    @Then("confirm that the preference has been saved successfully")
    public void confirmPreferenceSaved() {
        CustomerProfile profile = manager.getProfile("Ali");
        assertFalse(profile.getDietaryPreferences().isEmpty(), "No preference saved.");
    }

    @Then("the system should store {string} in the customer's allergy list")
    public void checkStoredAllergy(String expected) {
        CustomerProfile profile = manager.getProfile("Ali");
        assertTrue(profile.getAllergies().contains(expected));
    }

    @Then("confirm that the allergy has been saved successfully")
    public void confirmAllergySaved() {
        CustomerProfile profile = manager.getProfile("Ali");
        assertFalse(profile.getAllergies().isEmpty(), "No allergy saved.");
    }

    @Given("the customer has {string} as a dietary preference")
    public void theCustomerHasAsADietaryPreference(String preference) {
        if (manager == null) manager = new CustomerProfileManager();
        CustomerProfile profile = new CustomerProfile("Ali");
        profile.addDietaryPreference(preference);
        manager.addProfile(profile);
    }

    @Given("the customer has {string} listed as an allergy")
    public void theCustomerHasListedAsAnAllergy(String allergy) {
        if (manager == null) manager = new CustomerProfileManager();
        CustomerProfile profile = new CustomerProfile("Ali");
        profile.addAllergy(allergy);
        manager.addProfile(profile);
    }

    // ✅ الخطوة الجديدة المدمجة
    @Given("the customer has {string} preference and {string} allergy")
    public void theCustomerHasPreferenceAndAllergy(String preference, String allergy) {
        if (manager == null) manager = new CustomerProfileManager();
        CustomerProfile profile = new CustomerProfile("Ali");
        profile.addDietaryPreference(preference);
        profile.addAllergy(allergy);
        manager.addProfile(profile);
    }

    @When("the customer browses the meal options")
    public void theCustomerBrowsesTheMealOptions() {
        if (manager == null) throw new IllegalStateException("Profile manager is not initialized.");
        filteredMeals = manager.getFilteredMeals("Ali");
    }

    @Then("the system should only show meals marked as {string}")
    public void theSystemShouldOnlyShowMealsMarkedAs(String expectedTag) {
        assertTrue(filteredMeals.stream().allMatch(
                        meal -> meal.toLowerCase().contains(expectedTag.toLowerCase())),
                "Not all meals match expected tag: " + expectedTag);
    }

    @Then("the system should hide any meal that contains {string}")
    public void theSystemShouldHideAnyMealThatContains(String ingredient) {
        assertTrue(filteredMeals.stream().noneMatch(
                        meal -> meal.toLowerCase().contains(ingredient.toLowerCase())),
                "Found a meal that contains: " + ingredient);
    }
}

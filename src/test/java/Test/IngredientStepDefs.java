package Test;

import io.cucumber.java.en.*;
import models.Ingredient;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientStepDefs {

    private Ingredient ingredient1;
    private Ingredient ingredient2;

    @Given("an ingredient named {string}")
    public void anIngredientNamed(String name) {
        ingredient1 = new Ingredient(name);
    }

    @Then("the ingredient name should be {string}")
    public void theIngredientNameShouldBe(String expectedName) {
        assertEquals(expectedName, ingredient1.getName());
    }

    @Given("two ingredients {string} and {string}")
    public void twoIngredients(String name1, String name2) {
        ingredient1 = new Ingredient(name1);
        ingredient2 = new Ingredient(name2);
    }

    @Then("they should be considered equal")
    public void theyShouldBeEqual() {
        assertEquals(ingredient1, ingredient2);
    }

    @Then("they should not be considered equal")
    public void theyShouldNotBeEqual() {
        assertNotEquals(ingredient1, ingredient2);
    }

    @Then("their hash codes should be equal")
    public void theirHashCodesShouldBeEqual() {
        assertEquals(ingredient1.hashCode(), ingredient2.hashCode());
    }
}

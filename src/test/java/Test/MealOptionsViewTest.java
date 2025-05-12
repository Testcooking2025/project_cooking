package Test;

import io.cucumber.java.en.*;
import views.MealOptionsView;

import java.util.*;

public class MealOptionsViewTest {

    private final MealOptionsView view = new MealOptionsView();

    @Then("the system displays the following filtered meals:")
    public void theSystemDisplaysFilteredMeals(io.cucumber.datatable.DataTable table) {
        List<String> meals = table.asList();
        view.displayFilteredMeals(meals);
    }
}

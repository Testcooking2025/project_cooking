package views;

import java.util.List;

public class MealOptionsView {

    public void displayFilteredMeals(List<String> meals) {
        if (meals.isEmpty()) {
            System.out.println("No meals match your dietary preferences and allergy restrictions.");
        } else {
            System.out.println("Filtered Meal Options:");
            for (String meal : meals) {
                System.out.println("- " + meal);
            }
        }
    }
}

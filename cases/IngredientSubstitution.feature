Feature: Suggest ingredient substitutions based on dietary restrictions

  Scenario: Customer selects an unavailable ingredient
    Given the following ingredients are available
      | Tofu     |
      | Tomato   |
    And the following substitutions are defined:
      | Chicken | Tofu |
    When the customer selects "Chicken"
    Then the system should suggest "Tofu" as a substitution
    And show an alert message: "Chicken is unavailable. Suggesting Tofu"

  Scenario: Customer selects an ingredient that violates dietary restriction
    Given the customer has a "Vegan" dietary restriction
    And the following substitutions are defined:
      | Cheese | Vegan Cheese |
    When the customer selects "Cheese"
    Then the system should suggest "Vegan Cheese" as a substitution
    And show an alert message: "Cheese does not meet Vegan restrictions. Suggesting Vegan Cheese"

  Scenario: Substitution alert sent to chef
    Given the system applied a substitution: "Chicken" → "Tofu"
    When the chef views the ingredient list
    Then the system should alert: "Substitution applied: Chicken → Tofu"

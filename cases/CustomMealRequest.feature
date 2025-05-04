Feature: Allow customers to create custom meal requests

  Scenario: Customer creates a custom meal with valid ingredients
    Given the following ingredients are available:
      | Chicken |
      | Rice    |
      | Carrots |
    When the customer selects the following ingredients:
      | Chicken |
      | Rice    |
    Then the system should accept the custom meal
    And confirm the selected ingredients are valid

  Scenario: Customer tries to add an unavailable ingredient
    Given the following ingredients are available:
      | Chicken |
      | Rice    |
    When the customer selects the following ingredients:
      | Chicken |
      | Avocado |
    Then the system should reject the custom meal
    And display a message: "Avocado is not available"

  Scenario: Customer creates a meal with conflicting ingredients
    Given the system has incompatible ingredient rules:
      | Fish | Cheese |
    And the following ingredients are available:
      | Fish   |
      | Cheese |
      | Tomato |
    When the customer selects the following ingredients:
      | Fish   |
      | Cheese |
    Then the system should reject the custom meal
    And display a message: "Fish and Cheese cannot be combined"

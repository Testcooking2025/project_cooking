Feature: Ingredient comparison and name handling

  Scenario: Get trimmed ingredient name
    Given an ingredient named "  Tomato "
    Then the ingredient name should be "Tomato"

  Scenario: Compare two ingredients with same name but different case
    Given two ingredients "Onion" and "onion"
    Then they should be considered equal

  Scenario: Compare two ingredients with different names
    Given two ingredients "Salt" and "Sugar"
    Then they should not be considered equal

  Scenario: Compare hash codes of ingredients with same name but different case
    Given two ingredients "Milk" and "milk"
    Then their hash codes should be equal

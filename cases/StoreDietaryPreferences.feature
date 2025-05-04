Feature: Store dietary preferences and allergies

  Scenario: Customer adds a dietary preference
    Given the customer is logged in
    And is on the profile settings page
    When the customer enters "Vegan" as their dietary preference
    And clicks the "Save" button
    Then the system should store "Vegan" as the customer's dietary preference
    And confirm that the preference has been saved successfully

  Scenario: Customer adds an allergy
    Given the customer is logged in
    And is on the profile settings page
    When the customer enters "Peanuts" as an allergy
    And clicks the "Save" button
    Then the system should store "Peanuts" in the customer's allergy list
    And confirm that the allergy has been saved successfully

  Scenario: System recommends meals based on dietary preferences
    Given the customer has "Vegan" as a dietary preference
    When the customer browses the meal options
    Then the system should only show meals marked as "Vegan"

  Scenario: System filters meals that contain allergens
    Given the customer has "Vegan" as a dietary preference
    And the customer has "Peanuts" listed as an allergy
    When the customer browses the meal options
    Then the system should hide any meal that contains "Peanuts"

  Scenario: Chef views dietary preferences for an order
    Given a customer placed an order
    When the chef views the order details
    Then the system should display the customer's dietary preferences and allergies

  Scenario: Full scenario using combined preference and allergy
    Given the customer has "Vegan" preference and "Peanuts" allergy
    When the customer browses the meal options
    Then the system should only show meals marked as "Vegan"
    And the system should hide any meal that contains "Peanuts"

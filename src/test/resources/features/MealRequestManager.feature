Feature: Submit Custom Meal Requests

  Scenario: Submit a valid custom meal request
    Given the following ingredients are available in the system:
      | Chicken |
      | Rice    |
      | Tomato  |
    And the system has incompatibility rules:
      | Chicken | Tofu |
    When the customer submits a meal request with:
      | Chicken |
      | Rice    |
    Then the submitted request should be accepted
    And the total stored requests should be 1

  Scenario: Submit an invalid meal request due to incompatibility
    Given the following ingredients are available in the system:
      | Chicken |
      | Tofu    |
      | Rice    |
    And the system has incompatibility rules:
      | Chicken | Tofu |
    When the customer submits a meal request with:
      | Chicken |
      | Tofu    |
    Then the submitted request should be rejected
    And the total stored requests should be 0

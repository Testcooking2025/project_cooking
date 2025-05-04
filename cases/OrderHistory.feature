Feature: Track past orders and personalized meal plans

  Scenario: Customer views past orders
    Given a customer named "Ali" has placed the following orders:
      | Spaghetti Bologna |
      | Vegan Salad         |
    When the customer views their past orders
    Then the system should show the following orders:
      | Spaghetti Bologna |
      | Vegan Salad         |

  Scenario: Chef suggests meal plan based on order history
    Given a customer named "Sara" has placed the following orders:
      | Vegan Salad         |
      | Vegan Burger        |
    When the chef requests a personalized meal plan
    Then the system should suggest meals similar to:
      | Vegan Salad         |
      | Vegan Burger        |

  Scenario: System administrator retrieves order history for analysis
    Given the system stores the following customer orders:
      | Customer | Order              |
      | Ali      | Spaghetti Bologna|
      | Sara     | Vegan Salad        |
      | Sara     | Vegan Burger       |
    When the admin analyzes order trends
    Then the system should provide statistics including:
      | Customer | Number of Orders |
      | Ali      | 1                |
      | Sara     | 2                |

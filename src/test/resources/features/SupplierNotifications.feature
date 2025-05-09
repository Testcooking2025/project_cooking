Feature: Handle supplier restocking and notifications

  Scenario: System auto-notifies supplier when ingredient is below threshold
    Given the inventory stock with threshold is:
      | Ingredient | Quantity | Threshold |
      | Chicken    | 2        | 5         |
      | Rice       | 10       | 5         |
    When the system checks inventory levels
    Then the system should notify supplier to restock:
      | Chicken |

  Scenario: System does not notify when inventory is sufficient
    Given the inventory stock with threshold is:
      | Ingredient | Quantity | Threshold |
      | Tomato     | 8        | 5         |
    When the system checks inventory levels
    Then no supplier notification should be sent

  Scenario: Chef manually requests restocking
    When the chef requests restocking for "Onion"
    Then the system should send manual restock request for "Onion"

Feature: Alert chef when inventory is low

  Scenario: System alerts chef for low inventory items
    Given the inventory with minimum levels is:
      | Ingredient | Quantity | Minimum |
      | Chicken    | 3        | 5       |
      | Rice       | 10       | 5       |
    When the system scans inventory
    Then the chef should be alerted about:
      | Chicken |

  Scenario: System does not alert if all items are above minimum
    Given the inventory with minimum levels is:
      | Ingredient | Quantity | Minimum |
      | Tomato     | 8        | 5       |
      | Onion      | 6        | 5       |
    When the system scans inventory
    Then the chef should receive no alerts

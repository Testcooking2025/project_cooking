Feature: Manage kitchen inventory levels

  Scenario: Chef checks ingredient availability before cooking
    Given the inventory contains:
      | Ingredient | Quantity |
      | Chicken    | 5        |
      | Rice       | 10       |
    When the chef checks if the kitchen has enough of:
      | Ingredient | Quantity |
      | Chicken    | 3        |
      | Rice       | 2        |
    Then the system should confirm ingredients are available

  Scenario: Chef tries to cook with insufficient stock
    Given the inventory contains:
      | Ingredient | Quantity |
      | Chicken    | 2        |
    When the chef checks if the kitchen has enough of:
      | Ingredient | Quantity |
      | Chicken    | 4        |
    Then the system should display inventory alert: "Not enough Chicken in inventory"

  Scenario: System updates inventory after cooking
    Given the inventory contains:
      | Ingredient | Quantity |
      | Tomato     | 6        |
      | Onion      | 3        |
    When the chef prepares a meal using:
      | Ingredient | Quantity |
      | Tomato     | 2        |
      | Onion      | 1        |
    Then the inventory should be updated to:
      | Ingredient | Quantity |
      | Tomato     | 4        |
      | Onion      | 2        |

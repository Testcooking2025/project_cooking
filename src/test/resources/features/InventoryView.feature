Feature: Inventory view display

  Scenario: Show current inventory
    Given the following inventory items exist:
      | Name    | Quantity |
      | Rice    | 10       |
      | Chicken | 3        |
    Then the system displays the inventory list

Feature: ConsoleView display

  Scenario: Display message to user
    Then the system shows the message "Operation completed."

  Scenario: Display list of items
    Then the system shows the following list titled "Top Meals":
      | Vegan Salad |
      | Chicken Wrap |

  Scenario: Display map of items
    Then the system shows the following map titled "Stock Levels":
      | Rice   | 10 |
      | Chicken| 2  |

  Scenario: Print a separator
    Then the system prints a separator

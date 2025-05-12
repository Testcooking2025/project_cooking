Feature: Kitchen task board

  Scenario: Display kitchen task board
    Given the following kitchen tasks exist:
      | Task          | Staff | Status      |
      | Cook Rice     | Ali   | Pending     |
      | Grill Chicken | Sara  | Completed   |
    Then the system displays the kitchen task board

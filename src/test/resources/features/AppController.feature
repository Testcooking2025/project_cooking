Feature: System integration via AppController

  Scenario: Customer submits a valid meal request
    Given the available inventory is:
      | Chicken | 5 |
      | Rice    | 10 |
    And the incompatibility rules are:
      | Fish | Cheese |
    When the user submits a meal request with:
      | Chicken |
      | Rice    |
    Then the system should accept the request

  Scenario: System shows all kitchen tasks
    Given the following tasks are loaded:
      | Task        | Staff |
      | Prep Salad  | Ali   |
    When the user requests to view kitchen tasks
    Then the system should display the kitchen task board

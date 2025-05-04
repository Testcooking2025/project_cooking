Feature: Enable scheduling and management of kitchen tasks

  Scenario: Chef creates and assigns a new kitchen task
    Given the chef is logged into the system
    When the chef creates a task named "Prepare Salad" assigned to "Ali"
    Then the system should list the task "Prepare Salad" assigned to "Ali" with status "Pending"

  Scenario: Staff starts a kitchen task
    Given a task "Prepare Salad" is assigned to "Ali" with status "Pending"
    When "Ali" starts the task
    Then the task "Prepare Salad" should have status "In Progress"

  Scenario: Staff completes a kitchen task
    Given a task "Prepare Salad" is assigned to "Ali" with status "In Progress"
    When "Ali" marks the task as complete
    Then the task "Prepare Salad" should have status "Completed"

  Scenario: Chef views all tasks with their current status
    Given the following tasks exist:
      | Task            | Staff | Status      |
      | Prepare Salad   | Ali   | Completed   |
      | Cook Chicken    | Sara  | In Progress |
      | Clean Kitchen   | John  | Pending     |
    When the chef views the task list
    Then the system should display the tasks with their assigned staff and statuses

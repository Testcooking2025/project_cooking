Feature: Alert admin when unpaid invoices accumulate

  Scenario: System alerts admin when unpaid invoices exceed limit
    Given the current invoice list is:
      | Customer | Amount | Status  |
      | Ali      | 25.0   | Unpaid  |
      | Sara     | 40.0   | Unpaid  |
      | John     | 15.0   | Unpaid  |
    And the system alert threshold is 2
    When the system checks unpaid invoices
    Then the admin should be alerted: "3 unpaid invoices found. Alerting admin!"

  Scenario: System does not alert admin if invoices are within limit
    Given the current invoice list is:
      | Customer | Amount | Status  |
      | Ali      | 25.0   | Paid    |
      | Sara     | 40.0   | Unpaid  |
    And the system alert threshold is 2
    When the system checks unpaid invoices
    Then no admin alert should be sent

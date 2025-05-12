Feature: Display invoices

  Scenario: Show invoices
    Given the following invoices exist:
      | Customer | Amount | Status  |
      | Ali      | 25.0   | Paid    |
      | Sara     | 30.0   | Unpaid  |
    Then the system displays all invoices

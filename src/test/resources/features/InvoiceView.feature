Feature: Display invoices

  Scenario: Show invoices
    Given the following invoices are loaded for display:
      | Customer | Amount | Status  |
      | Ali      | 25.0   | Paid    |
      | Sara     | 30.0   | Unpaid  |
    Then the system displays all invoices

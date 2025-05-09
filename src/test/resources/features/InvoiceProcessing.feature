Feature: Generate invoices and track payments

  Scenario: System generates invoice after customer places an order
    Given a customer named "Ali" places an order for "Grilled Chicken" costing 25.0
    Then the system should generate an invoice with amount 25.0 and status "Unpaid"

  Scenario: Customer pays the invoice
    Given an invoice for "Ali" with amount 25.0 and status "Unpaid"
    When "Ali" pays the invoice
    Then the invoice for "Ali" should have status "Paid"

  Scenario: System lists unpaid invoices
    Given the following invoices exist:
      | Customer | Amount | Status  |
      | Ali      | 25.0   | Paid    |
      | Sara     | 40.0   | Unpaid  |
      | John     | 15.0   | Unpaid  |
    When the system lists unpaid invoices
    Then it should show the following customers:
      | Sara  |
      | John  |

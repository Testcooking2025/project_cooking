Feature: User Account Management

  Scenario: Successful user registration and login
    Given a user signs up with username "Ali", email "ali@example.com", password "1234", and role "customer"
    Then the sign-up should be successful

    When the user signs in with email "ali@example.com" and password "1234"
    Then the sign-in should be successful
    And the system should recognize the user as logged in
    And the current user's email should be "ali@example.com"

    When the user signs out
    Then no user should be logged in

  Scenario: Duplicate sign-up should fail
    Given a user signs up with username "Ali", email "ali@example.com", password "1234", and role "customer"
    Then the sign-up should be successful

    Given a user signs up with username "Ali2", email "ali@example.com", password "abcd", and role "admin"
    Then the sign-up should be failed

  Scenario: Invalid login attempt
    Given a user signs up with username "Sara", email "sara@example.com", password "pass", and role "customer"
    Then the sign-up should be successful

    When the user signs in with email "sara@example.com" and password "wrong"
    Then the sign-in should be failed

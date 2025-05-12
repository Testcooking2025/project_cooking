Feature: Email Notification Configuration

  Scenario: Proper configuration of sender email
    Given the system initializes the notification service
    When the system sets email "noreply@example.com" and password "secret"
    Then the service should be marked as configured

  Scenario: No configuration provided
    Given the system initializes the notification service
    Then the service should not be configured

  Scenario: Send email with invalid credentials should log an error
    Given the system initializes the notification service
    And the system sets email "fake@gmail.com" and password "wrongpass"
    When an email is sent to "target@example.com" with subject "Hello" and body "Test Message"
    Then an error message should be logged

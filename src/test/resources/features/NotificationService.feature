Feature: Email Notification Configuration

  Scenario: Proper configuration of sender email
    Given the system initializes the notification service
    When the system sets email "noreply@example.com" and password "secret"
    Then the service should be marked as configured

  Scenario: No configuration provided
    Given the system initializes the notification service
    Then the service should not be configured

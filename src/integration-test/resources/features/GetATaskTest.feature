Feature:
  Scenario: Given a Task is saved in the database and the id matches this task is returned.
    Given I have the following headers
      | transactionId | 1 |
      | taskId        | 2 |
    When I make a request to get a task
    Then I should get a status code 200
    And I should have a response body
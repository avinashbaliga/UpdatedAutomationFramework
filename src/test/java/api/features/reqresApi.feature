Feature: Reqres api

  Scenario: Reqres users
    Given I hit reqres get users API
          |/api/users|
    Then I should get user list